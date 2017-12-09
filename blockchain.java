import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class blockchain implements Serializable{
	
	private static final long serialVersionUID = 913215782133824680L;
	public block genesis = new block("Genesis");
	public block end = genesis;
	
	//Looks up what transactions have handled a certain item
	public LinkedList<transaction> lookUp(Item item){
		block current = genesis;
		LinkedList<transaction> transactions = new LinkedList<transaction>();
		while(current.getNext() != null){
			if(current.getTransactions() != null){
				for(transaction trans: current.getTransactions()){
					if(trans.getItem().equals(item.getName())){
						transactions.add(trans);
					}
				}
			}
			current = current.getNext();
		}
		return transactions;
	}
	
	//Looks up what transactions were handled by an employee
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
	
	//Look ups what transactions happened on a date
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
	
	//Adds a block to the chain with a list of transactions
	public block addBlock(transaction[] transactions) throws NoSuchAlgorithmException{
		end = new block(end, transactions);
		return end;
	}
}
