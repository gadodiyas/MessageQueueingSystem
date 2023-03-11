package com.practice;

import com.practice.service.MessageQueue;
import com.practice.service.Producer;
import com.practice.service.Subscriber;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MessageQueue queue = new MessageQueue();
        Producer producer = new Producer(queue);


        Subscriber sub1 = new Subscriber(".*id.*");
        Subscriber sub2 = new Subscriber(".*id.*", Arrays.asList(sub1));
        Subscriber sub3 = new Subscriber("^[A-Za-z0-9:{}\"]*$", Arrays.asList(sub1));
        Subscriber sub4 = new Subscriber(".*id.*");
        new Thread(() -> sub1.register(queue)).start();
        new Thread(() -> sub2.register(queue)).start();
        new Thread(() -> sub1.register(queue)).start();
        new Thread(() -> sub3.register(queue)).start();
        new Thread(() -> sub4.register(queue)).start();

        producer.publishMessage( "{\"id\": 123}");
        producer.publishMessage("{\"id\": 456}");
        producer.publishMessage("{\"id\": 789}");
        producer.publishMessage("{\"id\": 999}");



        sub3.deRegister(queue);


    }
}
