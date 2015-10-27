package com.openmarket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

public class MatrixClient {

	private RestTemplate rest = new RestTemplate();

	public static class CreateRoomResult {
		public String room_id;
	}

	public static class CreateRoomBody {
		public CreateRoomBody(String alias) {
			this.room_alias = alias;
			this.name = alias + "Room";
		}

		public String room_alias;
		public String visibility = "public";
		public String name = "CandyTestRoom";
		public String topic = "Topic of the day";
		public List<String> invite = new ArrayList<String>();
	}

	String base = "http://matrix.org/_matrix/client/api/v1/";
	String accessToken2 = "MDAxOGxvY2F0aW9uIG1hdHJpeC5vcmcKMDAxM2lkZW50aWZpZXIga2V5CjAwMTBjaWQgZ2VuID0gMQowMDIzY2lkIHVzZXJfaWQgPSBAdG9tczptYXRyaXgub3JnCjAwMTZjaWQgdHlwZSA9IGFjY2VzcwowMDFkY2lkIHRpbWUgPCAxNDQ1OTUyNjI5ODA3CjAwMmZzaWduYXR1cmUgL7AmN4U6WYARyi_YS-sLFiZrOKmPAPFmbCb8Q82-f5UK";

	String accessToken = "MDAxOGxvY2F0aW9uIG1hdHJpeC5vcmcKMDAxM2lkZW50aWZpZXIga2V5CjAwMTBjaWQgZ2VuID0gMQowMDIzY2lkIHVzZXJfaWQgPSBAdG9tczptYXRyaXgub3JnCjAwMTZjaWQgdHlwZSA9IGFjY2VzcwowMDFkY2lkIHRpbWUgPCAxNDQ1OTYyNTAyMDk2CjAwMmZzaWduYXR1cmUgmsWQWCM79rnzUaY5vZju_wr1JEB_aa_nBmme-tvSolgK";
	
	public String createRoom(String alias) {
		String path = base + "/createRoom?access_token=" + accessToken;

		CreateRoomBody crb = new CreateRoomBody(alias);

		// Alright invite during create doesn't work :(
		//crb.invite.add("toms");
		//crb.invite.add("@alastaird:matrix.org");

		CreateRoomResult roomResult = rest.postForObject(path, new CreateRoomBody(alias), CreateRoomResult.class);

		return roomResult.room_id;
	}

	public String createRoom(String alias, List<String> peeps) {

		String roomId = createRoom(alias);

		String path = base + "/createRoom?access_token=" + accessToken;

		for (String userId : peeps) {
			invite(roomId, userId);
		}

		return roomId;
	}

	public static class InviteBody {
		public String user_id;
	}

	public void invite(String room, String person) {

		InviteBody ib = new InviteBody();
		ib.user_id = person;

		String path = base + "/rooms/" + room + "/invite?access_token=" + accessToken;
		
		try {
		
		CreateRoomResult roomResult = rest.postForObject(path, ib, CreateRoomResult.class);

		} catch (Exception e) {
			// Right - try to catch the 403
		}
	}
}
