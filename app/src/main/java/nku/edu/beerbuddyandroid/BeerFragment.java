package nku.edu.beerbuddyandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class BeerFragment extends Fragment
{
    private BeerFragmentHost host;
    private ListView beerList;
    private BeerAdapter adapter;

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

        return v;
    }

    public void updateBeers(ArrayList<BeerItem> beers)
    {
        adapter = new BeerAdapter(getActivity(), R.layout.beer_item, beers);
        beerList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}