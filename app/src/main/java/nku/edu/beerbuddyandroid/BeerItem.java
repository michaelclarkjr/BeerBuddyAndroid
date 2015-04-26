package nku.edu.beerbuddyandroid;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class BeerItem implements   Parcelable {

    protected String name;

    protected int beer_id;

    protected String image_url;

    protected String category;
    protected String abv;
    protected String type;
    protected String brewer;
    protected String country;
    protected String on_sale;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeer_id() {
        return beer_id;
    }

    public void setBeer_id(int beer_id) {
        this.beer_id = beer_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAbv() {
        return abv;
    }

    public void setAbv(String abv) {
        this.abv = abv;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrewer() {
        return brewer;
    }

    public void setBrewer(String brewer) {
        this.brewer = brewer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String isOn_sale() {
        return on_sale;
    }

    public void setOn_sale(String on_sale) {
        this.on_sale = on_sale;
    }
    protected BeerItem(){}

        protected BeerItem(Parcel in) {
            name = in.readString();
            beer_id = in.readInt();
            image_url = in.readString();
            category = in.readString();
            abv = in.readString();
            type = in.readString();
            brewer = in.readString();
            country = in.readString();
            on_sale = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeInt(beer_id);
            dest.writeString(image_url);
            dest.writeString(category);
            dest.writeString(abv);
            dest.writeString(type);
            dest.writeString(brewer);
            dest.writeString(country);
            dest.writeString(on_sale);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<BeerItem> CREATOR = new Parcelable.Creator<BeerItem>() {
            @Override
            public BeerItem createFromParcel(Parcel in) {
                return new BeerItem(in);
            }

            @Override
            public BeerItem[] newArray(int size) {
                return new BeerItem[size];
            }
        };
}
