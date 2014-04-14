package edu.columbia.cs.test;

import java.io.IOException;
import java.util.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import twitter4j.conf.ConfigurationBuilder;
import twitter4j.*;

public class TwitterDownloader {
	
	public static void main(String args[]) throws IOException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("53rZh9JxfVmJVNf0lJ1LjWDa7")  // Copy all the twitter API keys accordingly
		.setOAuthConsumerSecret("zBcqlQ2mhH1yqxY1hvrH2kItrE0ncVOcvVuSgdJgq28kJeX937")
		.setOAuthAccessToken("2387480428-qecBzvVSDvBaREtqPFcBG4c7rEmbsvPsSktJfWl")
		.setOAuthAccessTokenSecret("pAbNnRli4HENnPUDskFr2zyFPY2Lipy9H6uSgrHh1dXln");

		// The factory instance is re-useable and thread safe.
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        try {
            Query query = new Query("soccer"); // Search tweets for particular words
            QueryResult result;
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {				
					System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
					// You can store the tweets in datastore                   
                }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
	}

}