package com.montecarlodata.cryptopricechecker.loader;

import com.montecarlodata.cryptopricechecker.config.LoaderConfig;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@ApplicationScoped
public class CurrencyPairLoader {
    private static final Logger LOGGER = Logger.getLogger(CurrencyPairLoader.class);

    private final LoaderConfig config;

    public CurrencyPairLoader(LoaderConfig config) {
        this.config = config;
    }

    public List<String> load() throws IOException {
        LOGGER.infov("Loading currency pairs from {0}", config.filename());
        var currencyPairs = Files.readAllLines(Path.of(config.filename()));
        LOGGER.infov("Loaded {0} currency pairs", currencyPairs.size());
        return currencyPairs;
    }
}
