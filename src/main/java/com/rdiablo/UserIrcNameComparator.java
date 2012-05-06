package com.rdiablo;

import java.util.Comparator;

public class UserIrcNameComparator implements Comparator<User> {

	@Override
	public int compare(final User user1, final User user2) {
		return user1.getIrcName().compareToIgnoreCase(user2.getIrcName());
	}

}
