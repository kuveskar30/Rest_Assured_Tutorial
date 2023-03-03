package serializationConcept;
//below is structure of a POJO class
//its structure depends on response/request made by API
public class PojoClassExample {
	
	public static void main(String[] args) {
		PojoClassExample pojo_object = new PojoClassExample();
				pojo_object.setMessage("hello");
				pojo_object.setContact("1234");
				
				//now we can send pojo_object as request body
				//it will be sent as json object by Java
				//this whole process is called serialization

	}

	private String message;
	private String contact_no;
	
	//getter for message
	public String getMessage() {
		return message;
	}
	//setter for message
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getContact() {
		return contact_no;
	}
	public void setContact(String contact_no) {
		this.contact_no = contact_no;
	}
	
}
