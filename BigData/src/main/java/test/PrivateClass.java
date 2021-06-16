package test;

public class PrivateClass {

    private String id = "127.0.0.1";
    private String port = "8080";

    private String url() {
        return id + ":" +  port;
    }
}