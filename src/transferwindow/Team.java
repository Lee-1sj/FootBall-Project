package transferwindow;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {
	private String teamName;
	private ArrayList<Player> playerList;
	
	public Team(String teamName) {
		super();
		this.teamName = teamName;
		this.playerList = new ArrayList<>();
	}
	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}


	public void addPlayer(Player player) {
		playerList.add(player);
	}
	
	public void removePlayer(Player player) {
		playerList.remove(player);
	}

	@Override
	public String toString() {
		return "[" + teamName + ", " + playerList + "]";
	}
	
	
}
