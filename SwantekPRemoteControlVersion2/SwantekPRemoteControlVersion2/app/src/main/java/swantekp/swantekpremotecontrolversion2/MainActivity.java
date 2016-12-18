package swantekp.swantekpremotecontrolversion2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;


public class MainActivity extends Activity {


    private static int CONFIGURE_FAVORITE = 100;  //request code for the intent to change the favorite channels

    private StringBuilder channel = new StringBuilder("");  //Holds the numbers the user is typing in for the channel selection

    //Default favorite channels, can be modified by using the configure favorite channel functionality
    private String favorite1 = "047";
    private String favorite2 = "217";
    private String favorite3 = "011";

    //Will allow the text of the favorite channel buttons to be changed
    private Button firstFavorite;
    private Button secondFavorite;
    private Button thirdFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        The TextViews that will hold the TV statistics at the top portion of the screen.
        Power status, volume level, and the current channel that the user has input will
        be shown.
         */
        final TextView powerStatus = (TextView)findViewById(R.id.power_status);
        final TextView volumeLevel = (TextView)findViewById(R.id.volume_level);
        final TextView currentChannel = (TextView)findViewById(R.id.current_channel);

        /*
        The power switch to turn the TV on/off.  The SeekBar allows the user to
        select a volume to set the TV to, volume level is 0 to 100 inclusive
         */
        Switch powerSwitch = (Switch)findViewById(R.id.power_switch);
        final SeekBar volumeAdjuster = (SeekBar)findViewById(R.id.volume_adjuster);

        /*
        All the different buttons of the remote control.  There will be numerical buttons
        for the user to use to enter the channel.  Two buttons exist that will allow the user
        to both increment and decrement the current channel by 1.  In addition, there are
        three preset buttons representing favorite channels that will automatically change the
        TV to that channel when pressed.
         */
        final Button zeroButton = (Button)findViewById(R.id.zero_button);
        final Button oneButton = (Button)findViewById(R.id.one_button);
        final Button twoButton = (Button)findViewById(R.id.two_button);
        final Button threeButton = (Button)findViewById(R.id.three_button);
        final Button fourButton = (Button)findViewById(R.id.four_button);
        final Button fiveButton = (Button)findViewById(R.id.five_button);
        final Button sixButton = (Button)findViewById(R.id.six_button);
        final Button sevenButton = (Button)findViewById(R.id.seven_button);
        final Button eightButton = (Button)findViewById(R.id.eight_button);
        final Button nineButton = (Button)findViewById(R.id.nine_button);
        final Button chPlusButton = (Button)findViewById(R.id.CH_plus_button);
        final Button chMinusButton = (Button)findViewById(R.id.CH_minus_button);

        firstFavorite = (Button)findViewById(R.id.favorite1_button);
        secondFavorite = (Button)findViewById(R.id.favorite2_button);
        thirdFavorite = (Button)findViewById(R.id.favorite3_button);

        final Button switchToDVRButton = (Button)findViewById(R.id.switch_to_dvr_button);
        final Button configureFavoriteButton = (Button)findViewById(R.id.switch_to_configure_button);

        /*
        The initial state of the remote and TV is off when the app first starts and all buttons
        and controls will be disabled, the user must then switch the power to on in order to start
        the TV and enable the buttons/controls.  All the buttons
        and volume slider will be disabled when the TV is turned off again, but the volume level
        and current channel will remain the same should the TV be turned back on.
         */
        volumeAdjuster.setEnabled(false);
        zeroButton.setEnabled(false);
        oneButton.setEnabled(false);
        twoButton.setEnabled(false);
        threeButton.setEnabled(false);
        fourButton.setEnabled(false);
        fiveButton.setEnabled(false);
        sixButton.setEnabled(false);
        sevenButton.setEnabled(false);
        eightButton.setEnabled(false);
        nineButton.setEnabled(false);
        chPlusButton.setEnabled(false);
        chMinusButton.setEnabled(false);
        firstFavorite.setEnabled(false);
        secondFavorite.setEnabled(false);
        thirdFavorite.setEnabled(false);


