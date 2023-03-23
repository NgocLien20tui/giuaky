package com.example.register

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.register.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.setImageResource(R.drawable.img)
        binding.imgLogin.setImageResource(R.drawable.img)


        binding.btnAdd.setOnClickListener {
            registerHandle()
        }

        binding.btnLogin.setOnClickListener {
            binding.llLogin.visibility = View.VISIBLE
            binding.llReg.visibility = View.GONE
        }

        binding.btnReg.setOnClickListener {
            binding.llLogin.visibility = View.GONE
            binding.llReg.visibility = View.VISIBLE
        }

        binding.btnLog.setOnClickListener {
            checkLogin()
        }
    }

    private fun checkLogin() {
        val username = binding.etLogUsername.text.toString()
        val password = binding.etLogPassword.text.toString()
        val db = MyDatabase(this).writableDatabase
        val query = "SELECT * FROM users WHERE username = '$username' AND password = '$password'"
        val cursor = db.rawQuery(query, null)
        val count = cursor.count
        cursor.close()
        db.close()
        if (count>0){
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show()

        }
    }

    private fun registerHandle() {
        val email = binding.etEmail.text.toString()
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        val repassword = binding.etRePassword.text.toString()
        if(email.isBlank()){
            binding.etEmail.error = "Email không được để trống!"
            return
        }
        if(username.isBlank()){
            binding.etUsername.error = "Tên đăng nhập không được để trống!"
            return
        }
        if(password.isBlank()){
            binding.etPassword.error = "Mật khẩu không được để trống!"
        }else{
            if(password != repassword){
                binding.etRePassword.error = "Mật khẩu không trùng khớp!"
                return
            }
        }
        try {
            val db = MyDatabase(this).writableDatabase
            val values = ContentValues().apply {
                put("email", email)
                put("username", username)
                put("password", password)
            }
            db.insert("users", null, values)
            db.close()
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
        }catch (err: EnumConstantNotPresentException){
            Toast.makeText(this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show()
        }
    }
}