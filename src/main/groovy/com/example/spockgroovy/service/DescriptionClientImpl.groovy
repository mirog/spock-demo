package com.example.spockgroovy.service

import com.example.spockgroovy.model.IcndbResponseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestOperations

@Service
class DescriptionClientImpl implements DescriptionClient {
    private final RestOperations restTemplate
    private final String address

    DescriptionClientImpl(
            RestOperations restTemplate,
            @Value('${icndb.url}') String address) {
        this.restTemplate = restTemplate
        this.address = address
    }

    @Override
    String getDescription(String firstName, String lastName) {
        restTemplate.getForObject("$address?firstName=$firstName&lastName=$lastName&exclude=explicit&limitTo=nerdy",
                IcndbResponseDto).value.joke
    }
}
