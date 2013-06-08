package edu.cui.wineapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import edu.cui.wineapp.DAOBU.DownloadWebpageText;
import edu.cui.wineapp.Models.DetailedWine;
import edu.cui.wineapp.Models.Food;
import edu.cui.wineapp.Models.Review;
import edu.cui.wineapp.Models.Wine;

public class WineDAO {

	public static final String TAG_WINES = "wines";
	public static final String TAG_ID = "_id";
	public static final String TAG_NAME = "name";
	public static final String TAG_CODE = "code";
	public static final String TAG_REGION = "region";
	public static final String TAG_WINERY = "winery";
	public static final String TAG_WINERYID = "winery_id";
	public static final String TAG_VARIETAL = "varietal";
	public static final String TAG_PRICE = "price";
	public static final String TAG_VINTAGE = "vintage";
	public static final String TAG_TYPE = "type";
	public static final String TAG_LINK = "link";
	public static final String TAG_TAGS = "tags";
	public static final String TAG_IMAGE = "image";
	public static final String TAG_SNOOTHRANK = "snoothrank";
	public static final String TAG_AVAILABILITY = "available";
	public static final String TAG_NUMMERCHANTS = "num_merchants";
	public static final String TAG_NUMREVIEWS = "num_reviews";

	private static SQLiteDatabase wineDataBase = null;	//If it doesn't work, remove static.
	private WineSQLiteHelper wineDBHelper = null;
	private SQLiteDatabase userDataBase = null;
	private UserSQLiteHelper userDBHelper = null;

	private JSONArray winesJSON = null;
	private JSONArray foodsJSON = null;
	private JSONArray detailedWinesJSON = null;
	private String apiKey = "ra4c57ui7tkz3knjur913q2ubeekm9dnoulmu9j40lmrehjy";
	private Context context;

	/*
	    public User createUser(User user, String password) {
	        ContentValues values = new ContentValues();
	        values.put(UserSQLiteHelper.COLUMN_NAME, user.getName());
	        values.put(UserSQLiteHelper.COLUMN_AGE, Integer.toString(user.getAge()));
	        values.put(UserSQLiteHelper.COLUMN_WEIGHT, Float.toString(user.getWeight()));
	        values.put(UserSQLiteHelper.COLUMN_EMAIL, user.getEmail());
	        values.put(UserSQLiteHelper.COLUMN_SEX, user.getSex());
	        values.put(UserSQLiteHelper.COLUMN_COUNTRY, user.getCountry());
	        values.put(UserSQLiteHelper.COLUMN_PHOTOURL, user.getPhotoUrl());
	        //values.put(UserSQLiteHelper.COLUMN_DRINKEDWINES,user.getDrinkedWines().toString());
	        //values.put(UserSQLiteHelper.COLUMN_COMMENTS, user.getComments().toString());
	        values.put(UserSQLiteHelper.COLUMN_PASSWORD, password);
	        long insertId = userDataBase.insert(UserSQLiteHelper.TABLE_USERS, null,
	                values);
	        Cursor cursor = userDataBase.query(UserSQLiteHelper.TABLE_USERS,
	                allColumnsforuser, UserSQLiteHelper.COLUMN_ID + " = " + insertId, null,
	                null, null, null);
	        cursor.moveToFirst();
	        User newUser = cursorToUser(cursor);
	        cursor.close();
	        return newUser;
	    }
	 */

	private WineDAO(Context context) {
		this.context = context;
		wineDBHelper = new WineSQLiteHelper(context);
		userDBHelper = new UserSQLiteHelper(context);
		open();
	}

	public static WineDAO getDAO(Context context) {
		return new WineDAO(context);
	}

	public void open() throws SQLException {
		wineDataBase = wineDBHelper.getWritableDatabase();
		userDataBase = userDBHelper.getWritableDatabase();
	}

