package footballsimulation;

import java.lang.reflect.Array;
import java.util.Iterator;

import collections.ArrayDictionary;
import collections.Dictionary;
import collections.Set;

public class FootballMatch {

	private FootballClub homeClub;
	private FootballManager homeManager;
	private Set<Player> homeStartingLineUp;
	private Set<Player> homeSubstitutePlayers;
	private int homeScore;
	private Dictionary<Integer, Integer> homeAchievements; 

	private FootballClub awayClub;
	private FootballManager awayManager;
	private Set<Player> awayStartingLineUp;
	private Set<Player> awaySubstitutePlayers;
	private int awayScore;
	private Dictionary<Integer, Integer> awayAchievements;
	
	public FootballClub getHomeClub() {
		return homeClub;
	}
	public FootballManager getHomeManager() {
		return homeManager;
	}
	public Set<Player> getHomeStartingLineUp() {
		return homeStartingLineUp;
	}
	public Set<Player> getHomeSubstitutePlayers() {
		return homeSubstitutePlayers;
	}
	public int getHomeScore() {
		return homeScore;
	}
	public Dictionary<Integer, Integer> getHomeAchievements() {
		return homeAchievements;
	}
	public FootballClub getAwayClub() {
		return awayClub;
	}
	public FootballManager getAwayManager() {
		return awayManager;
	}
	public Set<Player> getAwayStartingLineUp() {
		return awayStartingLineUp;
	}
	public Set<Player> getAwaySubstitutePlayers() {
		return awaySubstitutePlayers;
	}
	public int getAwayScore() {
		return awayScore;
	}
	public Dictionary<Integer, Integer> getAwayAchievements() {
		return awayAchievements;
	}
	
	public FootballMatch(FootballClub homeClub, FootballClub awayClub) {

		this.homeClub = homeClub;
		this.homeManager = homeClub.getManager();
		this.homeScore = 0;
		Player[] homeTeam = homeClub.getSquad().toArray();
		Dictionary<Integer, Integer> tempHome = new ArrayDictionary<Integer, Integer>();
		for (int i = 0; i < homeTeam.length; i++) {
			tempHome.add(homeTeam[i].hashCode(),0);
		}
		this.homeAchievements = tempHome;
		this.awayClub = awayClub;
		this.awayManager = awayClub.getManager();
		this.awayScore = 0;
		Player[] awayTeam = awayClub.getSquad().toArray();
		Dictionary<Integer, Integer> tempAway = new ArrayDictionary<Integer, Integer>();
		for (int i = 0; i < awayTeam.length; i++) {
			tempAway.add(awayTeam[i].hashCode(),0);
		}
		this.awayAchievements = tempAway;
	}

