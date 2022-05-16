package com.montecarlodata.cryptopricechecker.services;

import com.montecarlodata.cryptopricechecker.loader.CurrencyPairLoader;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;

@ApplicationScoped
public class CurrencyPriceWatcher {
    private static final Logger LOGGER = Logger.getLogger(CurrencyPriceWatcher.class);
    private final CurrencyPairLoader loader;

    public CurrencyPriceWatcher(CurrencyPairLoader loader) {
        this.loader = loader;
    }

    public void scheduleWatchers() throws IOException {
        LOGGER.info("Scheduling watchers");
        var currencyPairs = loader.load();
        LOGGER.debugv("pairs: {0}", currencyPairs);
    }
}
