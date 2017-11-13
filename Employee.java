//a standard employee
public class Employee extends User {
	
	//what the employee is not allowed to access
	 private Permission myPermission;
	
	 //instantiation
	  public Employee(String newPassword, String newName, int newWage, Boolean newStatus){
		    myPermission = new Permission();
		  	password = newPassword;
		  	name = newName;
		  	wage = newWage;
		  	status = newStatus;
	  }
	  
	  public void manageItem(){
	  	//not implemented
		  
		  
	  }
	  
	  //get statements
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
