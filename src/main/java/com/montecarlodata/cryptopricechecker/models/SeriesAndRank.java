package com.montecarlodata.cryptopricechecker.models;

import java.util.List;

public record SeriesAndRank(List<SeriesDatapoint> series, int rank, int totalCurrencies) {}
