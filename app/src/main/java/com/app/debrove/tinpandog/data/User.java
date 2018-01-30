package com.app.debrove.tinpandog.data;

import org.litepal.annotation.Column;
import org.litepal.annotation.Encrypt;
import org.litepal.crud.DataSupport;

/**
 * Created by debrove on 2017/11/12.
 * Package Name : com.app.debrove.tinpandog.data
 */

public class User extends DataSupport {

    @Column(unique = true)
    private String telephone;//电话

    private String number;//学号

    private String name;//姓名

    @Encrypt(algorithm = AES)
    private String password;//密码

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
