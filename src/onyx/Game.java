package onyx;
import java.awt.Color;
import java.util.Random;
import java.util.Scanner;

import onyx.GameWorld.FixedObjects.GameField;
import onyx.GameWorld.FixedObjects.TimeTicker;
import onyx.GameWorld.MoveableObjects.Car;
import onyx.GameWorld.MoveableObjects.MonsterBall;

import onyx.GameWorld.Location;


public class Game {
	private static final String REGEX = "^[a-zA-Z1-3]$";
	
	private static final String VALID_COMMANDS = "[nsewilbkgtdmq1-3]";
	
	private static final String CREATE_MONSTERBALL = "-->Add a ball to game's field.";
	
	private static final String CREATE_TIMETICKER = "-->Add a timeticker to game's field.";
	
	private static final String APPLICATION_EXIT = "-->Application exit.";
	
	private static final String CHANGE_CAR_HEADING_NORTH = "-->Change car's heading [north].";
	
	private static final String CHANGE_CAR_HEADING_SOUTH = "-->Change car's heading [south].";
	
	private static final String CHANGE_CAR_HEADING_EAST = "-->Change car's heading [east].";
	
	private static final String CHANGE_CAR_HEADING_WEST = "-->Change car's heading [west].";
	
	private static final String SHOW_OBJECTS = "-->Showing all current game's objects.";
	
	private static final String SPEED_INCREASE = "-->Car's speed increased by one unit.";
	
	private static final String COLLIDE_BALL = "-->Colliding with a ball.";
	
	private static final String COLLIDE_TIMETICKER = "-->Colliding with a timeticker.";
	
	private static final String COLLIDE_SQUARE = "-->Moving to new square, owning it.";
	
	private static final String COUNTDOWN = "-->Timeticker's time decreasing by one.";
	
	private static final String CURRENT_STATE = "-->Show current game's state.";
	
	private static final String OWNING_NEW_SQUARE = "-->Your car surrounded by a group of square and now owning them.";
	
	private static final String UNKNOWN_COMMAND = "-->Unknown command.";
	
	private static GameWorld gw = new GameWorld();
	
	private static GameState gs;
	
	public Game() {
		play();
	}
	
	public void generate(GameWorld gw) {

	}
	
	private void play() {
		Integer playing = 1;
		Random r = new Random();
		Car car = new Car("Car", new Location(247.5, 2.5), new Color(r.nextInt(256),r.nextInt(256), r.nextInt(256))
				, r.nextInt(10) + 1, 0, 5, 5);
		GameField field = new GameField("FieldSquares", new Location(249, 249), new Color(r.nextInt(256),r.nextInt(256),
				r.nextInt(256)), 500, 500);
		gw.addObject(car);
		gw.addObject(field);
		gs = new GameState(1, 60, 396, 3, 5000);
		while(playing == 1) {
			String commands = "";
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			while (commands == "") {
				System.out.print("Enter command: ");
				String input = sc.next();
				if(input.matches(REGEX) && input.matches(VALID_COMMANDS)) {
					commands = input;
				}
			}		
			System.out.print("Command name: " + commands + " ");
			Integer exe = 0;
			switch (commands) {
			case "n":
				System.out.println(CHANGE_CAR_HEADING_NORTH);
				break;
			case "w":
				System.out.println(CHANGE_CAR_HEADING_WEST);
				break;
			case "e":
				System.out.println(CHANGE_CAR_HEADING_EAST);
				break;
			case "s":
				System.out.println(CHANGE_CAR_HEADING_SOUTH);
				break;
			case "m":
				System.out.println(SHOW_OBJECTS);
				break;
			case "b":
				System.out.println(CREATE_MONSTERBALL);
				break;
			case "i":
				System.out.println(SPEED_INCREASE);
				break;
			case "1":
				System.out.println(COLLIDE_BALL);
				break;
			case "2":
				System.out.println(COLLIDE_SQUARE);
				break;
			case "3":
				System.out.println(COLLIDE_TIMETICKER);
				break;	
			case "t":
				System.out.println(COUNTDOWN);
				break;
			case "d":
				System.out.println(CURRENT_STATE);
				break;
			case "g":
				System.out.println(OWNING_NEW_SQUARE);
				break;
			case "k":
				System.out.println(CREATE_TIMETICKER);
				break;
			case "q":
				System.out.println(APPLICATION_EXIT);
				System.out.println("Give up?(y/n)");
				if(sc.next().equals("y")) {
					exe = 1;
				}
				break;
			default:
				System.out.println(UNKNOWN_COMMAND);
				break;
			}			
			this.executeCommand(commands);
		}
	}
	
