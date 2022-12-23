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
import ru.nhmt.authtestapp.databinding.ActivityMainBinding
import ru.nhmt.authtestapp.module.LoginData

class MainActivity : AppCompatActivity() {

    companion object{
        var authToken: String = ""
    }

    private lateinit var httpClient: HttpClient
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        httpClient = HttpClient() {
            install(ContentNegotiation){
                gson()
            }
        }
        binding.login.setOnClickListener { login() }
        binding.rigester.setOnClickListener {
            val intent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    fun login() {
        val data = LoginData(
            binding.email.text.toString(),
            binding.password.text.toString()
        )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: HttpResponse = httpClient.request {
                    url("http://172.16.0.100/auth/login")
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    setBody(data)
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, response.bodyAsText(), Toast.LENGTH_LONG)
                        .show()
                }

                Log.i("HTTP_CLIENT", response.bodyAsText())
                if (response.status == HttpStatusCode.OK) {
                    val token = Gson().fromJson(response.bodyAsText(), JsonObject::class.java)
                    authToken = token.get("token").asString
                    Log.i("HTTP_CLIENT", authToken)
                    val intent = Intent(this@MainActivity, ListActivity::class.java)
                    startActivity(intent)
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, response.bodyAsText(), Toast.LENGTH_LONG).show()
                    }
                }

            } catch (e: Exception) {
                Log.e("HTTP_CLIENT", e.stackTraceToString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error server!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }
}