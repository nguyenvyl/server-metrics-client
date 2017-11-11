package com.programmerscuriosity.client;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class MyClient {
    
    private static  List<String> urls = new ArrayList<>();
    private static List<RequestMetrics> allStats = new ArrayList<>();
    

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException, Exception {
        for(String url : urls){
            allStats.addAll(callGET(url));
        }
    }


    public static List<RequestMetrics> callGET(String url) {
        List<RequestMetrics> metrics = new ArrayList<>();
        Client client = ClientBuilder.newClient();
        try {
            metrics = client.target(url)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<RequestMetrics>>(){});
        }  catch (ProcessingException e) {
            System.out.println("Error message: " + e.getMessage());
            System.out.println("Stack trace: " + Arrays.toString(e.getStackTrace()));
        } catch (OutOfMemoryError e) {
            System.out.println("You don't have enough memory");
        } catch (Exception e) {
            return metrics;
        }
        return metrics;
    }
    
    private static void calculateMetrics(String instanceID, List<RequestMetrics> metricsList) {
        
    }
    
    /**
     * Get the median number, given a list
     *
     * @param latencyList a list stores all the latency data
     * @return a double
     */
    private static Double getMedian(List<Double> latencyList) {
        Collections.sort(latencyList);
        if (latencyList.size() % 2 == 0) {
            return (latencyList.get(latencyList.size() / 2 - 1) + latencyList.get(latencyList.size() / 2)) / 2.0;
        }
        return latencyList.get(latencyList.size()) / 2.0;
    }

}
