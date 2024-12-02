package org.gr8.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.java.Log;

import static java.lang.String.format;
import static org.gr8.config.EnvironmentManager.ENV;

@Log
public record ConfigProvider() {

    private static volatile ConfigProvider CONFIG_PROVIDER;
    private static final String APPLICATION_CONFIGURATION_FILE_NAME = "env/application-%s.conf";
    private static final Config CONFIG = getConfig();

    public <T>T getConfigParamValue(String paramName) {
        return (T) CONFIG.getAnyRef(paramName);
    }

    public static ConfigProvider getConfigProvider() {
        ConfigProvider configProvider = CONFIG_PROVIDER;
        if (configProvider != null) {
            return configProvider;
        }
        synchronized (ConfigProvider.class) {
            if (CONFIG_PROVIDER == null) {
                CONFIG_PROVIDER = new ConfigProvider();
            }
        }
        return CONFIG_PROVIDER;
    }

    private static Config getConfig() {
        log.info(format("Environment is '%s'", ENV.getEnvName()));
        var fileName = format(APPLICATION_CONFIGURATION_FILE_NAME, ENV.getEnvName());
        return ConfigFactory.load(fileName);
    }

}
