package ru.nhmt.authtestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.utils.EmptyContent.status
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.nhmt.authtestapp.Commo.Network
import ru.nhmt.authtestapp.module.LoginData
import ru.nhmt.authtestapp.module.RegData

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val list = findViewById<RecyclerView>(R.id.usersList)
        list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = UserListAdapter()
        list.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val httpResponse = Network.httpClient.request {
                    url(Network.URL_USER_LIST1)
                    method = HttpMethod.Get
                    header("Authorization", "Bearer ${MainActivity.authToken}")
                    contentType(ContentType.Application.Json)
                    //setBody("{key:val", ContentType.Application)
                    //parameter("key", "val")
                }
                if (httpResponse.status == HttpStatusCode.OK){
                   val data: ArrayList<RegData> = httpResponse.body()
                    Log.i("HTTP_USERLIST, ","Body: ${data.toString()}")
                    withContext(Dispatchers.Main){
                        adapter.update(data)
                    }
                }
                //Log.i( "HTTP_USERLIST", "status: ${httpResponse.status}, Msg: ${httpResponse.bodyAsText()}" )
            } catch (e:Exception){
                Log.e("HTTP_USERLIST",e.stackTraceToString())
            }
        }
    }
}