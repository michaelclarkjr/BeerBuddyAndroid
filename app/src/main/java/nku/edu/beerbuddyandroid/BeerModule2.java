package nku.edu.beerbuddyandroid;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class BeerModule2
{
	private ArrayList<BeerData> beerlist;
	private final OnDownloadBeerFinished listener;
    public BeerModule2(OnDownloadBeerFinished listener) {
        this.listener = listener;
    }


    public interface  OnDownloadBeerFinished
    {
        void onDownloadFinished(ArrayList<BeerData> images);
    }

    public void getBeer() {


        beerlist = new ArrayList<BeerData>();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://ontariobeerapi.ca").build();
        final BeerInterface beerInterface = restAdapter
                .create(BeerInterface.class);


        beerInterface.getBeer(new Callback<JsonObject>() {

            @Override
            public void failure(RetrofitError arg0) {
                arg0.printStackTrace();
            }

            @Override
            public void success(JsonObject arg0,
                                Response arg1) {
                // Log.d("GALLERY2",
                // arg0.toString());

                JsonArray beers = arg0.getAsJsonArray();


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
                listener.onDownloadFinished(beerlist);
            }

        });

    }

}







