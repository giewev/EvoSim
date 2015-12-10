
public abstract class GameObject {
	// Borders of the map the objects are in
	public static int MinX;
	public static int MinY;
	public static int MaxX;
	public static int MaxY;

	public static Map home;
	
	public static void setGameCoordinates(int minX, int minY, int maxX, int maxY){
		MinX = minX;
		MinY = minY;
		MaxX = maxX;
		MaxY = maxY;
	}
	
	public static void setHome(Map newHome){
		home = newHome;
	}
	
	public abstract boolean clippingFood();
	public abstract boolean clippingAnimal();
	public abstract boolean clippingStone();
	public abstract boolean outsideMap();
}
