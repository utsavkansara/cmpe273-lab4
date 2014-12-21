package edu.sjsu.cmpe.cache.client;

public class Client {

    public static void main(String[] args) throws Exception {

        System.out.println("Starting Cache Client...");

        CRDTClient crdtClient = new CRDTClient();
        System.out.println("PUT key: 1  -----> value: a on all three servers via asynchronous call");
        boolean isResponseSuccessful = crdtClient.put(1, "a");
        if (isResponseSuccessful) {
            System.out.println("PUT key: 1  -----> value: a was successful");
        } else {
            System.out.println("PUT key: 1  -----> value: a was unsuccessful");
        }
        System.out.println("Sleeping thread for 30 seconds and stop server A");
        Thread.sleep(30000);

        System.out.println("PUT key: 1  -----> value: b on all three servers via asynchronous call");
        isResponseSuccessful = crdtClient.put(1, "b");
        if (isResponseSuccessful) {
            System.out.println("PUT key: 1  -----> value: b was successful");
        } else {
            System.out.println("PUT key: 1  -----> value: b was unsuccessful");
        }
        System.out.println("Sleeping thread for 30 seconds and restart server A");
        Thread.sleep(30000);

        System.out.println("GET key: 1  -----> value from all three servers via asynchronous call");
        String cachedValue = crdtClient.get(1);
        System.out.println("GET key: 1  -----> value: "+ cachedValue + " from all three cache servers");


        System.out.println("Existing Cache Client...");
    }
}
