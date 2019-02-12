package ru.listqueue

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_queue.*

class QueueActivity : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()

    fun getViewByPosition(pos: Int, listView: ListView): View {
        val firstListItemPosition = listView.firstVisiblePosition
        val lastListItemPosition = firstListItemPosition + listView.childCount - 1

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.adapter.getView(pos, null, listView)
        } else {
            val childIndex = pos - firstListItemPosition
            return listView.getChildAt(childIndex)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mAuth.addAuthStateListener {
            if (mAuth.currentUser == null) {
                this.finish()
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queue)
        setSupportActionBar(findViewById(R.id.queue_toolbar))
        supportActionBar?.title = intent.getStringExtra("title")

        initRecyclerView(R.id.recyclerView_queue)
    }

    private fun initRecyclerView(id: Int) {
        val recyclerView = findViewById<RecyclerView>(id)
        val members = ArrayList<Member>()

        val list = arrayListOf(
            "Денис Графов", "Илья Сергеев", "Дмитрий Ахриреев", "Елена Сидорина",
            "Евгения Рязанова", "Анастасия Чуприна", "Михаил Косенков", "Малик Хираев", "Алексей Залата",
            "Александр Купцов", "Алексей Медведев", "Алексей Навальный", "Александр Пистолетов")

        for (mem in list) {
            members.add(Member(mem))
        }

        val adapter = QueueRecyclerViewAdapter(members) {
            Log.d("Click", it.name)
        }

        recyclerView_queue.adapter = adapter
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
}
