package com.example.login

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.View.INVISIBLE
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_snake_game.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class SnakeGame : AppCompatActivity() {

    var score : Int = 0
    var imageArray = ArrayList<ImageView>()
    var handler : Handler = Handler()
    var runnable : Runnable = Runnable {  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snake_game)
        startGame.setOnClickListener {
            Toast.makeText(this,"You have 30 second Let Go!! ",Toast.LENGTH_SHORT).show()
            startGame.visibility = View.GONE
            timeText.visibility = View.VISIBLE
            gridLayout.visibility = View.VISIBLE
            scoreText.visibility = View.VISIBLE
            gameStart()
        }
        loiginOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
            Toast.makeText(this,"You successfully Sign out from account ",Toast.LENGTH_SHORT).show()
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun hideImages(){
        runnable = object : Runnable{
            override fun run(){
                for(image in imageArray){
                    image.visibility = INVISIBLE
                }
                val random = Random()
                val index = random.nextInt(8-0)
                imageArray[index].visibility = View.VISIBLE
                handler.postDelayed(runnable, 500)
            }
        }
        handler.post(runnable)
    }

    fun gameStart(){
        timeText.setTextColor(Color.WHITE)
            score = 0
            imageArray = arrayListOf(
                imageView,
                imageView2,
                imageView3,
                imageView4,
                imageView5,
                imageView6,
                imageView7,
                imageView8,
                imageView9
            )
            hideImages()

            object : CountDownTimer(30000, 1000) {
                override fun onFinish() {
                    timeText.setText("Time: 0")
                    handler.removeCallbacks(runnable)
                    for (image in imageArray) {
                        image.visibility = INVISIBLE
                    }
                    alertMessage()
                }

                override fun onTick(p0: Long) {
                    if(p0/1000 <10)
                    {
                        timeText.setTextColor(Color.RED)
                        timeText.requestFocus()
                    }
                    timeText.setText("Time:" + p0 / 1000)
                }
            }.start()

    }

    fun increaseScore(view: View) {
        score++
        scoreText.setText("Score: $score")
    }

fun alertMessage(){

    val alertDialog :AlertDialog.Builder = AlertDialog.Builder(this)
    alertDialog.setMessage("Your highest score is $score")
    alertDialog.setPositiveButton("Try Again") { dialog:DialogInterface, which:Int ->
        Toast.makeText(this,"You have 30 second Let Go!! ",Toast.LENGTH_SHORT).show()
        gameStart()
    }
alertDialog.setNegativeButton("Exit"){dialog:DialogInterface,which:Int ->
    // Sign out Successfully
    FirebaseAuth.getInstance().signOut()
    finish()
    intent = Intent(this,MainActivity::class.java)
    startActivity(intent)
}
    alertDialog.show()
}


}
