package swantekp.swantekppointofsale;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    //Prebuilt menu of items for the app
    private static final String[] MENU_ITEMS = {"Cheeseburger", "Hamburger", "Coffee", "Tea", "French Fries",
                                                    "Soft Drink(Large)", "Soft Drink(Small)", "Sweet Potato Fries",
                                                    "Patty Melt", "Big Salad", "Cake", "Muffins", "Cookies"};
    //The associated prices for all items on menu
    private static final double[] MENU_PRICES = {10.50, 9.50, 2.50, 2.50, 3.50, 2.75,
                                                1.50, 4.00, 13.00, 8.50, 5.00, 3.00, 1.00};

    private Map<String, Double> currentMenu = new HashMap<>();  //Holds the current menu as a HashMap of item name keys with price for that item as the value
    private double runningTotal = 0.00;  //Running total of this current order
    private int currentItemCount = 1;   //Default count for an item is 1
    private StringBuilder summary = new StringBuilder("");  //StringBuilder used to print out the order summary when total is calculated


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildMenu(MENU_ITEMS, MENU_PRICES, currentMenu);


        /* Set up the Buttons, TextViews, and EditTexts */
        Button newOrder = (Button)findViewById(R.id.new_order_button);
        Button newItem = (Button)findViewById(R.id.new_item_button);
        final AutoCompleteTextView menuEntries = (AutoCompleteTextView)findViewById(R.id.menu_entries);
        final EditText itemAmounts = (EditText)findViewById(R.id.item_amounts);
        final EditText unitPrice = (EditText)findViewById(R.id.price);
        Button calculateTotal = (Button)findViewById(R.id.calculate_total);
        final TextView finalSum = (TextView)findViewById(R.id.final_sum);
        final TextView orderSummary = (TextView)findViewById(R.id.order_summary);

        /* Set ArrayAdapter on the AutoCompleteTextView to make it auto fill in the item entries */
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, MENU_ITEMS);
        menuEntries.setAdapter(adapter);


        /* Set listener for new order button, will reset all the text fields and the order contents */
        newOrder.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuEntries.setText(null);
                        itemAmounts.setText("1");
                        unitPrice.setText("0.00");
                        finalSum.setText(null);
                        orderSummary.setText(null);
                        currentItemCount = 1;
                        runningTotal = 0.00;
                        summary = new StringBuilder("");

                    }
                }
        );

        /* Set the listener for the new item button, will reset the fields for item entry, quantity, and prices only */
        newItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menuEntries.setText(null);
                        itemAmounts.setText("1");
                        unitPrice.setText("0.00");
                        currentItemCount = 1;
                    }
                }
        );

        /* Set the listener for the AutoCompleteTextView for item entries.  The action button when pressed,
            will dismiss the soft keyboard.  If the item entered is on the menu, the price for that item
            will be found in the HashMap and will be displayed in the unit price TextView.  If the item is
            not on the menu, the unit price remains 0.00.
         */
        menuEntries.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean wasHandled = false;
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(menuEntries.getWindowToken(), 0);  //Hide keyboard if action done pressed

                            String item = ((AutoCompleteTextView) v).getText().toString();
                            if (currentMenu.containsKey(item)) {
                                unitPrice.setText(String.format("%.2f", currentMenu.get(item))); //Set the unit price if item is on menu
                            }

                            wasHandled = true;
                        }

                        return wasHandled;
                    }
                }
        );


        /* Set the listener for entering item amounts.  The default value for an item count is 1, but the user can change to
            be another integer.  When user presses action done, the keyboard will be hidden and the contents of the EditText
            will be parsed to get an integer that will be used to update the current item count.
         */
        itemAmounts.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean wasHandled = false;
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(itemAmounts.getWindowToken(), 0);

                            currentItemCount = Integer.parseInt(((EditText) v).getText().toString());
                            wasHandled = true;
                        }

                        return wasHandled;
                    }
                }


        );


        /* Set the listener for the calculate total button.  Pressing total will update the running total of the order
            based on the current item, its count, and its unit price.  This total prices will then be displayed.  A summary
            of all the items ordered and in what quantities will then be displayed at the bottom of the screen.
         */
        calculateTotal.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runningTotal += currentItemCount * Double.parseDouble(unitPrice.getText().toString());
                        finalSum.setText(String.format("$%.2f", runningTotal));
                        summary.append(menuEntries.getText().toString());
                        summary.append(" x");
                        summary.append(currentItemCount);
                        summary.append("\n");

                        orderSummary.setText(summary.toString());
                    }
                }
        );

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


    /* Helper method used to build up a menu given a String array of item names and an array of
        doubles representing the prices.
     */
    private static void buildMenu(String[] items, double[] prices, Map<String, Double> menuToBuild){

        int shorterLength = Math.min(items.length, prices.length);

        for (int i = 0; i < shorterLength; i++)
            menuToBuild.put(items[i], prices[i]);


    }
}

