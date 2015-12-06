import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Animal {
	public static int MinX;
	public static int MinY;
	public static int MaxX;
	public static int MaxY;
	
	public Circle body;
	public int targetX;
	public int targetY;
	public double speed;
	
	public Animal(int x, int y, int size, double speed){
		this.body = new Circle(x, y, size);
		this.body.setStroke(Color.BLACK);
		this.body.setStrokeWidth(0.2);
		this.body.setFill(Color.YELLOW);
		this.body.setId("toDraw");
		this.randomTarget();
		this.speed = speed;
	}
	
	public static void setGameCoordinates(int minX, int minY, int maxX, int maxY){
		MinX = minX;
		MinY = minY;
		MaxX = maxX;
		MaxY = maxY;
	}
	
	public void draw(Group screen) {
		if(this.body.getId() != "toDraw") return;
		screen.getChildren().add(this.body);
		this.body.setId("Animal");
	}
	
	public void tick(){
		double xDist = this.targetX - this.body.getCenterX();
		double yDist = this.targetY - this.body.getCenterY();
		double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
		
		if(dist < speed){
			this.body.setCenterX(targetX);
			this.body.setCenterY(targetY);
			this.randomTarget();
		}
		else{
			double angle = Math.atan2(yDist, xDist);
			double yDiff = Math.sin(angle) * speed;
			double xDiff = Math.cos(angle) * speed;
			this.body.setCenterX(this.body.getCenterX() + xDiff);
			this.body.setCenterY(this.body.getCenterY() + yDiff);
		}
	}
	
	public void randomTarget(){
		this.targetX = (int)(Math.random() * (MaxX - MinX) + MinX);
		this.targetY = (int)(Math.random() * (MaxY - MinY) + MinY);
	}
}
