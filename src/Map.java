import java.util.ArrayList;

import javafx.scene.Group;

public class Map {
	public Terrain[][] tiles;
	public ArrayList<Food> foodPellets = new ArrayList<Food>();
	public ArrayList<Animal> animals = new ArrayList<Animal>();
	public int width;
	public int height;
	public int tileWidth;
	public int tileHeight;
	
	public Map(int newWidth, int newHeight, int size){
		this.width = newWidth;
		this.height = newHeight;
		this.tileHeight = size;
		this.tileWidth = size;
		this.tiles = new Terrain[this.width][this.height];
		this.reset("Stone");
	}
	
	// Moves the animals according to their internal logic
	public void tick(){
		for(Animal animal : animals){
			animal.tick();
		}
		
		this.animals.removeIf(e -> {
			return e.body.getId().equals("toDelete");
		});
		
		this.foodPellets.removeIf(e -> {
			return e.pellet.getId().equals("toDelete") || 
					e.clippingStone();
		});
	}
	
	// Draws all of the game items if they have not already been drawn
	public void draw(Group screen){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				this.tiles[i][j].draw(screen);
			}
		}
		
		for(Food pellet : this.foodPellets){
			pellet.draw(screen);
		}
		
		for(Animal animal : this.animals){
			animal.draw(screen);
		}
	}
	
	// Moves the map tiles to the back
	public void layer(){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				this.tiles[i][j].tile.toBack();
			}
		}
	}
	
	// Sets all tiles to the value of the current brush
	public void reset(String brush){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				this.setTile(Terrain.construct(brush, i * this.tileWidth, j * this.tileHeight, this.tileWidth, this.tileHeight));
			}
		}
		
		for(Food pellet : this.foodPellets){
			pellet.pellet.setId("toDelete");
		}
		this.foodPellets.clear();
		
		for(Animal animal : this.animals){
			animal.body.setId("toDelete");
		}
		this.animals.clear();
	}
	
	// Places this Terrain object into this map
	// Flags the previous items for deletion
	public void setTile(Terrain newTile){
		int x = (int)newTile.tile.getX() / this.tileWidth;
		int y = (int)newTile.tile.getY() / this.tileHeight;
		
		if(x >= this.width || x < 0) return;
		if(y >= this.height || y < 0) return;
		
		if(this.tiles[x][y] != null) this.tiles[x][y].tile.setId("toDelete");
		this.tiles[x][y] = newTile;
	}
	
	// Adds food to the map
	public void addFood(Food newFood){
		if(newFood.clippingFood()) return;
		if(newFood.clippingStone()) return;
		if(newFood.outsideMap()) return;
		
		this.foodPellets.add(newFood);
	}
	
	// Adds an animal to the map
	public void addAnimal(Animal newAnimal){
		if(newAnimal.clippingStone()) return;
		if(newAnimal.outsideMap()) return;
		if(newAnimal.clippingAnimal()) return;
		
		this.animals.add(newAnimal);
	}
}
