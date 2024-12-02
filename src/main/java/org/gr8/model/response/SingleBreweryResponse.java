package org.gr8.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleBreweryResponse {

    private UUID id;
    private String name;
    private String brewery_type;
    private String address_1;
    @JsonInclude(JsonInclude.Include.NON_NULL) private String address_2;
    @JsonInclude(JsonInclude.Include.NON_NULL) private String address_3;
    private String city;
    private String state_province;
    private String postal_code;
    private String country;
    @JsonInclude(JsonInclude.Include.NON_NULL) private String longitude;
    @JsonInclude(JsonInclude.Include.NON_NULL) private String latitude;
    @JsonInclude(JsonInclude.Include.NON_NULL) private String phone;
    @JsonInclude(JsonInclude.Include.NON_NULL) private String website_url;
    private String state;
    private String street;

}
