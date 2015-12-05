import javafx.scene.shape.*;
import javafx.scene.paint.*;

import javafx.scene.Group;

public class Dirt extends Terrain {
	
	public Dirt(int x, int y, int newWidth, int newHeight){
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
		thisSquare.setFill(Color.SADDLEBROWN);
		
		screen.getChildren().add(thisSquare);
	}
}
