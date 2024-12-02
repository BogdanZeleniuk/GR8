package org.gr8.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public record Constants() {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public static class ApplicationProperties {
        public static final String BASE_URI = "base.uri";
        public static final String BASE_PATH = "base.path";
    }

    public static class Numbers {

        public static final int ZERO = 0;
        public static final int ONE = 1;
        public static final int TWO = 2;
        public static final int THREE = 3;
        public static final int FOUR = 4;

    }

}
