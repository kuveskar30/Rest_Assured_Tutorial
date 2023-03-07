package graphQL;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class GraphQlScript {

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String locationName = "Thane";
		String resString = given().log().all().header("Content-Type", "application/json")
		//json for body can be obtained from network tab
		.body("{\"query\":\"mutation {\\n  createLocation(location: {name: \\\""+locationName+"\\\", type: \\\"village\\\", dimension: \\\"24x2kmSquare\\\"}) {\\n    id\\n  }\\n  createCharacter(character: {name: \\\"john\\\", type: \\\"villian\\\", status: \\\"single\\\", species: \\\"human\\\", gender: \\\"M\\\", image: \\\"img1\\\", originId: 1778, locationId: 1778}) {\\n    id\\n  }\\n}\\n\",\"variables\":null}")
		.post("/gq/graphql")
		.then().log().all().extract().asString();
		System.out.println(resString);
		
		JsonPath js = new JsonPath(resString);
		int locationId = js.getInt("data.createLocation.id");
		
		given().log().all().header("Content-Type", "application/json")
		.body("{\"query\":\"query {\\n location(locationId:"+locationId+"){\\n  id\\n  name\\n  type\\n  dimension\\n}\\n}\\n\",\"variables\":null}")
		.when().post("/gq/graphql")
		.then().log().all();

	}

}
