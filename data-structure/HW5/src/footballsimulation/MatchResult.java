package footballsimulation;

public class MatchResult {

	private int scoreOfHomeTeam;
	private int scoreOfAwayTeam;
	private Player playerOfTheMatch;
	
	public MatchResult(int scoreOfHomeTeam, int scoreOfAwayTeam, Player playerOfTheMatch) {

		this.scoreOfHomeTeam = scoreOfHomeTeam;
		this.scoreOfAwayTeam = scoreOfAwayTeam;
		this.playerOfTheMatch = playerOfTheMatch;
		
	}

	public int getScoreOfHomeTeam() {
		return scoreOfHomeTeam;
	}
	public int getScoreOfAwayTeam() {
		return scoreOfAwayTeam;
	}
	public Player getPlayerOfTheMatch() {
		return playerOfTheMatch;
	}
}
