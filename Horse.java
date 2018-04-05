import java.util.Random;

public class Horse {
	protected String name;
	protected int currentSpeed;
	protected SpeedType currentSpeedType;
	protected int health;

	protected Random rand;

	public Horse() {
		rand = new Random();
		health = rand.nextInt(100);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isHealthy() {
		return (health > 50);
	}

	public void setRandomSpeed(SpeedType type) {
		switch(type) {
			case NORMAL : currentSpeed = rand.nextInt(9) + 1; break;
			case BOOSTED : currentSpeed = rand.nextInt(19) + 1; break;
		}
	}

	public String toString() {
		return name;
	}


}//endClass
