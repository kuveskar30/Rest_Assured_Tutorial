package dynamicJsonAndLibraryApiExample;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.CustomPayloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utilities.ResusableMethods;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DnynamicJson2 {
	//we are not allowed to add same books in this api provided by rahul shetty
			//so every time we need to add unique data of isbn and aisle no.
			//otherwise api retyrn error of book already added
	@Test(dataProvider="bookData")
	public void addBook(String isbn,String aisle) {
		
		RestAssured.baseURI = "http://216.10.245.166";
		
		//Parameterize API with multiple data sets using @Dataprovider
		String add_book_response = given().log().all().header("Content-Type", "application/json")
				.body(CustomPayloads.addBookPayload(isbn,aisle))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ResusableMethods.rawstringToJson(add_book_response);
		
		System.out.println(js.getString("ID"));
		
		
	}
	
	@DataProvider(name="bookData")
	public Object[][] bookData() {
		return new Object[][] {{"isbn2","aisle2"},{"isbn3","aisle3"},{"isbn4","aisle4"}};
	}
	
	
	
}
