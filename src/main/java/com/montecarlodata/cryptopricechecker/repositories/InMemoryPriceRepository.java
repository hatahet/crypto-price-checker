package com.montecarlodata.cryptopricechecker.repositories;

import com.montecarlodata.cryptopricechecker.models.SeriesAndRank;
import com.montecarlodata.cryptopricechecker.models.SeriesDatapoint;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.IntStream;

@ApplicationScoped
public class InMemoryPriceRepository implements PriceRepository {
    private static final Logger LOGGER = Logger.getLogger(InMemoryPriceRepository.class);

    /**
     * This class is used to maintain series data points for the last 24 hours (at a once per minute query interval).
     * It also maintains a running standard deviation for its window size.
     */
    private static class SeriesForLastDay {
        private final Deque<SeriesDatapoint> queue = new ArrayDeque<>();
        private double sum;
        private double sum2;
        private double stdDev;

        private void add(ZonedDateTime time, double price) {
            queue.addLast(new SeriesDatapoint(time, price));
            var dropped = capToLast24Hours(queue);
            if (dropped != null) {
                sum -= dropped.price();
                sum2 -= dropped.price() * dropped.price();
            }
            sum += price;
            sum2 += price * price;

            final var N = queue.size(); // N is guaranteed to be > 0 by this point.
            stdDev = Math.sqrt(sum2 - (sum * sum) / N) / N;
        }

        private double getStdDev() {
            return stdDev;
        }

        private static SeriesDatapoint capToLast24Hours(Deque<SeriesDatapoint> queue) {
            final int MINUTES_PER_DAY_ENTRIES = 24 * 60; // FIXME: this will do for now; however, it will not work if the sampling frequency is changed to something other than once per minute.
            if (queue.size() <= MINUTES_PER_DAY_ENTRIES) {
                return null;
            }
            return queue.removeFirst();
        }
    }

    private final ConcurrentMap<String, SeriesForLastDay> prices = new ConcurrentHashMap<>();

    /**
     * Add the given price and time stamp to the time series for the provided currencyPair.
     */
    @Override
    public void addPrice(String currencyPair, ZonedDateTime time, double price) {
        LOGGER.debugv("Saving price for {0}: {1}", currencyPair, price);
        prices.computeIfAbsent(currencyPair, v -> new SeriesForLastDay()).add(time, price);
    }

    /**
     * Retrieve the time and price data points for the last 24 hours sorted in ascending order. Also retrieve the
     * currencyPair rank.
     *
     * @param currencyPair the desired currency pair (e.g. btcusd, ethusd, etc.)
     * @return a time series of up to 24 * 60 {@code TimeAndPrice} entries comprising the latest prices for the last 24 hours
     * sorted in ascending order. If the provided currency pair does not exist, return a Optional.empty().
     * <p>
     * The rank for the currencyPair is also returned.
     */
    @Override
    public Optional<SeriesAndRank> getPricesForLast24Hours(String currencyPair) {
        var series = prices.get(currencyPair);
        if (series == null) {
            return Optional.empty();
        }
        var sortedEntries = prices.entrySet().stream().sorted(
                Comparator.comparingDouble(e -> e.getValue().getStdDev())
        ).toList();
        var rank = IntStream.range(0, sortedEntries.size())
                .filter(i -> sortedEntries.get(i).getKey().equals(currencyPair))
                .map(i -> i + 1) // Minimum rank should start at 1.
                .findFirst()
                .orElse(-1);
        return Optional.of(new SeriesAndRank(series.queue.stream().toList(), rank, prices.size()));
    }
}
