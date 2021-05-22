package com.lamnt.firbasedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {
    companion object {
        const val TAG = "MainActivity"
    }

    private val userAdapter: UserAdapter by lazy { UserAdapter(ArrayList(), this) }
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var rvUsers: RecyclerView
    private val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference.child("users")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAdd = findViewById(R.id.btnAdd)
        rvUsers = findViewById(R.id.rvUsers)
        initRecyclerView()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = ArrayList<User>()
                for (u in snapshot.children) {
                    val user = u.getValue(User::class.java)
                    user?.let {
                        it.key = u.key.toString()
                        users.add(it)
                    }
                }
                userAdapter.submitData(users)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        initClick()
    }

    private fun initRecyclerView() {
        rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }

    private fun initClick() {
        btnAdd.setOnClickListener { startActivity(Intent(this, AddEditActivity::class.java)) }
    }

    override fun onClick(user: User) {
        val intent = Intent(this, AddEditActivity::class.java).apply {
            putExtra("user", user)
        }
        startActivity(intent)
    }

    override fun onLongClick(user: User) {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Do you want to delete ${user.name} ?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                database.child(user.key).removeValue()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}