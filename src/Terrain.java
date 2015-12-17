import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public abstract class Terrain extends GameObject {
	public Rectangle tile;
	
	public abstract void draw(Group screen);
	
	// Builds a new Terrain object of the class named in the "type" parameter
	public static Terrain construct(String type, int x, int y, int newWidth, int newHeight){
		if(type.equals("Dirt")) return new Dirt(x, y, newWidth, newHeight);
		if(type.equals("Stone")) return new Stone(x, y, newWidth, newHeight);
		if(type.equals("Water")) return new Water(x, y, newWidth, newHeight);
		if(type.equals("Grass")) return new Grass(x, y, newWidth, newHeight);
		
		return null;
	}
	
	public boolean clippingStone(){
		return false;
	}
	
	public boolean clippingWater(){
		return false;
	}
	
	public boolean outsideMap(){
		return false;
	}
	
	public boolean clippingAnimal(){
		for(Animal animal : home.animals){
			if(animal.body.intersects(this.tile.getX(), this.tile.getY(), home.tileWidth, home.tileHeight)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean clippingFood(){
		for(Food fruit : home.foodPellets){
			if(fruit.pellet.intersects(this.tile.getX(), this.tile.getY(), home.tileWidth, home.tileHeight)){
				return true;
			}
		}
		
		return false;
	}
}
