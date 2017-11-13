//represents a warehouse
public class Area {
	//map of the area
	private Map myMap;
	//name of the area
	public String name;

	//instantiation
	public Area(String myName){
		name = myName;
	}
	
	//changes map of area
	public void addMap(String myName, Integer myFloor, Integer myScale){
		myMap = new Map(myName, myFloor, myScale);
	}
	
	//get method
	 public String getName(){
		  return name;
	  }
	 
	 //set method
	  public void setName(String newName){
		  name = newName;
	  }
}