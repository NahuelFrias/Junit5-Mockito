package com.example.unittest.unittest.junit5;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureCalculatorTest {

    private static TemperatureCalculator tc;

    @BeforeAll
    public static void beforeAll() {
        tc =new TemperatureCalculator();
        System.out.println("beforeAll");
    }
    @AfterAll
    public static void afterAll(){
        tc = null;
        System.out.println("afterAll");
    }
    @Test
    public void toFahrenheit_Test(){
        assertEquals(33.8, tc.toFahrenheit(1), 0.1 );
        System.out.println("Test de temperatura hecho!");
    }

}