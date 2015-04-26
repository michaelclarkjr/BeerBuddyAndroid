package nku.edu.beerbuddyandroid;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

public class BeerService{

    private ArrayList<BeerItem> beerlist;

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
                JsonObject beerObject = parser.parse(result).getAsJsonObject();
                JsonArray beers = beerObject.getAsJsonArray();

                for (int j = 0; j < beers.size(); j++) {
                    BeerItem beer = new BeerItem();
                    JsonObject tempObject = beers.getAsJsonObject();
                    beer.setName(tempObject.get("name").getAsString());
                    beer.setBeer_id(tempObject.get(
                            "beer_id").getAsInt());
                    beer.setImage_url(tempObject.get(
                            "image_url").getAsString());
                    beer.setCategory(tempObject.get(
                            "category").getAsString());
                    beer.setAbv(tempObject.get(
                            "abv").getAsString());
                    beer.setType(tempObject.get(
                            "type").getAsString());
                    beer.setBrewer(tempObject.get(
                            "brewer").getAsString());
                    beer.setCountry(tempObject.get(
                            "country").getAsString());
                    beer.setOn_sale(tempObject.get(
                            "on_sale").getAsBoolean());


                    beerlist.add(beer); /**/
                }
            } catch (Exception e) {
                Log.d("BeerJsontask", e.getMessage());
            }
        }


        }
    public ArrayList<BeerItem> getBeerlist() {
        return beerlist;
    }
    }



