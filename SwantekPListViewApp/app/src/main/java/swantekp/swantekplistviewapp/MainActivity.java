package swantekp.swantekplistviewapp;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ListActivity {

    //Array containing restaurant objects that will be accessed by the adapter
    private static final Restaurant[] RESTAURANTS = {



            new Restaurant("Alinea", 5.0f, "$$$$", "1723 N Halsted St, Chicago, IL 60614", "Chef Grant Achatz draws foodies with New American tasting menus featuring highly creative plates." +
                    "  A definite must if you are in the area!\nZagat rated: best restaurants by Steppenwolf theater.", Restaurant.Picture.Alinea),

            new Restaurant("The Purple Pig", 4.5f, "$$", "500 N Michigan Ave, Chicago, IL 60611", "Adventurous small plates plus house-cured meats & a lengthy wine list in small, lively quarters."+
                    "  A perfect spot to meet with friends or to spend a romantic evening!\nZagat rated: best restuarants by Magnificent Mile.", Restaurant.Picture.PurplePig),

            new Restaurant("Quay", 4.0f, "$$$", "465 E Illinois St #70, Chicago, IL 60611", "Dining room, bar & sports lounge by the water boasting floor-to-ceiling windows & outdoor seating." +
                    "  A great spot to meet friends for an evening dinner after work.", Restaurant.Picture.Quay),

            new Restaurant("Girl & The Goat", 4.5f, "$$", "809 W Randolph St, Chicago, IL 60607", "Hot spot where Stephanie Izard serves up innovative small plates from a dramatic open kitchen." +
                    "  Note, space is limited and fills up quickly, be sure to make reservations early!\nZagat rated: best drink menu in the West Loop.", Restaurant.Picture.GirlAndGoat),

            new Restaurant("The Signature Room", 4.0f, "$$$", "875 N Michigan Ave, Chicago, IL 60611", "Upscale spot high atop the John Hancock Center with unreal views, American fare & a separate lounge." +
                    "  A stunning view awaits those dining at this fine establishment.\nZagat rated: best restaurant view.", Restaurant.Picture.SignatureRoom),

            new Restaurant("Yolk", 4.0f, "$$", "355 E Ohio St, Chicago, IL 60611", "Cafe chain serving creative breakfast dishes & sandwiches in a cheery, contemporary atmosphere." +
                    "  Huge menu containing a variety of breakfast and lunch items won't leave anyone disappointed!\nZagat rated: best restaurants by Navy Pier.", Restaurant.Picture.Yolk),

            new Restaurant("Gibson's Bar & Steakhouse", 4.5f, "$$$", "1028 N Rush St, Chicago, IL 60611", "Classic fare comes with a side of people-watching at the original location of the chophouse chain." +
                    "  If you're going to get a steak in the city, it should be at Gibson's.\nZagat rated: best restaurants by Magnificent Mile.", Restaurant.Picture.Gibsons),

            new Restaurant("Mity Nice Bar & Grill", 4.5f, "$$", "835 N Michigan Ave #510, Chicago, IL 60611", "Inside Water Tower Place, this sleek eatery serves American fare, including complimentary popovers." +
                    "  Great place to stop into after a long day of shopping!", Restaurant.Picture.MityNice),

            new Restaurant("RL Restaurant", 5.0f, "$$$", "115 E Chicago Ave, Chicago, IL 60611", "Upscale Ralph Lauren restaurant serving American classics in a sophisticated setting." +
                    "  If you enjoy a nice meal while doing some people watching, this is your spot!\nZagat rated: Chicago's best restaurant decor.", Restaurant.Picture.Rl),

            new Restaurant("Riva Restaurant on Navy Pier", 3.5f, "$$$", "700 E Grand Ave, Chicago, IL 60611", "Navy Pier perch featuring views of Lake Michigan & Downtown, with a large seafood selection." +
                    "  An excellent choice for high quality seafood with a lakefront view!\nZagat rated: best restaurants by Navy Pier.", Restaurant.Picture.Riva),

            new Restaurant("Avec", 4.5f, "$$$", "615 W Randolph St, Chicago, IL 60661", "Inventive small & large plates served in a cozy, minimalist space with communal seating." +
                    "  This intimate location makes for a perfect first date destination!\nZagat rated: best drink menu in the West Loop.", Restaurant.Picture.Avec),

            new Restaurant("Mercadito Chigago", 4.0f, "$$", "108 W Kinzie St, Chicago, IL 60654", "Cocktails and upscale Mexican eats keep this popular hangout bustling with a hip, young crowd." +
                    "  This locale offers both great food as well as a prime nightlife spot.\nZagat rated: best restaurants by Merchandise Mart.", Restaurant.Picture.Mercadito)


    };

    private Restaurant selectedRestaurant;  //Which restaurant was selected using the ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Use the default layout for a ListView
        //setContentView(R.layout.activity_main);
        setListAdapter(new RestaurantAdapter(this));  //Set the customized adapter
    }

    /*
    Behavior when a user clicks on a restaurant entry on the ListView.  Will create a new intent
    and put the current selected restaurant into that intent.  Sends this intent to the activity
    that will display the details about that particular restaurant.
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        selectedRestaurant = RESTAURANTS[position];

        Intent intent = new Intent(MainActivity.this, RestaurantDetail.class);
        intent.putExtra("Restaurant", selectedRestaurant);
        startActivity(intent);

    }


    /*
    Customized adapter for the ListView.  This adapter will cache Bitmaps for the image files associated with restaurants
    to become more efficient.  The getView() method is also optimized to recycle Views for the rows of the ListView if that
    View has already been inflated.  Also uses the ViewHolder pattern in order to hold references to View elements.
     */
    private static class RestaurantAdapter extends BaseAdapter{

        private LayoutInflater inflater;
        private Map<Restaurant.Picture, Bitmap> pictures;  //Cache the bitmaps for the picture files

        RestaurantAdapter(Context context){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            pictures = new HashMap<>();
            for(Restaurant.Picture picture : Restaurant.Picture.values()){
                pictures.put(picture, BitmapFactory.decodeResource(context.getResources(), Restaurant.getIconResource(picture)));
            }
        }


        @Override
        public int getCount() {
            return RESTAURANTS.length;
        }

        @Override
        public Object getItem(int position) {
            return RESTAURANTS[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /*
        Will recycle rows if they have already been created.  Employs the ViewHolder pattern.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            View row = convertView;

            //If row hasn't been inflated, inflate it and store View references in the ViewHolder
            if(row == null){
                row = inflater.inflate(R.layout.restaurant_row_item, parent, false);
                holder = new ViewHolder();
                holder.restaurantName = (TextView)row.findViewById(R.id.restaurant_name);
                holder.restaurantRating = (TextView)row.findViewById(R.id.restaurant_rating);
                holder.restaurantPrice = (TextView)row.findViewById(R.id.restaurant_price_range);
                holder.restaurantAddress = (TextView)row.findViewById(R.id.restaurant_address);
                row.setTag(holder);
            }

            else{
                holder = (ViewHolder)row.getTag();  //Recycle the row if not null
            }

            //Get the selected restaurant and set all the View elements with the appropriate data from the restaurant
            Restaurant restaurant = RESTAURANTS[position];
            holder.restaurantName.setText(restaurant.getName());
            holder.restaurantRating.setText(restaurant.getRating() + " stars");
            holder.restaurantPrice.setText(restaurant.getPriceRange());
            holder.restaurantAddress.setText(restaurant.getAddress());

            return row;
        }

        static class ViewHolder{

            TextView restaurantName;
            TextView restaurantRating;
            TextView restaurantPrice;
            TextView restaurantAddress;

        }
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
