package ru.listqueue

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : AppCompatActivity() {
    var mAuth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        mAuth.addAuthStateListener {
            if (mAuth.currentUser == null) {
                this.finish()
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(findViewById(R.id.list_toolbar))

        initRecyclerView(R.id.recyclerView_list)
    }

    private fun initRecyclerView(id: Int) {
        val recyclerView = findViewById<RecyclerView>(id)

        val members = arrayOf("Список 1", "Список 2", "Список 3", "Список 4")
        val places = arrayOf("Место в очереди: 1", "Место в очереди: 2", "Место в очереди: 3", "")

        val queues = ArrayList<Queue>()
        for (i in 0 until members.size)
            queues.add(Queue(members[i], places[i]))

        val adapter = ListRecyclerViewAdapter(queues) {
            val intent = Intent(this, QueueActivity::class.java)
            intent.putExtra("title", it.name)
            intent.putExtra("id", mAuth.currentUser?.email)
            startActivity(intent)
        }

        recyclerView_list.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.logout -> signOut()
        }
        return true
    }

    fun signOut() {
        mAuth.signOut()
    }

    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
    }

}

