import java.util.*;
import java.util.concurrent.*;

public class RaceEvent {
	private int raceDistance;
	private List<Racehorse> racers;

	private CyclicBarrier gateBarrier;
	private CyclicBarrier finishLineBarrier;
	private Ranker ranker;
	private ExecutorService executor;

	public RaceEvent(int distance, List<Racehorse> racers) {
		this.raceDistance = distance;
		this.racers = racers;
	}

	public void begin() {
		executor = Executors.newCachedThreadPool();

		prepareHorses();

		for(Racehorse racer : racers) {
			executor.execute(racer);
		}

		executor.shutdown();
	}

	private void prepareHorses() {
		gateBarrier = new CyclicBarrier(racers.size(), new GateAction());
		finishLineBarrier = new CyclicBarrier(racers.size(), new FinishLineAction());
		ranker = new Ranker(racers);

		for(Racehorse racer : racers) {
			racer.setTrackDistance(raceDistance);
			racer.setBarrier(gateBarrier, finishLineBarrier, ranker);
		}
	}

	class GateAction implements Runnable {
		@Override
		public void run() {
			System.out.println("\nEvery horse has reached the gate, opening the gate in: \n");
			for(int i = 3; i >= 0; i--) {
				System.out.println(i);
				try {
					Thread.sleep(1000);
				} catch(Exception e) {}
			}
			System.out.println("**********************************\n");
		}
	}

	class FinishLineAction implements Runnable {
		@Override
		public void run() {
			System.out.println("\nThe race is finished\n");
			ranker.showRanking();
			System.exit(0);
		}
	}
}
