package foodrecepies.practise.com.foodrecepies.data.model;

/**
 * Created by madhav on 3/13/2018.
 */

public class User {
    String userName;
    String userEmail;
    String userCountry;
    String userGender;

    public User(String userName, String userEmail, String userCountry, String userGender) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userCountry = userCountry;
        this.userGender = userGender;
    }

    public User() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}
