import org.testng.Assert;

import files.CustomPayloads;
import io.restassured.path.json.JsonPath;

public class WoringWithComplexJsonResponse {

	public static void main(String[] args) {
		
		//CustomPayloads.dummyjsonResponse()
		//above method is coming from files package CustomPayloads class which is made by me 
		JsonPath js = new JsonPath(CustomPayloads.dummyjsonResponse());
		
		//1. counting no of courses
		int courses_size = js.getInt("courses.size()");
		//size method in JsonPath is used for counting arrays
		System.out.println(courses_size);
		
		//2. Print purchase amount
		//dashboard.purchaseAmount <<- it is way of accessing json
		//travel parent to child using dot operator
		int total_amount = js.getInt("dashboard.purchaseAmount");
		System.out.println(total_amount);
		
		//3. Print title of first course
		String title = js.getString("courses[0].title");
		System.out.println(title);
		System.out.println("******");
		
		//4. Print all course titles and their respective prices
		for(int i=0;i<courses_size; i++) {
			System.out.println(js.getString("courses["+i+"].title"));
			System.out.println(js.getInt("courses["+i+"].price"));
		}
		
		//5. Print no of copies sold by rpa course
		String course_title = "cypress";
		for(int j=0;j<courses_size;j++) {
			if(js.getString("courses["+j+"].title").equalsIgnoreCase(course_title)) {
				System.out.println("**"+course_title+"**");
				System.out.println("no of copies= "+js.getString("courses["+j+"].copies"));
				break;
			};
		}
		
		//6. Print total sum for each course and total sum of all course
		
		int grand_toatal_for_all_course_price=0;
		for(int i=0;i<courses_size;i++) {
			int single_course_price = js.getInt("courses["+i+"].price");
			int no_of_course_copies = js.getInt("courses["+i+"].copies");
			int total_course_price = single_course_price*no_of_course_copies;
			
			grand_toatal_for_all_course_price = grand_toatal_for_all_course_price + total_course_price;
		}
		System.out.println("grand_toatal_for_all_course_price = " + grand_toatal_for_all_course_price);
		
		int actual_grand_toatal_for_all_course_price = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(actual_grand_toatal_for_all_course_price, grand_toatal_for_all_course_price);
	}

}
