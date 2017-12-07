
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


//a standard employee
public class Employee extends User {
	
	//what the employee is not allowed to access
	 private Permission myPermission;

	 //list of time spent working
	 private LinkedList<Date[]> workTime;
	 //array to keep track of when check in and out today, will be added to worktime after checkout
	 private Date[] temp = new Date[2];

	
	 //instantiation
	  public Employee(String newPassword, String newName, int newWage, Boolean newStatus){
		    myPermission = new Permission();
		  	password = newPassword;
		  	name = newName;
		  	wage = newWage;
		  	status = newStatus;
	  }
	  

	//removes item
		public void deleteItem(LinkedList<Item> items, Item searchItem){
			//list of everything except deleted item
			LinkedList<Item> tempList = new LinkedList<Item>();
			Boolean isSucess = false;
			//looks for item in list
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
				JOptionPane.showMessageDialog(new JFrame("error"), "Item not found.");
			}	    
		}
		
		//adds new item to list
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
		
		//records check in time
		public void checkIn(){
			temp[0] = new Date();
		}
	  
		//records check out time and adds todays times to list
		public void checkOut(){
			temp[1] = new Date();
			workTime.add(temp);
		}
		
		//calculates time worked between inputs
		public int hoursWorked(int begin, int end){
			int time = 0;
			for(Date[] in: workTime){
				if(in[0].getTime() > begin && in[1].getTime() < end){
					time += in[0].getTime()-in[1].getTime();
				}
			}
				
			return (int)((time) * 0.000000277778);
		}
		
		public int calcPay(int begin, int end){
			return wage*hoursWorked(begin, end);
		}

	  //get password
	  public String getPassword(){
		  return password;
	  }
	  //gets name
	  public String getName(){
		  return name;
	  }
	  //gets wage
	  public int getWage(){
		  return wage;
	  }
	  //gets status
	  public boolean getStatus(){
		  return status;
	  }
	  
	  //set passwoord
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
