package oAuthForGoogleFacebook;

public class Summary1 {

	//three types of API authentications
	//1. cookie based  -- used in jira
	//2. basic authentication
	//3. OAuth 2.0 authentication
	
	//3. OAuth 2.0 authentication:
	//OAuth has many grant types:
	
	//A. Authorization code grant type:
	//we login to different websites using google account login this is OAuth 2.0 authentication
	
	//parameters used in Oauth:
	//client_name,client_id(public),client_secret_id(confidential),resource_owner(User),
	//Resource/Authentication server (i.e. google/facebook server)

	//user sign in to google by hitting google authentication server and get code
	//application will use this code to hit google resource server in backend to get
	//access token, firstname, lastname, email contact etc.
	//application grants access to user by validating access token
	
	//Access token -- it is sent from google/facebook after authentication to client,
	//details like username,dob,userImage etc. sent based on client requirement
	
	//B. Client credentials grant type
	
	//in authorization code grant type, client was asking for info of user from google
	//so it was having step for code authentication for getting access token
	
	//but when client is asking for it's own info from google then there will be no
	//code authentication, directly access token will be sent on basis of client_id and
	//secret_client_id
	
	//only authorization code grant type contains code authentication step
	
	
}
