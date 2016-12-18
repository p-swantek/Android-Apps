package swantekp.swantekpsimplecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {


    private String numberDisplayed = ""; //String to hold the numbers that will be displayed on the screen
    private int runningTotal = 0;       //Keep a running total of the sum of all the numbers that have been input so far

    private int equalsResult = 0;       //If equals key is pressed, a final sum is found and stored here
                                        //Will be used if the user presses the equals button and then adds a number
                                        //to that result


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets the TextView element, will display the numbers users enters and the summation result
        final TextView resultField = (TextView)findViewById(R.id.result_field);

        //Sets up all the different buttons.  Have a button for a digit (0-9), plus key, and the equals key
        Button oneKey = (Button)findViewById(R.id.one_button);
        Button twoKey = (Button)findViewById(R.id.two_button);
        Button threeKey = (Button)findViewById(R.id.three_button);
        Button fourKey = (Button)findViewById(R.id.four_button);
        Button fiveKey = (Button)findViewById(R.id.five_button);
        Button sixKey = (Button)findViewById(R.id.six_button);
        Button sevenKey = (Button)findViewById(R.id.seven_button);
        Button eightKey = (Button)findViewById(R.id.eight_button);
        Button nineKey = (Button)findViewById(R.id.nine_button);
        Button zeroKey = (Button)findViewById(R.id.zero_button);
        Button equalsKey = (Button)findViewById(R.id.equals_button);
        Button plusKey = (Button)findViewById(R.id.plus_button);


        /*
        Set up the event listeners for digit, plus, and equals buttons respectively
        Will be made using anonymous inner classes implementing onClickListener interface
        How to handle number presses.  Will fill the string containing previously entered numbers
        by adding the clicked number to the end of the string.  Will put this string representing
        the current sequence of pressed digits to the TextView.  Pressing a digit will also reset
        the equalsAmount, and set up the environment for a new calculation
        */
        View.OnClickListener digitPressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberDisplayed += ((Button)v).getText().toString();
                resultField.setText(numberDisplayed);
                equalsResult = 0;
            }
        };


        /*
        How to handle plus presses.  First checks if there was a result that was obtained by pressing
        the equals button.  If there was, update the runningTotal to be that result, and then resets
        the equals result.  Then puts the current running total to the TextView.  If there was no previous
        equalsResult, the current number that is displayed on the screen is parsed to an int and added to the
        current running total.  This result is then put to the TextView.  After either of these cases, the
        string representing the number being displayed in the TextView is reset to an empty string to prepare
        for a new sequence of button presses
        */
        View.OnClickListener plusPressed = new View.OnClickListener() {
            public void onClick(View v) {
                if (equalsResult > 0){
                    runningTotal = equalsResult;
                    equalsResult = 0;
                    resultField.setText(String.valueOf(runningTotal));
                }
                else {
                    runningTotal += Integer.parseInt(numberDisplayed);
                    resultField.setText(String.valueOf(runningTotal));
                }
                numberDisplayed = "";

            }
        };


        /*
        How to handle equals presses.  Will parse the current number displayed in the TextView to an
        int and add that to the current running total.  This runningTotal will be displayed in the TextView
        then.  Since an equals operation was performed, set the variable equalsResult equal to the value
        of the running total. Will serve to keep track of the result of the equals operation in the case that
        a user chooses to add another number to the result that was obtained by pressing equals
        */
        View.OnClickListener equalsPressed = new View.OnClickListener(){
            public void onClick(View v) {
                runningTotal += Integer.parseInt(numberDisplayed);
                resultField.setText(String.valueOf(runningTotal));
                equalsResult = runningTotal;
                runningTotal = 0;
                numberDisplayed = "";

            }
        };

        oneKey.setOnClickListener(digitPressed);
        twoKey.setOnClickListener(digitPressed);
        threeKey.setOnClickListener(digitPressed);
        fourKey.setOnClickListener(digitPressed);
        fiveKey.setOnClickListener(digitPressed);
        sixKey.setOnClickListener(digitPressed);
        sevenKey.setOnClickListener(digitPressed);
        eightKey.setOnClickListener(digitPressed);
        nineKey.setOnClickListener(digitPressed);
        zeroKey.setOnClickListener(digitPressed);

        plusKey.setOnClickListener(plusPressed);
        equalsKey.setOnClickListener(equalsPressed);

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
