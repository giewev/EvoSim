import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Food {
	public static int MinX;
	public static int MinY;
	public static int MaxX;
	public static int MaxY;

	public static Map home;
	
	public Circle pellet;
	
	public Food(int x, int y, int size){
		this.pellet = new Circle(x, y, size);
		this.pellet.setStroke(Color.BLACK);
		this.pellet.setStrokeWidth(0.2);
		this.pellet.setFill(Color.PINK);
		this.pellet.setId("toDraw");
	}
	
	public static void setGameCoordinates(int minX, int minY, int maxX, int maxY){
		MinX = minX;
		MinY = minY;
		MaxX = maxX;
		MaxY = maxY;
	}
	
	public static void setHome(Map newHome){
		home = newHome;
	}
	
	// Draws the Food to the screen if it is flagged for drawing
	// The Food is then unflagged
	public void draw(Group screen) {
		if(this.pellet.getId() != "toDraw") return;
		screen.getChildren().add(this.pellet);
		this.pellet.setId("Food");
	}
	
	public boolean clippingOtherFood(){
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
}
