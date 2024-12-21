package com.ss303.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.abs

class CalcualtorView : ViewModel() {

    private var _result = MutableLiveData<String>()
    private var _history = MutableLiveData<String>()
    private var _sign = MutableLiveData<String>()
    private var showing_result = true

    val result: LiveData<String> get() = _result
    val history: LiveData<String> get() = _history
    val sign: LiveData<String> get() = _sign


    init {
        _result.value = "0"
        _history.value = "0"
        _sign.value = ""
    }


    private fun convertToString(num: Double): String {
        if (num > 1e10 || abs(num - 0) < 1e-5) return "0"
        var res = String.format("%.2f", num)
        if (res.contains(".00")) res = res.dropLast(3)
        return res
    }

    private fun calculate() {
        showing_result = true

        when(sign.value) {
            "+" -> _result.value = convertToString(history.value!!.toDouble() + result.value!!.toDouble())
            "-" -> _result.value = convertToString(history.value!!.toDouble() - result.value!!.toDouble())
            "*" -> _result.value = convertToString(history.value!!.toDouble() * result.value!!.toDouble())
            "/" -> {
                if (abs(result.value!!.toDouble() - 0.0) > 1e-4)
                    _result.value = convertToString(history.value!!.toDouble() / result.value!!.toDouble())
            }
            "%" -> _result.value = convertToString(history.value!!.toDouble() * (result.value!!.toDouble() / 100))
            else -> return
        }
    }

    fun press(input: Char) {
        val operators = "+-/*%="
        when (input) {
            in '0' .. '9' -> {
                if (showing_result) {
                    _history.value = result.value
                    _result.value = ""
                    showing_result = false
                }
                if (result.value == "0") _result.value = input.toString()
                else _result.value = _result.value + input.toString()
            }
            '.' -> {
                if (showing_result) {
                    _history.value = result.value
                    _result.value = "0"
                    showing_result = false
                } else if (!result.value!!.contains('.')){
                    _result.value = _result.value + input.toString()
                }
            }
            in operators -> {
                calculate()
                _sign.value = input.toString()
            }
            'C' -> {
                if (result.value != "0") _result.value = if (result.value!!.length != 1) result.value!!.dropLast(1) else "0"
                else {
                    _history.value = "0"
                    _sign.value = ""
                }
            }
            'M' -> _result.value = convertToString(result.value!!.toDouble() * -1)
            else -> {
                throw IllegalArgumentException("Illigal input: $input")
            }
        }
    }


}