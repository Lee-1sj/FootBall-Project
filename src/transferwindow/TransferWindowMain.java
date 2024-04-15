package transferwindow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TransferWindowMain {
	
	private static Admin admin = new Admin();
	private static final String USER_FILE = "user.dba";
	private static final String TEAM_FILE = "teams.dba";
	private static final String MARKET_FILE = "market.dba";
	private static ArrayList<User> userList = new ArrayList<>();
	private static ArrayList<Team> teamList = new ArrayList<>();
	private static  ArrayList <Player> faPlayerList = new ArrayList<>();

	public static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		boolean isLogin = false;
		boolean quit = false;
		String menu = null;
		boolean isAdmin = false;
		String userEmail = null;
		teamInfo();
		
		while(!quit) {
			if(!isLogin) {
				firstMenu();
				try {
					System.out.print("메뉴 선택 : ");
					int num = Integer.parseInt(sc.nextLine());
					switch(num) {
						case 1: 
							menu = "회원가입";
							break;
						case 2: 
							menu = "로그인";
							break;
						case 3: 
							menu = "관리자 로그인";
							break;	
						case 4: 
							menu = "종료";
							break;
						default:
							System.out.println("올바른 메뉴를 입력해주세요.");
							break;
					}//end of switch
					
				}catch(InputMismatchException e) {
					System.out.println("숫자를 입력해주세요");
					quit = true;
				}catch(Exception e) {
					System.out.println("잘못된 메뉴 선택으로 종료");
					quit = true;
				}
				
			} else {
				if(isAdmin) {
					adminMenu();
					try {
						System.out.print("메뉴 선택 : ");
						int num = Integer.parseInt(sc.nextLine());
						switch(num) {
						case 1: 
							menu = "팀 생성";
							break;
						case 2: 
							menu = "선수 생성";
							break;
						case 3: 
							menu = "팀 삭제";
							break;
						case 4: 
							menu = "선수 삭제";
							break;
						case 5: 
							menu = "종료";
							break;
						default:
							System.out.println("올바른 값 입력");
							break;
						}//end of switch
					} catch(InputMismatchException e) {
						System.out.println("숫자를 입력해주세요");
						quit = true;
					} catch(Exception e) {
						System.out.println("잘못된 메뉴 선택으로 종료합니다");
						quit = true;
					}
					
				} else {
					userMenu();
					try {
						System.out.print("메뉴 선택 : ");
						int num = Integer.parseInt(sc.nextLine());
						switch(num) {
						case 1: 
							myTeamMenu();
							try {
								System.out.print("메뉴 선택 : ");
								int num1 = Integer.parseInt(sc.nextLine());
								switch(num1) {
								case 1: 
									menu = "선수 방출";
									break;
								case 2: 
									menu = "선수 매각";
									break;
								case 3: 
									menu = "선수 영입";
									break;
								default: 
									System.out.println("올바른 값을 입력해주세요.");
									break;
								}
							} catch(InputMismatchException e) {
								System.out.println("숫자를 입력해주세요.");
							}
							break;
						case 2: 
							menu = "팀 목록";
							break;
						case 3: 
							menu = "이적시장";
							break;
						case 4: 
							menu = "종료";
							quit = true;
							break;
						default:
							System.out.println("올바른 값 입력");
							break;
						}//end of switch
					} catch (InputMismatchException e) {
						System.out.println("숫자를 입력해주세요");
						quit = true;
					} catch (Exception e) {
						System.out.println("잘못된 메뉴 선택으로 종료합니다");
						quit = true;
					}
					
				}
		
			}//end of if-else
			
			//각 메뉴가 작동
			switch(menu) {
				case "회원가입" : 
					joinMember();
					break;
				case "로그인" : 
					String str = userLogin();
					if(str != null) {
						System.out.println("로그인 성공");
						userEmail = str;
						isLogin = true;
					}else {
						System.out.println("로그인 실패");
					}
					break;
				case "관리자 로그인" :
					isAdmin = adminLogin();
					isLogin = isAdmin;
					break;
				case "팀 목록" :
					showMyTeam(userEmail);
					break;
				case "이적시장" : 
					displayFreeAgent();
					break;
				case "팀 생성" : 
					createTeam();
					break;
				case "선수 생성" : 
					createPlayer();
					break;
				case "팀 삭제" : 
					deleteTeam();
					break;
				case "선수 삭제" : 
					deletePlayer();
					break;
				case "선수 방출" : 
					releasePlayer(userEmail);
					break;
				case "선수 매각" : 
					sellPlayer(userEmail);
					break;
				case "선수 영입" : 
					buyPlayer(userEmail);
					break;
				case "종료" : 
					System.out.println("종료");
					quit = true;
					break;
				default : break;
			
			}//end of switch(menu)
		}//end of while
	}//end of main
	
	//유저의 팀 선수 목록 출력
	private static void showMyTeam(String userEmail) {
		readUserFromFile();
		for(User data : userList) {
			if(data.getEmail().equals(userEmail)) {
				System.out.println(data);
				return;
			}
		}
	}


	//유저의 팀 선수 영입
	private static void buyPlayer(String userEmail) {
		displayFreeAgent();
		System.out.println("구매할 선수 번호를 입력하세요(숫자입력) : ");
		int playerIndex = Integer.parseInt(sc.nextLine());
		
		Player selectedPlayer = faPlayerList.get(playerIndex - 1);
		int buyPrice = selectedPlayer.getPrice();
		for(User user : userList) {
			if(user.getEmail().equals(userEmail)) {
				user.buyPlayer(selectedPlayer, buyPrice);
				writeUserToFile();
			}
		}
	}

	// user의 팀 선수 판매
	private static void sellPlayer(String userEmail) {
		readUserFromFile();
		for(User user : userList) {
			if(user.getEmail().equals(userEmail)) {
				System.out.println("보유한 선수 목록");
				for(int i=0; i<user.getUserPlayerList().size(); i++) {
					System.out.println((i+1) + ". "  + user.getUserPlayerList().get(i));
				}
				
				if(user.getUserPlayerList().isEmpty()) {
					System.out.println("판매할 선수가 없습니다.");
					return;
				}
				System.out.print("판매할 선수를 선택하세요 : ");
				int playerIndex = Integer.parseInt(sc.nextLine());
				if(playerIndex < 1 || playerIndex > user.getUserPlayerList().size()) {
					System.out.println("잘못된 번호입니다.");
					return;
				}
				Player selectedPlayer = user.getUserPlayerList().get(playerIndex - 1);
				
				int sellPrice = selectedPlayer.getPrice();
				user.sellPlayer(selectedPlayer, sellPrice);
				writeUserToFile();
			}
		}
	}

	// user의 팀 선수 방출
	private static void releasePlayer(String userEmail) {
		readUserFromFile();
		for(User user : userList) {
			if(user.getEmail().equals(userEmail)) {
				if (user.getUserPlayerList().isEmpty()) {
					System.out.println("삭제할 선수가 없습니다.");
					return;
				}
				System.out.println("삭제할 선수를 선택하세요.");
				for (int i = 0; i < user.getUserPlayerList().size(); i++) {
					System.out.println((i + 1) + ". " + user.getUserPlayerList().get(i).getPlayerName());
				}
				
				int playerIndex = -1;
				while (playerIndex < 0 || playerIndex >= user.getUserPlayerList().size()) {
					System.out.print("선수 선택 : ");
					if (sc.hasNextInt()) {
						playerIndex = Integer.parseInt(sc.nextLine());
						if (playerIndex < 1 || playerIndex > user.getUserPlayerList().size()) {
							System.out.println("잘못된 선수 선택입니다.");
							playerIndex = -1;
						}
					} else {
						System.out.println("숫자를 입력하세요.");
					}
				}
				Player deletedPlayer = user.getUserPlayerList().remove(playerIndex - 1);
				System.out.println(deletedPlayer.getPlayerName() + " 선수가 삭제되었습니다.");
				writeUserToFile();
			}
		}
	}


	// 관리자 선수 삭제
	private static void deletePlayer() {
		readFaPlayerFromFile();
		displayFreeAgent();

        System.out.print("삭제할 선수 선택(숫자입력) : ");
        int playerIndex = Integer.parseInt(sc.nextLine());
        if (playerIndex < 1 || playerIndex > faPlayerList.size()) {
            System.out.println("잘못된 선수 선택입니다.");
            return;
        }
        faPlayerList.remove(playerIndex-1);
        System.out.println("선수가 성공적으로 삭제되었습니다.");
       
        writeFaPlayerToFile();
	}

	//자유 이적시장 선수 목록 출력
	private static void displayFreeAgent() {
		readFaPlayerFromFile();
		System.out.println("자유 이적시장 선수 목록");
		for (int i = 0; i < faPlayerList.size(); i++) {
            System.out.println((i + 1) + ". " + faPlayerList.get(i));
        }
	}


	// 관리자 팀 삭제
	private static void deleteTeam() {
		readTeamFromFile();
		if (teamList.isEmpty()) {
            System.out.println("삭제할 팀이 없습니다.");
            return;
        }

        System.out.println("삭제할 팀을 선택하세요");
        for (int i = 0; i < teamList.size(); i++) {
            System.out.println((i + 1) + ". " + teamList.get(i).getTeamName());
        }
        System.out.print("팀 선택 : ");
        int teamIndex = Integer.parseInt(sc.nextLine());

        if (teamIndex < 1 || teamIndex > teamList.size()) {
            System.out.println("잘못된 팀 선택입니다.");
            return;
        }

        Team deletedTeam = teamList.remove(teamIndex - 1);
        System.out.println(deletedTeam.getTeamName() + " 팀이 삭제되었습니다.");
		
        writeTeamToFile();
		
	}


	// 관리자 이적시장 선수 생성
	private static void createPlayer() {
		
		System.out.print("선수 이름을 입력하세요 : ");
        String playerName = sc.nextLine();
        System.out.print("선수 등번호를 입력하세요 : ");
        int backNum = Integer.parseInt(sc.nextLine());
        System.out.print("선수 shoot 능력치를 입력하세요 : ");
        int shoot = Integer.parseInt(sc.nextLine());
        System.out.print("선수 pass 능력치를 입력하세요 : ");
        int pass = Integer.parseInt(sc.nextLine());
        System.out.print("선수 포지션을 입력하세요(gk,df,mf,fw): ");
        String playerPosition = sc.nextLine();		
        System.out.print("선수 몸값을 입력하세요 : ");
        int price = Integer.parseInt(sc.nextLine());
		
        Player player = new Player(playerName, backNum, shoot, pass, playerPosition, price);
        faPlayerList.add(player);
        System.out.println(playerName + " 선수 생성 완료");
        writeFaPlayerToFile();
	}


	// 관리자 팀 생성
	private static void createTeam() {
		System.out.println("팀을 선택하세요");
		for (int i = 0; i < teamList.size(); i++) {
			System.out.println((i + 1) + ". " + teamList.get(i).getTeamName());
		}
		
		System.out.print("팀 이름을 입력하세요 : ");
		String teamName = sc.nextLine();
		
		Team selectedTeam = null;
		for (Team team : teamList) {
			if(team.getTeamName().equalsIgnoreCase(teamName)) {
				selectedTeam = team;
				break;
			}
		}
		
		if(selectedTeam == null) {
			selectedTeam = new Team(teamName);
			teamList.add(selectedTeam);
			System.out.println(teamName + " 팀이 생성됨");
		}
		
		System.out.print("선수의 이름을 입력하세요 : ");
        String playerName = sc.nextLine();
        System.out.print("선수의 등번호를 입력하세요 : ");
        int playerNumber = Integer.parseInt(sc.nextLine());
        System.out.print("선수의 슛 능력치를 입력하세요 : ");
        int shoot = Integer.parseInt(sc.nextLine());
        System.out.print("선수의 패스 능력치를 입력하세요 : ");
        int pass = Integer.parseInt(sc.nextLine());
        System.out.print("선수의 포지션을 입력하세요 : ");
        String playerPosition = sc.nextLine();
        System.out.print("선수의 몸값을 입력하세요 : ");
        int price = Integer.parseInt(sc.nextLine());
        

        System.out.print("팀 선택 : ");
        int teamIndex = Integer.parseInt(sc.nextLine());

        if (teamIndex < 1 || teamIndex > teamList.size()) {
            System.out.println("잘못된 팀 선택입니다.");
            return;
        }

        Player player = new Player(playerName, playerNumber, shoot, pass, playerPosition, price);
        selectedTeam.addPlayer(player);
        System.out.println(playerName + " 선수가 " + selectedTeam.getTeamName() + " 팀에 추가되었습니다.");
        
        writeTeamToFile();
        
	}

	// 관리자 로그인 메서드
	private static boolean adminLogin() {
		boolean isAdmin = false;
		try {
			System.out.println("관리자 정보를 입력하세요");
			System.out.print("이메일 입력 : ");
			String email = sc.nextLine();
			System.out.print("비밀번호 입력 : ");
			String pw = sc.nextLine();
			
			if(admin == null) {
				System.out.println("관리자 정보가 올바르게 설정되지 않았습니다.");
				return false;
			}
			
			if(admin.isLoginValid(email, pw)) {
				System.out.println("관리자 로그인 성공");
				isAdmin = true;
			}else {
				System.out.println("관리자 로그인 실패");
			}
		} catch (Exception e) {
			System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
		}
		return isAdmin;
	}

	// 유저 로그인 메서드
	public static String userLogin() {
		readUserFromFile();
		try {
			System.out.print("이메일 입력 : ");
			String email = sc.nextLine();
			System.out.print("비밀번호 입력 : ");
			String pw = sc.nextLine();
			
			if(isUserValid(email, pw)) {
				return email;
			}	
		}catch(Exception e) {
			System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
		}
		return null;
	}
	

	private static boolean isUserValid(String email, String pw) {
		for(User data : userList) {
			if((data.getEmail().equals(email)) && data.getPw().equals(pw)) {
				return true;
			}
		}
		return false;
	}

	//회원 가입 메서드
	public static void joinMember() {
		readUserFromFile();
		while(true) {
			try {
				System.out.print("이메일을 입력하세요 : ");
				String email = sc.nextLine();
				
				if(isEmailExists(email)) {
					System.out.println("이미 계정이 존재합니다");
					continue;	
				}
				
				System.out.print("비밀번호를 입력하세요 : ");
				String pw = sc.nextLine();
				System.out.println("초기 팀을 선택하세요");
		        for (int i = 0; i < teamList.size(); i++) {
		            System.out.println((i + 1) + ". " + teamList.get(i).getTeamName());
		        }
		        System.out.print("선택(숫자입력) : ");
		        int teamIndex = Integer.parseInt(sc.nextLine());
		        if (teamIndex < 1 || teamIndex > teamList.size()) {
		            System.out.println("잘못된 팀 선택입니다.");
		            return;
		        }
		        Team selectedTeam = teamList.get(teamIndex - 1);
		        User user = new User(email, pw, selectedTeam.getTeamName(),selectedTeam.getPlayerList());
		        userList.add(user);
				writeUserToFile();
				System.out.println("회원가입이 완료되었습니다");
				break;
			} catch(Exception e) {
				System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
			}
		}
	}
	
	private static boolean isEmailExists(String email) {
		for(User data : userList) {
			if(data.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	//User 정보를 USERFILE에 저장
	private static void writeUserToFile() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(USER_FILE));
			oos.writeObject(userList);
		} catch (IOException e) {
			System.out.println("파일을 쓰는 도중 오류가 발생했습니다: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					System.out.println("파일을 닫는 도중 오류가 발생했습니다: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	// user.dba 에서 User 정보 불러옴
	private static void readUserFromFile() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(USER_FILE));
			userList = (ArrayList<User>)ois.readObject();
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			System.out.println("파일을 읽는 도중 오류가 발생했습니다: " + e.getMessage());
			e.printStackTrace();
		
		} catch (ClassNotFoundException e) {
			System.out.println("클래스를 찾을 수 없습니다: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					System.out.println("파일을 닫는 도중 오류가 발생했습니다: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	
	// team.dba 에서 team 정보 불러옴
	private static void readTeamFromFile() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(TEAM_FILE));
			teamList = (ArrayList<Team>)ois.readObject();
		} catch (IOException e) {
			System.out.println("파일을 읽는 도중 오류가 발생했습니다: " + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("클래스를 찾을 수 없습니다: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					System.out.println("파일을 닫는 도중 오류가 발생했습니다: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	// team 정보를 team.dba에 저장
	private static void writeTeamToFile() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(TEAM_FILE));
			oos.writeObject(teamList);
		} catch (IOException e) {
			System.out.println("파일을 쓰는 도중 오류가 발생했습니다: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					System.out.println("파일을 닫는 도중 오류가 발생했습니다: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	// 자유이적시장 선수정보를 파일에서 불러옴
	private static void readFaPlayerFromFile() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(MARKET_FILE));
			faPlayerList = (ArrayList<Player>)ois.readObject();
		} catch (IOException e) {
			System.out.println("파일을 읽는 도중 오류가 발생했습니다: " + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("클래스를 찾을 수 없습니다: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if(ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					System.out.println("파일을 닫는 도중 오류가 발생했습니다: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	
	// 자유이적시장 선수 정보를 파일에 저장함
		private static void writeFaPlayerToFile() {
			ObjectOutputStream oos = null;
			try {
				oos = new ObjectOutputStream(new FileOutputStream(MARKET_FILE));
				oos.writeObject(faPlayerList);
			} catch (IOException e) {
				System.out.println("파일을 쓰는 도중 오류가 발생했습니다: " + e.getMessage());
				e.printStackTrace();
			} finally {
				if(oos != null) {
					try {
						oos.close();
					} catch (IOException e) {
						System.out.println("파일을 닫는 도중 오류가 발생했습니다: " + e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
		
	//내 팀 관리 메뉴 화면
	public static void myTeamMenu() {
		System.out.println("========================================");
		System.out.println("\t\t1. 선수 방출");
		System.out.println("\t\t2. 선수 매각");
		System.out.println("\t\t3. 선수 영입");
		System.out.println("========================================");
	}
	//관리자 메뉴 화면
	public static void adminMenu() {
		System.out.println("========================================");
		System.out.println("\t\t1. 팀 생성");
		System.out.println("\t\t2. 선수 생성");
		System.out.println("\t\t3. 팀 삭제");
		System.out.println("\t\t4. 선수 삭제");
		System.out.println("\t\t5. 종료");
		System.out.println("========================================");
	}
	//유저 메뉴 화면
	public static void userMenu() {
		System.out.println("========================================");
		System.out.println("\t\t1. 내 팀 관리");
		System.out.println("\t\t2. 팀 목록");
		System.out.println("\t\t3. 이적시장");
		System.out.println("\t\t4. 종료");
		System.out.println("========================================");
	}
	//초기 출력 화면
	public static void firstMenu() {
		System.out.println("========================================");
		System.out.println("\t\t1. 회원가입");
		System.out.println("\t\t2. 로그인");
		System.out.println("\t\t3. 관리자 로그인");
		System.out.println("\t\t4. 종료");
		System.out.println("========================================");
	}
	
	public static void teamInfo() {
		Team team1 = new Team("Tottenham Spurs");
		team1.addPlayer(new Player("Heung Min Son", 7, 98, 80, "fw", 98));
		team1.addPlayer(new Player("Richarlison", 9, 79, 72, "fw", 46));
		team1.addPlayer(new Player("Brennan Johnson", 22, 74, 67, "fw", 36));
		team1.addPlayer(new Player("James Maddison", 10, 81, 86, "mf", 88));
		team1.addPlayer(new Player("Rodrigo Bentancur", 30, 67, 81, "mf", 39));
		team1.addPlayer(new Player("Pierre-Emile Højbjerg", 5, 73, 78, "mf", 52));
		team1.addPlayer(new Player("Pedro Porro", 23, 73, 77, "df", 44));
		team1.addPlayer(new Player("Cristian Romero", 17, 46, 59, "df", 85));
		team1.addPlayer(new Player("Micky van de Ven", 37, 43, 59, "df", 26));
		team1.addPlayer(new Player("Destiny Udogie", 4, 63, 69, "df", 39));
		team1.addPlayer(new Player("Guglielmo Vicario", 13, 98, 80, "gk", 39));

		Team team2 = new Team("Manchester City");
		team2.addPlayer(new Player("Erling Haaland", 9, 93, 66, "fw", 195));
		team2.addPlayer(new Player("Phil Foden", 47, 79, 82, "fw", 117));
		team2.addPlayer(new Player("Jack Grealishn", 10, 76, 84, "fw", 156));
		team2.addPlayer(new Player("Kevin De Bruyne", 17, 88, 94, "mf", 208));
		team2.addPlayer(new Player("Rodri", 16, 73, 80, "mf", 114));
		team2.addPlayer(new Player("Bernardo Silva", 20, 78, 86, "mf", 156));
		team2.addPlayer(new Player("Kyle Walker", 2, 63, 77, "df", 91));
		team2.addPlayer(new Player("Nathan Aké", 6, 53, 72, "df", 83));
		team2.addPlayer(new Player("Joško Gvardiol", 24, 54, 69, "df", 104));
		team2.addPlayer(new Player("Sergio Gómez", 21, 70, 75, "df", 39));
		team2.addPlayer(new Player("Stefan Ortega", 18, 73, 86, "gk", 28));
		
		teamList.add(team1);
		teamList.add(team2);
		
		writeTeamToFile();
	}

}
