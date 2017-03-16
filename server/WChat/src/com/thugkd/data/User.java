package com.thugkd.data;

/**
 * Created with IntelliJ IDEA
 *
 * @Description:
 * @author: thugkd
 * @date: 15/03/2017 14:52
 */
public class User {
    private String phone;
    private String password;
    private String image;
    private String nickName;
    private int sex;

    public User(){

    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
