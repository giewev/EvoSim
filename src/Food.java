import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Food extends GameObject{
	public Circle pellet;
	
	public Food(int x, int y, int size){
		this.pellet = new Circle(x, y, size);
		this.pellet.setStroke(Color.BLACK);
		this.pellet.setStrokeWidth(0.2);
		this.pellet.setFill(Color.PINK);
		this.pellet.setId("toDraw");
	}
	
	// Draws the Food to the screen if it is flagged for drawing
	// The Food is then unflagged
	public void draw(Group screen) {
		if(this.pellet.getId() != "toDraw") return;
		screen.getChildren().add(this.pellet);
		this.pellet.setId("Food");
	}
	
	public boolean clippingFood(){
		for(Food other : home.foodPellets){
			if(other == this) continue;
			
			double radSum = this.pellet.getRadius() + other.pellet.getRadius();
			
			double xDiff = this.pellet.getCenterX() - other.pellet.getCenterX();
			if(xDiff > radSum)  continue;
			double yDiff = this.pellet.getCenterY() - other.pellet.getCenterY();
			if(yDiff > radSum) continue;
			double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
			if(distance < radSum) return true;
		}
		
		return false;
	}
	
	public boolean clippingStone(){
		int left = (int)((this.pellet.getCenterX() - this.pellet.getRadius()) / home.tileWidth);
		int right = (int)Math.ceil(((this.pellet.getCenterX() + this.pellet.getRadius()) / home.tileWidth));
		int up = (int)((this.pellet.getCenterY() - this.pellet.getRadius()) / home.tileHeight);
		int down = (int)Math.ceil(((this.pellet.getCenterY() + this.pellet.getRadius()) / home.tileHeight));
		
		for(int i = left; i <= right; i++){
			for(int j = up; j <= down; j++){
				if(i < 0 || i >= home.width) continue;
				if(j < 0 || j >= home.height) continue;
				
				if(home.tiles[i][j] instanceof Stone){
					if(this.pellet.intersects(i * home.tileWidth, j * home.tileHeight, home.tileWidth, home.tileHeight)){
						this.pellet.setId("toDelete");
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean clippingWater(){
		int left = (int)((this.pellet.getCenterX() - this.pellet.getRadius()) / home.tileWidth);
		int right = (int)Math.ceil(((this.pellet.getCenterX() + this.pellet.getRadius()) / home.tileWidth));
		int up = (int)((this.pellet.getCenterY() - this.pellet.getRadius()) / home.tileHeight);
		int down = (int)Math.ceil(((this.pellet.getCenterY() + this.pellet.getRadius()) / home.tileHeight));
		
		for(int i = left; i <= right; i++){
			for(int j = up; j <= down; j++){
				if(i < 0 || i >= home.width) continue;
				if(j < 0 || j >= home.height) continue;
				
				if(home.tiles[i][j] instanceof Water){
					if(this.pellet.intersects(i * home.tileWidth, j * home.tileHeight, home.tileWidth, home.tileHeight)){
						this.pellet.setId("toDelete");
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean outsideMap(){
		return this.pellet.getCenterX() - this.pellet.getRadius() < MinX ||
				this.pellet.getCenterX() + this.pellet.getRadius() > MaxX ||
				this.pellet.getCenterY() - this.pellet.getRadius() < MinY ||
				this.pellet.getCenterY() + this.pellet.getRadius() > MaxY;
	}
	
	public boolean clippingAnimal(){
		for(Animal other : home.animals){
			double radSum = this.pellet.getRadius() + other.body.getRadius();
			
			double xDiff = this.pellet.getCenterX() - other.body.getCenterX();
			if(xDiff > radSum)  continue;
			double yDiff = this.pellet.getCenterY() - other.body.getCenterY();
			if(yDiff > radSum) continue;
			double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
			if(distance < radSum) return true;
		}
		
		return false;
	}
}
