import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grass extends Terrain {
	public Grass(int x, int y, int newWidth, int newHeight){
		this.tile = new Rectangle(x, y, newWidth, newHeight);
		this.tile.setStroke(Color.BLACK);
		this.tile.setStrokeWidth(0.2);
		this.tile.setFill(Color.GREEN);
		this.tile.setId("toDraw");
	}
	
	@Override
	public void draw(Group screen) {
		if(this.tile.getId() != "toDraw") return;
		screen.getChildren().add(this.tile);
		this.tile.setId("Tile");
	}
}
