package com.example.finalproject.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextemail: EditText
    private lateinit var editTextParoli : EditText
    private lateinit var editTextParoliGameoreba: EditText
    private lateinit var buttRegister2 : Button
    private lateinit var buttBack : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()

        buttBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        buttRegister2.setOnClickListener {
            registerValidation()
        }

    }

    fun init () {

        editTextemail = findViewById(R.id.editTextemail)
        editTextParoli = findViewById(R.id.editTextParoli)
        editTextParoliGameoreba = findViewById(R.id.editTextParoliGameoreba)
        buttRegister2 = findViewById(R.id.btnRegistracia2)
        buttBack = findViewById(R.id.registraciaBackBtn)

    }

    fun registerValidation () {

        val email = editTextemail.text.toString()
        val paroli = editTextParoli.text.toString()
        val paroliGameoreba = editTextParoliGameoreba.text.toString()
        var n = 0


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"შეიყვანე სწორი მეილი!", Toast.LENGTH_SHORT).show()
            n+=1
        }

        if(paroli.length < 8 && paroli.length != 0){
            Toast.makeText(this,"პაროლი უდნა შეიცავდეს მინიმუმ 8 სიმბოლოს!", Toast.LENGTH_SHORT).show()
            n+=1
        }

        if(!paroli.matches(".*[A-Z].*".toRegex())){
            Toast.makeText(this,"პაროლი უნდა შეიცავდეს მინიმუმ 1 დიდ ასოს!", Toast.LENGTH_SHORT).show()
            n+=1
        }

        if(!paroli.matches(paroliGameoreba.toRegex())) {
            Toast.makeText(this,"პაროლები არ ემთხვევა ერთმანეთს!", Toast.LENGTH_SHORT).show()
            n+=1
        }

        if (n == 0) {

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email,paroli).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    else {
                        Toast.makeText(this,"დაფიქსირდა შეცდომა!", Toast.LENGTH_SHORT).show()
                    }
                }

        }


    }



}