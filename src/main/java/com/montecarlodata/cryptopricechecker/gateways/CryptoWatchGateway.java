package com.montecarlodata.cryptopricechecker.gateways;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@RegisterRestClient(configKey = "crypto-watch-gateway-api")
public interface CryptoWatchGateway {
    @GET
    @Path("/markets/kraken/{currencyPair}/price")
    Response getPrice(@PathParam("currencyPair") String currencyPair);

    record Result(double price) {}

    record Response(Result result) {}
}
