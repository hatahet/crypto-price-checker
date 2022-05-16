package com.montecarlodata.cryptopricechecker.services;

import com.montecarlodata.cryptopricechecker.loader.CurrencyPairLoader;
import com.montecarlodata.cryptopricechecker.models.TimeAndPrice;
import com.montecarlodata.cryptopricechecker.repositories.PriceRepository;
import com.montecarlodata.cryptopricechecker.watcher.PriceWatcher;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import java.io.IOException;
import java.util.List;

@ApplicationScoped
public class SchedulerService {
    private static final Logger LOGGER = Logger.getLogger(SchedulerService.class);
    private final CurrencyPairLoader loader;
    private final PriceWatcher watcher;
    private final PriceRepository priceRepository;

    public SchedulerService(CurrencyPairLoader loader, PriceWatcher watcher, PriceRepository priceRepository) {
        this.loader = loader;
        this.watcher = watcher;
        this.priceRepository = priceRepository;
    }

    public void scheduleWatchers() throws IOException {
        LOGGER.info("Scheduling watchers");
        var currencyPairs = loader.load();
        LOGGER.infov("pairs: {0}", currencyPairs);

        currencyPairs.forEach(watcher::watch);
    }

    public List<TimeAndPrice> getPricesForLast24Hours(String currencyPair) {
        return priceRepository.getPricesForLast24Hours(currencyPair);
    }
}
