package transferwindow;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Comparable<Player>, Serializable {
	private String playerName;
	private int backNum;
	private int shoot;
	private int pass;
	private String position;
	private int id;
	private int price;
	
	public Player(String playerName, int backNum, int shoot, int pass, String position, int price) {
		super();
		this.playerName = playerName;
		this.backNum = backNum;
		this.shoot = shoot;
		this.pass = pass;
		this.position = position;
		this.price = price;
		switch (position) {
		case "gk":
            this.id = 0;
            break;
        case "df":
            this.id = 1;
            break;
        case "mf":
            this.id = 2;
            break;
        default:
            this.id = 3;
            break;
		}
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getBackNum() {
		return backNum;
	}

	public void setBackNum(int backNum) {
		this.backNum = backNum;
	}

	public int getShoot() {
		return shoot;
	}

	public void setShoot(int shoot) {
		this.shoot = shoot;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public int compareTo(Player o) {
		return this.id - o.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(backNum, playerName, position);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return backNum == other.backNum && Objects.equals(playerName, other.playerName)
				&& Objects.equals(position, other.position);
	}

	@Override
	public String toString() {
		return "[" + playerName + ", backNum=" + backNum + ", shoot=" + shoot + ", pass=" + pass
				+ ", position=" + position + ", id=" + id + ", price=" + price + "]";
	}
	
	
	
	
	
}
