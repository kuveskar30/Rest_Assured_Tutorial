package hashMapAsPayload;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class UseofHashMapAsPayload {

	@Test
	public void addPlace() {
		//most of the time json payload is static
		
		//we are not allowed to add same books in this api provided by rahul shetty
		//so every time we need to add unique data of isbn and aisle no.
		//otherwise api retry error of book already added
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//we can pass hashmap object as body restassure will convert it to json internally
		Map<String,Object> locationMap = new HashMap<String,Object>();
		locationMap.put("lat", 33.56);
		locationMap.put("lng", 33.89);
		
		String[] locationTypes = {"village", "devotional place"};
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("location", locationMap);
		map1.put("accuracy", 50);
		map1.put("name", "anandVihar");
		map1.put("phone_number", "897");
		map1.put("address", "shegav");
		map1.put("types", locationTypes);
		map1.put("website", "shegavInfo.in");
		map1.put("language", "marathi");
		

		//AddPlace
		given().log().all().queryParam("key", "qaclick123")
			.header("Content-Type", "application/json")
			.body(map1)
		.when().post("maps/api/place/add/json")
		.then().log().all();
		
	}

}
