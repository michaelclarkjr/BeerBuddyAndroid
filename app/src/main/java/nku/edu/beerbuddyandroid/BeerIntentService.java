package nku.edu.beerbuddyandroid;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class BeerIntentService extends IntentService
{
    public static final String OUT_BEERS = "Beers";
    public static final String OUT_RESULT = "Result";

    private String beerURL = "http://ontariobeerapi.ca/beers/";

    public BeerIntentService()
    {
        super("BeerIntentService");
    }

    public static Intent createBeerIntentService(Context context)
    {
        Intent intent = new Intent(context, BeerIntentService.class);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        String result = "";
        ArrayList<BeerItem> beers = new ArrayList<BeerItem>();

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet(String.format(beerURL));

        try
        {
            HttpResponse response = httpclient.execute(get);
            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                InputStream inStream = entity.getContent();
                result = convertStreamToString(inStream);

                beers = new Gson().fromJson(result, new TypeToken<List<BeerItem>>(){}.getType());
            }
            else
            {
                result = "NOT WORKING!!";
            }
        }
        catch (ClientProtocolException e)
        {
            result = e.toString();
        }
        catch (IOException e)
        {
            result =  e.toString();
        }
        catch (Exception e)
        {
            result =  e.toString();
        }

        if (beers != null)
        {
            Intent broadcastIntent = new Intent(MainActivity.BeerReceiver.ACTION_RESP);
            broadcastIntent.setAction(MainActivity.BeerReceiver.ACTION_RESP);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putParcelableArrayListExtra(OUT_BEERS, beers);
            broadcastIntent.putExtra(OUT_RESULT, result);
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        }
    }

    private static String convertStreamToString(InputStream is)
    {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;

        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
        }
        catch (IOException e)
        {
            return e.toString();
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (IOException e)
            {
                return e.toString();
            }
        }

        return sb.toString();
    }
}
