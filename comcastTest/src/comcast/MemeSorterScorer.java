package comcast;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

public class MemeSorterScorer {

	/**
	 * @param args
	 * @throws JSONException
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, JSONException {
		sortByName();
		addRatingToJsonFile();

	}

	private static void addRatingToJsonFile() throws IOException, JSONException {
		InputStream in = MemeSorterScorer.class.getResourceAsStream("/internetmemes.json");

		BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);
		JSONObject j = addRatingtoJsonFile(new JSONObject(responseStrBuilder.toString()));

		FileWriter file = new FileWriter("/internetmemes.json");
		try {
			file.write(j.toString());
			System.out.println("Successfully Copied JSON Object to File...");

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			file.flush();
			file.close();
		}

	}
	private static List sortByName() throws IOException, JSONException {
		InputStream in = MemeSorterScorer.class.getResourceAsStream("/internetmemes.json");

		BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);
		return listFromJsonSorted(new JSONObject(responseStrBuilder.toString()));

	}

	public static JSONObject addRatingtoJsonFile(JSONObject json) {
		if (json == null)
			return null;
		Iterator i = json.keys();
		while (i.hasNext()) {
			try {
				String key = i.next().toString();
				JSONObject j = json.getJSONObject(key);
				j.append("lulz", new Random().nextInt(10));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return json;
	}

	public static List listFromJsonSorted(JSONObject json) {
		if (json == null)
			return null;
		SortedMap map = new TreeMap();
		Iterator i = json.keys();
		while (i.hasNext()) {
			try {
				String key = i.next().toString();
				JSONObject j = json.getJSONObject(key);
				map.put(key, j);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return new LinkedList(map.values());
	}

}
