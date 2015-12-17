import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Animal extends GameObject{
	public Circle body;
	public double speed;
	public double angle;
	public double energy;
	public double aquaticness;
	
	public Animal(int x, int y, double size, double speed){
		this.body = new Circle(x, y, size);
		this.body.setStroke(Color.BLACK);
		this.body.setStrokeWidth(0.2);
		this.body.setFill(Color.YELLOW);
		this.body.setId("toDraw");
		this.speed = speed;
		this.angle = Math.random() * Math.PI * 2;
		this.energy = 50;
		this.aquaticness = Math.random();
	}
	
	// Draws this animal to the screen if it is flagged for drawing
	// The animal will then be unflagged for drawing
	public void draw(Group screen) {
		if(this.body.getId() != "toDraw") return;
		screen.getChildren().add(this.body);
		this.body.setId("Animal");
	}
	
	// Steers and moves the animal
	// Will turn to either side if it can not continue on its path
	// If it can not move in any direction safely, it will hold in place
	public void tick(){
		this.steer();
		this.randomSteer(Math.PI / 16);
		this.normalizeAngle();
		
		double oldX = this.body.getCenterX();
		double oldY = this.body.getCenterY();
		
		double yDiff = Math.sin(this.angle) * speed;
		double xDiff = Math.cos(this.angle) * speed;
		this.body.setCenterX(this.body.getCenterX() + xDiff);
		this.body.setCenterY(this.body.getCenterY() + yDiff);
		
		if(this.clippingStone() || this.outsideMap() || this.clippingAnimal()){
			this.body.setCenterX(oldX);
			this.body.setCenterY(oldY);
		}
		
		this.eatFood();
		
		this.energy -= (this.speed / 100.0) + (this.body.getRadius() / 100.0);
		if(this.energy <= 0){
			this.body.setId("toDelete");
		}
	}
	
	public void hunt(){
		final double maximumDistance = this.speed * 25;
		Animal bestAnimalTarget = null;
		double animalDistance = maximumDistance;
		Food bestFoodTarget = null;
		double foodDistance = maximumDistance;
		
		for(Animal other : home.animals){
			// Don't hunt animals that we can't eat
			if(other.body.getRadius() > this.body.getRadius() * 0.60){
				continue;
			}
			
			double dist = this.distanceTo(other);
			if(dist < animalDistance){
				animalDistance = dist;
				bestAnimalTarget = other;
			}
		}
		
		for(Food food : home.foodPellets){
			double dist = this.distanceTo(food);
			if(dist < foodDistance){
				animalDistance = dist;
				bestFoodTarget = food;
			}
		}
	}
	
	// Steers the animal left or right if it can not continue straight
	// Tries to turn as little as possible without hitting an obstacle
	// If there is no safe path, no turn will be made
	public void steer(){
		double oldX = this.body.getCenterX();
		double oldY = this.body.getCenterY();
		
		for(double i = 0; i <= Math.PI; i += Math.PI / 16){
			boolean leftSafe = false;
			boolean rightSafe = false;
			
			double leftAngle = this.angle - i;
			double yDiffLeft = Math.sin(leftAngle) * speed;
			double xDiffLeft = Math.cos(leftAngle) * speed;
			this.body.setCenterX(oldX + xDiffLeft);
			this.body.setCenterY(oldY + yDiffLeft);
			
			leftSafe = !(this.clippingStone() || this.outsideMap() || this.clippingAnimal());
			this.body.setCenterX(oldX);
			this.body.setCenterY(oldY);
			
			double rightAngle = this.angle + i;
			double yDiffRight = Math.sin(rightAngle) * speed;
			double xDiffRight = Math.cos(rightAngle) * speed;
			this.body.setCenterX(oldX + xDiffRight);
			this.body.setCenterY(oldY + yDiffRight);
			
			rightSafe = !(this.clippingStone() || this.outsideMap() || this.clippingAnimal());
			this.body.setCenterX(oldX);
			this.body.setCenterY(oldY);
			
			if(leftSafe || rightSafe){
				if(leftSafe && rightSafe){
					if(Math.random() < 0.5){
						this.angle = leftAngle;
					}
					else{
						this.angle = rightAngle;
					}
				}
				else if(leftSafe){
					this.angle = leftAngle;
				}
				else if(rightSafe){
					this.angle = rightAngle;
				}
				
				return;
			}
		}
		
		this.angle %= Math.PI * 2;
	}
	
	// Gives some random variation to the Animal's direction
	public void randomSteer(double max){
		this.angle += (Math.random() * max * 2) - max;
	}
	
	// Converts the angle to a value in [0, 2PI)
	public void normalizeAngle(){
		this.angle %= Math.PI * 2;
	}
	
	// Returns true if this animal is intersecting or inside of stone
	public boolean clippingStone(){
		int left = (int)((this.body.getCenterX() - this.body.getRadius()) / home.tileWidth);
		int right = (int)Math.ceil(((this.body.getCenterX() + this.body.getRadius()) / home.tileWidth));
		int up = (int)((this.body.getCenterY() - this.body.getRadius()) / home.tileHeight);
		int down = (int)Math.ceil(((this.body.getCenterY() + this.body.getRadius()) / home.tileHeight));
		
		for(int i = left; i <= right; i++){
			for(int j = up; j <= down; j++){
				if(i < 0 || i >= home.width) continue;
				if(j < 0 || j >= home.height) continue;
				
				if(home.tiles[i][j] instanceof Stone){
					if(this.body.intersects(i * home.tileWidth, j * home.tileHeight, home.tileWidth, home.tileHeight)){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	// Returns true if this animal has part of its body outside the map
	public boolean outsideMap(){
		return this.body.getCenterX() - this.body.getRadius() < MinX ||
				this.body.getCenterX() + this.body.getRadius() > MaxX ||
				this.body.getCenterY() - this.body.getRadius() < MinY ||
				this.body.getCenterY() + this.body.getRadius() > MaxY;
	}
	
	// Returns true if this animal has part of its body overlapping with part of another animal
	public boolean clippingAnimal(){
		for(Animal other : home.animals){
			if(other == this) continue;
			
			double radSum = this.body.getRadius() + other.body.getRadius();
			
			double xDiff = this.body.getCenterX() - other.body.getCenterX();
			if(xDiff > radSum)  continue;
			double yDiff = this.body.getCenterY() - other.body.getCenterY();
			if(yDiff > radSum) continue;
			double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
			if(distance < radSum) return true;
		}
		
		return false;
	}
	
	// Eats any food that this animal is enveloping
	public void eatFood(){
		for(Food fruit : home.foodPellets){
			double xDiff = this.body.getCenterX() - fruit.pellet.getCenterX();
			double yDiff = this.body.getCenterY() - fruit.pellet.getCenterY();
			double dist = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
			
			if(dist < this.body.getRadius()){
				this.energy += 10;
				fruit.pellet.setId("toDelete");
			}
		}
	}
	
	// Returns true if this animal is overlapping with any food pellets
	public boolean clippingFood(){
		for(Food fruit : home.foodPellets){
			double xDiff = this.body.getCenterX() - fruit.pellet.getCenterX();
			double yDiff = this.body.getCenterY() - fruit.pellet.getCenterY();
			double dist = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
			
			if(dist < this.body.getRadius() + fruit.pellet.getRadius()){
				return true;
			}
		}
		
		return false;
	}
	
	// Creates a new animal with slightly mutated size, speed, and aquaticness
	public Animal mutatedOffspring(){
		double newSize = this.body.getRadius();
		double sizeMutation = 2 * (Math.random() - 0.5);
		newSize -= sizeMutation;
		if(newSize < 1) newSize = 1;
		
		double newSpeed = this.speed;
		double speedMutation = 2 * (Math.random() - 0.5);
		newSpeed -= speedMutation;
		if(newSpeed < 1) newSpeed = 1;
		
		double newAquaticness = this.aquaticness;
		double aquaticnessMutation = (Math.random() - 0.5) / 5.0;
		newAquaticness += aquaticnessMutation;
		if(newAquaticness < 0) newAquaticness = 0;
		if(newAquaticness > 0) newAquaticness = 1;
		
		Animal offspring = new Animal(0, 0, newSize, newSpeed);
		offspring.aquaticness = newAquaticness;
		
		return offspring;
	}
	
	public double distanceTo(Animal other){
		double xDiff = other.body.getCenterX() - this.body.getCenterX();
		double yDiff = other.body.getCenterY() - this.body.getCenterY();
		return Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
	}
	
	public double distanceTo(Food food){
		double xDiff = food.pellet.getCenterX() - this.body.getCenterX();
		double yDiff = food.pellet.getCenterY() - this.body.getCenterY();
		return Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
	}
}
