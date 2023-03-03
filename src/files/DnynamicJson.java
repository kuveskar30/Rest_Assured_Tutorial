package files;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utilities.ResusableMethods;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DnynamicJson {
	
	@Test
	public void addBook() {
		//most of the time json payload is static
		
		//we are not allowed to add same books in this api provided by rahul shetty
		//so every time we need to add unique data of isbn and aisle no.
		//otherwise api retyrn error of book already added
		RestAssured.baseURI = "http://216.10.245.166";
		
		//Dynamic json with external data input from test
		String add_book_response = given().log().all().header("Content-Type", "application/json")
				.body(CustomPayloads.addBookPayload("isbn1","aisle1"))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ResusableMethods.rawstringToJson(add_book_response);
		
		System.out.println(js.getString("ID"));
		
		
	}
	
	
	
	
}
