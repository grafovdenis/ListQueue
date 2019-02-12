package ru.listqueue

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class MainActivity : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mAuth.currentUser != null) {
            startActivity(Intent(this, ListActivity::class.java))
            finish()
        }
        setContentView(R.layout.activity_login)
        logout.setOnClickListener { view ->
            signIn(view, input_email.text.toString().trim(), input_password.text.toString().trim(), anon = false)
        }
        btn_anonimous.setOnClickListener { view ->
            signIn(view, input_email.text.toString().trim(), input_password.text.toString().trim(), anon = true)
        }
    }

    fun signIn(view: View, email: String, password: String, anon: Boolean) {
        showMessage(view, "Авторизация...")
        if (!anon)
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, ListActivity::class.java)
                        intent.putExtra("id", mAuth.currentUser?.email)
                        startActivity(intent)
                    } else {
                        val alertDialog = AlertDialog.Builder(this)
                        alertDialog.setTitle("Создать новый аккаунт?")
                        alertDialog.setPositiveButton(android.R.string.yes) { dialog, which ->
                            signUp(view, email, password)
                        }
                        alertDialog.setNegativeButton(android.R.string.no) {dialog, which -> }
                        alertDialog.show()
                    }
                })
        else mAuth.signInAnonymously().addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
            } else {
                showMessage(view, "Error: ${task.exception?.message}")
            }
        })
    }

    fun signUp(view: View, email: String, password: String) {
        showMessage(view, "Создаём аккаунт...")
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, ListActivity::class.java)
                    intent.putExtra("id", mAuth.currentUser?.email)
                    startActivity(intent)
                } else {
                    showMessage(view, "Error: ${task.exception?.message}")
                }
            })
    }

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }
}
