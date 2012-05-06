package com.rdiablo.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import us.davidandersen.HttpUtil;

import com.google.gson.Gson;
import com.rdiablo.User;

public class UserServiceClient {

	public List<User> getUsersByIrcName(final String ircName)
			throws IOException {
		final URL serviceUrl = getUsersByIrcNameUrl(ircName);
		final String json = HttpUtil.readUrl(serviceUrl);

		return parseUsers(json);
	}

	private List<User> parseUsers(final String json) {
		final Gson gson = new Gson();
		final User[] users = gson.fromJson(json, User[].class);

		return Arrays.asList(users);
	}

	private URL getUsersByIrcNameUrl(final String ircName)
			throws MalformedURLException {
		return new URL("http://battletags.rdiablo.com/user/irc_name/" + ircName
				+ "*/json");
	}

	private URL getUserByIrcNameUrl(final String ircName)
			throws MalformedURLException {
		return new URL("http://battletags.rdiablo.com/user/irc_name/" + ircName
				+ "/json");
	}

	public User getUserByIrcName(String ircName) throws IOException {
		final URL serviceUrl = getUserByIrcNameUrl(ircName);
		final String json = HttpUtil.readUrl(serviceUrl);

		return parseUsers(json).get(0);
	}
}
