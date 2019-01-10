package com.example.spockgroovy.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'visitors')
class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id
    String firstName
    String lastName
}
