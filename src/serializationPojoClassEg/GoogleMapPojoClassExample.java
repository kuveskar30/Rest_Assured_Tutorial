package serializationPojoClassEg;
import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import io.restassured.RestAssured;


public class GoogleMapPojoClassExample {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//constructing json by java pojo class
		AddPlacePojoClass add_place_data = new AddPlacePojoClass();
		
		add_place_data.setAccuracy(50);
		add_place_data.setName("datta mandir");
		add_place_data.setPhone_number("(+91) 983 893 3937");
		add_place_data.setAddress("near datta nagar, virar road");
		add_place_data.setWebsite("http://google.com");
		add_place_data.setLanguage("French-IN");
		
		List<String> types = new ArrayList<String>(Arrays.asList("central park","ashirwad bangla"));
		add_place_data.setTypes(types);
		
		Location ln = new Location();
		ln.setLat(-38.383494);
		ln.setLng(33.427362);
		add_place_data.setLocation(ln);
		
		
		
		String res = given().queryParam("key", "qaclick123").body(add_place_data)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(res);
		
	}
	
	
}
