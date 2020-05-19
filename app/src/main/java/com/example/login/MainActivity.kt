package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.login.newuser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_createnewuser.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.email
import kotlinx.android.synthetic.main.activity_main.password

class MainActivity : AppCompatActivity() {

     var auth: FirebaseAuth ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        submit.setOnClickListener {
            LoginClick()
        }
        createNew.setOnClickListener {
            val intent  = Intent(this,newuser::class.java)
            startActivity(intent)
        }
    } // end of OnCreate function
    fun LoginClick(){
        var email_value = email.text.toString().trim();
        var password_value = password.text.toString().trim();

        if (email_value.isEmpty())
        {
            email.error = "Please type your email"
            email.requestFocus()
            return
        }
        if (password_value.isEmpty())
        {
            password.error = "Please type your password"
            password.requestFocus()
            return
        }
        progressBar_front.visibility = View.VISIBLE

        auth?.signInWithEmailAndPassword(email_value,password_value)!!.addOnCompleteListener{ task ->
            if(task.isSuccessful)
            {
                Toast.makeText(this,"You successfully login in your account",Toast.LENGTH_LONG).show()
                progressBar_front.visibility = View.GONE
               val intent = Intent(this,SnakeGame::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this,"${task.exception!!.message.toString()}",Toast.LENGTH_LONG).show()
                progressBar_front.visibility = View.GONE
            }
        }

    } // end of loginClick
}
