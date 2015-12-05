import javafx.scene.Group;

public class Map {
	public Terrain[][] tiles;
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
		this.resetStone();
	}
	
	public void draw(Group screen){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				this.tiles[i][j].draw(screen);
			}
		}
	}
	
	public void resetStone(){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				this.setTile(new Stone(i * this.tileWidth, j * this.tileHeight, this.tileWidth, this.tileHeight));
			}
		}
	}
	
	public void setTile(Terrain newTile){
		int x = (int)newTile.tile.getX() / this.tileWidth;
		int y = (int)newTile.tile.getY() / this.tileHeight;
		
		if(x >= this.width || x < 0) return;
		if(y >= this.height || y < 0) return;
		
		if(this.tiles[x][y] != null) this.tiles[x][y].tile.setId("toDelete");
		this.tiles[x][y] = newTile;
	}
}
