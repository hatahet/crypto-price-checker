package com.montecarlodata.cryptopricechecker.models;

import java.time.ZonedDateTime;

public record SeriesDatapoint(ZonedDateTime time, double price) {}
