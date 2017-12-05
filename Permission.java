//This class is in a employee and dictates what items that employee is restricted from altering
public class Permission {
	
	//list of restricted items
	  private String[] items;
	 
	  //instantiation
	  public Permission(){
		    items = new String[0]; 
	}
		  
	  //check to see if item is in restricted list
	  public Boolean checkPermission(String search){
		   
		  //loop through each item an look for term
		  for(String item : items){
		      
		      if(item == search){
		        return false;
		      }
		      
		    }
		    return true;
		    
	  }
		  
	  //use linked list?
	  //add new item to list
	  public void addRestrictedItem(String restricted){
		  //makes new array and adds old items in
		  	String[] newItems = new String[items.length+1]; 
		  	Integer i = 0;
		  	for(String item : items){
		  		newItems[i]=item;
		  		i++;
		  	}
		  	//adds new item
		  	newItems[i+1] = restricted;
		  	items = newItems;
	  }
		  
	  //removes item from restricted list
	  public void removeRestrictedItem(String unrestricted){
		  
		  //loops through and looks for item to remove
		  	String[] newItems = new String[items.length-1]; 
		  	Integer i = 0;
		  	for(String item : items){
		  		if(item != unrestricted){
		  			newItems[i]=item;
		  		}
		  		i++;
		  	}
		  	items = newItems;
	  }
}
