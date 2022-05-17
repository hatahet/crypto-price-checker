package com.montecarlodata.cryptopricechecker.repositories;

import com.montecarlodata.cryptopricechecker.models.SeriesAndRank;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * This class would interface with a time series DB for storing and retrieving prices and ranks for existing currencies.
 */
public class TimeSeriesDbPriceRepository implements PriceRepository {
    @Override
    public Optional<SeriesAndRank> getPricesForLast24Hours(String currencyPair) {
        throw new RuntimeException("unimplemented");
    }

    @Override
    public void addPrice(String currencyPair, ZonedDateTime time, double price) {
        throw new RuntimeException("unimplemented");
    }
}
