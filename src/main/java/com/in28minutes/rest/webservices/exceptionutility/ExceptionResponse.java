package com.in28minutes.rest.webservices.exceptionutility;

import java.util.Date;


public class ExceptionResponse {
	
    private Date timrStamp;
    private String message;
    private String details;
    
	public ExceptionResponse(Date timrStamp, String message, String details) {
		super();
		this.timrStamp = timrStamp;
		this.message = message;
		this.details = details;
	}
	public Date getTimrStamp() {
		return timrStamp;
	}
	public void setTimrStamp(Date timrStamp) {
		this.timrStamp = timrStamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
    
}
