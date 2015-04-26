package nku.edu.beerbuddyandroid;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class BeerService{

    private ArrayList<BeerItem> beerlist= new ArrayList<BeerItem>();

    public BeerService()
    {
        new getBeerJSON().execute("http://ontariobeerapi.ca/beers/");
    }

    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
            } else {
                Log.d("JSON", "Failed to download file");
            }
        } catch (Exception e) {
            Log.d("readJSONFeed", e.getMessage());
        }
        return stringBuilder.toString();
    }

    public class getBeerJSON extends AsyncTask
            <String, String, String> {
        protected String doInBackground(String... urls) {
            Log.d("MyAsyncTask",urls[0] + " in background.");
            return readJSONFeed(urls[0]);
        }

        protected void onPreExecute() {
            Log.d("MyAsyncTask","MyAsyncTask Started");
        }
        protected void onPostExecute(String result)
        {
            try {
                JsonParser parser = new JsonParser();
                JsonArray beers = (JsonArray)parser.parse(result);
                Iterator iterator = beers.iterator();
                Gson gson = new Gson();

                while(iterator.hasNext()) {
                    JsonElement json2 = (JsonElement) iterator.next();
                    BeerItem beer = gson.fromJson(json2, BeerItem.class);
                    beerlist.add(beer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList<BeerItem> beertemp = beerlist;
            Bundle arguements = new Bundle();
            arguements.putParcelableArrayList("ID_BEERS", beerlist);

        }


        }

    }



