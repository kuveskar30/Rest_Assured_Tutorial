package oAuthForGoogleFacebook;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import deserializationConcept.Api;
import deserializationConcept.DeserializationPojoClass;
import deserializationConcept.WebAutomation;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuthTest {

	public static void main(String[] args) {

		
		
//		WebDriverManager.chromedriver().setup();
//		WebDriver d1 = new ChromeDriver();
//		
//		d1.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		d1.findElement(By.id("identifierId")).sendKeys("pratikkuveskar30"+Keys.ENTER);
//chrome not allowing above thing
		
		
		//A1.//https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php
		
		//above url is for login to rahulshetty.com through google authentication
		//google doesen't allowing login through automation
		//hence we logged in manually using above url through browser 
		//and copy pasted below URL manually
		
		//below url contains code by using this code we we get access_token
		//every time we login below url code parameter will change
		//so we need to login using above A1 url to get below url code parameter
		
		
		
		
		String copied_code = "4%2F0AWtgzh6JrV4gSznTXjLpuHFH57xdgzFTGVmXOBbhWANmJj-2gGrDcg_E1OYJtNV56XfLQw";
		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code="
					+copied_code+
					"&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";
		
		//extract code from url
		String partialcode = url.split("code=")[1];

		String code = partialcode.split("&scope")[0];

		System.out.println(code);

		//get access_token by sending extracted code through query parameters
		//to google authentication server
		String response =given().urlEncodingEnabled(false)
						.queryParams("code", code)
						.queryParams("client_id",
								"692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
						.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
						 .queryParam("scope",
						 "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")
						.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
						.queryParam("grant_type", "authorization_code")
						.when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		System.out.println(response);

		JsonPath jsonPath = new JsonPath(response);

		String accessToken = jsonPath.getString("access_token");

		System.out.println(accessToken+"****");

//		String r2 = given().header("Content-Type", "application/json")
//				.queryParams("access_token", accessToken)
//				.when()
//				.get("https://rahulshettyacademy.com/getCourse.php")
//				.asString();
		
							//OR
		DeserializationPojoClass r2 = given().header("Content-Type", "application/json")
		
		.queryParams("access_token", accessToken)
		.expect().defaultParser(Parser.JSON)
		//.expect().defaultParser(Parser.JSON) help to understand pojo class about 
		//how to treat response i.e. as a json or html etc.
		.when()
		
		.get("https://rahulshettyacademy.com/getCourse.php")
		
		.as(DeserializationPojoClass.class);

		System.out.println(r2.getInstructor());
		System.out.println(r2.getCourses());
		
		System.out.println(r2.getCourses().getApi().get(0).getCourseTitle());
		
		List<Api> api_courses = r2.getCourses().getApi();
		
		String[] expected_web_automation_course_list = 
			{"Selenium Webdriver Java","Cypress","Protractor"};
		List<String> expected_web_automation_course_arraylist = Arrays.asList(expected_web_automation_course_list);
		
		ArrayList<String> actual_web_automation_course_arraylist= new ArrayList<String>();
		List<WebAutomation> w = r2.getCourses().getWebAutomation();
		
		for(int i=0;i<w.size();i++) {
			actual_web_automation_course_arraylist.add(w.get(i).getCourseTitle());
		}
		
		System.out.println(actual_web_automation_course_arraylist);
		Assert.assertTrue(expected_web_automation_course_arraylist.equals(actual_web_automation_course_arraylist));
		
		
		for(int i=0;i<api_courses.size();i++) {
			String course_title = r2.getCourses().getApi().get(i).getCourseTitle();
			if(course_title.equalsIgnoreCase("SoapUI Webservices testing")) {
				String price = r2.getCourses().getApi().get(i).getPrice();
				System.out.println(course_title+" price:="+price);
				break;
			}
		}

	}

}
