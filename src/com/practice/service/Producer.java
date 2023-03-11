package com.practice.service;

public class Producer {

	MessageQueue messageQueue;

	public Producer(MessageQueue queue) {
		this.messageQueue = queue;
	}

	public void publishMessage(String message) {
		System.out.println("Publishing message:" + message);
		messageQueue.addMessage(message);
	}
}
