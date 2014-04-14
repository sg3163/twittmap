package edu.columbia.cs.controller;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import edu.columbia.cs.vo.MainTweet;
import edu.columbia.cs.vo.Tweet;
 
@Controller
@RequestMapping("/streamdata")
public class StreamController {
 
	//DI via Spring
	String message;
 
	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	public void getMovie(@PathVariable String name, ModelMap model) {
			
		BufferedReader reader = null;
		
		ObjectMapper mapper = new ObjectMapper();

		try {
		    URL url = new URL("http://development-uechjsetn2.elasticbeanstalk.com/rest/payment/gettweet");
		    reader = new BufferedReader(new InputStreamReader(url.openStream()));
		    String line;

		    while ((line = reader.readLine()) != null) {
		    	MainTweet maintweet = mapper.readValue(line, MainTweet.class);
		    	List<Tweet> tweets = maintweet.getTweets();
		    	
		    	for(Tweet tweet : tweets) {
		    		Entity tweetEntity = new Entity(tweet.getKeyword());
		    		
		    		tweetEntity.setProperty("content", tweet.getContent());
		    		
		    		String geoloc = tweet.getGeolocation();
		    		geoloc = geoloc.replace("GeoLocation{", "").replace("latitude=", "").replace("longitude=", "").replace("}", "");
		    		tweetEntity.setProperty("geolocation", geoloc);
		    		
		    		tweetEntity.setProperty("tweetid", tweet.getTweetid());
		    		tweetEntity.setProperty("userloc", tweet.getUserlocation());
		    		tweetEntity.setProperty("username", tweet.getUsername());

		            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		            datastore.put(tweetEntity);
		    	}
		    }

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        finally {
        	try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
 
	}
 
	public void setMessage(String message) {
		this.message = message;
	}
 
}