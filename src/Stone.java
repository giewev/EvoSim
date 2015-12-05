import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Stone extends Terrain {
	public Stone(int x, int y, int newWidth, int newHeight){
		this.X = x;
		this.Y = y;
		this.width = newWidth;
		this.height = newHeight;
	}
	
	@Override
	public void draw(Group screen) {
		Rectangle thisSquare = new Rectangle(this.X, this.Y, this.width, this.height);
		thisSquare.setStroke(Color.BLACK);
		thisSquare.setStrokeWidth(0.2);
		thisSquare.setFill(Color.GREY);
		
		screen.getChildren().add(thisSquare);
	}
}
