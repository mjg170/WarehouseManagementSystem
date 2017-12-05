import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.*;


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
		Integer warningNum = myWarningNum;
		Double price = myPrice;
		Double weight = myWeight;
		Integer barCode = myBarCode;
		myArea = setArea;
		yLoc = myYLoc;
		xLoc = myXLoc;
		alertVal = myAlertVal;
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

	public void setArea(Area newArea){
		myArea = newArea;
	}

	public void setYLoc(int newY){
		yLoc = newY;
	}

	public void setXLoc(int newX){
		xLoc = newX;
	}

	public int getXLoc(){
		return xLoc;
	}

	public int getYLoc(){
		return yLoc;
	}

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
