package edu.columbia.cs.test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class Googledatastoretest {

	public static void main(String[] args) {
		 Entity greeting = new Entity("username", "harsha");
         greeting.setProperty("profileloc", "mysore, india");
  //       greeting.setProperty("geolocation", status.getGeoLocation());
         greeting.setProperty("tweetid", 1232498239);
         greeting.setProperty("content", "This is data store testing");

         DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
         datastore.put(greeting);

	}

}
