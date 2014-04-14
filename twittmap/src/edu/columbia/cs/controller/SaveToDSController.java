package edu.columbia.cs.controller;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
 
@Controller
@RequestMapping("/savetods")
public class SaveToDSController {
 
	//DI via Spring
	String message;
 
	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	public String getMovie(@PathVariable String name, ModelMap model) {
 
		List<String> array = new ArrayList<>();
		
		array.add("game");
		array.add("summer");
		array.add("spring");
		array.add("winter");
		array.add("fall");
		array.add("rain");
		array.add("snow");
		array.add("cold");
		array.add("hot");
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		for(String arr : array) {
			Entity entity = new Entity("keywords");
			entity.setProperty("name", arr);
			datastore.put(entity);
		}
		
        return "saved";
 
	}
 
	public void setMessage(String message) {
		this.message = message;
	}
 
}