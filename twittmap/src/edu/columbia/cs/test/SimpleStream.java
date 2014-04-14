package edu.columbia.cs.test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class SimpleStream {
    public static void main(String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("asdf");
        cb.setOAuthConsumerSecret("asdf");
        cb.setOAuthAccessToken("asd-af");
        cb.setOAuthAccessTokenSecret("af");

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStatus(Status status) {
                User user = status.getUser();
                
                // gets Username
                String username = status.getUser().getScreenName();
                System.out.println(username);
                String profileLocation = user.getLocation();
                System.out.println(profileLocation);
                System.out.println("GEO location - " + status.getGeoLocation());
                long tweetId = status.getId(); 
                System.out.println(tweetId);
                String content = status.getText();
                System.out.println(content +"\n");
                
                Entity greeting = new Entity("username", username);
                greeting.setProperty("profileloc", profileLocation);
                greeting.setProperty("geolocation", status.getGeoLocation());
                greeting.setProperty("tweetid", tweetId);
                greeting.setProperty("content", content);

                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                datastore.put(greeting);

            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub

            }

			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}

        };
        FilterQuery fq = new FilterQuery();
    
        String keywords[] = {"game"};

        fq.track(keywords);

        twitterStream.addListener(listener);
        twitterStream.filter(fq);  
        try {
			Thread.sleep(30000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			twitterStream.shutdown();
		}
        
        
    }
}