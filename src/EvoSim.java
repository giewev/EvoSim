import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EvoSim extends Application {
	final static int tileSize = 10;
	final static int mapWidth = 80;
	final static int mapHeight = 40;
	
	static String currentBrush = "Dirt";
	
	static Map game = new Map(mapWidth, mapHeight, tileSize);
	static Slider brushSize;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		Group root = new Group();
		Scene scene = new Scene(root, mapWidth * tileSize + 200, mapHeight * tileSize);
		Animal.setGameCoordinates(0, 0, mapWidth * tileSize, mapHeight * tileSize);
		Animal.setHome(game);
		
		// Defines how the GUI handles a mouse click or drag
		EventHandler<MouseEvent> mouseHandler = e -> {
			if(e.getButton() == MouseButton.PRIMARY){
				int xIndex = (int)(e.getX() / tileSize);
				int yIndex = (int)(e.getY() / tileSize);
				
				int brushWidth = (int)(brushSize.getValue() - 1) / 2;
				
				for(int x = xIndex - brushWidth; x <= xIndex + brushWidth; x++){
					for(int y = yIndex - brushWidth; y <= yIndex + brushWidth; y++){
						game.setTile(Terrain.construct(currentBrush, x * tileSize, y * tileSize, tileSize, tileSize));
					}
				}
			}
			else if(e.getButton() == MouseButton.SECONDARY){
				game.addFood(new Food((int)e.getX(), (int)e.getY(), 5));
			}
			else{
				game.addAnimal(new Animal((int)e.getX(), (int)e.getY(), 10, 5));
			}
		};
		
		scene.setOnMouseClicked(mouseHandler);
		scene.setOnMouseDragged(mouseHandler);
		
		drawControlMenu(root);
		
		// Defines the behavior on every game tick
		EventHandler<ActionEvent> tick = e -> {
			root.getChildren().removeIf(a -> {
				return a.getId()!= null && a.getId().equals("toDelete");
			});
			game.tick();
			game.draw(root);
		};
		
		final Timeline loop = new Timeline(new KeyFrame(Duration.millis(33), tick));
		primaryStage.setScene(scene);
		primaryStage.show();
		loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
	}
	
	public static void drawControlMenu(Group screen){
		// Initialize brush type buttons
		Rectangle dirtButton = new Rectangle(mapWidth * tileSize + 30, 15, 50, 50);
		dirtButton.setFill(Color.SADDLEBROWN);
		dirtButton.setId("controlMenu");
		dirtButton.setStroke(Color.RED);
		dirtButton.setStrokeWidth(2);
		
		Rectangle stoneButton = new Rectangle(mapWidth * tileSize + 90, 15, 50, 50);
		stoneButton.setFill(Color.GREY);
		stoneButton.setId("controlMenu");
		stoneButton.setStroke(Color.RED);
		stoneButton.setStrokeWidth(0);
		
		Rectangle waterButton = new Rectangle(mapWidth * tileSize + 30, 80, 50, 50);
		waterButton.setFill(Color.BLUE);
		waterButton.setId("controlMenu");
		waterButton.setStroke(Color.RED);
		waterButton.setStrokeWidth(0);
		
		Rectangle grassButton = new Rectangle(mapWidth * tileSize + 90, 80, 50, 50);
		grassButton.setFill(Color.GREEN);
		grassButton.setId("controlMenu");
		grassButton.setStroke(Color.RED);
		grassButton.setStrokeWidth(0);
		
		// Add click listeners to the brush type buttons
		dirtButton.setOnMousePressed(e -> {
			dirtButton.setStrokeWidth(2);
			stoneButton.setStrokeWidth(0);
			waterButton.setStrokeWidth(0);
			grassButton.setStrokeWidth(0);
			currentBrush = "Dirt";
		});
		
		stoneButton.setOnMousePressed(e -> {
			dirtButton.setStrokeWidth(0);
			stoneButton.setStrokeWidth(2);
			waterButton.setStrokeWidth(0);
			grassButton.setStrokeWidth(0);
			currentBrush = "Stone";
		});
		
		waterButton.setOnMousePressed(e -> {
			dirtButton.setStrokeWidth(0);
			stoneButton.setStrokeWidth(0);
			waterButton.setStrokeWidth(2);
			grassButton.setStrokeWidth(0);
			currentBrush = "Water";
		});

		grassButton.setOnMousePressed(e -> {
			dirtButton.setStrokeWidth(0);
			stoneButton.setStrokeWidth(0);
			waterButton.setStrokeWidth(0);
			grassButton.setStrokeWidth(2);
			currentBrush = "Grass";
		});
		
		// Initialize the reset button and listener
		Button resetButton = new Button();
		resetButton.setLayoutX(mapWidth * tileSize + 40);
		resetButton.setLayoutY(160);
		resetButton.setText("Reset Map");
		resetButton.setId("controlMenu");
		resetButton.setOnMouseClicked(e -> {
			game.reset(currentBrush);
		});
		
		// Initialize the brush size slider
		Label brushSizeLabel = new Label();
		brushSizeLabel.setText("Brush Size");
		brushSizeLabel.setLayoutX(mapWidth * tileSize + 60);
		brushSizeLabel.setLayoutY(220);
		
		Slider brushSizeSlider = new Slider();
		brushSizeSlider.setLayoutX(mapWidth * tileSize + 20);
		brushSizeSlider.setLayoutY(240);
		brushSizeSlider.setShowTickLabels(true);
		brushSizeSlider.setShowTickMarks(true);
		brushSizeSlider.setMajorTickUnit(2);
		brushSizeSlider.setMinorTickCount(0);
		brushSizeSlider.setSnapToTicks(true);
		brushSizeSlider.setMax(9);
		brushSizeSlider.setMin(1);
		brushSize = brushSizeSlider;
		
		// Add our menu items to the screen
		screen.getChildren().add(dirtButton);
		screen.getChildren().add(stoneButton);
		screen.getChildren().add(waterButton);
		screen.getChildren().add(grassButton);
		screen.getChildren().add(resetButton);
		screen.getChildren().add(brushSizeLabel);
		screen.getChildren().add(brushSizeSlider);
	}
}