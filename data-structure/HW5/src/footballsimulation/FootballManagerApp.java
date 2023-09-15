package footballsimulation;

import collections.ArraySet;
import collections.Set;

public class FootballManagerApp {

	public static void main(String... args) {
		
	
		Set<Player> homeSquad = new ArraySet<Player>();
		homeSquad.add(new Player("ronaldo",7,Position.FC));
		homeSquad.add(new Player("ahmet",1,Position.FC));
		homeSquad.add(new Player("ali",2,Position.MC));
		homeSquad.add(new Player("mehmet",3,Position.MC));
		homeSquad.add(new Player("hüsnü",5,Position.ML));
		homeSquad.add(new Player("cenk",9,Position.MR));
		homeSquad.add(new Player("cedi",12,Position.DC));
		homeSquad.add(new Player("rüþtü",15,Position.DC));
		homeSquad.add(new Player("melih",16,Position.DL));
		homeSquad.add(new Player("efe",18,Position.DR));
		homeSquad.add(new Player("zekihan",19,Position.GK));
		homeSquad.add(new Player("oðuz",20,Position.GK));
		homeSquad.add(new Player("selman",23,Position.FC));
		homeSquad.add(new Player("sabri",24,Position.DL));
		homeSquad.add(new Player("berkay",33,Position.MC));
		homeSquad.add(new Player("batuhan",56,Position.MR));
		homeSquad.add(new Player("semih",89,Position.ML));
		homeSquad.add(new Player("berat",91,Position.DR));
		homeSquad.add(new Player("mustafa",99,Position.DC));
		
		Set<Player> awaySquad = new ArraySet<Player>();
		awaySquad.add(new Player("baran",7,Position.FC));
		awaySquad.add(new Player("kaan",8,Position.FC));
		awaySquad.add(new Player("ersan",9,Position.MC));
		awaySquad.add(new Player("hidayet",11,Position.MC));
		awaySquad.add(new Player("kerem",23,Position.MR));
		awaySquad.add(new Player("anelka",25,Position.ML));
		awaySquad.add(new Player("alex",33,Position.DC));
		awaySquad.add(new Player("messi",10,Position.DL));
		awaySquad.add(new Player("serdar",35,Position.DC));
		awaySquad.add(new Player("deniz",37,Position.DR));
		awaySquad.add(new Player("ataberk",39,Position.GK));
		awaySquad.add(new Player("ferit",44,Position.GK));
		awaySquad.add(new Player("dembaba",46,Position.FC));
		awaySquad.add(new Player("kemal",49,Position.DL));
		awaySquad.add(new Player("ekin",55,Position.MC));
		awaySquad.add(new Player("arda",17,Position.DC));
		awaySquad.add(new Player("can",66,Position.MR));
		awaySquad.add(new Player("sezgin",77,Position.ML));
		awaySquad.add(new Player("necip",88,Position.DR));
		awaySquad.add(new Player("onur",99,Position.FC));
		
		FootballManager manager1 = new AutomatedFootballManager();
		FootballManager manager2 = new AutomatedFootballManager();
		FootballClub homeClub = new FootballClub(manager1,"fenerbahçe",homeSquad);
		FootballClub awayClub = new FootballClub(manager2,"galatasaray",awaySquad);

		
		FootballMatch match = new FootballMatch(homeClub, awayClub);
		MatchResult result = match.simulateMatch();
		System.out.println(result.getScoreOfHomeTeam());
		System.out.println(result.getScoreOfAwayTeam());
		System.out.println(result.getPlayerOfTheMatch().getName());


		
	}

	
}