        /*
        The event listener for the power switch that controls the TV power state.  If checked on, will display
        that the TV is on and all the controls will become enabled for use.  If checked back off, the TV will
        display that it is now off and all the controls will be disabled for use.
         */
        powerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    channel = new StringBuilder("");
                    powerStatus.setText("On");
                    //volumeLevel.setText("0");
                    //currentChannel.setText("001");

                    volumeAdjuster.setEnabled(true);
                    zeroButton.setEnabled(true);
                    oneButton.setEnabled(true);
                    twoButton.setEnabled(true);
                    threeButton.setEnabled(true);
                    fourButton.setEnabled(true);
                    fiveButton.setEnabled(true);
                    sixButton.setEnabled(true);
                    sevenButton.setEnabled(true);
                    eightButton.setEnabled(true);
                    nineButton.setEnabled(true);
                    chPlusButton.setEnabled(true);
                    chMinusButton.setEnabled(true);
                    firstFavorite.setEnabled(true);
                    secondFavorite.setEnabled(true);
                    thirdFavorite.setEnabled(true);
                }
                else{

                    channel = new StringBuilder("");
                    powerStatus.setText("Off");
                    //volumeLevel.setText("0");
                    //currentChannel.setText("001");

                    volumeAdjuster.setEnabled(false);
                    zeroButton.setEnabled(false);
                    oneButton.setEnabled(false);
                    twoButton.setEnabled(false);
                    threeButton.setEnabled(false);
                    fourButton.setEnabled(false);
                    fiveButton.setEnabled(false);
                    sixButton.setEnabled(false);
                    sevenButton.setEnabled(false);
                    eightButton.setEnabled(false);
                    nineButton.setEnabled(false);
                    chPlusButton.setEnabled(false);
                    chMinusButton.setEnabled(false);
                    firstFavorite.setEnabled(false);
                    secondFavorite.setEnabled(false);
                    thirdFavorite.setEnabled(false);
                }


            }
        });

        /*
        The listener for the volume seek bar.  Allows the user to select from a range of volumes 0 to
        100 inclusive by sliding the slider either left or right respectively.  The default volume when
        first starting the app (when TV is off at first) will be 0.
         */
        volumeAdjuster.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumeLevel.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        /*
        The listener for when a digit button is pressed when a user to entering numbers for the channel
         */
        TextView.OnClickListener digitPressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (channel.length() < 3){   //A channel isn't completely entered until it has 3 digits
                    channel.append(((Button)v).getText().toString());

                    if (channel.toString().equals(("000"))){  //Channel 000 is invalid, will just auto change current channel back to 001
                        channel = new StringBuilder("");
                        currentChannel.setText("001");
                    }
                    else {
                        currentChannel.setText(channel.toString());  //set the current channel TextView on top of screen
                    }
                }
                else{  //After a full channel is entered, start a new StringBuilder to allow the next channel to be entered
                    channel = new StringBuilder("");
                    channel.append(((Button)v).getText().toString());
                    currentChannel.setText(channel.toString());
                }
            }
        };

        /*
        Set all the listeners for the digit buttons.
         */
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
                int channelToModify = Integer.parseInt(currentChannel.getText().toString());

                if (v.getId() == R.id.CH_plus_button){ //If the increment button was pressed, add 1 to current channel
                    channelToModify++;
                    if (channelToModify > 999){   //If the channel becomes > 999, will roll back to channel 001
                        currentChannel.setText("001");
                    }
                    else{      //Before setting the new channel, make sure its string version is of length 3, pad with zeros on left to make length 3
                        switch (String.valueOf(channelToModify).length()){
                            case 1:
                                currentChannel.setText("00" + String.valueOf(channelToModify));
                                break;
                            case 2:
                                currentChannel.setText("0" + String.valueOf(channelToModify));
                                break;
                            default:
                                currentChannel.setText(String.valueOf(channelToModify));
                                break;
                        }
                    }
                }

                else{  //If the decrement button was pressed
                    channelToModify--;
                    if (channelToModify < 1){  //If the channel becomes 0 when decrementing, roll back to channel 999
                        currentChannel.setText("999");
                    }
                    else{  //Again, pad with zeros to maintain length of 3
                        switch (String.valueOf(channelToModify).length()){
                            case 1:
                                currentChannel.setText("00" + String.valueOf(channelToModify));
                                break;
                            case 2:
                                currentChannel.setText("0" + String.valueOf(channelToModify));
                                break;
                            default:
                                currentChannel.setText(String.valueOf(channelToModify));
                                break;
                        }
                    }
                }
            }
        };

        /*
        Set the listeners for the increment and decrement buttons.
         */
        chPlusButton.setOnClickListener(modifyChannelNumber);
        chMinusButton.setOnClickListener(modifyChannelNumber);


        /*
        The listener for when the favorite channel buttons are pressed.
         */
        TextView.OnClickListener favoritesPressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){     //Find which favorite channel button was pressed and change the channel to that station's channel

                    case R.id.favorite1_button:
                        currentChannel.setText(favorite1);
                        break;
                    case R.id.favorite2_button:
                        currentChannel.setText(favorite2);
                        break;
                    case R.id.favorite3_button:
                        currentChannel.setText(favorite3);
                        break;

                }
            }
        };

        /*
        Set the listeners for the favorite channel buttons.
         */
        firstFavorite.setOnClickListener(favoritesPressed);
        secondFavorite.setOnClickListener(favoritesPressed);
        thirdFavorite.setOnClickListener(favoritesPressed);


        /*
        Set the listener for the switch to DVR button.
        Will make a new intent that will be used to start the DVR activity
         */
        switchToDVRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ControlDVR.class);
                startActivity(intent);
            }
        });

        /*
        Set the listener for the configure favorite channel button.
        Will make an intent that will start the configure channel activity, and wait for the returned result
        which will signify which favorite channel to change
         */
        configureFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConfigureFavorites.class);
                startActivityForResult(intent, CONFIGURE_FAVORITE);

            }
        });

    }

    /*
    Callback method to handle the results of configuring the favorite channel.
    The configure favorite channel activity will send back data signifying which
    favorite button is to be changed, what the new channel name will be, and also
    the new channel number for that favorite
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == CONFIGURE_FAVORITE){

            if(resultCode == RESULT_OK){

                int whichFavorite = data.getIntExtra("Which Favorite", 0);  //Which favorite button to change

                //Depending on which favorite button to change, change that button's text and associated channel
                //left, middle, and right favorite buttons are designated 1,2,3 respectively
                switch (whichFavorite){

                    case 1:
                        firstFavorite.setText(data.getCharSequenceExtra("New Channel Text").toString());
                        setFavorite(whichFavorite, data.getCharSequenceExtra("New Channel Number").toString());
                        break;

                    case 2:
                        secondFavorite.setText(data.getCharSequenceExtra("New Channel Text").toString());
                        setFavorite(whichFavorite, data.getCharSequenceExtra("New Channel Number").toString());
                        break;

                    case 3:
                        thirdFavorite.setText(data.getCharSequenceExtra("New Channel Text").toString());
                        setFavorite(whichFavorite, data.getCharSequenceExtra("New Channel Number").toString());
                        break;

                }



            }

        }
    }

    /*
    Setter method to set the private variables that hold the channel number for a certain favorite button
     */
    private void setFavorite(int whichFavorite, String newFavorite){

        switch (whichFavorite){

            case 1:
                favorite1 = newFavorite;
                break;
            case 2:
                favorite2 = newFavorite;
                break;
            case 3:
                favorite3 = newFavorite;
                break;

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
