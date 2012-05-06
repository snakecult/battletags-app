package com.rdiablo.android.btag;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.rdiablo.R;
import com.rdiablo.User;
import com.rdiablo.UserIrcNameComparator;
import com.rdiablo.R.id;
import com.rdiablo.R.layout;
import com.rdiablo.client.UserServiceClient;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class SearchActivity extends Activity {
	private String lastSearch = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final EditText searchText = (EditText) findViewById(R.id.search);
		searchText.setOnKeyListener(new SearchListener(searchText));

	}

	private void updateView(String search) {
		TableLayout tl;
		tl = (TableLayout) findViewById(R.id.table1);
		View child = tl.getChildAt(0);
		tl.removeAllViews();
		List<User> tags = null;
		try {
			tags = this.s.getUsersByIrcName(search);
			Collections.sort(tags, new UserIrcNameComparator());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tl.addView(child);
		for (final User tag : tags) {
			TableRow tr = new TableRow(this);
			TextView tv1 = new TextView(this);
			TextView tv2 = new TextView(this);
			tv2.setGravity(Gravity.RIGHT);
			createView(tr, tv1, tag.getIrcName());
			createView(tr, tv2, tag.getBattleTag());
			tl.addView(tr);
		}
	}

	public void createView(TableRow tr, TextView t, String viewdata) {
		t.setText(viewdata);
		t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tr.addView(t);
	}

	UserServiceClient s = new UserServiceClient();

	private final class SearchListener implements OnKeyListener {
		private final EditText edittext;

		private SearchListener(EditText edittext) {
			this.edittext = edittext;
		}

		public boolean onKey(View v, int keyCode, KeyEvent event) {
			// If the event is a key-down event on the "enter" button
			if ((event.getAction() == KeyEvent.ACTION_DOWN)
					&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
				String search = edittext.getText().toString();
				if (isValidSearch(search)) {
					updateView(search);
				}

				return true;
			}

			return false;
		}

		private boolean isValidSearch(String search) {
			return !search.equals(lastSearch) && !search.equals("");
		}
	}
}