	public void close() {
		wineDBHelper.close();
		userDBHelper.close();
	}
	public Wine createWine(Wine wine) {
		long insertId = -1;
		Wine newWine = wine;

		Log.i("DAO.java/createWine","Trying Wine: "+wine.getName());

		ContentValues values = new ContentValues();
		values.put(WineSQLiteHelper.COLUMN_NAME, wine.getName());
		values.put(WineSQLiteHelper.COLUMN_CODE, wine.getCode());
		values.put(WineSQLiteHelper.COLUMN_REGION, wine.getRegion());
		values.put(WineSQLiteHelper.COLUMN_WINERY, wine.getWinery());
		values.put(WineSQLiteHelper.COLUMN_WINERYID, wine.getWinery_id());
		values.put(WineSQLiteHelper.COLUMN_VARIETAL, wine.getVarietal());
		values.put(WineSQLiteHelper.COLUMN_PRICE, wine.getPrice());
		values.put(WineSQLiteHelper.COLUMN_VINTAGE, wine.getVintage());
		values.put(WineSQLiteHelper.COLUMN_TYPE, wine.getType());
		values.put(WineSQLiteHelper.COLUMN_LINK, wine.getLink());
		values.put(WineSQLiteHelper.COLUMN_TAGS, wine.getTags());
		values.put(WineSQLiteHelper.COLUMN_IMAGE, wine.getImage());
		values.put(WineSQLiteHelper.COLUMN_SNOOTHRANK, wine.getSnoothrank());
		values.put(WineSQLiteHelper.COLUMN_AVAILABILITY, wine.getAvailability());
		values.put(WineSQLiteHelper.COLUMN_NUMMERCHANTS, wine.getNum_merchants());
		values.put(WineSQLiteHelper.COLUMN_NUMREVIEWS, wine.getNum_reviews());


		try {
			//        	Log.e("DAO.java/createWine","ID: "+wineDataBase.insertOrThrow(WineSQLiteHelper.TABLE_WINES, null, values));
			insertId = wineDataBase.insertOrThrow(WineSQLiteHelper.TABLE_WINES, null, values);
			Cursor cursor = wineDataBase.query(WineSQLiteHelper.TABLE_WINES,
					null, WineSQLiteHelper.COLUMN_ID + " = " + insertId, null,
					null, null, null);
			cursor.moveToFirst();
			newWine = cursorToWine(cursor);
			cursor.close();
			//  closeWineData();

		} catch (SQLiteConstraintException e) {
			Log.e("DAO.java/createWine","Wine already in database, ignoring");
			Log.e("DAO.java/createWine","Wine name: "+ wine.getName());
			//newWine = getWineByNameFromDB(wine.getName());
		}
		return newWine;
	}

	//LOCAL wineDataBase/////////////
	public boolean deleteWine(Wine wine) {
		long id = wine.getId();
		if (id == -1) {
			return false;
		}
		System.out.println("Comment deleted with id: " + id);
		wineDataBase.delete(WineSQLiteHelper.TABLE_WINES, WineSQLiteHelper.COLUMN_ID
				+ " = " + id, null);
		return true;
	}

	public DetailedWine downloadDetailedWine(String name) {
		String searchTerm = name;
		String urlPreTerm = "http://api.snooth.com/wine/";
		String urlPostTerm = "?akey=" + apiKey + "&id=" + searchTerm + "&food=1&i=1&photos=1";
		String stringUrl = urlPreTerm + urlPostTerm;

		return parseDetailedWineXML(new DownloadWebpageText().execute(stringUrl));
	}

	//GETTER AND SETTER FROM ONLINE
	public ArrayList<Wine> downloadWineByName(String name) {


		String searchTerm = name;
		String urlPreTerm = "http://api.snooth.com/wines/";
		String urlPostTerm = "?akey=" + apiKey + "&q=" + searchTerm  + "&n=99"+"&s=sr";
		String stringUrl = urlPreTerm + urlPostTerm;

		Log.i("DEBUG", "url has been built");
		Log.i("DEBUG", "Manager has been built");

		return parseWineXML(new DownloadWebpageText().execute(stringUrl));
	}

