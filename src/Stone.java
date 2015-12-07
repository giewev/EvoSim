import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Stone extends Terrain {
	public Stone(int x, int y, int newWidth, int newHeight){
		this.tile = new Rectangle(x, y, newWidth, newHeight);
		this.tile.setStroke(Color.BLACK);
		this.tile.setStrokeWidth(0.2);
		this.tile.setFill(Color.GREY);
		this.tile.setId("toDraw");
	}
	
	// Draws the Stone to the screen if it is flagged for drawing
	// The Stone is then unflagged and moved to the back
	@Override
	public void draw(Group screen) {
		if(this.tile.getId() != "toDraw") return;
		screen.getChildren().add(this.tile);
		this.tile.toBack();
		this.tile.setId("Tile");
	}
}
