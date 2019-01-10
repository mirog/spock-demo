package com.example.spockgroovy.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
class VisitorDetailsDto {
    @JsonProperty
    String id
    @JsonProperty
    String firstName
    @JsonProperty
    String lastName
    @JsonProperty
    String description

    static def toDto(VisitorDetails visitorDetails) {
        new VisitorDetailsDto(
                id: visitorDetails.id,
                firstName: visitorDetails.firstName,
                lastName: visitorDetails.lastName,
                description: visitorDetails.description)
    }
}
