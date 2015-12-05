import javafx.animation.Timeline;

import javafx.scene.shape.Rectangle;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EvoSim extends Application {
	final static int tileSize = 10;
	final static int mapWidth = 60;
	final static int mapHeight = 40;
	
	static String currentBrush = "Dirt";
	
	Map tiles = new Map(mapWidth, mapHeight, tileSize);
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		Group root = new Group();
		Scene scene = new Scene(root, mapWidth * tileSize + 200, mapHeight * tileSize);
		
		// Defines how the GUI handles a mouse click or drag
		EventHandler<MouseEvent> mouseHandler = e -> {
			int xIndex = (int)(e.getX() / tileSize);
			int yIndex = (int)(e.getY() / tileSize);
			
			for(int x = xIndex - 1; x <= xIndex + 1; x++){
				for(int y = yIndex - 1; y <= yIndex + 1; y++){
					tiles.setTile(Terrain.construct(currentBrush, x * tileSize, y * tileSize, tileSize, tileSize));
				}
			}
		};
		
		scene.setOnMouseClicked(mouseHandler);
		scene.setOnMouseDragged(mouseHandler);
		
		Rectangle dirtButton = new Rectangle(mapWidth * tileSize + 30, 15, 50, 50);
		dirtButton.setFill(Color.SADDLEBROWN);
		dirtButton.setOnMousePressed(e -> {
			currentBrush = "Dirt";
		});
		
		Rectangle stoneButton = new Rectangle(mapWidth * tileSize + 90, 15, 50, 50);
		stoneButton.setFill(Color.GREY);
		stoneButton.setOnMousePressed(e -> {
			currentBrush = "Stone";
		});
		
		Rectangle waterButton = new Rectangle(mapWidth * tileSize + 30, 80, 50, 50);
		waterButton.setFill(Color.BLUE);
		waterButton.setOnMousePressed(e -> {
			currentBrush = "Water";
		});
		
		Rectangle grassButton = new Rectangle(mapWidth * tileSize + 90, 80, 50, 50);
		grassButton.setFill(Color.GREEN);
		grassButton.setOnMousePressed(e -> {
			currentBrush = "Grass";
		});
		
		Button resetButton = new Button();
		resetButton.setLayoutX(mapWidth * tileSize + 40);
		resetButton.setLayoutY(160);
		resetButton.setText("Reset Map");
		resetButton.setOnMouseClicked(e -> {
			tiles.resetStone();
		});
		
		EventHandler<ActionEvent> tick = e -> {
			root.getChildren().removeIf(a -> {
				return a.getId()!= null && a.getId().equals("toDelete");
			});
			tiles.draw(root);
		};
		
		root.getChildren().add(dirtButton);
		root.getChildren().add(stoneButton);
		root.getChildren().add(waterButton);
		root.getChildren().add(grassButton);
		root.getChildren().add(resetButton);
		
		final Timeline loop = new Timeline(new KeyFrame(Duration.millis(17), tick));
		primaryStage.setScene(scene);
		primaryStage.show();
		loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
	}
}