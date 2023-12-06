package projects.brewers.amateur.com.brixcalc

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import projects.brewers.amateur.com.brixcalc.Util.Util.approxAbvAlt
import projects.brewers.amateur.com.brixcalc.Util.Util.brixToSG
import projects.brewers.amateur.com.brixcalc.Util.Util.sgToBrix
import java.util.Locale

class MainActivity : AppCompatActivity() {
    @BindView(R.id.table_title)
    var tableTitle: TextView? = null

    @BindView(R.id.calc_param_lbl)
    var calcParamLabel: TextView? = null

    @BindView(R.id.param_num_field)
    var paramNumField: EditText? = null

    @BindView(R.id.update_button)
    var calculateButton: Button? = null

    @BindView(R.id.output_text_label)
    var outputValueName: TextView? = null

    @BindView(R.id.output_val_field)
    var outputValueField: TextView? = null

    @BindView(R.id.abv_output_field)
    var abvOutputField: TextView? = null

    @BindView(R.id.brix_radio)
    var brixRadio: RadioButton? = null

    @BindView(R.id.sg_radio)
    var sgRadio: RadioButton? = null

    @BindView(R.id.radio_group)
    var radios: RadioGroup? = null

    //String resources
    @BindString(R.string.brix_to_sg_title)
    var brix_to_sg: String? = null

    @BindString(R.string.sg_to_brix_title)
    var sg_to_brix: String? = null

    @BindString(R.string.brix_str)
    var brix_str: String? = null

    @BindString(R.string.sg_str)
    var sg_str: String? = null

    /** The current calculator state.  */
    private var curState = BRIX_TO_SG
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

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
        });*/radios!!.setOnCheckedChangeListener { radioGroup: RadioGroup?, i: Int ->
            when (i) {
                R.id.brix_radio -> if (curState != BRIX_TO_SG) {
                    curState = BRIX_TO_SG
                    setState(curState)
                    clearOutputFields()
                }

                R.id.sg_radio -> if (curState != SG_TO_BRIX) {
                    curState = SG_TO_BRIX
                    setState(curState)
                    clearOutputFields()
                }
            }
        }
        setState(curState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.calc_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.brix_to_sg_menuItem -> if (curState != BRIX_TO_SG) {
                brixRadio!!.isChecked = true
            }

            R.id.sg_to_brix_menuItem -> if (curState != SG_TO_BRIX) {
                sgRadio!!.isChecked = true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //TODO need to add onPause and onResume methods
    // maybe not, seems like it is doing it itself
    //save the current values so that when the app is resumed
    //it will reopen with the previous calculations values
    /**
     * Clears the output text boxes
     */
    private fun clearOutputFields() {
        paramNumField!!.setText("")
        outputValueField!!.text = ""
        abvOutputField!!.text = ""
    }

    /**
     * Retrieves the input values from the UI and depending on the current state
     * converts them to the appropriate format
     *
     */
    @OnClick(R.id.update_button)
    fun onCalcClick() {
        var input: Double? = null
        input = if (paramNumField!!.text.toString() != "") {
            paramNumField!!.text.toString().toDouble()
        } else {
            Toast.makeText(
                applicationContext,
                "Please Enter a Value", Toast.LENGTH_LONG
            ).show()
            return
        }
        val res = resources
        when (curState) {
            BRIX_TO_SG -> {
                val sg = brixToSG(input)
                outputValueField!!.text = String.format(
                    Locale.US,
                    "%s",
                    roundThousands(sg)
                )
                abvOutputField!!.text = res.getString(
                    R.string.output_str,
                    roundHundreds(approxAbvAlt(sg))
                )
            }

            SG_TO_BRIX -> {
                outputValueField!!.text = String.format(
                    Locale.US,
                    "%s",
                    roundThousands(sgToBrix(input))
                )
                abvOutputField!!.text = res.getString(
                    R.string.output_str,
                    roundHundreds(approxAbvAlt(input))
                )
            }
        }
        /*try {*/
        val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //This is better than calling getCurrentFocus().
        //getWindowToken() because it avoids the edge case
        //where current focus has been lost, resulting in a
        //NPE
        inputManager.hideSoftInputFromWindow(paramNumField!!.windowToken, 0)
        /*} catch (NullPointerException e) {
            //caught because the Keyboard was already hidden.  don't need to do anything
        }*/
    }

    /**
     * Sets the widget state, which calculator is being shown
     * @param state
     */
    private fun setState(state: Int) {
        when (state) {
            BRIX_TO_SG -> {
                tableTitle!!.text = brix_to_sg
                calcParamLabel!!.text = brix_str
                outputValueName!!.text = sg_str
            }

            SG_TO_BRIX -> {
                tableTitle!!.text = sg_to_brix
                calcParamLabel!!.text = sg_str
                outputValueName!!.text = brix_str
            }
        }
    }

    /**
     * Rounds a double to the thousands place
     * @param val The double value
     * @return double rounded to thousands place
     */
    private fun roundThousands(`val`: Double): Double {
        val tmp = `val` * 1000
        val rnd = Math.round(tmp).toDouble()
        return rnd / 1000
    }

    /**
     * Rounds a double to the hundreds place
     * @param val The double value
     * @return the rounded double
     */
    private fun roundHundreds(`val`: Double): Double {
        val tmp = `val` * 100
        val rnd = Math.round(tmp).toDouble()
        return rnd / 100
    }

    companion object {
        /** State indicator if the calculator is converting brix to SG  */
        private const val BRIX_TO_SG = 1

        /** State indicator if the calculator is converting SG to brix.  */
        private const val SG_TO_BRIX = 2
        private const val percent_symbol = "\\u0025"
    }
}
