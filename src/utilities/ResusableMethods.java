package utilities;
import io.restassured.path.json.JsonPath;

public class ResusableMethods {

	public static JsonPath rawstringToJson(String string_response_from_api) {
		return new JsonPath(string_response_from_api);
	}
	
	
}
