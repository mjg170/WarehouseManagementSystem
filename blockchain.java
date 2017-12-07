import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class blockchain {
	
	public block genesis = new block();
	public block end = genesis;
	
	public LinkedList<transaction> lookUp(Item item){
		block current = genesis;
		LinkedList<transaction> transactions = new LinkedList<transaction>();
		while(current.getNext() != null){
			if(current.getTransactions() != null){
				for(transaction trans: current.getTransactions()){
					if(trans.getItem().getName().equals(item.getName())){
						transactions.add(trans);
					}
				}
			}
			current = current.getNext();
		}
		return transactions;
	}
	
	public LinkedList<transaction> lookUp(Employee employee){
		block current = genesis;
		LinkedList<transaction> transactions = new LinkedList<transaction>();
		while(current.getNext() != null){
			if(current.getTransactions() != null){
				for(transaction trans: current.getTransactions()){
					if(trans.getEmployee().getName().equals(employee.getName())){
						transactions.add(trans);
					}
				}
			}
			current = current.getNext();
		}
		return transactions;
	}
	
	public LinkedList<transaction> lookUp(LocalDateTime day){
		block current = genesis;
		LinkedList<transaction> transactions = new LinkedList<transaction>();
		while(current.getNext() != null){
			if(current.getTransactions() != null){
				for(transaction trans: current.getTransactions()){
					if(trans.getTime().getYear() == day.getYear() && trans.getTime().getDayOfYear() == day.getDayOfYear()){
						transactions.add(trans);
					}
				}
			}
			current = current.getNext();
		}
		return transactions;
	}
	
	
	public block addBlock(transaction[] transactions) throws NoSuchAlgorithmException{
		end = new block(end, transactions);
		return end;
	}
}
