
//A type of user that is higher up
public class Manager extends User {
	  
	//makes new instance of manager
	public void user(String newPassword, String newName, int newWage, Boolean newStatus){
	  	password = newPassword;
	  	name = newName;
	  	wage = newWage;
	  	status = newStatus;
	}
		  
	public void veiwLog(){
		  
	}
		  
	public void manageArea(){
		  
	}
		  
	public void manageItem(){
			    
	}
	  
	//get statments
	public String getPassword(){
		return password;
	}
	public String getName(){
		return name;
	}
	public int getWage(){
		return wage;
	}
	public boolean getStatus(){
		return status;
	}
	  
	//set statements
	public void setPassword(String newPassword){
		password = newPassword;
	}
	public void setName(String newName){
		name = newName;
	}
	public void setWage(int newWage){
		wage = newWage;
	}
	public void setStatus(boolean newStatus){
		status = newStatus;
	}
}
