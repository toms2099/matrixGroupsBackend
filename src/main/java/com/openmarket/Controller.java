package com.openmarket;

import java.util.ArrayList;
import java.util.List;

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
	@Autowired
	private MatrixClient matrix;
	@Autowired
	private UserRepository users;
	
	public static class Room {
		public final String roomId;

		public Room(final String id) {
			this.roomId = id;
		}

		public Room() {
			this("hey");
		}

		public static Room withId(final String id) {
			return new Room(id);
		}
	}
	
	@RequestMapping(value="test", method=RequestMethod.GET, produces="application/json")
	Room test() {
	    return new Room();
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
	Room perform(@RequestBody Body body) {

		List<String> peeps = users.getUsers(body.keyword);
		
	    final String roomId = matrix.createRoom(body.keyword, peeps);
	    return Room.withId(roomId);
	}
	
	@RequestMapping(value="contactGroups/{id}", method=RequestMethod.GET, produces="application/json")
	List<String> get(@PathVariable String id) {
		
		return users.getUsers(id);
	}
}
