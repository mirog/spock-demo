package com.example.spockgroovy.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString(includeNames = true, includePackage = false)
@EqualsAndHashCode
class VisitorDetails {
    String id
    String firstName
    String lastName
    String description
}
