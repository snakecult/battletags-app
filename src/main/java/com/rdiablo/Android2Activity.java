package com.rdiablo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

import com.google.gson.Gson;

public class Android2Activity extends Activity {
	String lastSearch = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final EditText edittext = (EditText) findViewById(R.id.search);
		edittext.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					// Toast.makeText(HelloFormStuff.this, edittext.getText(),
					// Toast.LENGTH_SHORT).show();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
					String search = edittext.getText().toString();
					if (isValidSearch(search))
						updateView(search);
					return true;
				}
				return false;
			}

			private boolean isValidSearch(String search) {
				return !search.equals(lastSearch) && !search.equals("");
			}
		});

	}

	private void updateView(String search) {
		TableLayout tl;
		tl = (TableLayout) findViewById(R.id.table1);
		View child = tl.getChildAt(0);
		tl.removeAllViews();
		List<Tag> tags = null;
		try {
			tags = this.getTags(search);
			Collections.sort(tags, new TagComparator());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tl.addView(child);
		for (Tag tag : tags) {
			// Create a new row to be added.
			TableRow tr = new TableRow(this);
			// Create text views to be added to the row.
			TextView tv1 = new TextView(this);
			TextView tv2 = new TextView(this);
			tv2.setGravity(Gravity.RIGHT);
			// Put the data into the text view by passing it to a user defined
			// function createView()
			createView(tr, tv1, tag.irc_name);
			createView(tr, tv2, tag.bt);
			// Add the new row to our tableLayout tl
			tl.addView(tr);
		}

		// http://battletags.rdiablo.com/user/irc_name/chaosteil/json//
	}

	public void createView(TableRow tr, TextView t, String viewdata) {
		t.setText(viewdata);
		// adjust the porperties of the textView
		t.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		// t.setTextColor(Color.DKGRAY);
		// t.setBackgroundColor(Color.CYAN);
		// t.setPadding(20, 0, 0, 0);
		// tr.setPadding(0, 1, 0, 1);
		// tr.setBackgroundColor(Color.BLACK);
		tr.addView(t); // add TextView to row.
	}

	List<Tag> getTags(String search) throws IOException {
		// List<Tag> x = new ArrayList<Tag>();
		URL u = new URL("http://battletags.rdiablo.com/user/irc_name/" + search
				+ "*/json");
		HttpURLConnection c = (HttpURLConnection) u.openConnection();
		String json = readFromCon(c);
		final Gson gson = new Gson();
		Tag[] r = gson.fromJson(json, Tag[].class);
		// ArrayList<Tag> tags = new ArrayList<Tag>();
		return Arrays.asList(r);

	}

	private String readFromCon(HttpURLConnection c) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				c.getInputStream()));
		String inputLine;
		String line = "";
		while ((inputLine = in.readLine()) != null) {
			line += inputLine;
		}
		in.close();
		return line;
	}
}
