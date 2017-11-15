import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//Highest type of user
public class Administrator extends Manager {
	
	public Administrator(String newPassword, String newName, int newWage, Boolean newStatus) {
		super(newPassword, newName, newWage, newStatus);
		// TODO Auto-generated constructor stub
	}

	//deletes log entry
	public void deleteLog(LinkedList<String> logs, String searchLog){
		LinkedList<String> tempList = new LinkedList<String>();
		Boolean isSucess = false;
		for(String entry : logs){
			if(searchLog != entry){
				tempList.add(entry);
			}
			else{
				isSucess = true;
			}
		}
		
		if(isSucess){
			logs = tempList;
		}
		else{
			JOptionPane.showMessageDialog(new JFrame("error"), "Item not found.");
		}
	}
	
	//deletes a manager
	public void deleteManager(LinkedList<Manager> managerList, Manager searchManager) {
		LinkedList<Manager> tempList = new LinkedList<Manager>();
		Boolean isSucess = false;
		for(Manager entry : managerList){
			if(searchManager != entry){
				tempList.add(entry);
			}
			else{
				isSucess = true;
			}
		}
		
		if(isSucess){
			managerList = tempList;
		}
		else{
			JOptionPane.showMessageDialog(new JFrame("error"), "Item not found.");
		}
	}
	
	//adds a manager to list of managers
	public LinkedList<Manager> addMaanger(LinkedList<Manager> managerList, Manager addManager){
		managerList.add(addManager);
		return managerList;
	}
	
	//changes things abotu warehouse
	public void manageWarehouse(){
	
	}
}
