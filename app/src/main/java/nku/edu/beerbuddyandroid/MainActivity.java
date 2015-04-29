package nku.edu.beerbuddyandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements BeerFragment.BeerFragmentHost
{
    private BeerReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter(BeerReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new BeerReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new BeerFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void getBeers()
    {
        Intent intent = BeerIntentService.createBeerIntentService(this);
        startService(intent);
    }

    public class BeerReceiver extends BroadcastReceiver
    {
        public static final String ACTION_RESP =
                "nku.edu.beerbuddy.android.intent.action.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent)
        {
            BeerFragment fragment = (BeerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

            if (fragment != null)
            {
                ArrayList<BeerItem> beers = intent.getParcelableArrayListExtra(BeerIntentService.OUT_BEERS);
                fragment.updateBeers(beers);
            }
        }
    }
}
