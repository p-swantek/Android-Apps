package swantekp.swantekpremotecontrolversion2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class ConfigureFavorites extends Activity {

    private StringBuilder newChannel = new StringBuilder("");  //Holds the numbers the user is typing in for the new channel selection
    private String channelTitle = "";  //New title for the new favorite channel


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_favorites);

        //RadioButton leftButton = (RadioButton)findViewById(R.id.left_radio_button);
        //RadioButton middleButton = (RadioButton)findViewById(R.id.middle_radio_button);
        //RadioButton rightButton = (RadioButton)findViewById(R.id.right_radio_button);

        final RadioGroup group = (RadioGroup)findViewById(R.id.radio_group );

        final EditText newChannelText = (EditText)findViewById(R.id.new_favorite_channel_text);
        final TextView newChannelNumber = (TextView)findViewById(R.id.new_favorite_channel_number);

        Button zeroButton = (Button)findViewById(R.id.zero_button);
        Button oneButton = (Button)findViewById(R.id.one_button);
        Button twoButton = (Button)findViewById(R.id.two_button);
        Button threeButton = (Button)findViewById(R.id.three_button);
        Button fourButton = (Button)findViewById(R.id.four_button);
        Button fiveButton = (Button)findViewById(R.id.five_button);
        Button sixButton = (Button)findViewById(R.id.six_button);
        Button sevenButton = (Button)findViewById(R.id.seven_button);
        Button eightButton = (Button)findViewById(R.id.eight_button);
        Button nineButton = (Button)findViewById(R.id.nine_button);
        Button chPlusButton = (Button)findViewById(R.id.CH_plus_button);
        Button chMinusButton = (Button)findViewById(R.id.CH_minus_button);

        Button saveButton = (Button)findViewById(R.id.configure_save_button);
        Button cancelButton = (Button)findViewById(R.id.configure_cancel_button);


        /*
        Set the listener for the EditText where the user will enter the title for a new favorite channel
        The title must be between 2 and 4 characters long, if not an appropriate Toast is displayed
         */
        newChannelText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean wasHandled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(newChannelText.getWindowToken(), 0);  //Hide keyboard if action done pressed

                    String channelText = ((EditText) v).getText().toString();
                    if (channelText.length() >= 2 && channelText.length() <= 4) {
                        channelTitle = channelText;
                        wasHandled = true;
                    }

                    else {
                        Toast.makeText(getApplicationContext(), "The description for a channel must be between 2 and 4 characters", Toast.LENGTH_LONG).show();
                    }


                }

                return wasHandled;
            }
        });

        /*
        Listener to handle digit button clicks, when the user types in a new channel number, the number will be displayed
        in the TextView above the digit buttons.
         */
        TextView.OnClickListener digitPressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newChannel.length() < 3){   //A channel isn't completely entered until it has 3 digits
                    newChannel.append(((Button)v).getText().toString());

                    if (newChannel.toString().equals(("000"))){  //Channel 000 is invalid, will just auto change current channel back to 001
                        newChannel = new StringBuilder("");
                        newChannelNumber.setText("001");
                    }
                    else {
                        newChannelNumber.setText(newChannel.toString());  //set the TextView which displays the new channel
                    }
                }
                else{  //After a full channel is entered, start a new StringBuilder to allow the next channel to be entered
                    newChannel = new StringBuilder("");
                    newChannel.append(((Button)v).getText().toString());
                    newChannelNumber.setText(newChannel.toString());
                }
            }
        };

        //Set the listener for all the digit buttons
        zeroButton.setOnClickListener(digitPressed);
        oneButton.setOnClickListener(digitPressed);
        twoButton.setOnClickListener(digitPressed);
        threeButton.setOnClickListener(digitPressed);
        fourButton.setOnClickListener(digitPressed);
        fiveButton.setOnClickListener(digitPressed);
        sixButton.setOnClickListener(digitPressed);
        sevenButton.setOnClickListener(digitPressed);
        eightButton.setOnClickListener(digitPressed);
        nineButton.setOnClickListener(digitPressed);

        /*
        The listener for the buttons which increment or decrement the channel by 1.
         */
        TextView.OnClickListener modifyChannelNumber = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int channelToModify = Integer.parseInt(newChannelNumber.getText().toString());

                if (v.getId() == R.id.CH_plus_button){ //If the increment button was pressed, add 1 to current channel
                    channelToModify++;
                    if (channelToModify > 999){   //If the channel becomes > 999, will roll back to channel 001
                        newChannelNumber.setText("001");
                    }
                    else{      //Before setting the new channel, make sure its string version is of length 3, pad with zeros on left to make length 3
                        switch (String.valueOf(channelToModify).length()){
                            case 1:
                                newChannelNumber.setText("00" + String.valueOf(channelToModify));
                                break;
                            case 2:
                                newChannelNumber.setText("0" + String.valueOf(channelToModify));
                                break;
                            default:
                                newChannelNumber.setText(String.valueOf(channelToModify));
                                break;
                        }
                    }
                }

                else{  //If the decrement button was pressed
                    channelToModify--;
                    if (channelToModify < 1){  //If the channel becomes 0 when decrementing, roll back to channel 999
                        newChannelNumber.setText("999");
                    }
                    else{  //Again, pad with zeros to maintain length of 3
                        switch (String.valueOf(channelToModify).length()){
                            case 1:
                                newChannelNumber.setText("00" + String.valueOf(channelToModify));
                                break;
                            case 2:
                                newChannelNumber.setText("0" + String.valueOf(channelToModify));
                                break;
                            default:
                                newChannelNumber.setText(String.valueOf(channelToModify));
                                break;
                        }
                    }
                }
            }
        };

        //Set the channel increment and decrement button listeners
        chPlusButton.setOnClickListener(modifyChannelNumber);
        chMinusButton.setOnClickListener(modifyChannelNumber);



        /*
        Listener for the save button.  If all the inputs are valid (i.e. a radio button was checked,
        a valid title was entered, and a valid channel number was entered), the new favorite channel configuration
        will be passed back to the main activity so that the remote control interface will be updated to reflect
        the new favorite channel.
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if a valid title and channel are entered for the favorite, display a Toast if either is invalid
                if ((channelTitle.length() >= 2 && channelTitle.length() <= 4) && newChannelNumber.getText().toString().length() == 3) {
                    int whichRadioButton = group.getCheckedRadioButtonId(); //Get which radio button was checked from the group
                    if(whichRadioButton != -1) {  //If no radio button from the group is selected, -1 will be returned here.  If this occurs, display a Toast to tell user to click a RadioButton
                        Intent result = new Intent();

                        //Put all the data regarding the updated favorite channel into an intent and pass back to the main activity
                        result.putExtra("New Channel Text", channelTitle);
                        result.putExtra("New Channel Number", newChannelNumber.getText().toString());
                        switch (whichRadioButton) {

                            case R.id.left_radio_button:
                                result.putExtra("Which Favorite", 1);
                                break;
                            case R.id.middle_radio_button:
                                result.putExtra("Which Favorite", 2);
                                break;
                            case R.id.right_radio_button:
                                result.putExtra("Which Favorite", 3);
                                break;


                        }
                        setResult(RESULT_OK, result);
                        finish();
                    }

                    //If the user didn't select a RadioButton, display the appropriate Toast
                    else {
                        Toast.makeText(getApplicationContext(), "Please select which favorite channel to change.", Toast.LENGTH_LONG).show();
                    }


                }

                //Display an appropriate Toast if either the title or the channel number was invalid
                else {
                    Toast.makeText(getApplicationContext(),
                                    "Note: Either an invalid channel title or an invalid channel number was entered.\n" +
                                        "Please make sure the title is between 2 and 4 characters long and the channel number is 3 digits long.",
                                        Toast.LENGTH_LONG).show();
                }

            }
        });

        /*
        Cancel button will just transition back to the main activity
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configure_favorites, menu);
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
