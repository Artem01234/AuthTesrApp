package ru.nhmt.authtestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonToken
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import ru.nhmt.authtestapp.MainActivity.Companion.authToken
import ru.nhmt.authtestapp.databinding.ActivityMainBinding
import ru.nhmt.authtestapp.databinding.ActivitySignUpBinding
import ru.nhmt.authtestapp.module.RegData


class SignUpActivity : AppCompatActivity() {

    private lateinit var httpClient: HttpClient
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        httpClient = HttpClient() {
            install(ContentNegotiation){
                gson()
            }
        }
        binding.registr.setOnClickListener { registr() }
        binding.logIn.setOnClickListener {
            val intent = Intent(this@SignUpActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun registr() {
        val data = RegData(
            binding.name.text.toString(),
            binding.secondName.text.toString(),
            binding.emailAddress.text.toString(),
            binding.passwordText.text.toString())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response:HttpResponse = httpClient.request{
                    url ("http://172.16.0.100/auth/register")
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    setBody(data)
                }
                withContext(Dispatchers.Main){
                    Toast.makeText(this@SignUpActivity, response.bodyAsText(), Toast.LENGTH_LONG).show()
                }
                Log.i("HTTP_CLIENT", response.bodyAsText())
                if (response.status == HttpStatusCode.OK){

                    val intent = Intent(this@SignUpActivity, ListActivity::class.java)
                    startActivity(intent)
                }else {
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@SignUpActivity, response.bodyAsText(), Toast.LENGTH_LONG).show()
                    }
                }
            }catch (e: Exception){
                Log.e("HTTP_CLIENT", e.stackTraceToString())
                withContext(Dispatchers.Main){
                    Toast.makeText(this@SignUpActivity, "error server!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}