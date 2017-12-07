import java.time.LocalDateTime;

public class transaction {
	private Item item;
	private int amount;
	private LocalDateTime time;
	private Employee employee;
	
	public transaction(Item item, int amount, LocalDateTime time, Employee employee){
		this.item = item;
		this.amount = amount;
		this.time = time;
		this.employee = employee;
	}
	
	public LocalDateTime getTime(){
		return time;
		
	}
	
	public int getAmount(){
		return amount;
	}
	
	public Item getItem(){
		return item;
	}
	
	public Employee getEmployee(){
		return employee;
	}
	
	public String getTransaction(){
		return amount + item.getName() + employee.getName() + time.toString();
	}
}
