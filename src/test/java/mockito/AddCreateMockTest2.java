package mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class AddCreateMockTest2 {

    //creo el objeto que va aser inyectado con el Mock
    @InjectMocks
    private Add add;
    //creo el objeto Mock
    @Mock
    private ValidNumber validNumber;

    //inicializo los Mock
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addTest() {
        //ejecuto el cod de la clase bajo test
        add.add(3, 2);//llamo al metodo a testear
        Mockito.verify(validNumber).check(3);//verificamos que el metodo check de validNumber funque
    }
}