	@SuppressWarnings({ })
	private void executeCommand(String command) {
		if(command.equals("b")) {
			Random r = new Random();
			Location location = new Location();
			//Ball with radius = 6;
			//So MAX X/Y = 500 - 3, MIN X/Y = 0 + 3 (Field's size: 0-->500).
			//Generate random integer in range: ((MAX - MIN) + 1) - MIN ( https://stackoverflow.com ).
			int x = r.nextInt((497 - 3) + 1) - 3;
			location.x = x;
			int y = r.nextInt((497 - 3) + 1) - 3;
			location.y = y;
			Game.gw.addObject(new MonsterBall("Ball", location, new Color(r.nextInt(256),r.nextInt(256), r.nextInt(256))
					, r.nextInt(8) + 1, r.nextInt(361), 6));
		}
		else if(command.equals("k")) {
			Random r = new Random();
			Location location = new Location();
			//TimeTicker with width/height = 4/4
			//So MAX X/Y = 500 - 4, MIN X/Y = 0 + 4 (Field's size: 0-->500).
			//Generate random integer in range: ((MAX - MIN) + 1) - MIN ( https://stackoverflow.com ).
			location.setX(r.nextInt((496 - 4) + 1) - 4);
			location.setY(r.nextInt((496 - 4) + 1) - 4);
			Game.gw.addObject(new TimeTicker("TimeTicker", location, new Color(r.nextInt(256),r.nextInt(256), r.nextInt(256)), 
					4, 4, 30));
		}
		else if(command.equals("q")) {
			System.exit(0);
		}
		else if(command.equals("n")) {
			Game.gw.changeHeading(0);
		}
		else if(command.equals("e")) {
			Game.gw.changeHeading(90);
		}
		else if(command.equals("w")) {
			Game.gw.changeHeading(270);
		}
		else if(command.equals("s")) {
			Game.gw.changeHeading(180);
		}
		else if(command.equals("m")) {
			Game.gw.showMapContaining();
		}
		else if(command.equals("i")) {
			Game.gw.increaseSpeed();
		}
		else if(command.equals("t")) {
			Game.gw.updatingGameObjects();
			Game.gs.time--;
			//Missing case that current level's time's out and the case that player's life point equal 0.
		}
		else if(command.equals("3")) {
			//Destroy TimeTicker then add these's time to In-Game-Time
			Integer timePlus = Game.gw.collideTimeTicker();
			Game.gs.time += timePlus;
		}
		else if(command.equals("2")) {
			Game.gs.ownedSquares++;
			//Check condition if player's square's enough to advance to next level?
		}
		else if(command.equals("1")) {
			Game.gw.destroyCar();
			if(Game.gs.lifePoint >= 1) {
				Random r = new Random();
				Game.gw.addObject(new Car("Car", new Location(247.5, 2.5), new Color(r.nextInt(256),r.nextInt(256), r.nextInt(256))
				, r.nextInt(10) + 1, 0, 5, 5));
				Game.gs.setOwnedSquares(396);
				Game.gs.setTime(60);
				Game.gs.lifePoint--;
			}
			else {
				System.out.println("Game's over! Try again?");
				System.exit(0);
			}
		}
		else if(command.equals("g")) {
			Integer newSquares = Game.gw.generateSquares(10000 - gs.ownedSquares);
			Game.gs.ownedSquares += newSquares;
			if(Game.gs.score <= Game.gs.ownedSquares) {
				//Advance to next level
				System.out.println("Congratulation!");
			}
		}
		else if(command.equals("d")) {
			Game.gs.showCurrentState();
		}
		
	}
	public static class GameState {
		private Integer level;
		private Integer time;
		private Integer ownedSquares;
		private Integer lifePoint;
		private Integer score;
		
		public GameState(Integer level, Integer time, Integer ownedSquares, Integer lifePoint, Integer score) {
			super();
			this.level = level;
			this.time = time;
			this.ownedSquares = ownedSquares;
			this.lifePoint = lifePoint;
			this.score = score;
		}
		
		public GameState() {
			
		}
		
		public Integer getLevel() {
			return level;
		}
		public void setLevel(Integer level) {
			this.level = level;
		}	
		public Integer getTime() {
			return time;
		}
		public void setTime(Integer time) {
			this.time = time;
		}
		public Integer getOwnedSquares() {
			return ownedSquares;
		}
		public void setOwnedSquares(Integer ownedSquares) {
			this.ownedSquares = ownedSquares;
		}
		public Integer getLifePoint() {
			return lifePoint;
		}
		public void setLifePoint(Integer lifePoint) {
			this.lifePoint = lifePoint;
		}
		public Integer getScore() {
			return score;
		}
		public void setScore(Integer score) {
			this.score = score;
		}
		public void showCurrentState() {
			System.out.println("Level:" + this.level + ", Life:" + this.lifePoint + ", remain time:" + this.time + ", score:" +this.ownedSquares + ", require:" + this.score);
		}
		
	}
}
