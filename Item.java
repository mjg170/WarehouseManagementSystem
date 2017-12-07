
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.*;

//item in storage
public class Item {

	//name of item

	private String name;
	//number of the item stored
	private int quantity;
	//type of item stored
	private String category;

	//Area item is in
	private Area myArea;
	//y coordinate on map
	private int yLoc;
	//x coordinate on map
	private int xLoc;
	//amount that will trigger an alert
	private int alertVal;

	//instantiation
	public Item(String myName, Integer myQuantity, String myCategory, Integer myWarningNum, Double myPrice, Double myWeight, Integer myBarCode, Area setArea, int myYLoc, int myXLoc, int myAlertVal){

		name = myName;
		quantity = myQuantity;
		category = myCategory;
		//number of items left when too low and a warning needs to be sent
		Integer warningNum = myWarningNum;
		//price of item
		Double price = myPrice;
		//weight of item
		Double weight = myWeight;
		//bar code of item
		Integer barCode = myBarCode;

		myArea = setArea;
		yLoc = myYLoc;
		xLoc = myXLoc;
		alertVal = myAlertVal;
	}

	//item if little information input
	public Item(String myName, Integer myQuantity){
		name = myName;
		quantity = myQuantity;
	}

	//gets name
	public String getName(){
		return name;
	}
	//gets amount of item
	public int getQuantity(){
		return quantity;
	}
	//sets area of item
	public void setArea(Area newArea){
		myArea = newArea;
	}
	//changes y value of location
	public void setYLoc(int newY){
		yLoc = newY;
	}
	//changes x value of location
	public void setXLoc(int newX){
		xLoc = newX;
	}
	//returns x value of location
	public int getXLoc(){
		return xLoc;
	}
	//returns y value of location
	public int getYLoc(){
		return yLoc;
	}

	//change amount of item stored
	public int modifyQuantity(int quantity){
		if(quantity + this.quantity < 0){
			return -1;
		}
		else{

			if(this.quantity < alertVal){
				JOptionPane.showMessageDialog(new JFrame("error"), "Amount of item low.  More should be added soon.");
			}
			return this.quantity += quantity;
		}
	}

	//print info on item
	public void exportInfo(){
		PrintWriter writer;
		try {
			writer = new PrintWriter(name+"-data.txt", "UTF-8");
			writer.println("Name: " + name);
			writer.println("Category: " + category);
			writer.println("Quantity: " + quantity);
			writer.println("Area: " + myArea.name);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
