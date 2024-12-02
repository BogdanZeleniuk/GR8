package org.gr8.model.enums;

import lombok.Getter;

@Getter
public enum BreweryType {

    MICRO("micro"),
    BREWPUB("brewpub");

    private final String breweryType;

    BreweryType(String breweryType) {
        this.breweryType = breweryType;
    }

}
