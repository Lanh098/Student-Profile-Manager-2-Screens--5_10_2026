package com.ute.studentprofilemanager2screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val TAG = "LIFECYCLE_MAIN"

    private lateinit var imgAvatar: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvDetails: TextView
    private lateinit var tvGpaBadge: TextView

    private var student = Student(
        id = "2415053122123",
        name = "Bùi Tá Lanh",
        studentClass = "24T1",
        gpa = 3.5f,
        phone = "0375124178"
    )

    private val editProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            @Suppress("DEPRECATION")
            val updatedStudent = result.data?.getSerializableExtra("EXTRA_STUDENT") as? Student
            updatedStudent?.let {
                student = it
                bindStudentData()
                Toast.makeText(this, "Đã cập nhật hồ sơ thành công!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Đã hủy chỉnh sửa!", Toast.LENGTH_SHORT).show()
        }
    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imgAvatar.setImageURI(it)
            Toast.makeText(this, "Đã đổi ảnh đại diện!", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestCameraLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Quyền Camera: ĐÃ ĐƯỢC CẤP!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Quyền Camera: BỊ TỪ CHỐI!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() được gọi")
        setContentView(R.layout.activity_main)

        imgAvatar = findViewById(R.id.imgAvatar)
        tvName = findViewById(R.id.tvName)
        tvDetails = findViewById(R.id.tvDetails)
        tvGpaBadge = findViewById(R.id.tvGpaBadge)

        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile)
        val btnChangeAvatar = findViewById<Button>(R.id.btnChangeAvatar)
        val btnCallHotline = findViewById<Button>(R.id.btnCallHotline)
        val btnRequestCamera = findViewById<Button>(R.id.btnRequestCamera)

        bindStudentData()

        btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java).apply {
                putExtra("EXTRA_STUDENT", student)
            }
            editProfileLauncher.launch(intent)
        }

        btnChangeAvatar.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
        btnCallHotline.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${student.phone}")
            }
            startActivity(intent)
        }

        btnRequestCamera.setOnClickListener {
            requestCameraLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() được gọi")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() được gọi")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() được gọi")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() được gọi")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart() được gọi")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() được gọi")
    }
    
    private fun bindStudentData() {
        tvName.text = student.name
        tvDetails.text = "Lớp: ${student.studentClass} | MSSV: ${student.id}"

        val rank = when {
            student.gpa >= 3.6f -> "Xuất sắc"
            student.gpa >= 3.2f -> "Giỏi"
            student.gpa >= 2.5f -> "Khá"
            else -> "Trung bình"
        }
        tvGpaBadge.text = "GPA: ${student.gpa} ($rank)"
    }
}