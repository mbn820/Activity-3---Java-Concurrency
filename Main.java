
public class Main {
	private static RaceDetails details;
	private static RaceEvent raceEvent;
	private static ConsoleInputHandler input = new ConsoleInputHandler();

	public static void main(String[] args) {
		details = new RaceDetails();

		input.getAll("\nPress ENTER to continue...\n");

		startRace();
	}


	public static void startRace() {
		System.out.println("***********Start Race***********\n");

		raceEvent = new RaceEvent(details.getRaceDistance(), details.getLineup());

		raceEvent.begin();
	}

}
