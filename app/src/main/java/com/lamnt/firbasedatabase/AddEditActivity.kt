package com.lamnt.firbasedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class AddEditActivity : AppCompatActivity() {
    private var user: User? = null
    private lateinit var btnAction: Button
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private val userPreference by lazy {
        FirebaseDatabase.getInstance().reference.child("users")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        btnAction = findViewById(R.id.btnAction)
        edtName = findViewById(R.id.edtName)
        edtPhone = findViewById(R.id.edtPhone)
        intent?.getSerializableExtra("user")?.let {
            user = it as User
        }
        user?.let {
            btnAction.text = "Edit"
            edtName.setText(it.name)
            edtPhone.setText(it.phone)
        } ?: run {
            btnAction.text = "Add"
        }
        btnAction.setOnClickListener { addEditUser() }
    }

    private fun addEditUser() {
        val name = edtName.text.toString()
        val phone = edtPhone.text.toString()
        if (name.isNullOrBlank()) {
            Toast.makeText(this, "Please input Name", Toast.LENGTH_SHORT).show()
            return
        }

        if (phone.isNullOrBlank()) {
            Toast.makeText(this, "Please input Phone", Toast.LENGTH_SHORT).show()
            return
        }
        if ("Edit" == btnAction.text.toString()) {
            user?.key?.let {
                userPreference.child(it)
                    .setValue(User(name, phone))
            }
        } else {
            userPreference.child(UUID.randomUUID().toString()).setValue(User(name, phone))
        }
        finish()
    }
}