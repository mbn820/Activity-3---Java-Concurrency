import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class RandomHorseGenerator {
	List<Racehorse> randomHorses;

	public RandomHorseGenerator() {

	}

	public void generate(int numberOfHorses) {
		randomHorses = new ArrayList<Racehorse>();
		for(int i = 0; i < numberOfHorses; i++) {
			randomHorses.add(new Racehorse());
		}
	}

	public List<Racehorse> getAllHorses() {
		return this.randomHorses;
	}

	public List<Racehorse> getHealthyHorses() {
		return randomHorses.stream()
						   .filter(h -> h.isHealthy())
						   .collect(Collectors.toList());
	}

}//endClass
