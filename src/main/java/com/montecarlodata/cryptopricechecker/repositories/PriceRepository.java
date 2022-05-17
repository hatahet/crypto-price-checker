package com.montecarlodata.cryptopricechecker.repositories;

import com.montecarlodata.cryptopricechecker.models.SeriesAndRank;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface PriceRepository {
    Optional<SeriesAndRank> getPricesForLast24Hours(String currencyPair);

    void addPrice(String currencyPair, ZonedDateTime time, double price);
}
