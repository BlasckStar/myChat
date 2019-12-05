package br.com.makrosystems.mychat

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val FIREHASH = "0a63a95605b7d9662f46e2a4a757d09d26ca3185"
    }

    lateinit var photo_use: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        btnListeners()
    }

    fun login(){
        val createdView = LayoutInflater.from(this@MainActivity).inflate(R.layout.activity_login,
            window.decorView as ViewGroup,
            false)
        createdView.setBackgroundColor(0)
        AlertDialog.Builder(this@MainActivity)

            .setView(createdView)
            //.setPositiveButton("Save", object: DialogInterface.OnClickListener{ })
            .show()
    }

    fun performRegister() {
        val name = txt_createName.text.toString()
        val email = txt_createEmail.text.toString()
        val password = txt_createPassword.text.toString()
        try{
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(!it.isSuccessful){
                        Toast.makeText(this, "NOPE", Toast.LENGTH_LONG).show()
                        return@addOnCompleteListener
                    }
                    uploadImageToFirebase()
                    Toast.makeText(this, "YAS", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "MAOIO", Toast.LENGTH_LONG).show()
                }
        }catch(e: Exception){
            Toast.makeText(this, "$e", Toast.LENGTH_LONG).show()
        }
    }

    fun uploadImageToFirebase() {
        if(::photo_use.isInitialized){
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/imagens/$filename")

            ref.putFile(photo_use)
                .addOnSuccessListener {
                    Toast.makeText(this, "FOI O STORAGE", Toast.LENGTH_LONG).show()
                    ref.downloadUrl.addOnSuccessListener {
                        saveUserToFireData(it.toString())
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "NÃO FOI O STORAGE", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun saveUserToFireData(profileId: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid, txt_createName.text.toString(), profileId)

        ref.setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "FOI O DATABASE", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "NÃO FOI O DATABASE", Toast.LENGTH_LONG).show()
            }
    }


    fun btnListeners(){
        btn_toLogin.setOnClickListener {
            login()
            //startActivity(Intent(this, (LoginActivity::class.java)))
        }

        btn_create.setOnClickListener {
            performRegister()
        }

        btn_photoSelector.setOnClickListener {
            //Toast.makeText(this, "ahsduudhsaijd", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            Toast.makeText(this, "MASMAOSLAPSLASL", Toast.LENGTH_LONG).show()

            photo_use = data.data!!

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, photo_use)

            val bitmapDrawable = BitmapDrawable(bitmap)
            img_create.setImageDrawable(bitmapDrawable)
            btn_photoSelector.alpha = 0f

            //btn_photoSelector.background = bitmapDrawable
        }
    }


}

class User(val uid: String?, val username: String, val profileId: String)
