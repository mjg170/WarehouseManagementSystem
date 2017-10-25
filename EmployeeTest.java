import org.junit.Test;
import org.testng.Assert;


//test
public class EmployeeTest {
    @Test
    public void manageItem() throws Exception {
        Assert.fail("Not implemented");
    }

    @Test
    public void getPassword() throws Exception {
        Employee e = new Employee("test","tester",123,true);
        Assert.assertTrue(e.getPassword().equals("test"));
    }

    @Test
    public void getName() throws Exception {
        Employee e = new Employee("test","tester",123,true);
        Assert.assertTrue(e.getName().equals("tester"));
    }

    @Test
    public void getWage() throws Exception {
        Employee e = new Employee("test","tester",123,true);
        Assert.assertEquals(e.getWage(),123);
    }

    @Test
    public void getStatus() throws Exception {
        Employee e = new Employee("test","tester",123,true);
        Assert.assertTrue(e.getStatus());
    }

    @Test
    public void setPassword() throws Exception {
        Employee e = new Employee("test","tester",123,true);
        e.setPassword("test123");
        Assert.assertTrue(e.getPassword().equals("test123"));
    }

    @Test
    public void setName() throws Exception {
        Employee e = new Employee("test","tester",123,true);
        e.setName("tester123");
        Assert.assertTrue(e.getName().equals("tester123"));
    }

    @Test
    public void setWage() throws Exception {
        Employee e = new Employee("test","tester",123,true);
        e.setWage(456);
        Assert.assertEquals(e.getWage(),456);
    }

    @Test
    public void setStatus() throws Exception {
        Employee e = new Employee("test","tester",123,true);
        e.setStatus(false);
        Assert.assertFalse(e.getStatus());
    }



}
