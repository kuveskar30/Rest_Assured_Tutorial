package dynamicJsonAndLibraryApiExample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class DnynamicJson3 {
//pulling json from external json file	
	

	@Test
	public void extractJsonFromFile() throws IOException {
//		JsonPath js = new JsonPath(CustomPayloads.dummyjsonResponse());
		String path = System.getProperty("user.dir")+"\\src\\files\\staticJsonFile.json";
		JsonPath js = new JsonPath(new String(Files.readAllBytes(Paths.get(path))));
		
		//1. counting no of courses
		int courses_size = js.getInt("courses.size()");
		//size method in JsonPath is used for counting arrays
		System.out.println(courses_size);
		
		//2. Print all course titles and their respective prices
		for(int i=0;i<courses_size; i++) {
			System.out.println(js.getString("courses["+i+"].title"));
			System.out.println(js.getInt("courses["+i+"].price"));
		}
	}
	

	
	
}
