package mockito;

public class WebService {

    public void login(String user, String password, CallBack callBack) {
        if (checkLogin(user, password)) {
            callBack.onSucces("Se registro con exito");
        } else {
            callBack.onFail("Error");
        }
    }

    public boolean checkLogin(String user, String password) {
        if (user.equals("Alberto") && password.equals("1234")) {
            return true;
        }
        return false;
    }
}
