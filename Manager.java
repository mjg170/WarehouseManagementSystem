import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


//A type of user that is higher up
public class Manager extends User {
	  
	//makes new instance of manager
	public Manager(String newPassword, String newName, int newWage, Boolean newStatus){
	  	password = newPassword;
	  	name = newName;
	  	wage = newWage;
	  	status = newStatus;
	}
	
	/*Not currently needed based on implementation
	public void veiwLog(){
		  
	}*/
		  
	public void manageArea(){
		  
	}
		  
	//removes item
	public void deleteItem(LinkedList<Item> items, Item searchItem){
		LinkedList<Item> tempList = new LinkedList<Item>();
		Boolean isSucess = false;
		for(Item entry : items){
			if(searchItem != entry){
				tempList.add(entry);
			}
			else{
				isSucess = true;
			}
		}
		
		if(isSucess){
			items = tempList;
		}
		else{
			//show error
		}	    
	}
	
	//deletes a user
	public void deleteEmployee(LinkedList<Employee> userList, Employee searchEmployee) {
		LinkedList<Employee> tempList = new LinkedList<Employee>();
		Boolean isSucess = false;
		//goes through and looks for desired employee
		for(Employee entry : userList){
			if(searchEmployee != entry){
				tempList.add(entry);
			}
			else{
				isSucess = true;
			}
		}
		
		if(isSucess){
			userList = tempList;
		}
		else{
			JOptionPane.showMessageDialog(new JFrame("error"), "Item not found.");
		}
	}
	
	//creates new employee
	public LinkedList<Employee> addEmployee(LinkedList<Employee> employeeList, Employee addEmployee){
		employeeList.add(addEmployee);
		return employeeList;
	}
	
	//changes wage of employee
	public Employee changeWage(Employee target, int newWage){
		target.wage = newWage;
		return target;
	}
	
	//puts item in list of items
	public void addItem(LinkedList<Item> items, Item addItem){
		items.add(addItem);
	}
	
	public void exportData(){
		PrintWriter writer;
		try {
			writer = new PrintWriter(name+"'s-data.txt", "UTF-8");
			writer.println("Name: " + name);
			writer.println("Wage: " + wage);
			writer.println("Status: " + status);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
