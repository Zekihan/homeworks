package footballsimulation;

import collections.Set;

public interface FootballManager {
	/**
	 * Decides the starting line up.
	 * @param ownClub The club that manager managed.
	 * @param opponent The opposing club.
	 * @return The Set of players that going to start.
	 */
	public Set<Player> decideStartingLineUp(FootballClub ownClub, FootballClub opponent);
	/**
	 * Decides the substitute players.
	 * @param ownClub The club that manager managed.
	 * @param opponent The opposing club.
	 * @param startinglineUp The starting line up.
	 * @return The Set of players that going to be substituted.
	 */
	public Set<Player> decideSubstitutePlayers(FootballClub ownClub, FootballClub opponent, Set<Player> startinglineUp);
	/**
	 * Decides the substitutions.
	 * @param ownClub The club that manager managed.
	 * @param footballMatch The football match that players playing.
	 * @return The Set of players with that already made substitutions.
	 */
	public Set<Player> makeSubstitutions(FootballClub ownClub, FootballMatch footballMatch); 

	
}
