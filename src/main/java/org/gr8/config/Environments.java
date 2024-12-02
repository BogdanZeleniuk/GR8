package org.gr8.config;

import lombok.Getter;

@Getter
public enum Environments {

    DEV("dev"),
    QA("qa");

    private final String envName;

    Environments(String envName) {
        this.envName = envName;
    }

}
