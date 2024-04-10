package transferwindow;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	private String email;
	private String pw;
	private int balance;
	private String teamName;
	private ArrayList<Player> userPlayerList;
	
	public User() {
		super();
	}
	
	public User(String email, String pw, String teamName, ArrayList<Player> userPlayerList) {
		super();
		this.email = email;
		this.pw = pw;
		this.balance = 3000;
		this.teamName = teamName;
		this.userPlayerList = userPlayerList;
	}
	

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public ArrayList<Player> getUserPlayerList() {
		return userPlayerList;
	}

	public void setUserPlayerList(ArrayList<Player> userPlayerList) {
		this.userPlayerList = userPlayerList;
	}

	public void buyPlayer(Player player, int price) {
        if (balance >= price) {
            userPlayerList.add(player);
            balance -= price;
            System.out.println(player.getPlayerName() + " 선수를 구매했습니다.");
            System.out.println("남은 예산 : " + balance);
        } else {
            System.out.println("예산이 부족합니다.");
            System.out.println("예산 : " + balance);
        }
    }
	
	public void sellPlayer(Player player, int price) {
        if (userPlayerList.contains(player)) {
            userPlayerList.remove(player);
            balance += price;
            System.out.println(player.getPlayerName() + " 선수를 판매했습니다.");
            System.out.println("남은 예산 : " + balance);
        } else {
            System.out.println("해당 선수를 보유하고 있지 않습니다.");
        }
    }
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
	    sb.append(email).append("님의 ").append(teamName).append(" 팀 목록\n");
	    for (Player player : userPlayerList) {
	        sb.append(player.toString()).append("\n");
	    }
	    return sb.toString();
	}
	
}
