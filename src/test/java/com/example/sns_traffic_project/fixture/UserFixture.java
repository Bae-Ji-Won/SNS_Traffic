package com.example.sns_traffic_project.fixture;


import com.example.sns_traffic_project.domain.User;

public class UserFixture {
    public static User get(String userName, String pw){
        User user = User.of(userName, pw);

        return user;
    }
}
