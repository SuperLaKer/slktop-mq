package slktop.rabbit.a_spring.beans;

import java.io.File;

public class B_MessageListenerDelegate {

    public void callback(String message) {
        System.out.println(message);
    }


    public void consumerFile(File file) {
        System.out.println(file.getName());
    }
}