package com.ss303.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.ss303.calculator.databinding.ActivityCalculatorBinding

class Calculator : AppCompatActivity() {
    private lateinit var binding: ActivityCalculatorBinding
    private val model: CalcualtorView by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCalculatorBinding.inflate(getLayoutInflater())
        setContentView(binding.getRoot())

        binding.btn0.setOnClickListener { model.press('0') }
        binding.btn1.setOnClickListener { model.press('1') }
        binding.btn2.setOnClickListener { model.press('2') }
        binding.btn3.setOnClickListener { model.press('3') }
        binding.btn4.setOnClickListener { model.press('4') }
        binding.btn5.setOnClickListener { model.press('5') }
        binding.btn6.setOnClickListener { model.press('6') }
        binding.btn7.setOnClickListener { model.press('7') }
        binding.btn8.setOnClickListener { model.press('8') }
        binding.btn9.setOnClickListener { model.press('9') }
        binding.btnPlus.setOnClickListener { model.press('+') }
        binding.btnSub.setOnClickListener { model.press('-') }
        binding.btnMul.setOnClickListener { model.press('*') }
        binding.btnDiv.setOnClickListener { model.press('/') }
        binding.btnEqal.setOnClickListener { model.press('=') }
        binding.btnParcent.setOnClickListener { model.press('%') }
        binding.btnC.setOnClickListener { model.press('C') }
        binding.btnPlusMinus.setOnClickListener { model.press('M') }

        model.result.observe(this, Observer {result -> binding.calcAns.text = result })
        model.history.observe(this, Observer {history -> binding.calcHistory.text = history })
        model.sign.observe(this, Observer {sign -> binding.calcSymbol.text = sign })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.calc_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}