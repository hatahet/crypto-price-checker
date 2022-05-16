package com.montecarlodata.cryptopricechecker.lifecycle;

import com.montecarlodata.cryptopricechecker.services.CurrencyPriceWatcher;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.IOException;

@ApplicationScoped
public class ApplicationLifecycleBean {
    private static final Logger LOGGER = Logger.getLogger(ApplicationLifecycleBean.class);

    private final CurrencyPriceWatcher watcher;

    public ApplicationLifecycleBean(CurrencyPriceWatcher watcher) {
        this.watcher = watcher;
    }

    void onStart(@Observes StartupEvent ev) throws IOException {
        LOGGER.info("The application is starting...");
        watcher.scheduleWatchers();
    }
}
