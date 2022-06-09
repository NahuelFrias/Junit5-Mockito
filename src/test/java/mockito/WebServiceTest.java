package mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class WebServiceTest {

    private WebService webService;
    @Mock
    private CallBack callBack;

    @BeforeEach
    public void setUp() {
        webService = new WebService();
        MockitoAnnotations.initMocks(this); //inicio el Mock
    }

    @Test
    public void checkLoginTest() {
        assertTrue(webService.checkLogin("Alberto", "1234"));
    }

    @Test
    public void checkLoginErrorTest() {
        assertFalse(webService.checkLogin("Alberto", "aaa"));
    }

    public void loginTest() {
        webService.login("Alberto", "1234", callBack);
        verify(callBack).onSucces("Se registro con exito");
    }

    public void loginErrorTest() {
        webService.login("Maria", "1234", callBack);
        verify(callBack).onFail("Error");
    }

}