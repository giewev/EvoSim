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
	
	public void draw(Group screen) {
		if(this.pellet.getId() != "toDraw") return;
		screen.getChildren().add(this.pellet);
		this.pellet.setId("Food");
	}
}
