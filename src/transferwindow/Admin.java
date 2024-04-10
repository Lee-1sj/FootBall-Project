package transferwindow;

public class Admin {
	private String email = "Admin";
	private String pw = "Admin123";
	
	public Admin() {
	}
	
	public boolean isLoginValid(String email, String pw) {
		return this.email.equals(email) && this.pw.equals(pw);
	}
	
}
