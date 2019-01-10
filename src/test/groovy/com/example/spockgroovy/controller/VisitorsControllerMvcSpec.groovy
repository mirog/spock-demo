package com.example.spockgroovy.controller

import com.example.spockgroovy.model.Visitor
import com.example.spockgroovy.model.VisitorDetails
import com.example.spockgroovy.repository.VisitorRepository
import com.github.tomakehurst.wiremock.junit.WireMockRule
import groovy.json.JsonOutput
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.get as wiremockGet
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class VisitorsControllerMvcSpec extends Specification {
    @Autowired
    MockMvc mockMvc

    @Autowired
    VisitorRepository visitorRepository

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8100)

    void setup() {
    }

    void cleanup() {
    }

    def "should return data"() {
        given:
        def visitor = visitorRepository.save(new Visitor(firstName: 'Anna', lastName: 'Nowak'))
        stubFor(wiremockGet(urlMatching(".*firstName=Anna&lastName=Nowak.*"))
                .willReturn(aResponse().withHeader(
                HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE).withBody(
                JsonOutput.toJson([value: [joke: 'Opis']])))
        )

        expect:
        mockMvc.perform(get("/api/${visitor.id}")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                JsonOutput.toJson([
                        id: "${visitor.id}",
                        firstName: 'Anna',
                        lastName: 'Nowak',
                        description: 'Opis']
                )))
//                .andExpect(jsonPath('$.id').value(visitor.id))
//                .andExpect(jsonPath('$.firstName').value('Anna'))
//                .andExpect(jsonPath('$.lastName').value('Nowak'))
//                .andExpect(jsonPath('$.description').value('Opis'))
    }

    def "should return proper data for view"() {
        given:
        def visitor = visitorRepository.save(new Visitor(firstName: 'Anna', lastName: 'Nowak'))
        stubFor(wiremockGet(urlMatching(".*firstName=Anna&lastName=Nowak.*"))
                .willReturn(aResponse().withHeader(
                HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_VALUE).withBody(
                JsonOutput.toJson([value: [joke: 'Opis']])))
        )

        expect:
        mockMvc.perform(get("/visitor/${visitor.id}/details")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute('visitor', new VisitorDetails(id: visitor.id, firstName: 'Anna', lastName: 'Nowak', description: 'Opis')))
    }
}
