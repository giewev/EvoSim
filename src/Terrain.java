import javafx.scene.Group;

public abstract class Terrain {
	public int X;
	public int Y;
	public int width;
	public int height;
	
	public abstract void draw(Group screen);
	
	public static Terrain construct(String type, int x, int y, int newWidth, int newHeight){
		if(type.equals("Dirt")) return new Dirt(x, y, newWidth, newHeight);
		if(type.equals("Stone")) return new Stone(x, y, newWidth, newHeight);
		if(type.equals("Water")) return new Water(x, y, newWidth, newHeight);
		
		return null;
	}
}
