
//A type of user that is higher up
public class Manager extends User {
	  
	//makes new instance of manager
	public Manager(String newPassword, String newName, int newWage, Boolean newStatus){
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
	  
	//get password
	public String getPassword(){
		return password;
	}
	//get name
	public String getName(){
		return name;
	}
	//get wage
	public int getWage(){
		return wage;
	}
	//get status
	public boolean getStatus(){
		return status;
	}
	  
	//set password
	public void setPassword(String newPassword){
		password = newPassword;
	}
	//set name
	public void setName(String newName){
		name = newName;
	}
	//set wage
	public void setWage(int newWage){
		wage = newWage;
	}
	//set status
	public void setStatus(boolean newStatus){
		status = newStatus;
	}
}
