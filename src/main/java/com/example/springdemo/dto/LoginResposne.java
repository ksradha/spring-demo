package com.example.springdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResposne {

    
	public LoginResposne(String message, Boolean status) {
		super();
		this.message = message;
		this.status = status;
	}
	private String message;
    Boolean status;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "LoginResposne [message=" + message + ", status=" + status + "]";
	}
	public LoginResposne() {
		super();
	}
    
    
}
