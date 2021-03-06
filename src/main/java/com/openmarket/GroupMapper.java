package com.openmarket;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;

@Component
public class GroupMapper implements UserRepository {
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private ApplicationContext appContext;

	public static class ContactGroups {
		public List<String> contacts;
	}

	Map<String, ContactGroups> cg = null;

	public void init() {
		if (cg == null) {

			Resource res = appContext.getResource("classpath:contactGroups.json");
			String json = "";

			try {
				json = CharStreams.toString(new InputStreamReader(res.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			TypeReference<HashMap<String, ContactGroups>> typeRef = new TypeReference<HashMap<String, ContactGroups>>() {
			};

			try {
				cg = mapper.readValue(res.getInputStream(), typeRef);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List<String> getUsers(String keyword) {
		init();
		List<String> contacts = cg.get(keyword).contacts;
		return contacts;
	}

	@Override
	public List<String> getGroups() {
		init();
		List<String> groups = new ArrayList<String>();
		groups.addAll(cg.keySet());
		return groups;
	}
}
