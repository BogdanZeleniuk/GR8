package org.gr8.api.tests;

import org.apache.commons.lang3.StringUtils;
import org.gr8.api.data.StaticProvider;
import org.gr8.model.enums.BreweryType;
import org.gr8.model.response.ConcurrentRequestExceptionResponse;
import org.gr8.model.response.SingleAutocompleteBreweryResponse;
import org.gr8.model.response.SingleBreweryResponse;
import org.gr8.setup.BaseSetup;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static org.gr8.utils.Constants.Numbers.ZERO;

public class SearchBreweriesTest extends BaseSetup {

    @Test(dataProvider = "Search breweries by valid search params", dataProviderClass = StaticProvider.class)
    public void validateSearchForBreweriesByValidInputParameters(String city, Integer itemsPerPage) {
        var response = openBreweryDbService.getSearchBreweriesSuccessful(city, itemsPerPage);
        if (
                city.equalsIgnoreCase(StringUtils.EMPTY) ||
                city.equalsIgnoreCase(StringUtils.SPACE) ||
                itemsPerPage == ZERO
        ) {
            softAssert.assertTrue(response.isEmpty(), format("Expected response does not contain any brewery, but found %s", response.size()));
        } else {
            var breweryOne = new SingleBreweryResponse(
                  UUID.fromString("385a2831-a7d5-4d07-bcff-d9d3312c571c"), "Mikkeller Brewing San Diego", BreweryType.MICRO.getBreweryType(),
                    "9366 Cabot Dr", null, null, "San Diego", "California", "92126-4311", "United States",
                    "-117.144171", "32.89146307", null, null, "California", "9366 Cabot Dr"
            );
            var breweryTwo = new SingleBreweryResponse(
                    UUID.fromString("7713be83-2ff6-4dc5-a28e-a4f629978402"), "San Diego Brewing Co", BreweryType.BREWPUB.getBreweryType(),
                    "10450 Friars Rd Ste L", null, null, "San Diego", "California", "92120-2311", "United States",
                    null, null, "6192842739", "http://www.sandiegobrewing.com", "California", "10450 Friars Rd Ste L"
            );
            var breweryThree = new SingleBreweryResponse(
                    UUID.fromString("28945428-2326-41b3-b2d9-97f6e8154783"), "Gordon Biersch Brewery Restaurant - San Diego", BreweryType.BREWPUB.getBreweryType(),
                    "5010 Mission Center Rd", null, null, "San Diego", "California", "92108-3211", "United States",
                    "-117.1539235", "32.7740128", "6196881120", "http://www.craftworksrestaurants.com", "California", "5010 Mission Center Rd"
            );
            List<SingleBreweryResponse> expectedListResponse = List.of(breweryOne, breweryTwo, breweryThree);
            softAssert.assertTrue(response.equals(expectedListResponse), format("Expected breweries list '%s' is same as actual result '%s'",
                    expectedListResponse, response));
        }
        softAssert.assertAll();
    }

    @Test(dataProvider = "Search breweries by invalid search params", dataProviderClass = StaticProvider.class)
    public void validateSearchForBreweriesByInvalidInputParameters(String city, Integer itemsPerPage) {
        var expectedStatusCode = 429;
        var response = openBreweryDbService.getSearchBreweriesResponse(city, itemsPerPage, expectedStatusCode);
        softAssert.assertEquals(response.getStatusCode(), expectedStatusCode);

        ConcurrentRequestExceptionResponse deserializedResponse = openBreweryDbService.deserializeAsObject(response,
                ConcurrentRequestExceptionResponse.class);
        softAssert.assertEquals(deserializedResponse.getMessage(), CONCURRENT_EXCEPTION_MESSAGE);
        softAssert.assertAll();
    }

    @Test
    public void validateBreweriesAutocompleteFeature() {
        var city = "San Diego";
        var response = openBreweryDbService.getAutocompleteBreweriesSuccessful(city);
        var expectedAutocompleteResponse = List.of(
                new SingleAutocompleteBreweryResponse(UUID.fromString("385a2831-a7d5-4d07-bcff-d9d3312c571c"), "Mikkeller Brewing San Diego"),
                new SingleAutocompleteBreweryResponse(UUID.fromString("7713be83-2ff6-4dc5-a28e-a4f629978402"), "San Diego Brewing Co"),
                new SingleAutocompleteBreweryResponse(UUID.fromString("28945428-2326-41b3-b2d9-97f6e8154783"), "Gordon Biersch Brewery Restaurant - San Diego"),
                new SingleAutocompleteBreweryResponse(UUID.fromString("01b395ba-7b97-4214-a98c-365ad281d9dd"), "G8 Development, Inc."),
                new SingleAutocompleteBreweryResponse(UUID.fromString("0338af09-60df-4e16-9fd6-89d3033c9cc2"), "Deft Brewing")
        );
        softAssert.assertEquals(response.size(), 15);
        softAssert.assertTrue(response.containsAll(expectedAutocompleteResponse));
        softAssert.assertAll();
    }

}
