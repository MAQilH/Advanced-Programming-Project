package ir.sharif.server;

import ir.sharif.model.Pair;

import java.util.ArrayList;

public class FriendRequestService {
	private static FriendRequestService instance;
	private final ArrayList<Pair<String, String>> friendRequests = new ArrayList<>();
	private final ArrayList<Pair<String, String>> friends = new ArrayList<>();

	private FriendRequestService() {
	}

	public static FriendRequestService getInstance() {
		if (instance == null) {
			instance = new FriendRequestService();
		}
		return instance;
	}

	public void createFriendRequest(String fromUsername, String targetUsername) {
		friendRequests.add(new Pair<>(fromUsername, targetUsername));
	}

	public void acceptFriendRequest(String fromUsername, String targetUsername) {
		friendRequests.remove(new Pair<>(fromUsername, targetUsername));
		friends.add(new Pair<>(fromUsername, targetUsername));
		friends.add(new Pair<>(targetUsername, fromUsername));
	}

	public ArrayList<String> getFriends(String user) {
		ArrayList<String> friendsList = new ArrayList<>();
		for (Pair<String, String> friend : friends) {
			if (friend.getFirst().equals(user)) {
				friendsList.add(friend.getSecond());
			}
		}

		return friendsList;
	}
}
