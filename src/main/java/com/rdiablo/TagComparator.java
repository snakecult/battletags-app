package com.rdiablo;

import java.util.Comparator;

public class TagComparator implements Comparator<Tag> {

	@Override
	public int compare(Tag arg0, Tag arg1) {
		return arg0.irc_name.compareToIgnoreCase(arg1.irc_name);
	}

}
