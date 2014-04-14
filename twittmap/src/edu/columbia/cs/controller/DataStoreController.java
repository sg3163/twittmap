package edu.columbia.cs.controller;
 
import java.util.List;
import java.util.logging.Level;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
 
@Controller
@RequestMapping("/getdata")
public class DataStoreController {
 
	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	public @ResponseBody List<Entity> getMovie(@PathVariable String name, ModelMap model) {
		
		if(name == null) {
			return null;
		}
		
		String key = name;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	    // Using the synchronous cache
	    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    List<Entity> value = (List<Entity>) syncCache.get(key); // read from cache
	    if (value == null) {
	    	Query query1 = new Query(key);
	        List<Entity> tweets = datastore.prepare(query1).asList(FetchOptions.Builder.withDefaults());
	        syncCache.put(key, tweets); 
	    }

		
		return (List<Entity>) syncCache.get(key);
	}
 
}