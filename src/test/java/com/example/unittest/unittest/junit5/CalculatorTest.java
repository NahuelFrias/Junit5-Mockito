package com.example.unittest.unittest.junit5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;
    private Calculator calculatorNull;

    /*
     //METODO////////////////////////////////////////ESPECIFICACIOM//////////////////////////////////////////////////////////////////////////////
    int sumar(                  |Este método devuelve un int resultado de la suma de numero 1 y numero2
        int numero1,            |
        int numero2)            |
    ------------------------------------------------------------------------------------------------------------------------
    int restar(                 |Este método devuelve un int resultado de la resta de numero 1 y numero2
        int numero1,            |
        int numero2)            |
    Método a Probar                 |      Entrada      |       Salida Esperada
    sumar(int a, int b)             |a = 10, b=20       |30
    sumar(int a, int b)             |a = 7, b=4         |11
    restar(int a, int b)            |a = 7, b=4         |3
    restar(int a, int b)            |a = 10, b=20       |-10
     */

    @BeforeEach // antes de cada test se ejecuta este codigo
    public void setUp(){
        calculator = new Calculator();
        // ej. Este sout no es esencial en codigo de produccion
        System.out.println("Ejecutando BeforeEach");
    }

    //liberamos recursos, esto es util cuando utilizamos bbdd
    @AfterEach
    public void tearDown(){
        calculator = null;
        System.out.println("Ejecutando AfterEach");
    }

    @Test
    public void CalculatorNotNullTest(){

        //assert es una afirmacion sobre como funciona un metodo
        assertNotNull(calculator, "Calculator debe ser not null (estar instanciado)");
        // ej. Este sout no es esencial en codigo de produccion
        System.out.println("Ejecutando Test");
    }

    @Test
    public void CalculatorNullTest(){
        assertNull(calculatorNull);
        System.out.println("Ejecutando 2do Test");
    }
/*
    @Test
    public void addAssertTest(){
        //1.-SetUp lo que necesito para hacer el test
        // calculator de ejecuta en el BeforeEach
        int resEsperado = 30;
        int resActual;
        //2.-Action, accion a ejecutar
        resActual = calculator.add(10,20);
        //3.-Assert, afirmacion que nos dice si el metodo hace o no lo que debe
        assertEquals(resEsperado, resActual);
        System.out.println("Ejecutando addAssertTest");
    }
*/
    // esto es lo mismo de arriba
    @Test
    public void addTest(){
        assertEquals(30, calculator.add(10,20));
    }

    @Test
    public void assertTypes(){
        Calculator cal1 = new Calculator();
        Calculator cal2 = new Calculator();
        Calculator cal3 = cal1;

        assertSame(cal1, cal3);
        assertNotSame(cal1, cal2);

        assertEquals("hola", "hola", "Fallo");
        assertEquals(1,1.4,0.5);
    }

    @Test
    public void subsTest(){
        assertEquals(5,calculator.subtract(20,15));
    }

}