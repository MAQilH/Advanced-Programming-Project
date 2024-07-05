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
			if (friend.getFirst().equals(user) && !friendsList.contains(friend.getSecond())) {
				friendsList.add(friend.getSecond());
			}
		}

		return friendsList;
	}

	public boolean areFriends(String first, String second) {
		for (Pair<String, String> friend : friends)
			if (friend.getFirst().equals(first) && friend.getSecond().equals(second))
				return true;

		return false;
	}

	public ArrayList<String> getPendingFriends(String username) {
		System.err.println(username);
		ArrayList<String> result = new ArrayList<>();
		for (Pair<String, String> friendRequest : friendRequests) {
			if (areFriends(friendRequest.getFirst(), friendRequest.getSecond()))
				continue;

			if (friendRequest.getSecond().equals(username) && !result.contains(friendRequest.getFirst()))
				result.add(friendRequest.getFirst());
		}

		return result;
	}

	public void rejectFriend(String fromUsername, String targetUsername) {
		for (Pair<String, String> friendRequest : friendRequests) {
			if (friendRequest.getFirst().equals(fromUsername) && friendRequest.getSecond().equals(targetUsername)) {
				friendRequests.remove(friendRequest);
				return;
			}
		}
	}
}
