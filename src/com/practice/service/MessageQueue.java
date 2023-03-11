package com.practice.service;

import java.util.ArrayList;

import java.util.List;


public class MessageQueue {
	List<String> messages = new ArrayList<>();


	public void addMessage(String message) {
		messages.add(message);
	}



	public String getMessage(int i) {
		if(i >= messages.size()) {
			return null;
		}
		return messages.get(i);
	}
}
