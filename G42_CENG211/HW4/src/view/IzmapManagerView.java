package view;

public class IzmapManagerView {

	public void startMessage() {
		System.out.println("Welcome to the Iztech Map Application");
	}
	
	public void menu() {
		System.out.println( System.lineSeparator() +
				"Menu:" + System.lineSeparator() + 
				"1) Find Shortest Path" + System.lineSeparator() +
				"2) Add/Remove Locations" + System.lineSeparator() + 
				"3) Possible reachable locations from a given location and distance" + System.lineSeparator() + 
				"4) Show the neighbors of given location id" + System.lineSeparator()+
				"5) Exit" + System.lineSeparator());
	}
	
	public void menuCase1() {
		System.out.println( "Please enter the first location and then enter the other");
	}
	
	
	public void menuCase2() {
		System.out.println( System.lineSeparator() +
				"Add/Remove:" + System.lineSeparator() + 
				"1) Add Location" + System.lineSeparator() +
				"2) Remove Location" + System.lineSeparator());
	}
	
	public void menuCase2Case1() {
		System.out.println("Enter node information in format \"Name,Category,CategoryType\" : ");
		
	}
	public void menuCase2Case2() {
		System.out.println( "Please enter Id of a location: ");
	}
	
	public void menuCase3() {
		System.out.println( "Please enter Id of a location and then enter the distance you want: ");
	}
	
	public void menuCase4() {
		System.out.println( "Please write Id of a location: ");
	}
	
	public void menuCase5() {
		System.out.println( "Thanks for using our application!");
	}
	
	public void illegalArgumentExcepitonMessage() {
		System.out.println("You have entered location id that does not exist, try again!");
	}

	public void exceptionMessage() {
		System.out.println("You did something wrong, try again!");
		
	}

	public void menuDefaultCase() {
		System.out.println("That is not a valid menu option");
		
	}
}
