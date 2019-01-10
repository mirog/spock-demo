package com.example.spockgroovy.controller


import com.example.spockgroovy.model.VisitorDetails
import com.example.spockgroovy.service.VisitorService
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [VisitorsController])
class VisitorsControllerMvcMockSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    VisitorService visitorService

    void setup() {
    }

    void cleanup() {
    }

    def "should return data"() {
        given:
        visitorService.fetchDataWithDetails(1) >> new VisitorDetails(id: '1', firstName: 'Anna', lastName: 'Nowak', description: 'Opis')

        expect:
        mockMvc.perform(get('/api/1')).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                JsonOutput.toJson([
                        id: "1",
                        firstName: 'Anna',
                        lastName: 'Nowak',
                        description: 'Opis']
                )))
    }

    def "should return proper data for view"() {
        given:
        visitorService.fetchDataWithDetails(1) >> new VisitorDetails(id: '1', firstName: 'Anna', lastName: 'Nowak', description: 'Opis')

        expect:
        mockMvc.perform(get('/visitor/1/details')).andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute('visitor', new VisitorDetails(id: '1', firstName: 'Anna', lastName: 'Nowak', description: 'Opis')))
    }

    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        VisitorService registrationService() {
            return detachedMockFactory.Stub(VisitorService)
        }
    }
}
