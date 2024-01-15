package com.example.finalproject.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth

class AgdgenaActivity : AppCompatActivity() {

    private lateinit var buttBack : ImageButton
    private lateinit var agdgenaEmail : EditText
    private lateinit var agdgenaGagzavna: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agdgena)

        init()

        buttBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        agdgenaGagzavna.setOnClickListener {

            val email = agdgenaEmail.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this,"შეიყვანე მეილი!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"მეილი გამოგზავნილია! გთხოვთ შეამოწმოთ.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this,"დაფიქსირდა შეცდომა!", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    fun init() {
        buttBack = findViewById(R.id.btnAgdgenaBack)
        agdgenaEmail = findViewById(R.id.editTextAgdgenaEmail)
        agdgenaGagzavna = findViewById(R.id.btnAgdgenaGagzavna)

    }
}