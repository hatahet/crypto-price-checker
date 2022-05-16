package com.montecarlodata.cryptopricechecker.lifecycle;

import com.montecarlodata.cryptopricechecker.services.SchedulerService;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.IOException;

@ApplicationScoped
public class ApplicationLifecycleBean {
    private static final Logger LOGGER = Logger.getLogger(ApplicationLifecycleBean.class);

    private final SchedulerService watcherService;

    public ApplicationLifecycleBean(SchedulerService watcherService) {
        this.watcherService = watcherService;
    }

    void onStart(@Observes StartupEvent ev) throws IOException {
        LOGGER.info("The application is starting...");
        watcherService.scheduleWatchers();
    }
}
