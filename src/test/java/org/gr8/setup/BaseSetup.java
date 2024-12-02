package org.gr8.setup;

import org.gr8.service.OpenBreweryDbService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

public class BaseSetup {

    protected OpenBreweryDbService openBreweryDbService;
    protected SoftAssert softAssert;
    protected static final String CONCURRENT_EXCEPTION_MESSAGE = "Concurrent request limit exceeded. Please delay concurrent calls " +
            "using debounce or throttle.";

    @BeforeSuite
    public void setupService() {
        openBreweryDbService = new OpenBreweryDbService();
    }

    @BeforeMethod
    public void init() {
        softAssert = new SoftAssert();
    }

}
