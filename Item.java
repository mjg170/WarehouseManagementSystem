
public class Item {

    //name of item
	private String name;
	//number of the item stored
	private int quantity;
	//type of item stored
	private String category;
	
    //instantiation
	public Item(String myName, Integer myQuantity, String myCategory, Integer myWarningNum, Double myPrice, Double myWeight, Integer myBarCode){
		name = myName;
		quantity = myQuantity;
		category = myCategory;
		Integer warningNum = myWarningNum;
		Double price = myPrice;
		Double weight = myWeight;
		Integer barCode = myBarCode;
	}
	
	public Item(String myName, Integer myQuantity){
		name = myName;
		quantity = myQuantity;
	}
	
	public String getName(){
		return name;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public int modifyQuantity(int quantity){
		if(quantity + this.quantity < 0){
			return -1;
		}
		else{
			return this.quantity += quantity; 
		}
	}
}
