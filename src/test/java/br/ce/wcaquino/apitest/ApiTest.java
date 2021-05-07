package br.ce.wcaquino.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI ="http://localhost:8001/tasks-backend";
	}

	@Test
	public void deveRetornarTarefa() {
		RestAssured
		.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured
		.given()
		.body("{ \"task\":\"novo\", \"dueDate\": \"2021-05-08\" } ")
		.contentType(ContentType.JSON) 
		.log().all()
		.when()
		.post("/todo")
		.then()
		.log().all()
		.statusCode(201);
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured
		.given()
		.body("{ \"task\":\"data passada\", \"dueDate\": \"2010-05-08\" } ")
		.contentType(ContentType.JSON) 
		.log().all()
		.when()
		.post("/todo")
		.then()
		.log().all()
		.statusCode(400)
		.body("message", CoreMatchers.is("Due date must not be in past"));
	}
}
