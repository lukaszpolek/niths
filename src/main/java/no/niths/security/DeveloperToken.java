package no.niths.security;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * Wrapper class for token
 *
 */
@XmlRootElement
public class DeveloperToken {
	
	private String token;
	private String message;
	
	public DeveloperToken(){
		this("Not valid", "Email sendt with instuctions, check your inbox");
	}
	
	public DeveloperToken(String token){
		this.token = token;
	}
	public DeveloperToken(String token, String message){
		this.token = token;
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}