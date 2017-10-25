
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
}
