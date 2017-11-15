import java.util.LinkedList;

//abstract class containing all user types
public abstract class User {
	//password to get into account
	  String password;
	  //name of user
	  String name;
	  //how much money user making per hour
	  int wage;
	  //?
	  boolean status;
	  
	  //change things about an item
	  abstract void deleteItem(LinkedList<Item> items, Item searchItem);
	  abstract void addItem(LinkedList<Item> items, Item addItem);
	  
	  //get methods
	  abstract String getPassword();
	  abstract String getName();
	  abstract int getWage();
	  abstract boolean getStatus();
	  
	  //set methods
	  abstract void setPassword(String newPassword);
	  abstract void setName(String newName);
	  abstract void setWage(int newWage);
	  abstract void setStatus(boolean newStatus);
	  
	  //exports data on user to a file
	  abstract void exportData();
}
