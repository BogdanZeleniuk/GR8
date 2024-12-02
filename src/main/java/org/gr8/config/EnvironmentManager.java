package org.gr8.config;

import java.util.EnumSet;
import java.util.Optional;

import static java.lang.String.format;

public record EnvironmentManager() {

    public static final Environments ENV;
    private static final Environments DEFAULT_ENV = Environments.QA;
    private static final String ENV_PARAM_NAME = "env";

    static {
        var envFromSystem = getEnvFromSystem();
        ENV = getEnvFromString(envFromSystem);
    }

    private static String getEnvFromSystem() {
        var sysEnv = System.getProperty(ENV_PARAM_NAME);
        return sysEnv != null && !sysEnv.isEmpty() ?
                sysEnv : DEFAULT_ENV.getEnvName();
    }

    private static Environments getEnvFromString(String env) {
        return Optional.of(EnumSet.allOf(Environments.class).stream()
                        .filter(environments -> environments.getEnvName().equalsIgnoreCase(env))
                        .findFirst())
                        .orElseThrow(() -> new RuntimeException(format("Environment '%s' does not exist", env)))
                .get();
    }

}
