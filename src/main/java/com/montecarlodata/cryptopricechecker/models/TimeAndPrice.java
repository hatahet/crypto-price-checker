package com.montecarlodata.cryptopricechecker.models;

import java.time.ZonedDateTime;

public record TimeAndPrice(ZonedDateTime time, double price) {}
