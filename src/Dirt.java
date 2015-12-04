import javafx.scene.shape.*;
import javafx.scene.paint.*;

import javafx.scene.Group;

public class Dirt extends Terrain {
	
	public Dirt(int x, int y, int size){
		this.X = x;
		this.Y = y;
		this.size = size;
	}
	
	@Override
	public void draw(Group screen) {
		Rectangle thisSquare = new Rectangle(this.X, this.Y, this.size, this.size);
		thisSquare.setStroke(Color.BLACK);
		thisSquare.setStrokeWidth(0.2);
		thisSquare.setFill(Color.BROWN);
		
		screen.getChildren().add(thisSquare);
	}

}
