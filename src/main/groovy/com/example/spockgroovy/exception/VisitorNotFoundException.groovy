package com.example.spockgroovy.exception

class VisitorNotFoundException extends RuntimeException {
    VisitorNotFoundException(Integer id) {
        super("Wpis o id $id nie został odnaleziony")
    }
}