	public ArrayList<Wine> getAllWinesInwineDataBase() {
		ArrayList<Wine> winesInData = new ArrayList<Wine>();

		Cursor cursor = wineDataBase.query(WineSQLiteHelper.TABLE_WINES,
				null, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Wine wine = cursorToWine(cursor);
			winesInData.add(wine);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return winesInData;
	}

	public Wine getWineById(long wineId) {

		Cursor cursor = wineDataBase.query(WineSQLiteHelper.TABLE_WINES,
				null, WineSQLiteHelper.COLUMN_ID + " = " + wineId, null, null, null, null);
		if (cursor.moveToFirst()) {
			Wine newWine = cursorToWine(cursor);
			cursor.close();
			return newWine;
		} else {
			return null;
		}
	}

	public ArrayList<Wine> getWineByName(String name) {
		ArrayList<Wine> winesInData = new ArrayList<Wine>();
		//TODO: FIX THIS SERIOUSLY DO
		if(name.contains("'")){
			name = name.replace('\'','|');
		}

		Cursor cursor = wineDataBase.query(WineSQLiteHelper.TABLE_WINES, null,
				WineSQLiteHelper.COLUMN_NAME + " LIKE '%" + name + "%'", null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Wine wine = cursorToWine(cursor);
			winesInData.add(wine);
			cursor.moveToNext();
		}
		cursor.close();
		/*
	    if(winesInData.get(0).getName().contains("|")){
	        	 winesInData.get(0).setName(winesInData.get(0).getName().replace('|', '\''));
	    }
		 */
		return winesInData;
	}

	public Wine getWineByNameFromDB(String wineName){
		Log.e("DAO.java/createWine","Wine name: "+ wineName);
		Cursor cursor = 
				//wineDataBase.query(WineSQLiteHelper.TABLE_WINES, 
				//null, WineSQLiteHelper.COLUMN_NAME + " = " + wineName, null, null, null, null);
				wineDataBase.rawQuery("SELECT * FROM "+WineSQLiteHelper.TABLE_WINES+" WHERE "+ WineSQLiteHelper.COLUMN_NAME+" = "+wineName, null);
		if(cursor.moveToFirst()){
			Wine newWine = cursorToWine(cursor);
			cursor.close();
			return newWine;
		} else{
			return null;
		}
	}

	/*
	 * COMMAND REQ- USER DB
	 * 
	 * command = signup
	 * 		userid	-- unique
	 * 		age
	 * 		weight
	 * 		email
	 * 		sex
	 * 		country
	 * 		photourl
	 * 		password
	 * 		
	 * 		I don't need to check for user exists
	 * 
	 * 		succesful response -- OK USER HAS BEEN CREATED
	 * 
	 * command = login
	 * 		userid
	 * 		password
	 * 
	 * 		returns json file
	 * 
	 * 
	 * command = setting 
	 * 
	 * 		(ALLOWS FOR CHANGING ASPECTS OF EXISTING USER PROFILE)
	 * 
	 * 		userid
	 * 		colum
	 * 			age
	 * 			weight
	 * 			sex
	 * 			country
	 * 			photourl
	 * 		newvalue
	 * 		
	 * command = winehistory
	 * 		userid
	 * 		
	 * 		returns jsonfile
	 * 
	 * command = drink
	 * 		userid
	 * 		winecode
	 * 
	 * command = clearhistory
	 * 		userid
	 * 
	 * 		clears entire wine history
	 * 
	 * 
	 * hello-zhaoyang-udacity.appspot.com/
	 * 										winehistory
	 * 											GET
	 * 												SUPPORTS RAW QUERY
	 * 											POST
	 * 										user
	 * 											GET
	 * 											POST
	 */	



	public ArrayList<Wine> getWineByQuery(String query){			//Tomas method
		String apiKey = "ra4c57ui7tkz3knjur913q2ubeekm9dnoulmu9j40lmrehjy";
		String searchTerm = query;
		String urlPreTerm = "http://api.snooth.com/wines/";
		String urlPostTerm = "?akey="+apiKey+"&format=json&"+searchTerm;
		String snoothUrl = urlPreTerm + urlPostTerm;

		String response="";
		try {
			response=new DownloadWebpageText().downloadUrl(snoothUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<Wine> thiswines = parseWineXML(response); //MAY CAUSE PROBLEM

		return thiswines;
	}

	private Wine cursorToWine(Cursor cursor) {
		Wine wine = new Wine(
				cursor.getLong(0),
				cursor.getString(1),
				cursor.getString(2),
				cursor.getString(3),
				cursor.getString(4),
				cursor.getString(5),
				cursor.getString(6),
				cursor.getFloat(7),
				cursor.getString(8),
				cursor.getString(9),
				cursor.getString(10),
				cursor.getString(11),
				cursor.getString(12),
				cursor.getFloat(13),
				cursor.getString(14),
				cursor.getString(15),
				cursor.getString(16)
				);
		return wine;
	}

	@SuppressWarnings("finally")
	public ArrayList<Wine> parseWineXML(String preParsed) {
		ArrayList<Wine> wines = new ArrayList<Wine>();

		long wineRank = 0;
		long winePrice = 0;
		try {
			JSONObject myJSON = new JSONObject(preParsed);
			winesJSON = myJSON.getJSONArray(TAG_WINES);
			for (int i = 0; i < winesJSON.length(); i++) {
				JSONObject currentWine = winesJSON.getJSONObject(i);

				try {
					wineRank = currentWine.getLong(TAG_SNOOTHRANK);
				} catch (JSONException e) {
					wineRank = 0;
					Log.e("DAO.java.parseWineXML()","Catch: wineRank set to 0");
				}


				try {
					winePrice = currentWine.getLong(TAG_PRICE);
				} catch (JSONException e) {
					winePrice = -1;
					Log.e("DAO.java.parseWineXML()","Catch: winePrice set to -1");
				}

				Wine newWine = new Wine(
						-1,
						currentWine.getString(TAG_NAME),
						currentWine.getString(TAG_CODE),
						currentWine.getString(TAG_REGION),
						currentWine.getString(TAG_WINERY),
						currentWine.getString(TAG_WINERYID),
						currentWine.getString(TAG_VARIETAL),
						winePrice,
						currentWine.getString(TAG_VINTAGE),
						currentWine.getString(TAG_TYPE),
						currentWine.getString(TAG_LINK),
						currentWine.getString(TAG_TAGS),
						currentWine.getString(TAG_IMAGE),
						wineRank,
						currentWine.getString(TAG_AVAILABILITY),
						currentWine.getString(TAG_NUMMERCHANTS),
						currentWine.getString(TAG_NUMREVIEWS)
						);

				if(newWine.getName()!=null)
					Log.e("DAO.java/parseWineXML","Wine creation succeeded: "+i);

				wines.add(newWine);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		finally{
			return wines;
		}
	}

	public class DownloadWebpageText {
		private static final String DEBUG_TAG = "HttpExample";

		@SuppressWarnings("finally")
		protected String execute(String... urls) {
			String fetchResult = "";
			try {
				fetchResult = downloadUrl(urls[0]);
			} catch (IOException e) {
				fetchResult = "Unable to retrieve web page. URL may be invalid.";
			}
			finally{
				return fetchResult;
			}
		}

		@SuppressWarnings("finally")
		private String downloadUrl(String myurl) throws IOException {
			InputStream is = null;
			String fetchResult = "";
			try {
				URL url = new URL(myurl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(10000 /* milliseconds */);
				conn.setConnectTimeout(15000 /* milliseconds */);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.connect();
				int response = conn.getResponseCode();
				Log.d(DEBUG_TAG, "The response is: " + response);
				is = conn.getInputStream();

				fetchResult = readIt(is);

			} finally {
				if (is != null) is.close();
				return fetchResult;
			}
		}

		public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {

			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				Log.e("DEBUG_DAO",line);
				sb.append(line + "\n");
			}
			reader.close();
			String result = sb.toString();
			return result;
		}


	}


	public DetailedWine parseDetailedWineXML(String preParsed) {
		DetailedWine detailedWine = new DetailedWine();
		long wineRank = 0;
		long winePrice = 0;

		try {
			JSONObject myJSON = new JSONObject(preParsed);
			foodsJSON = myJSON.getJSONArray(TAG_WINES);
			myJSON = foodsJSON.getJSONObject(0);
			JSONObject currentWine = myJSON;

			try {
				wineRank = myJSON.getLong(TAG_SNOOTHRANK);
			} catch (JSONException e) {
				wineRank = 0;
			}            
			try {
				winePrice = myJSON.getLong(TAG_PRICE);
			} catch (JSONException e) {
				winePrice = -1;
			}

			detailedWine = new DetailedWine(
					-1,
					currentWine.getString(TAG_NAME),
					currentWine.getString(TAG_CODE),
					currentWine.getString(TAG_REGION),
					currentWine.getString(TAG_WINERY),
					currentWine.getString(TAG_WINERYID),
					currentWine.getString(TAG_VARIETAL),
					winePrice,
					currentWine.getString(TAG_VINTAGE),
					currentWine.getString(TAG_TYPE),
					currentWine.getString(TAG_LINK),
					"",
					currentWine.getString(TAG_IMAGE),
					wineRank,
					currentWine.getString(TAG_AVAILABILITY),
					currentWine.getString(TAG_NUMMERCHANTS),
					currentWine.getString(TAG_NUMREVIEWS),
					currentWine.getString("wm_notes"),
					currentWine.getString("winery_tasting_notes"),
					currentWine.getString("sugar"),
					Float.parseFloat(currentWine.getString("alcohol")),
					Float.parseFloat(currentWine.getString("ph")),
					currentWine.getString("acidity"),
					getReviewArrayListFromJSON(myJSON),
					getRecipeArrayListFromJSON(myJSON)
					);
			Log.i("DAO.java/parseDetailedWineXML","Sugar: "+ currentWine.getString("sugar") +" Alcohol: " + Float.parseFloat(currentWine.getString("alcohol")));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		finally{
			return detailedWine;
		}
	}

	@SuppressWarnings("finally")
	private ArrayList<Food> getRecipeArrayListFromJSON(JSONObject myJSON){

		ArrayList<Food> helperFoods = new ArrayList<Food>();

		try{
			JSONArray myJSONArr = myJSON.getJSONArray("recipes");
			for (int i = 0; i < myJSONArr.length(); i++) {
				JSONObject currentFood = myJSONArr.getJSONObject(i);
				Log.i("Current Food Name", currentFood.getString("name"));

				Food newFood = new Food(
						currentFood.getString("name"),
						currentFood.getString("link"),
						currentFood.getString("source_link"),
						currentFood.getInt("source_id"),
						currentFood.getString("image")
						);
				//NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE
				helperFoods.add(newFood);
				//NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE

			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		finally{
			return helperFoods;
		}
	}

	private ArrayList<Review> getReviewArrayListFromJSON(JSONObject myJSON){
		ArrayList<Review> helperReviews = new ArrayList<Review>();

		try{
			JSONArray myJSONArr = myJSON.getJSONArray("reviews");
			for(int i = 0; i < myJSONArr.length(); ++i){
				JSONObject currentReview = myJSONArr.getJSONObject(i);
				Log.i("DAO.java/getReviewArrayListFromJSON", "Current Review Name: "+currentReview.getString("name"));

				int source = -1;

				try{
					source = currentReview.getInt("source");
				}catch(JSONException e){
					Log.i("DAO.java/getReviewArrayListFromJSON","Catch: JSON Exception. No source provided");
					source = -1;
				}

				Review myReview = new Review(
						currentReview.getString("name"),
						Float.parseFloat(currentReview.getString("rating")),
						currentReview.getString("body").trim(),
						currentReview.getInt("date"),
						currentReview.getString("lang"),
						source
						);
				//NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE
				if(myReview.getBody().length() > 3)
					helperReviews.add(myReview);
				//NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE

			}

			Log.i("DAO.java/getReviewArrayListFromJSON","helperReviews.size() "+helperReviews.size());
		}catch(JSONException e){
			e.printStackTrace();
		}
		finally{
			return helperReviews;
		}
	}

}