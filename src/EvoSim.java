import javafx.animation.Timeline;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EvoSim extends Application {
	final static int tileSize = 10;
	final static int mapWidth = 60;
	final static int mapHeight = 40;
	
	Terrain[][] map = new Terrain[mapWidth][mapHeight];
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		Group root = new Group();
		Scene scene = new Scene(root, mapWidth * tileSize, mapHeight * tileSize);
		
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				map[i][j] = new Stone(i * tileSize, j * tileSize, tileSize);
			}
		}
		
		scene.setOnMouseClicked(e -> {
			int xIndex = (int)(e.getX() / tileSize);
			int yIndex = (int)(e.getY() / tileSize);
			for(int x = xIndex - 1; x <= xIndex + 1; x++){
				for(int y = yIndex - 1; y <= yIndex + 1; y++){
					if(x < 0 || x > mapWidth || y < 0 || y > mapHeight) continue;
					
					if(e.getButton() == MouseButton.PRIMARY){
						this.map[x][y] = new Dirt(x * tileSize, y * tileSize, tileSize);
					}
					else if(e.getButton() == MouseButton.SECONDARY){
						this.map[x][y] = new Stone(x * tileSize, y * tileSize, tileSize);
					}
				}
			}
		});
		
		scene.setOnMouseDragged(e -> {
			int xIndex = (int)(e.getX() / tileSize);
			int yIndex = (int)(e.getY() / tileSize);
			for(int x = xIndex - 1; x <= xIndex + 1; x++){
				for(int y = yIndex - 1; y <= yIndex + 1; y++){
					if(x < 0 || x > mapWidth || y < 0 || y > mapHeight) continue;
					
					if(e.isPrimaryButtonDown()){
						this.map[x][y] = new Dirt(x * tileSize, y * tileSize, tileSize);
					}
					else if(e.isSecondaryButtonDown()){
						this.map[x][y] = new Stone(x * tileSize, y * tileSize, tileSize);
					}
				}
			}
		});
		
		
		EventHandler<ActionEvent> handler = e -> {
			root.getChildren().clear();
			
			for(int i = 0; i < mapWidth; i++){
				for(int j = 0; j < mapHeight; j++){
					this.map[i][j].draw(root);
				}
			}
		};
		
		final Timeline loop = new Timeline(new KeyFrame(Duration.millis(17), handler));
		primaryStage.setScene(scene);
		primaryStage.show();
		loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
	}
}