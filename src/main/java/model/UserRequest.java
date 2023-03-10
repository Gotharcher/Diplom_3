package model;

import env.Constants;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserRequest {

    public static Response createUser(User user) {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(Constants.API_USER_CREATE);
    }

    public static Response authUser(User user) {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(Constants.API_AUTH_LOGIN);
    }

    public static Response deleteUser(String authToken) {
        String URIaddress = Constants.API_USER_AUTH;
        return given()
                .header("Authorization", authToken)
                .delete(URIaddress);
    }
}
