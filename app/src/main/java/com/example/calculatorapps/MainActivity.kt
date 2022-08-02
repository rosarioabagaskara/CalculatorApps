package com.example.calculatorapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var textNumber: TextView? = null
    var textNull: Boolean = true
    var firstZero: Boolean = false
    var lastDot: Boolean = false
    var lastNumeric: Boolean = false
    var lastOperator: Boolean = false
    var lastMinus: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textNumber = findViewById<TextView>(R.id.textView)

    }

    fun textInput(view: View) {
        if(textNull){
            if((view as Button).text == "0"){
                textNumber?.setText((view as Button).text)
                firstZero = true
                textNull = false
                lastNumeric = true
                lastMinus = false
            }else if((view as Button).text != "0"){
                textNumber?.setText((view as Button).text)
                firstZero = false
                textNull = false
                lastNumeric = true
                lastMinus = false
            }
        }else if(!textNull && firstZero){
            if((view as Button).text == "0" && !lastDot){
                textNumber?.setText((view as Button).text)
                firstZero = true
                lastNumeric = true
                lastMinus = false
            }else if((view as Button).text != "0" || lastDot){
                textNumber?.append((view as Button).text)
                firstZero = false
                lastNumeric = true
                lastMinus = false
            }
        }else if(!textNull && !firstZero){
            textNumber?.append((view as Button).text)
            lastOperator = false
            lastNumeric = true
            lastMinus = false
        }
    }

    fun clear(view: View){
        textNumber?.setText("")
        lastDot = false
        lastNumeric = false
        textNull = true
        firstZero = false
        lastMinus = false
    }

    fun dot(view: View){
        if(lastNumeric && !lastDot) {
            textNumber?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun operator(view: View){
        if(lastNumeric && !lastOperator) {
            textNumber?.append((view as Button).text)
            lastOperator = true
            lastNumeric = false
        }
    }

    fun minus(view: View){
        if(textNumber?.text?.startsWith("-") == true && !lastNumeric){
            lastMinus = true
        }
        if(!lastMinus) {
            textNumber?.append((view as Button).text)
            lastNumeric = false
            textNull = false
            lastMinus = true
        }
    }

    fun equal(view: View){
       if(lastNumeric){
           var textNumberValue = textNumber?.text.toString()
           var tempMinus = ""
           try{
               if(textNumberValue.startsWith("-")){
                   tempMinus = "-"
                   textNumberValue = textNumberValue.substring(1)
               }
               if(textNumberValue.contains("-")){
                   val splitValue = textNumberValue.split("-")
                   var value1 = splitValue[0]
                   var value2 = splitValue[1]

                   if(tempMinus.isNotEmpty()){
                       value1 = tempMinus + value1
                   }

                   textNumber?.text = removeDotZero((value1.toDouble() - value2.toDouble()).toString())
               }
               if(textNumberValue.contains("+")){
                   val splitValue = textNumberValue.split("+")
                   var value1 = splitValue[0]
                   var value2 = splitValue[1]

                   if(tempMinus.isNotEmpty()){
                       value1 = tempMinus + value1
                   }

                   textNumber?.text = removeDotZero((value1.toDouble() + value2.toDouble()).toString())
               }
               if(textNumberValue.contains(":")){
                   val splitValue = textNumberValue.split(":")
                   var value1 = splitValue[0]
                   var value2 = splitValue[1]

                   if(tempMinus.isNotEmpty()){
                       value1 = tempMinus + value1
                   }

                   textNumber?.text = removeDotZero((value1.toDouble() / value2.toDouble()).toString())
               }
               if(textNumberValue.contains("*")){
                   val splitValue = textNumberValue.split("*")
                   var value1 = splitValue[0]
                   var value2 = splitValue[1]

                   if(tempMinus.isNotEmpty()){
                       value1 = tempMinus + value1
                   }

                   textNumber?.text = removeDotZero((value1.toDouble() * value2.toDouble()).toString())
               }
           }catch (e: ArithmeticException){
               e.printStackTrace()
           }
       }
    }

    fun removeDotZero(result: String): String{
        var value = result
        if(result.contains(".0")){
            value = result.substring(0, result.length-2)
        }

        return value
    }
}