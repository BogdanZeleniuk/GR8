package org.gr8.api.data;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;

import static org.gr8.utils.Constants.Numbers.*;

public class StaticProvider {

    @DataProvider(name = "Search breweries by valid search params", parallel = true)
    public Object[][] searchBreweriesByValidSearchParamsData() {
        return new Object[][] {
                { "San Diego", THREE},
                { "California", ZERO},
                {StringUtils.EMPTY, FOUR},
                {StringUtils.SPACE, ONE}
        };
    }

    @DataProvider(name = "Search breweries by invalid search params", parallel = true)
    public Object[][] searchBreweriesByInvalidSearchParamsData() {
        return new Object[][] {
                {"Washington", -TWO},
        };
    }

}
