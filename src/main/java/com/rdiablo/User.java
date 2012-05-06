package com.rdiablo;

import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("irc_name")
	private String ircName;

	@SerializedName("bt")
	private String battleTag;

	@SerializedName("tz")
	private String timeZone;

	@SerializedName("realm")
	private String realm;

	@SerializedName("localtime")
	private String localTime;

	@SerializedName("steam_name")
	private String steamName;

	@SerializedName("reddit_name")
	private String redditName;

	@SerializedName("url")
	private String url;

	@SerializedName("cmt")
	private String comment;

	public String getIrcName() {
		return ircName;
	}

	public void setIrc_name(final String ircName) {
		this.ircName = ircName;
	}

	public String getBattleTag() {
		return battleTag;
	}

	public void setBattleTag(final String battleTag) {
		this.battleTag = battleTag;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getLocalTime() {
		return localTime;
	}

	public void setLocalTime(String localTime) {
		this.localTime = localTime;
	}

	public String getSteamName() {
		return steamName;
	}

	public void setSteamName(String steamName) {
		this.steamName = steamName;
	}

	public String getRedditName() {
		return redditName;
	}

	public void setRedditName(String redditName) {
		this.redditName = redditName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
