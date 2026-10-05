package com.ute.studentprofilemanager2screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class EditProfileActivity : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtClass: EditText
    private lateinit var edtGpa: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button
    private var currentStudent: Student? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        edtName = findViewById(R.id.edtName)
        edtClass = findViewById(R.id.edtClass)
        edtGpa = findViewById(R.id.edtGpa)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)
        @Suppress("DEPRECATION")
        currentStudent = intent.getSerializableExtra("EXTRA_STUDENT") as? Student
        currentStudent?.let {
            edtName.setText(it.name)
            edtClass.setText(it.studentClass)
            edtGpa.setText(it.gpa.toString())
        }
        btnSave.setOnClickListener {
            val name = edtName.text.toString().trim()
            val studentClass = edtClass.text.toString().trim()
            val gpaStr = edtGpa.text.toString().trim()
            // Kiểm tra rỗng
            if (name.isEmpty() || studentClass.isEmpty() || gpaStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val gpa = gpaStr.toFloatOrNull()
            if (gpa == null || gpa < 0.0f || gpa > 4.0f) {
                edtGpa.error = "GPA phải trong khoảng từ 0.0 đến 4.0"
                return@setOnClickListener
            }
            val updatedStudent = currentStudent?.copy(
                name = name,
                studentClass = studentClass,
                gpa = gpa
            ) ?: Student(name = name, studentClass = studentClass, gpa = gpa)
            val resultIntent = Intent().apply {
                putExtra("EXTRA_STUDENT", updatedStudent)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        // 4. Xử lý nút Hủy Bỏ
        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}