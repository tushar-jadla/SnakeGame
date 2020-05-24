package com.example.login

import com.example.login.R
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.opencensus.common.ServerStatsFieldEnums
import kotlinx.android.synthetic.main.activity_createnewuser.*
import java.util.regex.Pattern

class newuser : AppCompatActivity() {
     lateinit var auth : FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createnewuser)
        auth = FirebaseAuth.getInstance()
        createAccount.setOnClickListener {
            button_createAccount()
        }
    }
    fun button_createAccount(){
        var email_text = emailforcreate.text.toString().trim();
        var password_text = createnewpassword.text.toString().trim();

       //  Checking  the email and password is empty or not
        if (email_text.isEmpty())
        {
            emailforcreate.error = "Please type your email"
            emailforcreate.requestFocus()
            return
        }
        if (password_text.isEmpty())
        {
            createnewpassword.error = "Please type your password"
            createnewpassword.requestFocus()
            return
        }
        progressBar.visibility =View.VISIBLE
        auth.createUserWithEmailAndPassword(email_text,password_text).addOnCompleteListener{ task ->
            if (task.isSuccessful)
            {
                Toast.makeText(this,"User account is created",Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
                // You can use the intent here to navigate to main Login page
              val intent  = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this,"${task.exception!!.message.toString()}",Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
            }

        }

    }


    }





