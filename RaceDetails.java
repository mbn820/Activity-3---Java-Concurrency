import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class RaceDetails {
	private List<Racehorse> lineup;
	private int raceDistance;

	public static final int MIN_RACE_DISTANCE = 10;
	public static final int MAX_RACE_DISTANCE = 100;

	private ConsoleInputHandler input = new ConsoleInputHandler();
	private RandomHorseGenerator horseGenerator = new RandomHorseGenerator();

	public RaceDetails() {
		setDetails();
	}

	public void setDetails() {
		setNumberOfHorses();
		setRaceDistance();
		setHorseNames();
		System.out.println("Details have been set");
	}

	public List<Racehorse> getLineup() {
		return this.lineup;
	}

	public int getRaceDistance() {
		return this.raceDistance;
	}

	private void setNumberOfHorses() {
		int min = 2;
		int max = 10;
		String errorMsg = String.format("Invalid input, MIN : %d | MAX : %d", min, max);// include sa CInputHandler

		System.out.println("----------------------------------");

		do {
			int numOfHorses = input.getIntegerBetween("Enter number of horses: ", min, max, errorMsg);
			horseGenerator.generate(numOfHorses);
			lineup = horseGenerator.getHealthyHorses();

			viewHorseCondition();

			if(lineup.size() <= 1) {
				System.out.println("Insufficient number of healthy horses generated, try again");
			}

		} while(lineup.size() <= 1);

		System.out.printf("Successfully generated %d healthy horses\n", lineup.size());
	}

	private void setRaceDistance() {
		String errorMsg = String.format("Invalid input, MIN : %d | MAX : %d", MIN_RACE_DISTANCE, MAX_RACE_DISTANCE);

		System.out.println("----------------------------------");

		raceDistance = input.getIntegerBetween("Enter race Distance: ", MIN_RACE_DISTANCE, MAX_RACE_DISTANCE, errorMsg);

		System.out.printf("Race distance set to %d meters.\n", this.raceDistance);
	}

	private void setHorseNames() {
		String horseName;
		int count = 1;
		List<String> nameRecords = new ArrayList<String>();

		System.out.println("----------------------------------");

		for(Racehorse horse : lineup) {
			do {
				horseName = input.getAnyString("Enter name for horse no. " + count + " : ");
				if(nameRecords.contains(horseName)) {
					System.out.println("Name already exists, try again");
				}
			} while(nameRecords.contains(horseName));

			nameRecords.add(horseName);
			horse.setName(horseName);

			count++;
		}


		System.out.println(lineup);
	}

	private void viewHorseCondition() {
		List<Racehorse> allGeneratedHorses = horseGenerator.getAllHorses();
		Map<String, String> conditions = new HashMap<String, String>();
		
		int number = 0;

		for(Racehorse horse : allGeneratedHorses) {
			if(horse.isHealthy()) {
				conditions.put("horse" + number, "Healthy");
			} else {
				conditions.put("horse" + number, "Unhealthy");
			}
			number++;
		}
		System.out.println(conditions);
	}
	
	
}//endClass
