package footballsimulation;

import collections.ArraySet;
import collections.Set;


public class FootballClub {

	private FootballManager footballManager;
	
	private String clubName;
	
	private Set<Player> squad;
	
	public FootballClub(FootballManager footballManager, String clubName, Set<Player> squad) {
		
		this.footballManager = footballManager;
		this.clubName = clubName;
		this.squad = new ArraySet<Player>((ArraySet<Player>) squad);
	}
	
	public FootballManager getManager()
	{
		return footballManager;
	}
	public String getClubName()
	{
		return clubName;
	}
	public Set<Player> getSquad()
	{
		Set<Player> squadCopy = new ArraySet<Player>((ArraySet<Player>) squad);
		return squadCopy;
	}
}
