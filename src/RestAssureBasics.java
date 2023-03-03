import io.restassured.RestAssured;



import io.restassured.path.json.JsonPath;
import utilities.ResusableMethods;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.CustomPayloads;
public class RestAssureBasics {

	public static void main(String[] args) {
		//validate if addplace api is working as expected
		
		//given - all input details
		//when  - submit the api
		//then  - validate the response

		//for equalTo() method hamcrest package is imported
		//static packages need to be imported manually eclipse will not show suggestions for
		//such packages
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//AddPlace
		String respone = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(CustomPayloads.addPlacePayload())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		
		System.out.println(respone);
		String new_address = "Near central park"; 
		//extract place_id from response
		//for performing operations on json object string we are using JsonPath class
		JsonPath js = ResusableMethods.rawstringToJson(respone);//for parsing json object string
		String place_id = js.getString("place_id");
		
		//update place
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+place_id+"\",\r\n"
				+ "\"address\":\""+new_address+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//get_place:
		//we haven't used header here as we don't send body in GET method we don't specify 
		//content type
		
		String get_place_response = given().log().all().queryParam("key", "qaclick123").queryParams("place_id", place_id)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js2 = ResusableMethods.rawstringToJson(get_place_response);
		String actual_address = js2.getString("address");
		System.out.println(actual_address);
		
		Assert.assertEquals(actual_address, new_address);
	
		
		//place_id:
		//9db351895a589b5e634a837392b29eec
		//2f16fd2f7676647fad206050ce507aa9
		//69e59f192af4d71ac7ab462eef9052a0
		//5162e67b226e0f1b130de772784836a4

	}

}
