package com.wonx.small;

import java.util.Date;

public class T_ai_message {

	private String message_id;
	
	private String uuid;
	
	private String intent_id;
	
	private String channel;
	
	private String sender;
	
	private String language;
	
	private String message_type;
	
	private String message_text;
	
	private Double confidence_score;
	
	private String timestamp;
	
	private String contextPayload;
	
	private String flow;
	
	private Integer id;

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getIntent_id() {
		return intent_id;
	}

	public void setIntent_id(String intent_id) {
		this.intent_id = intent_id;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getMessage_text() {
		return message_text;
	}

	public void setMessage_text(String message_text) {
		this.message_text = message_text;
	}

	public Double getConfidence_score() {
		return confidence_score;
	}

	public void setConfidence_score(Double confidence_score) {
		this.confidence_score = confidence_score;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getContextPayload() {
		return contextPayload;
	}

	public void setContextPayload(String contextPayload) {
		this.contextPayload = contextPayload;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
}
