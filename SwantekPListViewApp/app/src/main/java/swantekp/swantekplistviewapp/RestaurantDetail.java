package swantekp.swantekplistviewapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;



public class RestaurantDetail extends Activity {

    /*
    Activity to handle the display of the detailed view of a restaurant's information, using the data from the intent
    containing the particular restaurant to fill out all the required fields
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        TextView restaurantName = (TextView) findViewById(R.id.detail_name);
        RatingBar restaurantRating = (RatingBar) findViewById(R.id.detail_rating_value);
        TextView restaurantPriceRange = (TextView) findViewById(R.id.detail_price_range);
        TextView restaurantAddress = (TextView) findViewById(R.id.detail_address);
        TextView restaurantDescription = (TextView) findViewById(R.id.detail_description);
        Button backButton = (Button) findViewById(R.id.detail_back_button);
        ImageView restaurantPicture = (ImageView) findViewById(R.id.detail_picture);

        //Back button will pop this activity from the stack and return to the main restaurant List
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();

        /*
        If the intent isn't null, obtain the restaurant from the intent.  Use the data from that restaurant
        to fill out all of the respective data fields for display.
         */
        if(intent != null){

            Restaurant restaurant = intent.getParcelableExtra("Restaurant");
            restaurantName.setText(restaurant.getName());
            restaurantRating.setRating(restaurant.getRating());
            restaurantPriceRange.setText(restaurant.getPriceRange());
            restaurantAddress.setText(restaurant.getAddress());
            restaurantDescription.setText(restaurant.getDescription());
            restaurantPicture.setImageResource(Restaurant.getIconResource(restaurant.getPicture()));

        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_restaurant_detail, menu);
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
