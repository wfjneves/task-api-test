package br.ce.wcaquino.apitest;

import java.time.LocalDate;

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
		.body("{ \"task\":\"novo\", \"dueDate\": \""+LocalDate.now().plusDays(1)+"\" } ")
		.contentType(ContentType.JSON) 
		.when()
		.post("/todo")
		.then()
		.statusCode(201);
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured
		.given()
		.body("{ \"task\":\"data passada\", \"dueDate\": \"2010-05-08\" } ")
		.contentType(ContentType.JSON) 
		.when()
		.post("/todo")
		.then()
		.statusCode(400)
		.body("message", CoreMatchers.is("Due date must not be in past"));
	}
	
	
	@Test
	public void deveRemoverTarefaComSucesso() {
		//inserir 
		
		Integer id = RestAssured
		.given()
		.body("{ \"task\":\"novo para excluir\", \"dueDate\": \""+LocalDate.now().plusDays(1)+"\" } ")
		.contentType(ContentType.JSON) 
		.when()
		.post("/todo")
		.then()
		.statusCode(201)
		.extract().<Integer>path("id");
		
		System.out.println(id);
		
		RestAssured
		.given()
		.when()
		.delete("/todo/"+id)
		.then()
		.statusCode(204);
	}	
}
