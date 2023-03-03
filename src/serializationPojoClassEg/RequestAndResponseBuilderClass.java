package serializationPojoClassEg;
import static io.restassured.RestAssured.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class RequestAndResponseBuilderClass {

	public static void main(String[] args) {
		
//		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
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
		
		//RequestSpecification builder 
		RequestSpecification req_spec_builder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).addQueryParam("key", "qaclick123").build();
		//ResponseSpecification builder
		ResponseSpecification res_spec_builder= new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		
		//request part
		RequestSpecification req_spec = given().spec(req_spec_builder).body(add_place_data);
		//response part
		Response res = req_spec.when().post("/maps/api/place/add/json")
		.then().spec(res_spec_builder).extract().response();
		
		String res_string = res.asString();
		
		System.out.println(res_string);
		
	}
	
	
}
