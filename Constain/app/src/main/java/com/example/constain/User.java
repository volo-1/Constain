package com.example.constain;

public class User {
    private String name,mob,pass,email,height,weight,age,heightUnit, gender;
    private long lastOrder;

    public User() {
    }

    public User(String name, String mob, String pass, String email, String height, String weight, String age, String heightUnit, String gender) {
        this.name = name;
        this.mob = mob;
        this.pass = pass;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.heightUnit = heightUnit;
        this.gender = gender;
        lastOrder = 0;
    }

    public long getLastOrder() {
        return lastOrder;
    }

    public void setLastOrder(long lastOrder) {
        this.lastOrder = lastOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(String heightUnit) {
        this.heightUnit = heightUnit;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
