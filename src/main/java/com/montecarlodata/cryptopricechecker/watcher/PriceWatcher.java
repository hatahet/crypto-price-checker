package com.montecarlodata.cryptopricechecker.watcher;

import com.montecarlodata.cryptopricechecker.gateways.CryptoWatchGateway;
import com.montecarlodata.cryptopricechecker.repositories.PriceRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class PriceWatcher {
    private static final Logger LOGGER = Logger.getLogger(PriceWatcher.class);

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(0);

    private final CryptoWatchGateway cryptoWatchGateway;
    private final PriceRepository priceRepository;

    public PriceWatcher(@RestClient CryptoWatchGateway cryptoWatchGateway, PriceRepository priceRepository) {
        this.cryptoWatchGateway = cryptoWatchGateway;
        this.priceRepository = priceRepository;
    }

    public void watch(String currencyPair) {
        LOGGER.infov("Watching {0}", currencyPair);
        executor.scheduleAtFixedRate(() -> {
            try {
                fetchAndSavePrice(currencyPair);
            } catch (Throwable e) {
                LOGGER.error("Error", e);
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private void fetchAndSavePrice(String currencyPair) {
        LOGGER.debugv("Fetching price for {0}", currencyPair);
        var response = cryptoWatchGateway.getPrice(currencyPair);
        LOGGER.debugv("Received price for {0}: {1}", currencyPair, response.result().price());
        priceRepository.addPrice(currencyPair, ZonedDateTime.now(), response.result().price());
    }

    public void shutdownNow() {
        executor.shutdownNow();
    }
}
