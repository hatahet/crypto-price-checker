package com.montecarlodata.cryptopricechecker.controllers;

import com.montecarlodata.cryptopricechecker.models.SeriesAndRank;
import com.montecarlodata.cryptopricechecker.services.SchedulerService;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/prices")
public class PricesController {
    private final SchedulerService schedulerService;

    public PricesController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @GET
    @Path("/{currencyPair}")
    public SeriesAndRank getPricesForLast24Hours(@PathParam("currencyPair") String currencyPair) {
        return schedulerService.getPricesForLast24Hours(currencyPair)
                .orElseThrow(NotFoundException::new);
    }
}
