import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Stone extends Terrain {
	public Stone(int x, int y, int size){
		this.X = x;
		this.Y = y;
		this.size = size;
	}
	
	@Override
	public void draw(Group screen) {
		Rectangle thisSquare = new Rectangle(this.X, this.Y, this.size, this.size);
		thisSquare.setStroke(Color.BLACK);
		thisSquare.setStrokeWidth(0.2);
		thisSquare.setFill(Color.GREY);
		
		screen.getChildren().add(thisSquare);
	}
}
