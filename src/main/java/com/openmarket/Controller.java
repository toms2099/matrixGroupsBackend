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
	
	@RequestMapping(value="escalate", method=RequestMethod.POST, produces="application/json")
	Room perform(@RequestBody Body body) {
		return Room.withId(matrix.createRoom(body.keyword, users.getUsers(body.keyword)));
	}
	
	@RequestMapping(value="contactGroups", method=RequestMethod.GET, produces="application/json")
	List<String> getGroups() {

		return users.getGroups();
	}
	
	@RequestMapping(value="contactGroups/{id}", method=RequestMethod.GET, produces="application/json")
	List<String> get(@PathVariable String id) {
		
		return users.getUsers(id);
	}
}
