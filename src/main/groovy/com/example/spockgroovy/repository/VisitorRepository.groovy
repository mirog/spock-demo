package com.example.spockgroovy.repository

import com.example.spockgroovy.model.Visitor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VisitorRepository extends CrudRepository<Visitor, Integer> {
    List<Visitor> findAll()
}