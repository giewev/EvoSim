import javafx.scene.shape.*;
import javafx.scene.paint.*;

import javafx.scene.Group;

public class Dirt extends Terrain {
	public Dirt(int x, int y, int newWidth, int newHeight){
		this.tile = new Rectangle(x, y, newWidth, newHeight);
		this.tile.setStroke(Color.BLACK);
		this.tile.setStrokeWidth(0.2);
		this.tile.setFill(Color.SADDLEBROWN);
		this.tile.setId("toDraw");
	}
	
	@Override
	public void draw(Group screen) {
		if(this.tile.getId() != "toDraw") return;
		screen.getChildren().add(this.tile);
		this.tile.setId("Tile");
	}
}
