package com.example.demo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DemoApplicationTests {

  // TODO: Move to config file
  public static final String BASE_URI = "http://jsonplaceholder.typicode.com";


  @Before
  public void setUp() {
    // set endpoint
    RestAssured.baseURI = BASE_URI;
  }

	@Test
  public void searchPost() {

    RestAssured.baseURI = BASE_URI;
    given()
        .contentType("application/json")
        .accept("application/json")
        .when()
        .get("/posts/1")
        .then()
        .statusCode(200)
        .contentType("application/json")
        .body("userId", equalTo(1))
        .body("id", equalTo(1))
        .body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
        .body("body", equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
  }

  @Test
  public void searchUser() {
    RestAssured.baseURI = BASE_URI;
    Response response = given()
        .contentType("application/json")
        .accept("application/json")
        .queryParam("username", "Samantha")
        .when()
        .get("/users")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .extract().response();

    JsonPath jsonPathEvaluator = response.jsonPath();
    List<User> userList = jsonPathEvaluator.getList("", User.class);
    for (User user: userList) {
      System.out.println("Username: " + user.getUsername());
    }


  }

}
