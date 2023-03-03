package oAuthForGoogleFacebook;

import static io.restassured.RestAssured.given;

import deserializationConcept.DeserializationPojoClass;
import io.restassured.parsing.Parser;

public class Summary2 {
//OAuth Url info:
	
	//query parameters:
	//1.scope : application/client asking for details like name, email, id etc. from google/facebook etc.
	// for different info asked different scope may be there
	
	//2.auth_url :which server we are trying to authorize ourself
	
	//3.client_id: when client first time registered with google he will get client_id,
	//secret_client_id etc.
	
	//4. response_code: what type of response we are expecting, e.g. otp
	
	//5. redirect_url: where google should send back respone
	
	//6. state: it is optional parameter used for security purpose
	
	//7. code: required for getting access token
}


