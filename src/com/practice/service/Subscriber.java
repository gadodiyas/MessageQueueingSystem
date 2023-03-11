package com.practice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Pattern;
public class Subscriber {
	static int cnt = 0;
	int id;
	List<String> consumedMessages = new ArrayList<>();
	String regEx;
	List<Subscriber> predecessors = new ArrayList<>();
	int offset = 0;
	boolean stop ;


	public Subscriber(String regEx) {
		id = cnt++;
		this.regEx = regEx;
	}


	public Subscriber(String regEx, List<Subscriber> predecessors) {
		this(regEx);
		this.predecessors = predecessors;

	}

	public void register(MessageQueue queue)  {
		while(!stop) {
			String message;
			System.out.println("Subscriber:" + id + " is trying to consume message with offset: " + offset);
			message = queue.getMessage(offset);
			if(message == null) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				continue;
			}

			if (!Pattern.matches(regEx, message)) {
				System.out.println("Regex did not match for subscriber: " + id + " for offset:" + offset);
				offset++;
				continue;
			}
			System.out.println("Regex matched for subscriber: " + id + " for offset:" + offset);
			boolean consumedBypredecessor = true;
			for (Subscriber predecessor : predecessors) {
				consumedBypredecessor = predecessor.offset > offset;
				if(!consumedBypredecessor) {
					System.out.println("Predecessor: " + predecessor.id + " has not consumed the offset:" + offset + " yet");

					break;
				}
			}
			if(consumedBypredecessor) {
				System.out.println("Message is consumed by subscriber: " + id + " offset: " + offset);
				consumedMessages.add(message);
				offset++;
			}else{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				continue;
			}
		}
	}

	public void deRegister(MessageQueue queue) {
		stop = true;
	}



}
