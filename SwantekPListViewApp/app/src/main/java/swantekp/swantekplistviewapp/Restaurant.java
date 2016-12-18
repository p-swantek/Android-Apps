package swantekp.swantekplistviewapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Data type representing a restaurant.  All restaurants have a name, a rating (out of 5), a price range, an address, a description,
 * as well as a picture associated with the restaurant.  Implements the Parcelable interface in order to allow for a restauant
 * object to passed efficiently using Intents.
 *
 */
public class Restaurant implements Parcelable {

    //Which picture should be associated with the current restaurant
    enum Picture {Alinea, Avec, Gibsons, GirlAndGoat, Mercadito, MityNice, PurplePig, Quay, Riva, Rl, SignatureRoom, Yolk}

    //The required data for a restaurant
    private String name;
    private float rating;
    private String priceRange;
    private String address;
    private String description;
    private Picture picture;

    //Constructor for a restaurant
    public Restaurant(String newName, float currRating, String currPriceRange, String currAddress, String newDescription, Picture currPicture){

        setName(newName);
        setRating(currRating);
        setPriceRange(currPriceRange);
        setAddress(currAddress);
        setDescription(newDescription);
        setPicture(currPicture);

    }



    //Protected constructor, used as part of the implementation of Parcelable
    protected Restaurant(Parcel in) {
        name = in.readString();
        rating = in.readFloat();
        priceRange = in.readString();
        address = in.readString();
        description = in.readString();
        picture = Picture.values()[in.readInt()];
    }


    //Parcelable implementation
    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };


    /*
    Setters and getters for all the instance variables
     */
    private void setName(String newName){
        name = newName;
    }

    private void setRating(float newRating){
        rating = newRating;
    }

    private void setPriceRange(String newPriceRange){
        priceRange = newPriceRange;
    }

    private void setAddress(String newAddress){
        address = newAddress;
    }

    private void setDescription(String newDescription){
        description = newDescription;
    }

    private void setPicture(Restaurant.Picture newPicture){
        picture = newPicture;
    }

    public String getName(){return name;}
    public float getRating(){return rating;}
    public String getPriceRange(){return priceRange;}
    public String getAddress(){return address;}
    public String getDescription(){return description;}
    public Restaurant.Picture getPicture(){return picture;}


    /*
    Static method that will retrieve the resource ID of the drawable resource that will be used
    for the restaurant's picutre
     */
    public static int getIconResource(Restaurant.Picture picture){

        switch (picture){

            case Alinea:
                return R.drawable.alinea;

            case Avec:
                return R.drawable.avec;

            case Gibsons:
                return R.drawable.gibsons;

            case GirlAndGoat:
                return R.drawable.girlandgoat;

            case Mercadito:
                return R.drawable.mercadito;

            case MityNice:
                return R.drawable.mitynice;

            case PurplePig:
                return R.drawable.purplepig;

            case Quay:
                return R.drawable.quay;

            case Riva:
                return R.drawable.riva;

            case Rl:
                return R.drawable.rl;

            case SignatureRoom:
                return R.drawable.signatureroom;

            case Yolk:
                return R.drawable.yolk;

        }

        return -1;
    }


    @Override
    public String toString(){
        return getName();
    }


    /*
    Methods implemented as part of the Parcelable interface.  Write all the relevant data for a restaurant to a Parcel
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(getName());
        dest.writeFloat(getRating());
        dest.writeString(getPriceRange());
        dest.writeString(getAddress());
        dest.writeString(getDescription());
        dest.writeInt(getPicture().ordinal());

    }
}
