package base;

import UserMaster.UserMaster;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import Login.Login;
import tests.TicketMaster;

public class RunTest extends Config {



    @Test(groups = "amazePOS.test.login", priority = 1)
    @Parameters({"url", "username", "password"})
    public void login(String url, String username, String password) throws InterruptedException {
        Login login = new Login();
        login.login_test(url, username, password);
    }

    @Test(groups = "amazePOS.test.ticketMaster", priority = 2)
    public void ticketMaster(){
        TicketMaster tm = new TicketMaster();
        tm.ticketMaster();
    }

    @Test(groups = "amazePOS.test.userMaster", priority = 3)
    @Parameters({"url", "username", "password"})
    public void userMaster(String url, String username, String password) throws InterruptedException {
        UserMaster userMaster = new UserMaster();
        userMaster.userMaster_test(url, username, password);
    }
}
