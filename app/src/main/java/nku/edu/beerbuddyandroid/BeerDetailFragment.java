package nku.edu.beerbuddyandroid;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class BeerDetailFragment extends Fragment
{
    private  BeerItem beer;
    TextView  beerName;
    ImageView image;
    TextView  beerCategory;
    TextView  beerAbv;
    TextView  beerBrewer;



    public BeerDetailFragment(){/*Required empty public constructor*/}

    @Override
    public void onAttach( Activity activity )
    {
        super.onAttach(activity);

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            beer=(BeerItem)getArguments().getParcelable("beerDetails");
        }

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle state )
    {
        View v = inflater.inflate( R.layout.detail_fragment, container, false);
        beerName = (TextView) v.findViewById(R.id.beer_name);
        beerCategory = (TextView) v.findViewById(R.id.beer_category);
        beerAbv = (TextView) v.findViewById(R.id.beer_abv);
        beerBrewer = (TextView) v.findViewById(R.id.beer_brewer);
        image = (ImageView)v.findViewById(R.id.beer_image);
        beerName.setText(beer.getName());
        beerCategory.setText(beer.getCategory()+"-"+beer.getType());
        beerAbv.setText(beer.getAbv());
        beerBrewer.setText(beer.getBrewer());
        Picasso.with(getActivity()).load(beer.getImage_url()).noFade().placeholder(R.drawable.bottle).into(image);
        return v;
    }


}