package me.rodik.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String TAG = "MyActivity";

    public Long current_sum = (long) 0;
    public Long previous_sum = (long) 0;
    private TextView screen;
    private String last_action = "num";
    private String current_modifier = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        renderCalculator();
        screen = (TextView) findViewById(R.id.screen);
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

    private void renderCalculator() {
        LinearLayout calculatorLayout = (LinearLayout) findViewById(R.id.calculator);
        for (int i = 0; i < 3; i++) {
            Log.d("MainActivity", "Creating pad line");
            LinearLayout line = (LinearLayout) calculatorLayout.getChildAt(i+1);
            line.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 3; j > 0; j--) {
                createNumberButton(Long.toString(i * 3 + j), line);
            }
        }

        Log.d("MainActivity", "Creating pad line");
        LinearLayout line = (LinearLayout) calculatorLayout.getChildAt(4);
        line.setOrientation(LinearLayout.HORIZONTAL);
        createNumberButton(Long.toString(0), line);


    }

    private void createNumberButton(String text, LinearLayout line) {
        Button button = new Button(this);
        button.setText(text);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.MATCH_PARENT, 1f);
        button.setLayoutParams(params);
        button.setOnClickListener(buttonClickListener);
        line.addView(button,0);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String value = b.getText().toString();
            Log.v(TAG, "Pressed: " + value);

            if (last_action.equals("num")) {
                current_sum *= 10;
                current_sum += Long.valueOf(value);
            } else {
                previous_sum = current_sum;
                current_sum = Long.valueOf(value);
            }
            screen.setText(current_sum.toString());
            last_action = "num";
        }
    };

    public void calc_action(View view) {
        Button b = (Button) view;
        String value = b.getText().toString();
        Log.v(TAG, "Action: " + value);
        last_action = "action";
        switch(current_modifier) {
            case "+":
                current_sum = previous_sum + current_sum;
                break;
            case "-":
                current_sum = previous_sum - current_sum;
                break;
            case "*":
                current_sum = previous_sum * current_sum;
                break;
            case "/":
                if (current_sum != 0) {
                    current_sum = previous_sum / current_sum;
                }
                break;
            default:
                break;
        }
        current_modifier = value;
        screen.setText(current_sum.toString());

        // TODO:
        // clicking many times should continue to add / subtract
    }
}
