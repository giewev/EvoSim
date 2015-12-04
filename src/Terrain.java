import javafx.scene.Group;

public abstract class Terrain {
	public int X;
	public int Y;
	public int size;
	
	public abstract void draw(Group screen);
}
