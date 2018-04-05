import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Ranker extends Phaser {
	private List<Racehorse> ranking = new CopyOnWriteArrayList<Racehorse>();
	private List<Racehorse> racers;
	private boolean secondTurn = false;

	public Ranker(List<Racehorse> racers) {
		this.racers = racers;
	}


	public void showTrailing() {
		String border = "----------------------------";

		if(super.getUnarrivedParties() == 1) {
			Collections.sort(racers);
			System.out.println(border);
			System.out.printf("Trailing:  %s\n", racers.get(racers.size() - 1));
			System.out.println(border);

			try {
				TimeUnit.SECONDS.sleep(1L);
			} catch(InterruptedException e) {}
		}

		secondTurn = true;

		super.arriveAndAwaitAdvance();
	}

	public boolean isTrailing(Racehorse horse) {
		Racehorse trailing = racers.get(racers.size() - 1);
		return (trailing.equals(horse) && secondTurn);
	}

	public void crossFinishLine(Racehorse horse) {
		ranking.add(horse);
		super.arriveAndDeregister();
	}

	public void showRanking() {
		System.out.printf("%s --- %s\n", "RANK", "NAME");
		for(int i = 0; i < ranking.size(); i++) {
			System.out.printf(" %d   --- %s\n", i + 1, ranking.get(i));
		}
	}


}
