package com.openmarket;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

	private MatrixClient matrix = new MatrixClient();
	
	public static class Result {
		public String name = "hey";
	}
	
	@RequestMapping(value="test", method=RequestMethod.GET, produces="application/json")
	Result test() {
	    return new Result();	
	}
	
	public static class Body {
		public String keyword; 
	}
	
	public List<String> getList(String keyword) {
		List<String> peeps = new ArrayList<String>();
		
		peeps.add("@toms:matrix.org");
		peeps.add("@alastaird:matrix.org");
		peeps.add("@rwyrobek:matrix.org");
		peeps.add("@zevans:matrix.org");
		
		peeps.add("@+447473157070:matrix.openmarket.com");
		
		return peeps;
	}
	
	@Autowired
	GroupMapper gm;
	
	@RequestMapping(value="perform", method=RequestMethod.POST, produces="application/json")
	Result perform(@RequestBody Body body) {
		
		gm.getUsers("test");
		
		List<String> peeps = getList(body.keyword);
		
	    matrix.createRoom("candyTestRoom2", peeps);
	    return new Result();
	}
	
	@RequestMapping(value="contactGroups/{id}", method=RequestMethod.GET, produces="application/json")
	List<String> get(@PathVariable String id) {
		
		return gm.getUsers(id);
	}
}
