package br.com.makrosystems.mychat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnListeners()
    }

    fun btnListeners(){
        btn_toCreate.setOnClickListener {
            finish()
        }
    }
}