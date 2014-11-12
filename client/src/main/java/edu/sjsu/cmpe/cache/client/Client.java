package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Connected to Client...");
        //Creating list of cache servers where data is to be cached
        List<CacheServiceInterface> cacheServerList = new ArrayList<CacheServiceInterface>();
        cacheServerList.add(new DistributedCacheService("http://localhost:3000"));
        cacheServerList.add(new DistributedCacheService("http://localhost:3001"));
        cacheServerList.add(new DistributedCacheService("http://localhost:3002"));

        //creating list of objects which will be cached onto the server
        List<String> objectList = new ArrayList<String>();
        objectList.add(" ");
        objectList.add("a");
        objectList.add("b");
        objectList.add("c");
        objectList.add("d");
        objectList.add("e");
        objectList.add("f");
        objectList.add("g");
        objectList.add("h");
        objectList.add("i");
        objectList.add("j");

        //Cache data into cache servers using Hashing Function which uses MD5 to generate hash value
        System.out.println("Caching data..");
        int serverKey = 0;
        for(int objectKey=1;objectKey<=objectList.size()-1;objectKey++)
        {

            serverKey = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(objectKey)), cacheServerList.size());
            CacheServiceInterface CacheServerToPutData = cacheServerList.get(serverKey);
            CacheServerToPutData.put(objectKey,objectList.get(objectKey));
            System.out.println("Caching => Key=" + objectKey + " and Value="+ objectList.get(objectKey) + " routed to Cache server at localhost://300"+ serverKey);
        }

        System.out.println(" Data Cached .. ");

        System.out.println(" ");

        //Fetching data from the cache server using same hash function on the key
        System.out.println("GET data from Cache... ");

        for(int objectKey=1;objectKey<=objectList.size()-1;objectKey++){
            serverKey = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(objectKey)), cacheServerList.size());
            CacheServiceInterface CacheServerToFetchData = cacheServerList.get(serverKey);
            String fetchedValue = CacheServerToFetchData.get(objectKey);
            System.out.println("Fetching data => Fetched Key=" + objectKey + " and Value="+ fetchedValue + " from Cache server at localhost://300"+ serverKey);
        }

        System.out.println("\nFetched Data .. ");
        System.out.println("Exiting from Cache Client...");

    }
}
