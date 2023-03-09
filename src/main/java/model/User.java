package model;

import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private String email;
    private String password;
    private String name;

    public static User createRandomUser(){
        User user = new User();
        String randomName = RandomStringUtils.randomAlphabetic(10);
        user.setEmail(randomName.toLowerCase()+"@mail.com");
        user.setName(randomName.toLowerCase());
        user.setPassword("12345678");
        return user;
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
