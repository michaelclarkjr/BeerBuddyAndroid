package nku.edu.beerbuddyandroid;

import android.content.Intent;
import android.support.v4.app.*;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    BeerFragment beerFrag;
    private ArrayList<BeerItem> beers = new ArrayList<BeerItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BeerService BS = new BeerService();
        beers = BS.getBeerlist();
        ArrayList<BeerItem> beers2= beers;
        Bundle arguements = new Bundle();
        arguements.putParcelableArrayList("ID_BEERS", beers);


       // FragmentManager fragMan = getFragmentManager();
       // FragmentTransaction fragTransaction = fragMan.beginTransaction();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
