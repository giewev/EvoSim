import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Food {
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
}
