package com.nfteam.server;

import io.jsonwebtoken.io.IOException;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;


@RunWith(SpringRunner.class)
@SpringBootTest
class ServerApplicationTests {

//	@Before
//	public void Setup(){
//		RestAssured.port=8082;
//	}
//
//	@Test
//	public void BasicPathIndexHtmlCall( )throws IOException{
//		given()
//				.when()
//				.get("/")
//				.then()
//				.statusCode(200)
//				.contentType("text/html")
//				.body(containsString("권한관리"));
//	}
}
