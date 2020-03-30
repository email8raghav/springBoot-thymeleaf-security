package com.raghav.thymeleaf.exception;


import java.util.Date;

public class ErrorMessage {
	
	private Date timestamp;
	
	private String meassage;

	public ErrorMessage(){
		
	}
	
	public ErrorMessage(Date timestamp, String meassage){
		this.timestamp=timestamp;
		this.meassage=meassage;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMeassage() {
		return meassage;
	}

	public void setMeassage(String meassage) {
		this.meassage = meassage;
	}
	
	
	

}
