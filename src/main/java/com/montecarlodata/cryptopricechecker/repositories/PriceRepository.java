package com.montecarlodata.cryptopricechecker.repositories;

import com.montecarlodata.cryptopricechecker.models.TimeAndPrice;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
public class PriceRepository {
    private static final Logger LOGGER = Logger.getLogger(PriceRepository.class);

    private final ConcurrentMap<String, List<TimeAndPrice>> prices = new ConcurrentHashMap<>();

    public void addPrice(String currencyPair, ZonedDateTime time, double price) {
        LOGGER.infov("Saving price for {0}: {1}", currencyPair, price);
        prices.computeIfAbsent(currencyPair, v -> new ArrayList<>()).add(new TimeAndPrice(time, price));
    }

    public List<TimeAndPrice> getPricesForLast24Hours(String currencyPair) {
        var pricesList = prices.getOrDefault(currencyPair, List.of());
        final int MINUTES_PER_DAY = 24 * 60;
        if (pricesList.size() <= MINUTES_PER_DAY) {
            return pricesList;
        }
        return pricesList.subList(pricesList.size() - MINUTES_PER_DAY, pricesList.size());
    }
}
