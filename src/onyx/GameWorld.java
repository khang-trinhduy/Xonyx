package onyx;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import onyx.GameWorld.FixedObjects.TimeTicker;
import onyx.GameWorld.MoveableObjects.Car;
import onyx.GameWorld.MoveableObjects.MonsterBall;

public class GameWorld {
	private static List<FixedObjects> fixedObjectList = new ArrayList<>();
	private static List<MoveableObjects> moveableObjectList = new ArrayList<>();
	protected String name;
	protected Location location;
	protected Color color;
	
	private GameWorld(String name, Location location, Color color) {
		this.name = name;
		this.location = location;
		this.color = color;
	}
	
	public GameWorld() {
		
	}

	public void increaseSpeed() {
		for (MoveableObjects object : moveableObjectList) {
			if(object instanceof Car) {
				object.speed++;
			}
		}
	}
	
	public Integer collideTimeTicker() {
		Integer timer = 0;
		int index = 0;
		for (FixedObjects fixedObjects : fixedObjectList) {		
			if(fixedObjects instanceof TimeTicker) {
				timer = ((TimeTicker) fixedObjects).getTime();
				fixedObjectList.remove(index);
			}
			index++;
		}
		return timer;
	}
	
	public void destroyCar() {
		int index = 0;
		for (MoveableObjects object : moveableObjectList) {		
			if(object instanceof Car) {
				fixedObjectList.remove(index);
			}
			index++;
		}
	}
	
	public Integer generateSquares(Integer avaiSquares) {
		Random r = new Random();
		return r.nextInt((avaiSquares - 1) + 1) - 1;
	}
	
	public void updatingGameObjects() {
		for (MoveableObjects object : moveableObjectList) {
			Integer currSpeed = object.getSpeed();
			Integer currHeading = object.getHeading();
			Location newLoc = object.location;
			if(object instanceof Car) {
				if(currHeading.equals(0)) {
					newLoc.y += currSpeed;
					if(newLoc.y >= 497.5) {
						//Car cannot move out of game field.
						newLoc.y = 497.5;
					}
				}
				else if(currHeading.equals(90)) {
					newLoc.x += currSpeed;
					if(newLoc.x >= 497.5) {
						//Car cannot move out of game field.
						newLoc.x = 497.5;
					}
				}
				else if(currHeading.equals(180)) {
					newLoc.y -= currSpeed;
					if(newLoc.y <= 2.5) {
						//Car cannot move out of game field.
						newLoc.y = 2.5;
					}
				}
				else {
					newLoc.x -= currSpeed;
					if(newLoc.x <= 2.5) {
						//Car cannot move out of game field.
						newLoc.x = 2.5;
					}
				}
				object.setLocation(newLoc);
			}
			else if(object instanceof MonsterBall) {
				if(currHeading >=0 && currHeading < 90) {
					double temp = currSpeed * Math.cos(Math.toRadians(currSpeed));
					newLoc.y = (double)Math.round((newLoc.y + temp) * 100d) / 100d;
					newLoc.x = (double)Math.round((newLoc.x + Math.sqrt((currSpeed * currSpeed)-(temp * temp)))* 100d) / 100d;
				}
				else if(currHeading >=90 && currHeading < 180) {
					double temp = currSpeed * Math.cos(Math.toRadians(currSpeed));
					newLoc.x = (double)Math.round((newLoc.x + temp)*100d)/100d;
					newLoc.y = (double)Math.round((newLoc.y + Math.sqrt((currSpeed * currSpeed)-(newLoc.x * newLoc.x)))*100d)/100d;
				}
				else if(currHeading >=180 && currHeading < 270) {
					double temp = currSpeed * Math.cos(Math.toRadians(currSpeed));
					newLoc.y = (double)Math.round((newLoc.y + temp)*100d)/100d;
					newLoc.x = (double)Math.round((newLoc.x + Math.sqrt((currSpeed * currSpeed)-(temp * temp)))*100d)/100d;
				}
				else if(currHeading >=270 && currHeading <= 360) {
					double temp = currSpeed * Math.cos(Math.toRadians(currSpeed));
					newLoc.x = (double)Math.round((newLoc.x + temp)*100d)/100d;
					newLoc.y = (double)Math.round((newLoc.y + Math.sqrt((currSpeed * currSpeed)-(temp * temp)))*100d)/100d;	
				}
				object.setLocation(newLoc);
			}
		}
		int index = 0;
		for (FixedObjects fixedObjects : fixedObjectList) {	
			if(fixedObjects instanceof TimeTicker) {
				((TimeTicker) fixedObjects).time--;
				if(((TimeTicker) fixedObjects).time <= 0) {
					fixedObjectList.remove(index);
				}
			
			}
			index++;
		}
	}
	
	public void addObject(GameWorld object) {
		if(object instanceof FixedObjects) {
			fixedObjectList.add((FixedObjects)object);
		}
		else {
			moveableObjectList.add((MoveableObjects)object);
		}
	}
	
