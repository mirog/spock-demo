package com.example.spockgroovy.controller

import com.example.spockgroovy.model.Visitor
import com.example.spockgroovy.model.VisitorDetailsDto
import com.example.spockgroovy.repository.VisitorRepository
import com.github.tomakehurst.wiremock.junit.WireMockRule
import groovy.json.JsonOutput
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static com.github.tomakehurst.wiremock.client.WireMock.get as wiremockGet

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class VisitorsControllerRestSpec extends Specification {
    @Autowired
    VisitorRepository visitorRepository

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8100)

    TestRestTemplate restTemplate = new TestRestTemplate()

    @LocalServerPort
    private int port

    void setup() {
    }

    void cleanup() {
    }

    def "should return data with resttemplate"() {
        given:
        def visitor = visitorRepository.save(new Visitor(firstName: 'Anna', lastName: 'Nowak'))
        stubFor(wiremockGet(urlMatching(".*firstName=Anna&lastName=Nowak.*"))
                .willReturn(aResponse().withHeader(
                HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE).withBody(
                JsonOutput.toJson([value: [joke: 'Opis']])))
        )

        when:
        def result = restTemplate.getForEntity(
                "http://localhost:${port}/api/${visitor.id}",
                VisitorDetailsDto
        )

        then:
        result.statusCode == HttpStatus.OK
        result.body == new VisitorDetailsDto(
                id: visitor.id,
                firstName: 'Anna',
                lastName: 'Nowak',
                description: 'Opis'
        )
    }
}
