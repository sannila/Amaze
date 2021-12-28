package base;

import UserMaster.UserMasterEditUpdate;
import UserMaster.UserMasterNew;
import org.testng.annotations.DataProvider;
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

    @Test(groups = "amazePOS.test.user.new", priority = 3)
    @Parameters({"url", "username", "password"})
    public void userMaster(String url, String username, String password) throws InterruptedException {
        UserMasterNew userMasterNew = new UserMasterNew();
        userMasterNew.userMaster_test(url, username, password);
    }

    @Test(groups = "amazePOS.test.user.edit", priority = 4)
    @Parameters({"url", "username", "password"})
    public void userMasterEdit(String url, String username, String password) throws InterruptedException {
        UserMasterEditUpdate userMasterEditUpdate = new UserMasterEditUpdate();
        userMasterEditUpdate.userMasterEdit_test(url, username, password);
    }

}
