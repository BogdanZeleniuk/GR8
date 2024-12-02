package org.gr8.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleAutocompleteBreweryResponse {

    private UUID id;
    private String name;

}
