package eComEndToEnd;

import eComEndToEndPojoClasses.LoginRequestPojoClass;
import eComEndToEndPojoClasses.LoginResponsePojoClass;
import eComEndToEndPojoClasses.OrderDetails;
import eComEndToEndPojoClasses.Orders;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EcommApiTest {

	public static void main(String[] args) {
		
		//RequestSpecBuilder
		RequestSpecification req_spec_builder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
		
		//1. login to app usin API
		LoginRequestPojoClass login_request = new LoginRequestPojoClass();
		login_request.setUserEmail("pratikkuveskar@gmail.com");
		login_request.setUserPassword("Pratik@123");
		//a.login request
		RequestSpecification login_req_spec = given().log().all()
													 .spec(req_spec_builder)
												     .body(login_request);
		//b.login response
		LoginResponsePojoClass login_res = login_req_spec.when().post("/api/ecom/auth/login")
					  .then().log().all().extract().response().as(LoginResponsePojoClass.class);

		String token = login_res.getToken();
		String userId = login_res.getUserId();
		System.out.println(login_res.getToken());
		System.out.println(login_res.getUserId());
		
		//2. add new product
		//RequestSpecBuilder for addProduct
		RequestSpecification addProductReqSpecBuilder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.MULTIPART).addHeader("Authorization", token).build();
		
		//here we are sending data as multipart/form-data parameters
		//so we have used .param for data sending as key vaule pair and
		//.multipart for sending file attachment
		
		//a.addProduct Request
		RequestSpecification addProductReqSpec =given().log().all().spec(addProductReqSpecBuilder)
											  .param("productName", "Laptop")
											  .param("productAddedBy", userId)
											  .param("productCategory", "electronics")
											  .param("productSubCategory", "computers")
											  .param("productPrice", "62000")
											  .param("productDescription", "lenovo gaming 3")
											  .param("productFor", "anyone")
											  .multiPart("productImage",new File("E:\\pratik30\\Software testing\\API Testing\\laptop_image.jpg"));
		//b.addProduct String response
		String addProductStringRes = addProductReqSpec.when().post("/api/ecom/product/add-product")
		.then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(addProductStringRes);
		String productId = js1.getString("productId");
		
		//3. place order
		//RequestSpecBuilder
		RequestSpecification placeOrderReqSpecBuilder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addHeader("Authorization", token).setContentType(ContentType.JSON).build();		
		//orders pojo class
		OrderDetails orderDetails1 = new OrderDetails();
		orderDetails1.setCountry("India");
		orderDetails1.setProductOrderedId(productId);
		
		List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
		orderDetailsList.add(orderDetails1);
		
		Orders orders = new Orders();
		orders.setOrders(orderDetailsList);
		
		//a. place order request
		RequestSpecification placeOrderReqSpec = given().log().all()
				.spec(placeOrderReqSpecBuilder).body(orders);
		//b. place order response string
		String placeOrderResString = placeOrderReqSpec.when().post("/api/ecom/order/create-order")
		.then().log().all().extract().response().asString();
		
//		JsonPath js2 = new JsonPath(placeOrderResString);
		
		//4. Delete product
		RequestSpecification deleteOrderReqSpecBuilder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		RequestSpecification deleteOrderReqSpec =given().spec(deleteOrderReqSpecBuilder).log().all().pathParam("productId", productId);
		
		Response deleteOrder = deleteOrderReqSpec.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response();
		
		
	}

}
