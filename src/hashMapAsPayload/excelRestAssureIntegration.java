package hashMapAsPayload;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import driveDataFromExcel.DriveDataExcel;
import files.CustomPayloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utilities.ResusableMethods;

public class excelRestAssureIntegration {

	@Test
	public void addBook() throws IOException {
		//most of the time json payload is static
		
		//we are not allowed to add same books in this api provided by rahul shetty
		//so every time we need to add unique data of isbn and aisle no.
		//otherwise api retyrn error of book already added
		RestAssured.baseURI = "http://216.10.245.166";
		//drive data from excel
		DriveDataExcel data = new DriveDataExcel();
		ArrayList<String> testData = data.getTestData("libraryAddBookData", "RestAssured");
		System.out.println(testData);
		
		//create hashmap pass to body as payload
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("name", testData.get(1));
		map1.put("isbn", testData.get(2));
		map1.put("aisle", testData.get(3));
		map1.put("author", testData.get(4));
		//add book
		given().log().all().header("Content-Type", "application/json")
				.body(map1)
		.when().post("/Library/Addbook.php")
		.then().log().all();
		
		
		
	}

}
