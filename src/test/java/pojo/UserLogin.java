package pojo;

public class UserLogin {

	private String userLoginEmailId;
    private String password;

    public UserLogin() {}

    public UserLogin(String userLoginEmailId, String password) {
        this.userLoginEmailId = userLoginEmailId;
        this.password = password;
    }

    public String getUserLoginEmailId() {
        return userLoginEmailId;
    }

    public void setUserLoginEmailId(String userLoginEmailId) {
        this.userLoginEmailId = userLoginEmailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
