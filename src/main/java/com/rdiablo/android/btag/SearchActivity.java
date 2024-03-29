package com.rdiablo.android.btag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.rdiablo.R;
import com.rdiablo.User;
import com.rdiablo.UserIrcNameComparator;
import com.rdiablo.client.UserServiceClient;

public class SearchActivity extends ListActivity {
	private String lastSearch = "";
	UserServiceClient s = new UserServiceClient();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		final EditText searchText = (EditText) findViewById(R.id.search);
		searchText.setOnKeyListener(new SearchListener(searchText));
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		setListAdapter(adapter);
		ListView lv1;
		lv1 = (ListView) findViewById(android.R.id.list);
		lv1.setTextFilterEnabled(true);
		lv1.setOnItemClickListener(new OnItemClickListener() {
			// @Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				// Toast.makeText(ListRecords.this,"Clicked!",
				// Toast.LENGTH_LONG).show();
				final Intent myIntent = new Intent(v.getContext(),
						ViewUserActivity.class);
//				android.R.drawable.ic_menu_search
				final Bundle bundle = new Bundle();
				bundle.putString("ircName", listItems.get(position));
				myIntent.putExtras(bundle);

				startActivityForResult(myIntent, 0);
			}
		});
	}

	private void updateView(String search) {
		List<User> users = null;
		try {
			users = this.s.getUsersByIrcName(search);
			Collections.sort(users, new UserIrcNameComparator());
		} catch (IOException e) {
			e.printStackTrace();
		}

		populateUserList(users);
	}

	ArrayList<String> listItems = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	private void populateUserList(List<User> users) {
		// ListView view = (ListView) findViewById(R.id.users);
		listItems.clear();
		for (User user : users) {
			// addList(view, user);
			listItems.add(user.getIrcName());
		}
		adapter.notifyDataSetChanged();
	}

	private void addList(ListView tr, final User user) {
		TextView t = new TextView(this);
		t.setText(user.getIrcName());
		t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		tr.setBackgroundResource(R.drawable.rounded);
		tr.setClickable(true);
		tr.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				final Intent myIntent = new Intent(view.getContext(),
						ViewUserActivity.class);
				final Bundle bundle = new Bundle();
				bundle.putString("ircName", user.getIrcName());
				myIntent.putExtras(bundle);

				startActivityForResult(myIntent, 0);
			}
		});
		tr.addView(t);
		// tr.addv
	}

	private void genTableView(List<User> tags) {
		TableLayout tl;
		tl = (TableLayout) findViewById(R.id.table1);
		View child = tl.getChildAt(0);
		tl.removeAllViews();
		tl.addView(child);
		for (final User tag : tags) {
			TableRow tr = new TableRow(this);
			TextView tv1 = new TextView(this);
			TextView tv2 = new TextView(this);
			tv2.setGravity(Gravity.RIGHT);
			createView(tr, tv1, tag.getIrcName(), tag.getIrcName());
			createView(tr, tv2, tag.getBattleTag(), tag.getIrcName());
			tl.addView(tr);
		}
	}

	public void createView(TableRow tr, TextView t, String viewdata,
			final String ircName) {
		t.setText(viewdata);
		t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		tr.setBackgroundResource(R.drawable.rounded);
		tr.setClickable(true);
		tr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Intent myIntent = new Intent(v.getContext(),
						ViewUserActivity.class);
				final Bundle bundle = new Bundle();
				bundle.putString("ircName", ircName);
				myIntent.putExtras(bundle);

				startActivityForResult(myIntent, 0);
			}
		});
		tr.addView(t);
	}

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
