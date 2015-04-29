package nku.edu.beerbuddyandroid;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BeerAdapter extends ArrayAdapter
{
    private final Activity   context;
    private final List<BeerItem> beers;

    public BeerAdapter(Activity context, int resource, List<BeerItem> beers)
    {
        super(context, resource, beers);
        this.context = context;
        this.beers = beers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder vh;

        if (convertView == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.beer_item, null);

            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) convertView.getTag();
        }

        BeerItem beer = this.beers.get(position);
        vh.beerName.setText(beer.getName());

        return convertView;
    }

    private class ViewHolder
    {
        TextView  beerName;
        ImageView image;

        public ViewHolder(View v)
        {
            beerName = (TextView) v.findViewById(R.id.beer_name);
            image = (ImageView) v.findViewById(R.id.beer_image);
        }
    }
}
