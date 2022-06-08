package com.example.unittest.unittest.junit5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.stream.Stream;

class CalculatorTest {

    private Calculator calculator;
    private Calculator calculatorNull;
    private static Calculator calStatic;

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

    //BeforeAll se ejecuta antes de los test, pero solo una vez
    @BeforeAll
    public static void beforeAll(){
        calStatic = new Calculator();
        System.out.println("Ejecutando BeforeAll!!");
    }

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

    @AfterAll
    public static void afterAllTests(){
        calStatic = null;
        System.out.println("ejecutando AfterAll");
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

    @Test
    //con DisplayName cambio el nombre para ver en consola
    @DisplayName("Funcion que divide!")
    public void divideTest(){
        assertEquals(3,calculator.divide(9,3));
    }

    /* este test es un ejemplo de un metodo que falla
    y debemos corregir
    Lo desabilitamos*/
    @Disabled("Esperando a resolver bug")
    @Test
    public void divideByZeroTest(){
        fail("Fallo detectado: No se puede dividir por cero!");
        calculator.divideByZero(10,0);
    }

    @Test
    public void divideByZero_ExpectedException(){
        //expectedType: excepcion que espera
        //metodo pasado por lambda
        //mensaje
        assertThrows(ArithmeticException.class, ()->calculator.divideByZero(10,0), "No se puede dividir por cero!");
    }

    /*AssertAll ejecuta todos los test
    no es recomendable*/
    @Test
    public void assertAll_Test(){
        assertAll(
                ()->assertEquals(30,calculator.add(10,5)),
                ()->assertEquals(10,calculator.subtract(50,40),"Ejecutando Test")
        );
    }

    /*
    Nested sirve para hacer varios test
    de una misma funcion con parametros distintos
     */
    @Nested
    class AddTest{
        @Test
        public void add_test_positive(){
            assertEquals(20,calculator.add(5,5));
        }
        @Test
        public void add_test_negative(){
            assertEquals(-30,calculator.add(-15,-15));
        }
        @Test
        public void add_test_zero(){
            assertEquals(0,calculator.add(0,0));
        }
    }

    /*
    Ejemplo en nuestra división queremos hacer 5 pruebas
    Positivo / Positivo = Positivo
    Positivo / Negativo = Negativo
    Negativo / Positivo = Negativo
    Negativo / Negativo = Positivo
    Positivo / 0 = Excepción
    Método a Probar                 |      Entrada      |       Salida Experarada
    dividir(int a, int b)           |a = 6, b=2         |3
    dividir(int a, int b)           |a = 6, b=-2        |-3
    dividir(int a, int b)           |a = -6, b=2        |-3
    dividir(int a, int b)           |a = -6, b=-2       |3
    dividir(int a, int b)           |a = -6, b=0        |Excepción
     */


    //HACER LAS IMPORTACIONES!
    //indico los indices de los parametros
    @ParameterizedTest(name = "{index} => a={0}, b={1}, sum={2}")
    //de donde obtenemos los datos
    @MethodSource("addProviderData")
    public void addParameterizedTest(int a, int b, int sum){
        assertEquals(sum, calculator.add(a,b));
    }

    //paso los datos
    public static Stream<Arguments> addProviderData(){
        return Stream.of(
                Arguments.of(6,2,8),
                Arguments.of(6,-2,4),
                Arguments.of(-6,2,-4),
                Arguments.of(-6,-2,-8),
                Arguments.of(6,0,6)
        );
    }

    /*este test verifica que el metodo tarde 500 milisegundos
    si tardara mas, el test no pasa!
    con el operador Lambda le decimos que metodo estamos testeando
    */

    @Test
    public void timeOutTest(){
        assertTimeout(Duration.ofMillis(2000), ()->{
            calculator.longTaskOperation();
        });
    }
}