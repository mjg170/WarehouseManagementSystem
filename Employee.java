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
	 private LinkedList<Date[]> workTime;
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
		
		public void checkIn(){
			temp[0] = new Date();
		}
	  
		public void checkOut(){
			temp[1] = new Date();
			workTime.add(temp);
		}
		

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
