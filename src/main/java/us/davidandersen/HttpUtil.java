package us.davidandersen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

	static String readFromCon(HttpURLConnection c) throws IOException {
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

	public static String readUrl(final URL u) throws IOException {
		final HttpURLConnection c = (HttpURLConnection) u.openConnection();
		final String json = readFromCon(c);
		return json;
	}
}
