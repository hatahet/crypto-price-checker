package com.montecarlodata.cryptopricechecker.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "loader")
public interface LoaderConfig {
    String filename();
}
