package com.rdiablo;

import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("irc_name")
	private String ircName;

	@SerializedName("bt")
	private String battleTag;

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
}
