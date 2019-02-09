package com.example.schandiramani.currencyconvertor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.text.TextUtils
import android.widget.Toast
import android.text.Editable
import android.text.TextWatcher
import android.R.attr.orientation
import android.content.res.Configuration
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {

     private var usdText: EditText? = null
     private var inrText: EditText? = null
     private var usdWatcher : TextWatcher? = null
     private var inrWatcher : TextWatcher? = null
     private var usdValue : Double = 0.0
     private var inrValue : Double = 0.0

    companion object {
        const val USD_TO_INR_RATE = 63.89
        const val INR_TO_USD_RATE = 0.016
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        usdWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.length != 0)
                    inrText!!.setText("")
            }
        }

        inrWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.length != 0)
                    usdText!!.setText("")
            }
        }

        setContentView(R.layout.activity_main)
        inrText =  findViewById<EditText>(R.id.etINRAmount)
        inrText!!.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        usdText = findViewById<EditText>(R.id.etUSDAmount)
        usdText!!.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        usdText!!.addTextChangedListener(usdWatcher)
        inrText!!.addTextChangedListener(inrWatcher)

        val btnConvert_click = findViewById<Button>(R.id.btnConvert)
        btnConvert_click.setOnClickListener {
            click()
        }
    }

    private fun convertUSDToINR() {
        val a = usdText!!.text.toString().toDouble()
        val result : Double = a * USD_TO_INR_RATE
        inrText!!.setText(result.roundTo2DecimalPlaces().toString())
    }

    private fun convertINRToUSD() {
        val a = inrText!!.text.toString().toDouble()
        val result: Double = a * INR_TO_USD_RATE
        usdText!!.setText(result.roundTo2DecimalPlaces().toString())
    }

    fun Double.roundTo2DecimalPlaces() =
            BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()

    private fun click() {

        inrText!!.removeTextChangedListener(inrWatcher)
        usdText!!.removeTextChangedListener(usdWatcher)
        if(TextUtils.isEmpty(inrText!!.text.toString()) && TextUtils.isEmpty(usdText!!.text.toString())){
            Toast.makeText(applicationContext, getString(R.string.missing_amount_error_message),
                    Toast.LENGTH_LONG).show()
        }

        else if (TextUtils.isEmpty(usdText!!.text.toString())) {
            convertINRToUSD()
        }

        else if (TextUtils.isEmpty(inrText!!.text.toString())) {
            convertUSDToINR()
        }
        usdText!!.addTextChangedListener(usdWatcher)
        inrText!!.addTextChangedListener(inrWatcher)
    }


}
