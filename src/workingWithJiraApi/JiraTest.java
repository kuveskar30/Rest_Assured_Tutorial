package workingWithJiraApi;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;
public class JiraTest {

	public static void main(String[] args) {
		
		
		RestAssured.baseURI = "http://localhost:8080";
		
		//A session filter can be used record the session id returned from the server 
		//as well as automatically apply this session id in subsequent requests.
		SessionFilter sessionFilter = new SessionFilter();
		
		//1. login to Jira
		given().relaxedHTTPSValidation().log().all().header("Content-Type", "application/json")
			.body("{ \"username\": \"pratikkuveskar30\", \"password\": \"Myjira@123\" }")
			.filter(sessionFilter)
		.when().post("/rest/auth/1/session")
		.then().log().all().assertThat().statusCode(200);
		
		//2. add comment to existing issue
		//lecture 43 in rest assured Udemy
		//{id} in post() method will look for value of id in pathParam method
		//and it will take that value
		String expected_comment_text = "Hi how are you?";
		
		String add_comment_response = given().log().all().pathParam("id", "10002").header("Content-Type", "application/json")
			.body("{\r\n"
				+ "    \"body\": \""+expected_comment_text+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}")
			.filter(sessionFilter)
		.when().post("rest/api/2/issue/{id}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js1 = new JsonPath(add_comment_response);
		String comment_id = js1.getString("id");
		
		//first we need to allow attachments through jira setting tab
		//3. add attachment
		given().log().all().filter(sessionFilter).header("X-Atlassian-Token","no-check")
		.pathParam("issueId", "10002")
		.header("Content-Type","multipart/form-data")
		.multiPart("file",new File("E:\\pratik30\\Software testing\\Eclipse_workfiled\\RestAssure_Basics\\src\\files\\sampleFileattachment.txt"))
		.when().post("/rest/api/2/issue/{issueId}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		//4. get issue
		String issue_details = given().log().all().filter(sessionFilter)
		.queryParam("fields", "comment").pathParam("issueId", "10002")
		.when().get("/rest/api/2/issue/{issueId}")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js2 = new JsonPath(issue_details);
		int comment_array_size = js2.getInt("fields.comment.comments.size()");
		
		for(int i=0;i<comment_array_size;i++) {
			String id = js2.getString("fields.comment.comments["+i+"].id");
			System.out.println(id);
			if(id.equalsIgnoreCase(comment_id)) {
				String actual_comment_text = js2.getString("fields.comment.comments["+i+"].body");
				System.out.println(expected_comment_text);
				System.out.println(comment_id);
				System.out.println(actual_comment_text);
				Assert.assertEquals(actual_comment_text, expected_comment_text);
			}
		}
		

		
		
		
	}

}
