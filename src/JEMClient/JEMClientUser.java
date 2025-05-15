package JEMClient;

/***********************************
 * 
 * @author Johan Engwall
 * This class defines the userobject used
 * by the JEMClient.
 ***********************************/

public class JEMClientUser {
	
	private String username;
	private String password;
	
	public JEMClientUser(String UserName, String Password){
		username = UserName;
		password = Password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String Password) {
		this.username = Password;
	}

	public String getPassword() {
		return password;
	}

	public void setLastname(String lastname) {
		this.password = lastname;
	}
}
