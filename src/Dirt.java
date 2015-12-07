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
	
	// Draws the Dirt to the screen if it is flagged for drawing
	// The Dirt is then unflagged and moved to the back
	@Override
	public void draw(Group screen) {
		if(this.tile.getId() != "toDraw") return;
		screen.getChildren().add(this.tile);
		this.tile.toBack();
		this.tile.setId("Tile");
	}
}
