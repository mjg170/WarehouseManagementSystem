import org.testng.Assert;

public class ItemTest {
    public void getName() throws Exception {
        Item i = new Item("test",123);
        Assert.assertTrue(i.getName().equals("test"));
    }

    public void getQuantity() throws Exception {
        Item i = new Item("test",123);
        Assert.assertEquals(i.getClass(),123);

    }

    public void modifyQuantity() throws Exception {
        Item i = new Item("test",123);
        i.modifyQuantity(456);
        Assert.assertEquals(i.getClass(),456);
    }

}