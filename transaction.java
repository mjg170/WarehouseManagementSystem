import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class transaction implements Serializable{
	
	private String item;
	private int amount;
	private long time;
	private User user;
	
	//Constructor 
	public transaction(String item, int amount, LocalDateTime time, User user){
		this.item = item;
		this.amount = amount;
		this.time = time.toInstant(ZoneOffset.UTC).toEpochMilli();
		this.user = user;
	}
	
	public transaction(){
		//Empty Constructor
	}
	
	//Gets the time that the transaction occured
	public LocalDateTime getTime(){
		return LocalDateTime.ofEpochSecond(time * 1000, 0, ZoneOffset.UTC);
		
	}
	
	//Gets the amount transfered
	public int getAmount(){
		return amount;
	}
	
	
	//Gets the item type
	public String getItem(){
		return item;
	}
	
	//Gets the Employee that does the transaction
	public User getEmployee(){
		return user;
	}
	
	//Gets the transaction's string
	public String getTransaction(){
		return amount + item + user.getName() + time;
	}
}
