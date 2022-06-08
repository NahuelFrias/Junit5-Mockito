package mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AddCreateMockTest1 {

    private Add add;
    private ValidNumber validNumber;

    @BeforeEach
    public void setUp() {
        validNumber = Mockito.mock(ValidNumber.class); //1 paso: mockeo la dependencia validNumber
        add = new Add(validNumber); //el validNumber que paso aca es un mock
    }

    @Test
    public void addTest() {
        //2 ejecuto el cod de la clase bajo test
        add.add(3, 2); //llamo al metodo a testear
        Mockito.verify(validNumber).check(3);//verificamos que el metodo check de ValidNumber funcione
        Mockito.verify(validNumber).check(5);//falla porque el metodo check no fue llamado con 5
    }
}