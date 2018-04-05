import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;


public class Racehorse extends Horse implements Runnable, Comparable<Racehorse> {
	private int trackDistance;
	private int barnToGateDistance = 10;

	private int remainingDistanceToFinishL;
	private int remainingDistanceToGate;

	private CyclicBarrier gate;
	private CyclicBarrier finishLine;
	private Ranker ranker;

	public Racehorse(String name, int trackDistance) {
		this.name = name;
		this.trackDistance = trackDistance;
	}

	public Racehorse() {
		super();
	}

	@Override
	public void run() {
		toGate();
		toFinishLine();
	}

	public void toGate() {
		remainingDistanceToGate = barnToGateDistance;

		while(remainingDistanceToGate > 0) {
			setRandomSpeed(SpeedType.NORMAL);
			remainingDistanceToGate -= currentSpeed;

			if(remainingDistanceToGate <= 0) {
				break;
			}

			System.out.printf("%s : \n\tRemaining Distance to Gate: %d meters \n", name, remainingDistanceToGate);
			pause();
		}

		System.out.printf("%s \n\thas reached the gate\n", name);
		pause();
		waitAtTheGate();
	}

	public void waitAtTheGate() {
		try {
			gate.await();
		} catch(Exception e) {

		}
	}

	public void toFinishLine() {
		remainingDistanceToFinishL = trackDistance;

		ranker.register();

		while(remainingDistanceToFinishL > 0) {
			if(ranker.isTrailing(this)) {
				setRandomSpeed(SpeedType.BOOSTED);
				currentSpeedType = SpeedType.BOOSTED;
			} else {
				setRandomSpeed(SpeedType.NORMAL);
				currentSpeedType = SpeedType.NORMAL;
			}

			remainingDistanceToFinishL -= currentSpeed;

			if(remainingDistanceToFinishL <= 0) {
				break;
			}

			System.out.printf("%s \n\thas galloped %d meters | Remaining Distance to Finish Line: %d meters | SPEEDTYPE : %s \n", name, currentSpeed, remainingDistanceToFinishL, currentSpeedType);
			//ranker.showTrailing();
		}

		ranker.crossFinishLine(this);
		System.out.printf("%s \n\thas galloped %d meters | ********* crossed the finish line! ********* | SPEEDTYPE : %s \n", name, currentSpeed, currentSpeedType);

		pause();
		//waitForLastHorse();
	}

	public void waitForLastHorse() {
		try {
			finishLine.await();
		} catch(Exception e) {

		}
	}

	public void setTrackDistance(int trackDistance) {
		this.trackDistance = trackDistance;
	}

	public void setBarrier(CyclicBarrier gate, CyclicBarrier finishLine, Ranker ranker) {
		this.gate = gate;
		this.finishLine = finishLine;
		this.ranker = ranker;
	}


	@Override
	public int compareTo(Racehorse horse) {
		Integer thisRemaining = new Integer(this.remainingDistanceToFinishL);
		Integer otherRemaining = new Integer(horse.remainingDistanceToFinishL);
		return thisRemaining.compareTo(otherRemaining);
	}


	public void pause() {
		try {
			TimeUnit.SECONDS.sleep(1L);
		} catch(Exception e) {}
	}


}// endClass
