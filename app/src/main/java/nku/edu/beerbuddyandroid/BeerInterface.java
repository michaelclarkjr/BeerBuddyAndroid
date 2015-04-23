package nku.edu.beerbuddyandroid;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import com.google.gson.JsonObject;

public interface BeerInterface {


    @GET("/beers/")
    void getBeer(Callback<JsonObject> callback);

}
