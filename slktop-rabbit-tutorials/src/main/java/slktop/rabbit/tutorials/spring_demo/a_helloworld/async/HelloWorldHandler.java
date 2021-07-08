package slktop.rabbit.tutorials.spring_demo.a_helloworld.async;

public class HelloWorldHandler {

	public void handleMessage(String text) {
		System.out.println("Received: " + text);
	}

}
