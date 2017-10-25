import org.junit.Test;
import org.testng.Assert;

public class ManagerTest {

    @Test
    public void veiwLog() throws Exception {
        Assert.fail("Not implemented");
    }

    @Test
    public void manageArea() throws Exception {
        Assert.fail("Not implemented");

    }

    @Test
    public void manageItem() throws Exception {
        Assert.fail("Not implemented");
    }

    @Test
    public void getPassword() throws Exception {
        Manager e = new Manager("test","tester",123,true);
        Assert.assertTrue(e.getPassword().equals("test"));
    }

    @Test
    public void getName() throws Exception {
        Manager e = new Manager("test","tester",123,true);
        Assert.assertTrue(e.getName().equals("tester"));
    }

    @Test
    public void getWage() throws Exception {
        Manager e = new Manager("test","tester",123,true);
        Assert.assertEquals(e.getWage(),123);
    }

    @Test
    public void getStatus() throws Exception {
        Manager e = new Manager("test","tester",123,true);
        Assert.assertTrue(e.getStatus());
    }

    @Test
    public void setPassword() throws Exception {
        Manager e = new Manager("test","tester",123,true);
        e.setPassword("test123");
        Assert.assertTrue(e.getPassword().equals("test123"));
    }

    @Test
    public void setName() throws Exception {
        Manager e = new Manager("test","tester",123,true);
        e.setName("tester123");
        Assert.assertTrue(e.getName().equals("tester123"));
    }

    @Test
    public void setWage() throws Exception {
        Manager e = new Manager("test","tester",123,true);
        e.setWage(456);
        Assert.assertEquals(e.getWage(),456);
    }

    @Test
    public void setStatus() throws Exception {
        Manager e = new Manager("test","tester",123,true);
        e.setStatus(false);
        Assert.assertFalse(e.getStatus());
    }

}