package com.rdiablo.android.btag;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.rdiablo.R;
import com.rdiablo.User;
import com.rdiablo.client.UserServiceClient;

public class ViewUserActivity extends Activity {
	UserServiceClient s = new UserServiceClient();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.view);

		Bundle bundle = this.getIntent().getExtras();
		String ircName = bundle.getString("ircName");
		setTitle(ircName);
		TableLayout tl = (TableLayout) findViewById(R.id.viewUserLayout);

		User user = null;
		try {
			user = s.getUserByIrcName(ircName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		addRow(tl, "Reddit Name", user.getRedditName());
		addRow(tl, "IRC Name", user.getIrcName());
		addRow(tl, "BattleTag", user.getBattleTag());
		addRow(tl, "Realm", user.getRealm());
		addRow(tl, "Steam Name", user.getSteamName());
		addRow(tl, "Timezone", user.getTimeZone());
		addRow(tl, "Local Time", user.getLocalTime());
		addRow(tl, "URL", user.getUrl());
		addRow(tl, "Comment", user.getComment());

	}

	private void addRow(TableLayout tl, String field, String value) {
		TableRow tr = new TableRow(this);
		TextView tv1 = new TextView(this);
		TextView tv2 = new TextView(this);
		tv2.setGravity(Gravity.RIGHT);
		createView(tr, tv1, field);
		createView(tr, tv2, value != null ? value : "");
		tl.addView(tr);
	}

	public void createView(TableRow tr, TextView t, String viewdata) {
		t.setText(viewdata);
		t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tr.addView(t);
	}
}
