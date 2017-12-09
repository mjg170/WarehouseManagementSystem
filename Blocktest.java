import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class Blocktest {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		blockchain bc = new blockchain();
		Item item1 = new Item("Cars", 10, "Goods", 10, 10000.0, 2000.00, 1);
		Item item2 = new Item("Dogs", 100, "Animals", 10, 1000.0, 85.0, 2);
		Item item3 = new Item("Cats", 100, "Animals", 100, 1000.0, 20.0, 3);
		Employee mark = new Employee("Mark", "Mark Wang", 10, false);
		
		
		transaction[] transactions = {new transaction("Cars", -5, LocalDateTime.now(), mark), new transaction("Dogs", -5, LocalDateTime.now(), mark), new transaction("Feces", 10, LocalDateTime.now(), mark)};
		
		bc.addBlock(transactions);
		System.out.println("done");
	}

}
