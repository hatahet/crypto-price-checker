package com.montecarlodata.cryptopricechecker.controllers;

import com.montecarlodata.cryptopricechecker.gateways.CryptoWatchGateway;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/prices")
public class PricesController {
    private final CryptoWatchGateway cryptoWatchGateway;

    public PricesController(@RestClient CryptoWatchGateway cryptoWatchGateway) {
        this.cryptoWatchGateway = cryptoWatchGateway;
    }

    @GET
    public CryptoWatchGateway.Response getPrice() {
        return cryptoWatchGateway.getPrice("btcusd");
    }
}
