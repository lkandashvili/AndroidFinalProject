package com.example.finalproject.login

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.finalproject.MyWorkManager
import com.example.finalproject.NotificationReceiver
import com.example.finalproject.ProductDatabase
import com.example.finalproject.ProductViewModel
import com.example.finalproject.R
import com.example.finalproject.SecondActivity
import com.example.finalproject.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ProductDatabase::class.java,
            "Products.db"
        ).build()
    }
    private  val viewModel by viewModels<ProductViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductViewModel(db.dao) as T
                }
            }
        }
    )

    private lateinit var buttlogin: Button
    private lateinit var buttregister: Button
    private lateinit var loginEmail : EditText
    private lateinit var loginParoli: EditText
    private lateinit var parolisAgdgena: Button

//    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.apply {
//            btnshesvla.setOnClickListener{
//                myWork()
//            }
//            parolisAgdgena.setOnClickListener{
//                myOneTimeWork()
//            }
//        }
        setContentView(R.layout.activity_main)



        init()
        registerListeners()

        val notificationReceiver = NotificationReceiver()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_BATTERY_LOW)
        filter.addAction(Intent.ACTION_BATTERY_OKAY)
        this.registerReceiver(notificationReceiver, filter)

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val CHANNEL_ID = "1"
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)


            // work manager //

        }
    }


    private fun init() {

        parolisAgdgena = findViewById(R.id.textViewAgdgena)
        buttlogin = findViewById(R.id.btnshesvla)
        buttregister = findViewById(R.id.btnregistracia)
        loginEmail = findViewById(R.id.editTextLoginEmail)
        loginParoli = findViewById(R.id.editTextTextLoginParoli)
    }

    private fun registerListeners() {


        parolisAgdgena.setOnClickListener {
            startActivity(Intent(this, AgdgenaActivity::class.java))
        }


        buttlogin.setOnClickListener {

            myWork()

            val email = loginEmail.text.toString()
            val paroli = loginParoli.text.toString()

            if (email.isEmpty() || paroli.isEmpty()) {
                Toast.makeText(this,"შეავსე!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email,paroli)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"MOVEDIIIIII", Toast.LENGTH_LONG).show()

                        startActivity(Intent(this, SecondActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this,"მეილი ან პაროლი არასწორია!", Toast.LENGTH_LONG).show()
                    }
                }

        }

        buttregister.setOnClickListener {
            startActivity(Intent(this,
                RegisterActivity::class.java))
        }

        parolisAgdgena.setOnClickListener {
            myOneTimeWork()
        }

    }

    private fun myWork() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val myWorkRequest = PeriodicWorkRequest.Builder(
            MyWorkManager::class.java,
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .addTag("rame")
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork("rame",ExistingPeriodicWorkPolicy.KEEP, myWorkRequest)


    }

    private fun myOneTimeWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(true)
            .build()

        val myWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(MyWorkManager::class.java)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)
    }

}