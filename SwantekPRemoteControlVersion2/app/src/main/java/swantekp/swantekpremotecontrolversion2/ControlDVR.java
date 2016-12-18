package swantekp.swantekpremotecontrolversion2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ControlDVR extends Activity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_dvr);


        /*
        The TextViews that display the current DVR status
         */
        final TextView dvrPowerStatus = (TextView)findViewById(R.id.dvd_power_status);
        final TextView dvrState = (TextView)findViewById(R.id.dvr_state);

        Switch powerSwitch = (Switch)findViewById(R.id.dvr_power_switch); //DVR power toggle

        /*
        The buttons of the DVR
         */
        final Button playButton = (Button)findViewById(R.id.play_button);
        final Button stopButton = (Button)findViewById(R.id.stop_button);
        final Button pauseButton = (Button)findViewById(R.id.pause_button);
        final Button fastForwardButton = (Button)findViewById(R.id.fast_forward_button);
        final Button fastRewindButton = (Button)findViewById(R.id.fast_rewind_button);
        final Button recordButton = (Button)findViewById(R.id.record_button);
        final Button switchToTVButton = (Button)findViewById(R.id.switch_to_tv_button);

        /*
        Initially DVR starts out in the "Off" state, all buttons initially disabled
         */
        playButton.setEnabled(false);
        stopButton.setEnabled(false);
        pauseButton.setEnabled(false);
        fastForwardButton.setEnabled(false);
        fastRewindButton.setEnabled(false);
        recordButton.setEnabled(false);


        /*
        Listener for the DVR power switch, turning the DVR on will update the power status and enable buttons,
        turning off will then disable all the buttons and update power status accordingly
         */
        powerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    dvrPowerStatus.setText("On");
                    playButton.setEnabled(true);
                    stopButton.setEnabled(true);
                    pauseButton.setEnabled(true);
                    fastForwardButton.setEnabled(true);
                    fastRewindButton.setEnabled(true);
                    recordButton.setEnabled(true);

                }

                else{

                    dvrPowerStatus.setText("Off");
                    dvrState.setText("Stopped");
                    playButton.setEnabled(false);
                    stopButton.setEnabled(false);
                    pauseButton.setEnabled(false);
                    fastForwardButton.setEnabled(false);
                    fastRewindButton.setEnabled(false);
                    recordButton.setEnabled(false);



                }


            }
        });


        /*
        Listener for the buttons of the DVR.  Pressing the buttons will transition the DVR to different states, and will update the DVR's state
        at the top of the screen accordingly.  Note that there are some restrictions on which states can be transferred to depending on the current
        state.  If an invalid state transition is attempted, an appropriate Toast will be displayed.
         */
        TextView.OnClickListener buttonPressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){

                    //Can only transition to Playing state if the state is not Recording
                    case R.id.play_button:
                        if(!dvrState.getText().toString().equals("Recording")) {
                            dvrState.setText("Playing");
                            break;
                        }
                        else {
                            makeToast("Recording", "Playing");
                            break;
                        }

                    //Puts the DVR in the Stopped state
                    case R.id.stop_button:
                        dvrState.setText("Stopped");
                        break;

                    //Puts DVR in Paused state, DVR can only be paused if it is currently in Playing state
                    case R.id.pause_button:
                        if(dvrState.getText().toString().equals("Playing")) {
                            dvrState.setText("Paused");
                            break;
                        }
                        else {
                            makeToast(dvrState.getText().toString(), "Paused");
                            break;
                        }

                    //Put the DVR into either Fast Forwarding/Fast Rewinding state, can only make this transition if state is Playing
                    case R.id.fast_forward_button:
                        if(dvrState.getText().toString().equals("Playing")) {
                            dvrState.setText("Fast Forwarding");
                            break;
                        }
                        else {
                            makeToast(dvrState.getText().toString(), "Fast Forwarding");
                            break;
                        }

                    case R.id.fast_rewind_button:
                        if(dvrState.getText().toString().equals("Playing")) {
                            dvrState.setText("Fast Rewinding");
                            break;
                        }
                        else {
                            makeToast(dvrState.getText().toString(), "Fast Rewinding");
                            break;
                        }

                    //Transition DVR to a Recording state, can only transition to Recording if the DVR is Stopped
                    case R.id.record_button:
                        if(dvrState.getText().toString().equals("Stopped")) {
                            dvrState.setText("Recording");
                            break;
                        }
                        else {
                            makeToast(dvrState.getText().toString(), "Recording");
                            break;
                        }

                    //transition back to the main activity
                    case R.id.switch_to_tv_button:
                        finish();
                        break;

                }
            }
        };

        //Set the listener for all the buttons
        playButton.setOnClickListener(buttonPressed);
        stopButton.setOnClickListener(buttonPressed);
        pauseButton.setOnClickListener(buttonPressed);
        fastForwardButton.setOnClickListener(buttonPressed);
        fastRewindButton.setOnClickListener(buttonPressed);
        recordButton.setOnClickListener(buttonPressed);
        switchToTVButton.setOnClickListener(buttonPressed);




    }

    //Helper method for making the Toasts when invalid state transitions are attempted
    private void makeToast(String currState, String attemptedState){

        Toast.makeText(this, "Invalid state transition!\nCan't transition from " + currState
                        + " to " + attemptedState, Toast.LENGTH_LONG).show();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_control_dvr, menu);
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
