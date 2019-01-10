package com.example.spockgroovy.service

import com.example.spockgroovy.exception.VisitorNotFoundException
import com.example.spockgroovy.model.Visitor
import com.example.spockgroovy.repository.VisitorRepository
import spock.lang.Specification

class VisitorServiceSpec extends Specification {

    VisitorService service
    VisitorRepository visitorRepository
    DescriptionClient descriptionClient

    void setup() {
        descriptionClient = Mock(DescriptionClient)
        visitorRepository = Mock(VisitorRepository)
        service = new VisitorService(visitorRepository, descriptionClient)
    }

    void cleanup() {

    }

    def "should fetch data"() {
        given:
        1 * visitorRepository.findById(1) >> Optional.of(new Visitor(id: 1, firstName: 'Jan', lastName: 'Kos'))
        descriptionClient.getDescription(_, _) >> 'Opis'

        when:
        def result = service.fetchDataWithDetails(1)

        then:
        result.firstName == 'Jan'
        result.lastName == 'Kos'
        result.description == 'Opis'
    }

    def "should return error when fetching inexisting data"() {
        given:
        visitorRepository.findById(1) >> Optional.empty()

        when:
        def result = service.fetchDataWithDetails(1)

        then:
        thrown VisitorNotFoundException
        0 * descriptionClient.getDescription(_, _)
    }
}
