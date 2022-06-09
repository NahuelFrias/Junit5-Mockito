package mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoginTest {

    @InjectMocks
    private Login login;
    @Mock
    private WebService webService;
    @Captor
    private ArgumentCaptor<CallBack> callBackArgumentCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void doLoginTest() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                //InvocationOnMock nos trae los parametros user,password y callBack
                String user = (String) invocationOnMock.getArguments()[0];//la posicion 0 es user
                assertEquals("Alberto", user);
                String password = (String) invocationOnMock.getArguments()[1];
                assertEquals("1234", password);
                CallBack callBack = (CallBack) invocationOnMock.getArguments()[2];
                callBack.onSucces("Ok");
                return null;
            }
        }).when(webService).login(anyString(), anyString(), any(CallBack.class));//se ejecuta cuando se llama al Mock webService, al metodo login
        login.doLogin();
        verify(webService, times(1)).login(anyString(), anyString(), any(CallBack.class));
        assertEquals(login.isLogin, true);
    }

    @Test
    public void doLoginErrorTest() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                //InvocationOnMock nos trae los parametros user,password y callBack
                String user = (String) invocationOnMock.getArguments()[0];//la posicion 0 es user
                assertEquals("Alberto", user);
                String password = (String) invocationOnMock.getArguments()[1];
                assertEquals("1234", password);
                CallBack callBack = (CallBack) invocationOnMock.getArguments()[2];
                callBack.onFail("Error");
                return null;
            }
        }).when(webService).login(anyString(), anyString(), any(CallBack.class));//se ejecuta cuando se llama al Mock webService, al metodo login
        login.doLogin();
        verify(webService, times(1)).login(anyString(), anyString(), any(CallBack.class));
        assertEquals(login.isLogin, false);
    }

    //capturaremos el callBack para ver que ocurre cuando pasa por el metodo onSucces o onFail
    @Test
    public void doLoginCaptureTest() {
        login.doLogin();
        verify(webService, times(1)).login(anyString(), anyString(), callBackArgumentCaptor.capture());

        CallBack callBack = callBackArgumentCaptor.getValue();
        callBack.onSucces("Ok");
        assertEquals(login.isLogin, true);
    }
}