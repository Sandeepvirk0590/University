package com.sandeepkaur.University;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class UniversityApiTests {

	@BeforeClass
	public static void setUp() {
		RestAssured.baseURI = "http://127.0.0.1:4010";
	}

	@Test
	public void testHappyPath() {
		given().header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e").when()
				.get("http://127.0.0.1:4010/university?universityName=omnis").then().statusCode(200)
				.contentType(ContentType.JSON);
	}

	@Test
	public void testInvalidRequests() {
		given().header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e").contentType(ContentType.JSON).when()
				.post("/university").then().statusCode(422);
	}

	@Test
	public void create() {
		given().header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e").contentType(ContentType.JSON).when()
				.post("/university").then().statusCode(201);
	}

	@Test
	public void testUnauthorizedAccess() {
		given().when().get("/universities").then().statusCode(401);
	}

	@Test
	public void testMissingAuth() {
		given().when().get("/universities?__server=https://zafin-interview.io/api").then().statusCode(404);
	}

	@Test
	public void testUrlPathError() {
		given().header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e").when().get("/invalidEndpoint").then()
				.statusCode(404);
	}

	@Test
	public void testUnencodedParameter() {
		given().header("api_key", "f3c84cbb-1f9a-4b87-bb5b-2d1691b24e1e").queryParam("param", "special&character")
				.when().get("/universities").then().statusCode(200);
	}

}
