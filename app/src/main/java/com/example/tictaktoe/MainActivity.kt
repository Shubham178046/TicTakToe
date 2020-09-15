package com.example.tictaktoe

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var flag = 0
    var count = 0
    var playerOne : Boolean? = null
    var playerTwo : Boolean? = null
    var score = 0
    private val mHandler = Handler()
    private lateinit var mRunnable:Runnable
    private val buttons =
        Array(
            3
        ) { arrayOfNulls<Button>(3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (i in 0..2) {
            for (j in 0..2) {
                val buttonID = "button_$i$j"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                buttons!!.get(i)[j] = findViewById<View>(resID) as Button?
               // buttons!!.get(i)[j]!!.setOnClickListener(this)
            }
        }

        button_00.setOnClickListener(this)
        button_01.setOnClickListener(this)
        button_02.setOnClickListener(this)
        button_10.setOnClickListener(this)
        button_11.setOnClickListener(this)
        button_12.setOnClickListener(this)
        button_20.setOnClickListener(this)
        button_21.setOnClickListener(this)
        button_22.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        count++
        Log.d("Status", "onClick: "+flag)
        if (!(view as Button).getText().toString().equals("")) {
            return
        }
        if (flag == 0) {
            (view as Button).text = "X"
            playerOne = true
            playerTwo = false
            (view as Button).setTextColor(Color.WHITE)
            flag = 1
        } else {
            if (flag == 1)
                (view as Button).text = "O"
            playerTwo = true
            playerOne = false
            (view as Button).setTextColor(Color.BLACK)
            flag = 0
        }
        if(checkForWins())
        {
            if(playerOne!!)
            {
                var score1 : Int =  Integer.parseInt(player1_score.text.toString()) + 1
                Toast.makeText(applicationContext, "Player 1 Winner" , Toast.LENGTH_LONG).show()
                player1_score.setText(score1.toString())
            }
            else
            {
                if(playerTwo!!)
                {
                    var score2 : Int =  Integer.parseInt(player2_score.text.toString()) + 1
                    Toast.makeText(applicationContext, "Player 2 Winner" , Toast.LENGTH_LONG).show()
                    player2_score.setText(score2.toString())
                }
            }
            Handler().postDelayed({
                for (i in 0..2) {
                    for (j in 0..2) {
                        buttons!!.get(i)[j]!!.text = ""
                    }
                }
                reset()
            },1000)

        }
        else if(count == 9)
        {
            Toast.makeText(applicationContext, "Match Draw" , Toast.LENGTH_LONG).show()
        }
    }

    fun checkForWin() : Boolean {
        if( button_00.text.toString().equals(button_01.text.toString()) && button_00.text.toString().equals(button_02.text.toString()))
        {
            Toast.makeText(applicationContext, "Row 1 Matched", Toast.LENGTH_LONG).show()
            return true
        }
       else if(button_10.text.toString().equals(button_11.text.toString()) && button_10.text.toString().equals(button_12.text.toString()))
        {
            Toast.makeText(applicationContext, "Row 2 Matched", Toast.LENGTH_LONG).show()
            return true
        }
        else if( button_20.text.toString().equals(button_21.text.toString()) && button_20.text.toString().equals(button_22.text.toString()))
        {
            Toast.makeText(applicationContext, "Row 3 Matched", Toast.LENGTH_LONG).show()
            return true
        }

        /// Vertical Check

        else if( button_00.text.toString().equals(button_10.text.toString()) && button_00.text.toString().equals(button_20.text.toString()))
        {
            return true
        }
        else if( button_01.text.toString().equals(button_11.text.toString()) && button_01.text.toString().equals(button_21.text.toString()))
        {
            return true
        }
        else if( button_02.text.toString().equals(button_12.text.toString()) && button_02.text.toString().equals(button_22.text.toString()))
        {
            return true
        }

        //// Cross Check
        else if(button_00.text.toString().equals(button_11.text.toString()) && button_00.text.toString().equals(button_22.text.toString()))
        {
            return true
        }

        else if( button_02.text.toString().equals(button_11.text.toString()) && button_02.text.toString().equals(button_20.text.toString()))
        {
            return true
        }

        return false
    }

    private fun checkForWins(): Boolean {
        val field =
            Array(3) { arrayOfNulls<String>(3) }
        for (i in 0..2) {
            for (j in 0..2) {
                field[i][j] = buttons[i][j]!!.text.toString()
            }
        }
        for (i in 0..2) {
            if (field[i][0] == field[i][1] && field[i][0] == field[i][2] && field[i][0] != ""
            ) {
                return true
            }
        }
        for (i in 0..2) {
            if (field[0][i] == field[1][i] && field[0][i] == field[2][i] && field[0][i] != ""
            ) {
                return true
            }
        }
        if (field[0][0] == field[1][1] && field[0][0] == field[2][2] && field[0][0] != ""
        ) {
            return true
        }
        return if (field[0][2] == field[1][1] && field[0][2] == field[2][0] && field[0][2] != ""
        ) {
            true
        } else false
    }
    fun reset()
    {
        flag = 0
        count = 0
        playerOne = null
        playerTwo = null
    }
}