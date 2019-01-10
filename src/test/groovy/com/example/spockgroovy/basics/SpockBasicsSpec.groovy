package com.example.spockgroovy.basics

import spock.lang.Specification
import spock.lang.Unroll

class SpockBasicsSpec extends Specification {
    def "should add two numbers"() {
        given:
        def a = 1
        def b = 2

        when:
        def c = a + b

        then:
        c == 3
    }

    def "should add another two numbers"() {
        given:
        def a = 2
        def b = 2

        when:
        def c = a + b

        then:
        c == 5
    }

    def "should add numbers in a short way"() {
        expect:
        2 + 3 == 5
    }

    def "should add numbers several times"() {
        expect:
        a + b == c

        where:
        a | b || c
        1 | 2 || 3
        2 | 4 || 6
        3 | 1 || 5
    }

    @Unroll
    def "should add #a + #b == #c"() {
        expect:
        a + b == c

        where:
        a | b || c
        1 | 2 || 3
        2 | 4 || 6
        3 | 1 || 5
    }

    @Unroll
    def "should add #a + #b == #c once again"() {
        expect:
        a + b == c

        where:
        a << [1, 2, 3]
        b << [2, 4, 1]
        c << [3, 6, 5]
    }

    def "should throw exception"() {
        when: "divide by zero"
        def result = 5 / 0

        then: "we get an ArithmeticException"
        thrown ArithmeticException
    }
}
