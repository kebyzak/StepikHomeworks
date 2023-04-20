package com.example.stepik

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat

const val CORRECT_PIN = "1567"
const val PIN_LENGTH = 4

class MainActivity : AppCompatActivity() {
    private var pinText = ""
    private var pinNumber = 1
    private lateinit var tvPin: PinCompound
    private val numButtons = arrayOf(
        R.id.btn_one,
        R.id.btn_two,
        R.id.btn_three,
        R.id.btn_four,
        R.id.btn_five,
        R.id.btn_six,
        R.id.btn_seven,
        R.id.btn_eight,
        R.id.btn_nine,
        R.id.btn_zero
    )

    var errorColor: Int = Color.BLACK
    var pinTextColor: Int = Color.BLACK
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initColors()
        initTvPin()
        initNumButtons()
        initBackspaceButton()
        initOkButton()
    }

    private fun initColors() {
        errorColor = ContextCompat.getColor(this, R.color.error)
        pinTextColor = ContextCompat.getColor(this, R.color.color_primary)
    }

    private fun initTvPin() {
        tvPin = findViewById(R.id.tv_pin)
    }

    private fun initNumButtons() {
        for (buttonId in numButtons) {
            findViewById<Button>(buttonId).setOnClickListener(this::onClick)
        }
    }

    private fun initBackspaceButton() {
        val btnBackspace = findViewById<Button>(R.id.btn_backspace)
        btnBackspace.setOnClickListener {
            pinText = pinText.dropLast(1)
            updateDelete()
        }

        btnBackspace.setOnLongClickListener {
            pinText = ""
            clear()
            pinNumber = 1
            true
        }
    }

    private fun initOkButton() {
        val btnOk = findViewById<Button>(R.id.btn_ok)
        btnOk.setOnClickListener {
            checkIfPinIsCorrect()
        }
    }

    private fun onClick(view: View) {
        if (view !is Button) return

        val clickedNum = view.text
        pinText += clickedNum

        updatePin(clickedNum.toString())
    }

    private fun updatePin(num: String) {
        if (pinText.length > PIN_LENGTH) {
            pinText = pinText.substring(0, PIN_LENGTH)
            return
        }

        val pinViewList = arrayOf(tvPin.pinOne, tvPin.pinTwo, tvPin.pinThree, tvPin.pinFour)
        val pinView = pinViewList[pinNumber - 1]
        pinView.text = num
        pinView.setTextColor(pinTextColor)
        pinView.border(pinTextColor)
        pinNumber++
    }

    private fun updateDelete() {
        if (pinNumber > 1) {
            val pinViewList = arrayOf(tvPin.pinOne, tvPin.pinTwo, tvPin.pinThree, tvPin.pinFour)
            val pinView = pinViewList[pinNumber - 2]
            pinView.text = ""
            pinView.border(0)
            pinNumber--
        }
    }


    private fun clear() {
        tvPin.clear()
    }

    private fun checkIfPinIsCorrect() {
        if (pinText.length != PIN_LENGTH) {
            Toast.makeText(this, R.string.pin_fits, Toast.LENGTH_LONG).show()
            tvPin.showError(errorColor)
            tvPin.clear()
        } else if (pinText == CORRECT_PIN) {
            Toast.makeText(this, R.string.pin_correct, Toast.LENGTH_LONG).show()
        } else {
            tvPin.showError(errorColor)
        }
    }
}