package nku.edu.beerbuddyandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;


public class BeerFragment extends Fragment
{
    private BeerFragmentHost host;
    private ListView beerList;
    private BeerAdapter adapter;
    private final String[] menu = {"A-Z","Category"};

    public interface BeerFragmentHost
    {
        void getBeers();
    }

    public BeerFragment(){/*Required empty public constructor*/}

    @Override
    public void onAttach( Activity activity )
    {
        super.onAttach(activity);

        try
        {
            host = (BeerFragmentHost) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement BeerFragmentHost");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        host = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (host != null)
        {
            host.getBeers();
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle state )
    {
        View v = inflater.inflate( R.layout.beer_fragment, container, false);
        beerList = (ListView) v.findViewById(R.id.beer_list);

        adapter = new BeerAdapter(getActivity(), R.layout.beer_item, new ArrayList<BeerItem>());
        beerList.setAdapter(adapter);
        beerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position,long id){
                BeerDetailFragment detailFragment = new BeerDetailFragment();
                Bundle args = new Bundle();
                args.putParcelable("beerDetails",  (BeerItem)adapter.getItem(position));
                detailFragment.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.fragment_container, detailFragment).hide(BeerFragment.this).addToBackStack(null).commit();
            }
        });


        return v;
    }

    public void updateBeers(ArrayList<BeerItem> beers)
    {
        adapter = new BeerAdapter(getActivity(), R.layout.beer_item, beers);
        beerList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

            int id = item.getItemId();

            if (id == R.id.sort) {

                menuAlert();
            }


        return super.onOptionsItemSelected(item);
    }

    public void menuAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the dialog title
        builder.setTitle("Sort by")

                // specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive call backs when items are selected
                // again, R.array.choices were set in the resources res/values/strings.xml
                .setSingleChoiceItems(menu, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }

                })

                        // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        if(selectedPosition==0)
                        {
                            adapter.sortAZ();
                            adapter.notifyDataSetChanged();
                        }
                        if(selectedPosition==1)
                        {
                            adapter.sortCategory();
                            adapter.notifyDataSetChanged();
                        }

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })

                .show();

    }

}