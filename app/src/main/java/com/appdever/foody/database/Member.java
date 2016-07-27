package com.appdever.foody.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by arisak on 25/7/2559.
 */
public class Member extends RealmObject{
    @PrimaryKey
    String MemberID;
    String UserName;
    String Password;
    String Name;
    String Tel;
    String Email;
    String Pic;

    public String getMemberID() {
        return MemberID;
    }

    public void setMemberID(String memberID) {
        MemberID = memberID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }
}
