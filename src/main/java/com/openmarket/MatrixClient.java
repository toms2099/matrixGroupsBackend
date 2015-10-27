package com.openmarket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.client.RestTemplate;

public class MatrixClient {
	private static final Random random = new Random();
	private RestTemplate rest = new RestTemplate();

	public static class CreateRoomResult {
		public String room_id;
	}

	public static class CreateRoomBody {
		public CreateRoomBody(String alias) {
			final int randInt = random.nextInt(10000);
			this.room_alias = String.format("%s-%d", alias, randInt);
			this.name = String.format("%sRoom-%d", alias, randInt);
		}

		public String room_alias;
		public String visibility = "public";
		public String name = "CandyTestRoom";
		public String topic = "Topic of the day";
		public List<String> invite = new ArrayList<String>();
	}

	String base = "http://matrix.org/_matrix/client/api/v1/";
	String accessToken = "MDAxOGxvY2F0aW9uIG1hdHJpeC5vcmcKMDAxM2lkZW50aWZpZXIga2V5CjAwMTBjaWQgZ2VuID0gMQowMDIzY2lkIHVzZXJfaWQgPSBAdG9tczptYXRyaXgub3JnCjAwMTZjaWQgdHlwZSA9IGFjY2VzcwowMDFkY2lkIHRpbWUgPCAxNDQ1OTYyNTAyMDk2CjAwMmZzaWduYXR1cmUgmsWQWCM79rnzUaY5vZju_wr1JEB_aa_nBmme-tvSolgK";
	
	public String createRoom(String alias) {
		String path = base + "/createRoom?access_token=" + accessToken;

		CreateRoomResult roomResult = rest.postForObject(path, new CreateRoomBody(alias), CreateRoomResult.class);

		return roomResult.room_id;
	}

	public String createRoom(String alias, List<String> peeps) {
		String roomId = createRoom(alias);

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
