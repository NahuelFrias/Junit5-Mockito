package com.example.unittest.unittest.junit5;

public class Calculator {
    /*
    //METODO////////////////////////////////////////ESPECIFICACIOM//////////////////////////////////////////////////////////////////////////////
    int sumar(                  |Este método devuelve un int resultado de la suma de numero 1 y numero2
        int numero1,            |
        int numero2)            |
    ------------------------------------------------------------------------------------------------------------------------
    int restar(                 |Este método devuelve un int resultado de la resta de numero 1 y numero2
        int numero1,            |
        int numero2)            |
    ------------------------------------------------------------------------------------------------------------------------
     */

    private int result;

    public int add(int n1, int n2){
        result = n1 + n2;
        return result;
    }

    public int subtract(int n1, int n2){
        result = n1 - n2;
        return result;
    }
}
