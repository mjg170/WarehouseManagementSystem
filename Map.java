
public class Map {
	//what floor the map depicts
	private int floor;
	//name of map
	private String name;
	//scale of map (to determine size)
	private int scale;

	//instantiation
	public Map(String myName, Integer myFloor, Integer myScale) {
		floor = myFloor;
		name = myName;
		scale = myScale;
	}
	
	//changes name of map
	public void changeName(String newName){
		name = newName;
	}
}
