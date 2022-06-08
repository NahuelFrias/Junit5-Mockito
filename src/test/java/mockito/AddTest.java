package mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AddTest {
    //creo el objeto que va aser inyectado con el Mock
    @InjectMocks
    private Add add;
    //creo el objeto Mock
    @Mock
    private ValidNumber validNumber;
    @Mock
    private Print print;
    @Captor
    private ArgumentCaptor<Integer> captor;

    //inicializo los Mock
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addTest() {
        /*
        este test deberia estar mal porque al pasar un 3 el metodo check devuelve true
        pero con el patron when-thenReturn cambio el comportamiento para que de devuelva false
         */
        when(validNumber.check(3)).thenReturn(false);
        boolean checkNumber = validNumber.check(3);
        assertEquals(false, checkNumber);

        //este test esta bien
        when(validNumber.check(3)).thenReturn(true);
        checkNumber = validNumber.check(3);
        assertEquals(true, checkNumber);
    }

    @Test
    public void addTestException() {
        when(validNumber.checkZero(0)).thenThrow(new ArithmeticException("No aceptamos cero"));
        Exception exception = null;
        try {
            validNumber.checkZero(0);
        } catch (ArithmeticException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    //thenCallRealMethod no se usa, es preferible moquear validNumber
    @Test
    public void addRealMethodTest() {
        when(validNumber.check(0)).thenCallRealMethod();
        assertEquals(false, validNumber.check(3));
    }

    @Test
    public void addDoubleToIntTest() {
        Answer<Integer> answer = new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                return 7;
            }
        };
        when(validNumber.doubleToInt(7.7)).thenAnswer(answer);
        assertEquals(14, add.addDouble(7.7));
    }

    //Patron de testing
    @Test
    public void patter() {
        //Arrange
        when(validNumber.check(4)).thenReturn(true);
        when(validNumber.check(5)).thenReturn(true);
        //Act
        int res = add.add(4, 5);
        //Assert
        assertEquals(9, res);
        //Act y Assert pueden hacerse en la misma linea
    }

    @Test
    public void patterBDD() {
        //Given
        given(validNumber.check(4)).willReturn(true);
        given(validNumber.check(5)).willReturn(true);
        //When
        int res = add.add(4, 5);
        //Then
        assertEquals(9, res);
    }

    //podemos poner cualquier entero con anyInt, nos ahorramos una linea de cod
    @Test
    public void argumentMatcherTest() {
        //Given
        given(validNumber.check(anyInt())).willReturn(true);
        //When
        int res = add.add(4, 5);
        //Then
        assertEquals(9, res);
    }

    @Test
    public void addPrintTest() {
        //Given
        //paso solo una vez 4, porque usamos el mismo parametro 2 veces
        given(validNumber.check(4)).willReturn(true);
        //When
        add.addPrint(4, 4);
        //Then
        // verificamos el objeto validNumber con su metodo check y que se ejecute 2 veces
        //porque pasa por el metodo dos veces
        verify(validNumber, times(2)).check(4);
        verify(validNumber, never()).check(99); //nunca a sido ejecutado
        verify(validNumber, atLeast(2)).check(4); // como minimo 2 veces
        verify(validNumber, atMost(6)).check(4);//como mucho 6 veces
        // que verifique de print el metodo showMessage
        verify(print).showMessage(8);//en when dimos 4 y 4, asi verificamos que el metodo se esta eje

        verify(print, never()).Error();//verificamos que el metodo Error nunca fue llamado
    }

    @Test
    public void captorTest() {
        //Given
        //paso solo una vez 4, porque usamos el mismo parametro 2 veces
        given(validNumber.check(4)).willReturn(true);
        //When
        add.addPrint(4, 4);
        //Then
        // verificamos el objeto validNumber con su metodo check y que se ejecute 2 veces
        //porque pasa por el metodo dos veces
        verify(print).showMessage(captor.capture());
        assertEquals(captor.getValue().intValue(), 8);
    }

    //esto deberia ir arriba
    @Spy
    List<String> spyList = new ArrayList<>();
    @Mock
    List<String> mockList = new ArrayList<>();

    //este t
    @Test
    public void spyTest() {
        spyList.add("1");
        spyList.add("2");
        verify(spyList).add("1");
        verify(spyList).add("2");
        assertEquals(2, spyList.size());
    }

    //este test no pasa porque mockList no fue mockeado
    //
    @Test
    public void mockTest() {
        mockList.add("1");
        mockList.add("2");
        verify(mockList).add("1");
        verify(mockList).add("2");
        given(mockList.size()).willReturn(2);
        assertEquals(2, mockList.size());
    }
}