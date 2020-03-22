package projects.brewers.amateur.com.brixcalc;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import projects.brewers.amateur.com.brixcalc.Util.Util;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.table_title)
     TextView tableTitle;
    @BindView(R.id.calc_param_lbl)
     TextView calcParamLabel;
    @BindView(R.id.param_num_field)
     EditText paramNumField;
    @BindView(R.id.update_button)
     Button calculateButton;
    @BindView(R.id.output_text_label)
     TextView outputValueName;
    @BindView(R.id.output_val_field)
     TextView outputValueField;
    @BindView(R.id.abv_output_field)
     TextView abvOutputField;
    @BindView(R.id.brix_radio)
     RadioButton brixRadio;
    @BindView(R.id.sg_radio)
     RadioButton sgRadio;
    @BindView(R.id.radio_group)
     RadioGroup radios;

    //String resources
    @BindString(R.string.brix_to_sg_title)
    String brix_to_sg;
    @BindString(R.string.sg_to_brix_title)
    String sg_to_brix;
    @BindString(R.string.brix_str)
    String brix_str;
    @BindString(R.string.sg_str)
    String sg_str;

    /** State indicator if the calculator is converting brix to SG */
    private static final int BRIX_TO_SG = 1;
    /** State indicator if the calculator is converting SG to brix. */
    private static final int SG_TO_BRIX = 2;

    /** The current calculator state. */
    private int curState = BRIX_TO_SG;

    private static final String percent_symbol = "\\u0025";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /*radios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.brix_radio:
                        if (curState != BRIX_TO_SG) {
                            curState = BRIX_TO_SG;
                            setState(curState);
                            clearOutputFields();
                        }
                        break;
                    case R.id.sg_radio:
                        if (curState != SG_TO_BRIX) {
                            curState = SG_TO_BRIX;
                            setState(curState);
                            clearOutputFields();
                        }
                        break;
                }
            }
        });*/
        radios.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.brix_radio:
                    if (curState != BRIX_TO_SG) {
                        curState = BRIX_TO_SG;
                        setState(curState);
                        clearOutputFields();
                    }
                    break;
                case R.id.sg_radio:
                    if (curState != SG_TO_BRIX) {
                        curState = SG_TO_BRIX;
                        setState(curState);
                        clearOutputFields();
                    }
                    break;
            }
        });

        setState(curState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calc_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.brix_to_sg_menuItem:
                if (curState != BRIX_TO_SG) {
                    brixRadio.setChecked(true);
                }
                break;
            case R.id.sg_to_brix_menuItem:
                if (curState != SG_TO_BRIX) {
                    sgRadio.setChecked(true);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO need to add onPause and onResume methods
    // maybe not, seems like it is doing it itself
    //save the current values so that when the app is resumed
    //it will reopen with the previous calculations values

    /**
     * Clears the output text boxes
     */
    private void clearOutputFields() {
        paramNumField.setText("");
        outputValueField.setText("");
        abvOutputField.setText("");
    }

    /**
     * Retrieves the input values from the UI and depending on the current state
     * converts them to the appropriate format
     */
    @SuppressWarnings("All")
    @OnClick(R.id.update_button)
    public void onCalcClick(View v) {
        Double input = null;
        if (!paramNumField.getText().toString().equals("")) {
            input = Double.parseDouble(paramNumField.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please Enter a Value", Toast.LENGTH_LONG).show();
            return;
        }
        Resources res = getResources();

        switch (curState) {
            case BRIX_TO_SG:
                double sg = Util.brixToSG(input);
                outputValueField.setText(
                        String.format(
                                Locale.US,
                                "%s",
                                roundThousands(sg)));
                abvOutputField.setText(
                        res.getString(
                                R.string.output_str,
                                roundHundreds(Util.approxAbvAlt(sg))));
                break;
            case SG_TO_BRIX:
                outputValueField.setText(
                        String.format(
                                Locale.US,
                                "%s",
                                roundThousands(Util.sgToBrix(input))));
                abvOutputField.setText(
                        res.getString(
                                R.string.output_str,
                                roundHundreds(Util.approxAbvAlt(input))));
                break;
        }
        /*try {*/
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        //This is better than calling getCurrentFocus().
        //getWindowToken() because it avoids the edge case
        //where current focus has been lost, resulting in a
        //NPE
        inputManager.hideSoftInputFromWindow(paramNumField.getWindowToken(), 0);
        /*} catch (NullPointerException e) {
            //caught because the Keyboard was already hidden.  don't need to do anything
        }*/
    }

    /**
     * Sets the widget state, which calculator is being shown
     * @param state
     */
    private void setState(int state) {
        switch (state) {
            case BRIX_TO_SG:
                tableTitle.setText(brix_to_sg);
                calcParamLabel.setText(brix_str);
                outputValueName.setText(sg_str);
                break;
            case SG_TO_BRIX:
                tableTitle.setText(sg_to_brix);
                calcParamLabel.setText(sg_str);
                outputValueName.setText(brix_str);
        }
    }

    /**
     * Rounds a double to the thousands place
     * @param val The double value
     * @return double rounded to thousands place
     */
    private double roundThousands(double val) {
        double tmp = val * 1000;
        double rnd = Math.round(tmp);
        double fin = rnd / 1000;
        return fin;
    }

    /**
     * Rounds a double to the hundreds place
     * @param val The double value
     * @return the rounded double
     */
    private double roundHundreds(double val) {
        double tmp = val * 100;
        double rnd = Math.round(tmp);
        double fin = rnd / 100;
        return fin;
    }
}