	public MatchResult simulateMatch() {
		
		homeStartingLineUp = homeManager.decideStartingLineUp(homeClub, awayClub);
		homeSubstitutePlayers = homeManager.decideSubstitutePlayers(homeClub, awayClub, homeStartingLineUp);
		
		awayStartingLineUp = awayManager.decideStartingLineUp(awayClub, homeClub);
		awaySubstitutePlayers = awayManager.decideSubstitutePlayers(awayClub, homeClub, awayStartingLineUp);
		
		MatchResult result;
		boolean violate = false;
		if (checkStartingTeam(homeStartingLineUp)||checkSubstituteTeam(homeSubstitutePlayers)) {
			awayScore = 3;
			violate = true;
		}
		if (checkStartingTeam(awayStartingLineUp)||checkSubstituteTeam(awaySubstitutePlayers)) {
			homeScore = 3;
			violate = true;
		}
		if ((homeScore == 3)&&(awayScore == 3)) {
			homeScore = 0;
			awayScore = 0;
		}
		result = new MatchResult(homeScore, awayScore, null);

		if (!violate) {
			for(int minute = 0; minute < 45; minute++) {

				randomEvent(homeStartingLineUp, awayStartingLineUp);
				
			}

			Set<Player> secondHalfLineUpHome = homeManager.makeSubstitutions(homeClub, this);
			Set<Player> secondHalfLineUpAway = awayManager.makeSubstitutions(awayClub, this);

			for(int minutes = 45; minutes < 90; minutes++) {

				randomEvent(secondHalfLineUpHome, secondHalfLineUpAway);
			}
			
			
			Integer[] home = findMax(homeAchievements);
			Integer[] away = findMax(awayAchievements);
			Player[] homeArray = combine(homeStartingLineUp.toArray(),secondHalfLineUpHome.toArray());
			Player[] awayArray = combine(awayStartingLineUp.toArray(),secondHalfLineUpAway.toArray());
			Player playerOfTheMatch ;
			if (home[1] < away[1]) {
				playerOfTheMatch = getPlayer(awayArray, away[0]);
			}
			else {
				playerOfTheMatch = getPlayer(homeArray, home[0]);
			}
			
			result = new MatchResult(homeScore, awayScore, playerOfTheMatch);
		}
		return result;
	}
	private <T> T[] combine(T[] a, T[] b) {
	    int aLen = a.length;
	    int bLen = b.length;

	    @SuppressWarnings("unchecked")
	    T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	private boolean checkStartingTeam(Set<Player> startingTeam) {
		boolean rule = false;
		Player[] team = startingTeam.toArray();
		if (team.length != 11) {
			rule = true;
		}
		int gk = 0, dl = 0, dc = 0, dr = 0, ml = 0, mc = 0, mr = 0, fc = 0;
		for (int i = 0; i < team.length; i++) {
			Player player = team[i];
			if (player.getPosition() == Position.GK) {
				gk += 1;
				continue;
			}
			if (player.getPosition() == Position.DL) {
				dl += 1;
				continue;
			}
			if (player.getPosition() == Position.DC) {
				dc += 1;
				continue;
			}
			if (player.getPosition() == Position.DR) {
				dr += 1;
				continue;
			}
			if (player.getPosition() == Position.MC) {
				mc += 1;
				continue;
			}
			if (player.getPosition() == Position.ML) {
				ml += 1;
				continue;
			}
			if (player.getPosition() == Position.MR) {
				mr += 1;
				continue;
			}
			if (player.getPosition() == Position.FC) {
				fc += 1;
				continue;
			}
		}
		if ((gk != 1)&&(dl != 1)&&(dc != 2)&&(dr != 1)&&(mc != 2)&&(ml != 1)&&(mr != 1)&&(fc != 2)) {
			rule = true;
		}
		return rule;
	}
	private boolean checkSubstituteTeam(Set<Player> startingTeam) {
		boolean rule = false;
		Player[] team = startingTeam.toArray();
		if (team.length != 7) {
			rule = true;
		}
		return rule;
	}
	private Integer[] findMax(Dictionary<Integer, Integer> achievement) {
		
		Iterator<Integer> achievementValue = achievement.getValueIterator(),achievementKey = achievement.getKeyIterator();
		Integer achievementMaxValue = 0, achievementKeyMax = 0, tempAchievementKey = 0, tempAchievementValue = 0;
		while (achievementValue.hasNext()) {
			tempAchievementValue = achievementValue.next();
			tempAchievementKey = achievementKey.next();
			if (tempAchievementValue != null) {
				if (tempAchievementValue > achievementMaxValue) {
					achievementMaxValue = tempAchievementValue;
					achievementKeyMax = tempAchievementKey;
				}
			}
			
		}
		Integer result[] = new Integer[2];
		result[0] = achievementKeyMax;
		result[1] = achievementMaxValue;
		return result;
	}
	private void randomEvent(Set<Player>homeTeam, Set<Player>awayTeam) {
		double rand = Math.random();
		if (rand <= 0.5) {
			
			Player player = randomPlayer(homeTeam);
			if (Math.random() < 0.04) {
				if (randomGoal(player)) {
					homeScore++;
					Integer tempvalue = homeAchievements.remove(player.hashCode()) ;
					tempvalue += 20;
					homeAchievements.add(player.hashCode(), tempvalue);	
				}
				
			}
			if (Math.random() < 0.05) {
				if (randomAssist(player)) {
					Integer tempvalue = homeAchievements.remove(player.hashCode()) ;
					tempvalue += 10;
					homeAchievements.add(player.hashCode(), tempvalue);	
				}
			}
			if (Math.random() < 0.5) {
				if (randomTackle(player)) {
					Integer tempvalue = homeAchievements.remove(player.hashCode()) ;
					tempvalue += 2;
					homeAchievements.add(player.hashCode(), tempvalue);	
				}
			}
			if (Math.random() < 0.5) {
				if (randomPass(player)) {
					Integer tempvalue = homeAchievements.remove(player.hashCode()) ;
					tempvalue += 1;
					homeAchievements.add(player.hashCode(), tempvalue);	
				}
			}
		}
		if (rand > 0.5) {
			
			Player player = randomPlayer(awayTeam);
			if (Math.random() < 0.04) {
				if (randomGoal(player)) {
					awayScore++;
					Integer tempvalue = awayAchievements.remove(player.hashCode()) ;
					tempvalue += 20;
					awayAchievements.add(player.hashCode(), tempvalue);	
				}
				
			}
			if (Math.random() < 0.05) {
				if (randomAssist(player)) {
					Integer tempvalue = awayAchievements.remove(player.hashCode()) ;
					tempvalue += 10;
					awayAchievements.add(player.hashCode(), tempvalue);	
				}
			}
			if (Math.random() < 0.5) {
				if (randomTackle(player)) {
					Integer tempvalue = awayAchievements.remove(player.hashCode()) ;
					tempvalue += 2;
					awayAchievements.add(player.hashCode(), tempvalue);	
				}
			}
			if (Math.random() < 0.5) {
				if (randomPass(player)) {
					Integer tempvalue = awayAchievements.remove(player.hashCode()) ;
					tempvalue += 1;
					awayAchievements.add(player.hashCode(), tempvalue);	
				}
			}
		}
		
	}
	private Player randomPlayer(Set<Player> team) {
			int rand = (int) (Math.random()*11);
			Player[] arrayTeam = team.toArray();
			Player player = arrayTeam[rand];
		return player;
	}
	private Player getPlayer(Player[] team,Integer key) {
		Player[] arrayTeam = team;
		Player player = null;
		for (int i = 0; i < arrayTeam.length; i++) {
			if (arrayTeam[i] != null) {
				if (arrayTeam[i].hashCode() == key) {
					player = arrayTeam[i];
				}
			}
		}
		return player;
	}
	private boolean randomGoal(Player player) {
		boolean goal = false;
		int rand = (int) Math.random();
		if (player.getPosition() == Position.GK) {
			if (rand <= 0.005) {
				goal = true;
			}
		}
		if (player.getPosition() == Position.DL) {
			if (rand <= 0.05) {
				goal = true;
			}
		}
		if (player.getPosition() == Position.DC) {
			if (rand <= 0.05) {
				goal = true;
			}
		}
		if (player.getPosition() == Position.DR) {
			if (rand <= 0.05) {
				goal = true;
			}
		}
		if (player.getPosition() == Position.MC) {
			if (rand <= 0.25) {
				goal = true;
			}
		}
		if (player.getPosition() == Position.ML) {
			if (rand <= 0.25) {
				goal = true;
			}
		}
		if (player.getPosition() == Position.MR) {
			if (rand <= 0.25) {
				goal = true;
			}
		}
		if (player.getPosition() == Position.FC) {
			if (rand <= 0.5) {
				goal = true;
			}
		}
		return goal;
	}
	private boolean randomAssist(Player player) {
		boolean assist = false;
		int rand = (int) Math.random();
		if (player.getPosition() == Position.GK) {
			if (rand <= 0.005) {
				assist = true;
			}
		}
		if (player.getPosition() == Position.DL) {
			if (rand <= 0.05) {
				assist = true;
			}
		}
		if (player.getPosition() == Position.DC) {
			if (rand <= 0.05) {
				assist = true;
			}
		}
		if (player.getPosition() == Position.DR) {
			if (rand <= 0.05) {
				assist = true;
			}
		}
		if (player.getPosition() == Position.MC) {
			if (rand <= 0.3) {
				assist = true;
			}
		}
		if (player.getPosition() == Position.ML) {
			if (rand <= 0.3) {
				assist = true;
			}
		}
		if (player.getPosition() == Position.MR) {
			if (rand <= 0.3) {
				assist = true;
			}
		}
		if (player.getPosition() == Position.FC) {
			if (rand <= 0.5) {
				assist = true;
			}
		}
		return assist;
	}
	private boolean randomTackle(Player player) {
		boolean tackle = false;
		int rand = (int) Math.random();
		if (player.getPosition() == Position.GK) {
			if (rand <= 0.05) {
				tackle = true;
			}
		}
		if (player.getPosition() == Position.DL) {
			if (rand <= 0.5) {
				tackle = true;
			}
		}
		if (player.getPosition() == Position.DC) {
			if (rand <= 0.5) {
				tackle = true;
			}
		}
		if (player.getPosition() == Position.DR) {
			if (rand <= 0.5) {
				tackle = true;
			}
		}
		if (player.getPosition() == Position.MC) {
			if (rand <= 0.5) {
				tackle = true;
			}
		}
		if (player.getPosition() == Position.ML) {
			if (rand <= 0.5) {
				tackle = true;
			}
		}
		if (player.getPosition() == Position.MR) {
			if (rand <= 0.5) {
				tackle = true;
			}
		}
		if (player.getPosition() == Position.FC) {
			if (rand <= 0.5) {
				tackle = true;
			}
		}
		return tackle;
	}
	private boolean randomPass(Player player) {
		boolean pass = false;
		int rand = (int) Math.random();
		if (player.getPosition() == Position.GK) {
			if (rand <= 0.05) {
				pass = true;
			}
		}
		if (player.getPosition() == Position.DL) {
			if (rand <= 0.5) {
				pass = true;
			}
		}
		if (player.getPosition() == Position.DC) {
			if (rand <= 0.5) {
				pass = true;
			}
		}
		if (player.getPosition() == Position.DR) {
			if (rand <= 0.5) {
				pass = true;
			}
		}
		if (player.getPosition() == Position.MC) {
			if (rand <= 0.5) {
				pass = true;
			}
		}
		if (player.getPosition() == Position.ML) {
			if (rand <= 0.5) {
				pass = true;
			}
		}
		if (player.getPosition() == Position.MR) {
			if (rand <= 0.5) {
				pass = true;
			}
		}
		if (player.getPosition() == Position.FC) {
			if (rand <= 0.5) {
				pass = true;
			}
		}
		return pass;
	}

}
