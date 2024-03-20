//User class for store users information
public class User {
    String userName;
    String pw;

    public User(String userName, String pw) {
        this.userName = userName;
        this.pw = pw;
    }

    //Getter and Setter methods
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