	public void changeHeading(Integer degrees) {
		try {
			for (MoveableObjects object : moveableObjectList) {
				if(object instanceof Car) {
					object.changeHeading(degrees);
				}
			}
		}
		catch (Exception e) {
			System.out.println("[Have nothing to change.]");
			e.printStackTrace();
		}
	}
	
	public void showMapContaining() {
		for (MoveableObjects moveableObject : moveableObjectList) {
			if(moveableObject instanceof Car) {
				((Car) moveableObject).show();
			}
			else if(moveableObject instanceof MonsterBall) {
				((MonsterBall) moveableObject).show();
			}
		}
		for (FixedObjects fixedObjects : fixedObjectList) {
			if(fixedObjects instanceof TimeTicker) {
				((TimeTicker) fixedObjects).show();
			}
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public static abstract class FixedObjects extends GameWorld {
		protected Integer height;
		private FixedObjects(String name, Location location, Color color, Integer height, Integer width) {
			super(name, location, color);
			// TODO Auto-generated constructor stub
			this.height = height;
			this.width = width;
		}
		protected Integer width;
		public Integer getWidth() {
			return width;
		}

		public void setWidth(Integer width) {
			this.width = width;
		}

		public Integer getHeight() {
			return height;
		}

		public void setHeight(Integer height) {
			this.height = height;
		}

		
		
		public static class GameField extends FixedObjects {
			public GameField(String name, Location location, Color color, Integer width, Integer height) {
				super(name, location, color, width, height);
				// TODO Auto-generated constructor stub
			}
			
		}
		
		public static class TimeTicker extends FixedObjects {
			private Integer time;
			public TimeTicker(String name, Location location, Color color, Integer width, Integer height, Integer time) {
				super(name, location, color, width, height);
				// TODO Auto-generated constructor stub
				this.time = time;
			}
			public Integer getTime() {
				return time;
			}
			public void setTime(Integer time) {
				this.time = time;
			}
			public void minusTime() {
				this.time--;
			}
			public void show() {
				System.out.println("TimeTicker: loc=" + this.location.x + "," + this.location.y 
						+ " color=[" + this.color.getRed() + ", " + this.color.getGreen() + ", " 
						+ this.color.getRed() + "] width=" + this.getWidth() + " height=" + this.getHeight()
						 + " time=" + this.time);
			}
		}
	}
	
	public static abstract class MoveableObjects extends GameWorld {
		protected Integer speed;
		protected Integer heading;
		
		private MoveableObjects(String name, Location location, Color color, Integer speed, Integer heading) {
			super(name, location, color);
			// TODO Auto-generated constructor stub
			this.speed = speed;
			this.heading = heading;
		}

		
		public Integer getSpeed() {
			return speed;
		}
		
		public void setSpeed(Integer speed) {
			this.speed = speed;
		}
		
		public Integer getHeading() {
			return heading;
		}
		
		public void setHeading(Integer heading) {
			this.heading = heading;
		}
		
		public static class Car extends MoveableObjects {
			private Integer width;
			private Integer height;
			
			public Car(String name, Location location, Color color, Integer speed, Integer heading, Integer width, Integer height) {
				super(name, location, color, speed, heading);
				// TODO Auto-generated constructor stub
				this.width = width;
				this.height = height;
			}
			
			public Integer getWidth() {
				return width;
			}
			public void setWidth(Integer width) {
				this.width = width;
			}
			public Integer getHeight() {
				return height;
			}
			public void setHeight(Integer height) {
				this.height = height;
			}
			
			public void changeHeading(Integer degrees) {
				this.setHeading(degrees);
			}
			
			public void show() {
				System.out.println("Car: loc=" + this.location.x + "," + this.location.y + 
						" color=[" + this.color.getRed() + ", " + this.color.getGreen()
						+ ", " + this.color.getBlue() + "] speed=" + this.getSpeed() + 
						" heading=" + this.getHeading() + " width=" + this.width + " heigh=" + this.height
						);
			}
		}
		
		public static class MonsterBall extends MoveableObjects {
			private Integer radius;
			
			public MonsterBall(String name, Location location, Color color, Integer speed, Integer heading, Integer radius) {
				super(name, location, color, speed, heading);
				// TODO Auto-generated constructor stub
				this.radius = radius;
			}

			public Integer getRadius() {
				return radius;
			}

			public void setRadius(Integer radius) {
				this.radius = radius;
			}
			
			public void changeHeading(Integer degrees) {
				this.setHeading(degrees);
			}
			
			public void show() {
				System.out.println("Ball: loc=" + this.location.x + "," + this.location.y + 
						" color=[" + this.color.getRed() + ", " + this.color.getGreen()
						+ ", " + this.color.getBlue() + "] speed=" + this.getSpeed() + 
						" heading=" + this.getHeading() + " radius=" + this.radius
						);
			}
			
		}
		
		
	}
	
	
	
	public static class Location {
		public double x;
		public double y;
		
		public Location (double d, double e) {
			this.x = d;
			this.y = e;
		}
		
		public Location () {
		}
		
		public double getX() {
			return x;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}

	}

}
