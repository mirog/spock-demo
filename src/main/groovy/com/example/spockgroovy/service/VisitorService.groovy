package com.example.spockgroovy.service

import com.example.spockgroovy.exception.VisitorNotFoundException
import com.example.spockgroovy.model.Visitor
import com.example.spockgroovy.model.VisitorDetails
import com.example.spockgroovy.model.VisitorListing
import com.example.spockgroovy.repository.VisitorRepository
import org.springframework.stereotype.Service

@Service
class VisitorService {
    private final VisitorRepository repository
    private final DescriptionClient descriptionClient

    VisitorService(VisitorRepository repository, DescriptionClient descriptionClient) {
        this.descriptionClient = descriptionClient
        this.repository = repository
    }

    Visitor fetchData(Integer id) {
        repository.findById(id)
                .orElseThrow { new VisitorNotFoundException(id) }
    }

    VisitorDetails fetchDataWithDetails(Integer id) {
        Visitor visitor = fetchData(id)

        new VisitorDetails(
                id: visitor.id,
                firstName: visitor.firstName,
                lastName: visitor.lastName,
                description: descriptionClient.getDescription(visitor.firstName, visitor.lastName)
        )
    }

    VisitorListing fetchListing() {
        new VisitorListing(visitors: repository.findAll())
    }

    void deleteVisitor(Integer id) {
        repository.deleteById(id)
    }

    def addUser(String firstName, String lastName) {
        repository.save(new Visitor(firstName: firstName, lastName: lastName))
    }
}
