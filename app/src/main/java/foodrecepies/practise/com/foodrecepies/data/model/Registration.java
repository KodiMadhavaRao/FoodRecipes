package foodrecepies.practise.com.foodrecepies.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madhav on 2/28/2018.
 */

public class Registration {
    private String displayName;
    private String email;
    private String password;
    private String gender;
    private String birthday;
    private String country;
    private List<String> intrestedIn = new ArrayList<>();

    public Registration(String displayName, String email, String password, String gender, String birthday, String country, List<String> intrestedIn) {
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.country = country;
        this.intrestedIn = intrestedIn;
    }

    public Registration() {

    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getIntrestedIn() {
        return intrestedIn;
    }

    public void setIntrestedIn(List<String> intrestedIn) {
        this.intrestedIn = intrestedIn;
    }
}
