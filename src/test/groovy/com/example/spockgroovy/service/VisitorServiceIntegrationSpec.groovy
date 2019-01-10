package com.example.spockgroovy.service

import com.example.spockgroovy.exception.VisitorNotFoundException
import com.example.spockgroovy.model.Visitor
import com.example.spockgroovy.repository.VisitorRepository
import com.github.tomakehurst.wiremock.junit.WireMockRule
import groovy.json.JsonOutput
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching

@SpringBootTest
@ActiveProfiles("test")
class VisitorServiceIntegrationSpec extends Specification {

    @Autowired
    VisitorRepository repository

    @Autowired
    VisitorService service

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8100)

    void setup() {

    }

    void cleanup() {
        repository.deleteAll()
    }

    def "should fetch data"() {
        given:
        def first = repository.save(new Visitor(firstName: 'Jan', lastName: 'Kos'))
        def second = repository.save(new Visitor(firstName: 'Anna', lastName: 'Nowak'))

        when:
        def result = service.fetchData(second.id)

        then:
        result.firstName == 'Anna'
        result.lastName == 'Nowak'
    }

    def "should fetch data with details"() {
        given:
        def first = repository.save(new Visitor(firstName: 'Jan', lastName: 'Kos'))
        def second = repository.save(new Visitor(firstName: 'Anna', lastName: 'Nowak'))

        stubFor(get(urlMatching(".*firstName=Anna&lastName=Nowak.*"))
                .willReturn(aResponse().withHeader(
                HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE).withBody(
                JsonOutput.toJson([value: [joke: 'Opis']])))
        )

        when: "Calling a service fetchDataWithDetails method"
        def result = service.fetchDataWithDetails(second.id)

        then: "expect a correct response"
        with (result) {
            firstName == 'Anna'
            lastName == 'Nowak'
            description == 'Opis'
        }
    }

    def "should return exception when fetching data for not existing visitor"() {
        given:

        when: "Calling a service fetchDataWithDetails method"
        def result = service.fetchDataWithDetails(1)

        then: "expect a correct response"
        thrown VisitorNotFoundException
    }
}
