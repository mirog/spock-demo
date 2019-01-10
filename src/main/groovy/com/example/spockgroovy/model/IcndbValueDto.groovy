package com.example.spockgroovy.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class IcndbValueDto {
    @JsonProperty
    String joke
}
