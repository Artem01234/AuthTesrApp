//package ru.nhmt.authtestapp.module
//
//package ru.nhmt.authtestapp.ui.profile
//
//import android.content.Intent
//import android.content.res.ColorStateList
//import android.os.Bundle
//import android.util.Log
//import android.view.ActionMode
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.MenuItem
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.content.ContextCompat
//import androidx.core.graphics.drawable.DrawableCompat
//import androidx.core.view.get
//import androidx.recyclerview.widget.LinearLayoutManager
//import coil.load
//import io.ktor.client.*
//import io.ktor.client.call.*
//import io.ktor.client.plugins.contentnegotiation.*
//import io.ktor.client.request.*
//import io.ktor.client.statement.*
//import io.ktor.http.*
//import io.ktor.serialization.gson.*
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import ru.nhmt.authtestapp.AuthActivity
//import ru.nhmt.authtestapp.R
//import ru.nhmt.authtestapp.common.BASE_URL
//import ru.nhmt.authtestapp.common.utils.ApiUtils.authToken
//import ru.nhmt.authtestapp.controller.HomeListAdapter
//import ru.nhmt.authtestapp.databinding.FragmentProfileBinding
//import ru.nhmt.authtestapp.model.MoviesData
//import ru.nhmt.authtestapp.model.UserData
//
//
//class ProfileFragment : Fragment() {
//    //объявление переменной _binding для связи с FragmentProfileBinding
//    private var _binding: FragmentProfileBinding? = null
//    //объявление переменной binding
//    private val binding get() = _binding!!
//    //создание переменной в которой записана сслыка на аватарки
//    val BASE_URL_AVATAR = "$BASE_URL/images/avatar/"
//    //объявление метода onCreateView для разметки
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        //получение корневого представление фрашмента FragmentProfileBinding
//        _binding = FragmentProfileBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//        //получение значения из HomeListAdapter
//        val adapter = HomeListAdapter()
//        //создание разметки в imagesList
//        binding.imagesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        //добавление содержимое adapter в imagesList
//        binding.imagesList.adapter = adapter
//        //привязка к перемнной logout toolbar
//        val logout = binding.toolbar.menu.add("logout")
//        //как этот элемент должен отображаться при наличии панели действий
//        logout.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
//        //дает logout иконку log_out
//        logout.setIcon(R.drawable.log_out)
//        if (logout.icon != null) {
//            //добавление цвета background в logout.icon
//            DrawableCompat.setTint(logout.icon!!, ContextCompat.getColor(requireContext(), android.R.color.background_light))
//        }
//        //обработка нажатия на элемент logout
//        logout.setOnMenuItemClickListener {
//            //запуск AuthActivity
//            startActivity(Intent(context, AuthActivity::class.java))
//            //заверешние текущей активности
//            requireActivity().finish()
//            //возвращение true в setOnMenuItemClickListener
//            return@setOnMenuItemClickListener true
//        }
//        //ссылка на клиент
//        val httpClient = HttpClient() {
//            //поддержка сериализации
//            install(ContentNegotiation) {
//                gson()
//            }
//        }
//        //работает пока приложение работает
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                //создание переменной в которую записывается ссылка на профиль
//                val responseProfile: HttpResponse = httpClient.request {
//                    // http://172.16.0.100/auth/register
//                    //ссылка на профиль пользователя
//                    url("$BASE_URL/user")
//                    //переменная получает ссылку HttpMethod
//                    method = HttpMethod.Get
//                    //заголовок окна
//                    header("Authorization","Bearer ${authToken}")
//                    //преобразование содержимого окна в формат Json
//                    contentType(ContentType.Application.Json)
//                }
//                //проверка статуса
//                if (responseProfile.status == HttpStatusCode.OK) {
//                    //запись в переменную data содержимого responseProfile
//                    val data: UserData = responseProfile.body()
//                    //для работы с пользовательскким интерфейсом
//                    withContext(Dispatchers.Main) {
//                        //вывод имени и фамилии
//                        binding.toolbarLayout.title = "${data.firstName} ${data.lastName}"
//                        //вывод аватарки пользователя
//                        binding.avatar.load(BASE_URL_AVATAR+data.avatar)
//                        //вывод в poster изображения bg_polygon
//                        binding.poster.setImageResource(R.drawable.bg_polygon)
//                    }
//                }
//                //запись в response запроса из httpClient.request
//                val response: HttpResponse = httpClient.request {
//                    // http://172.16.0.100/auth/register
//                    //обращение к usermovies с сайта
//                    url("$BASE_URL/usermovies")
//                    //переменная получает ссылку HttpMethod
//                    method = HttpMethod.Get
//                    //заголовок окна
//                    header("Authorization","Bearer ${authToken}")
//                    //преобразование содержимого окна в формат Json
//                    contentType(ContentType.Application.Json)
//                }
//                //проверка статуса
//                if (response.status == HttpStatusCode.OK) {
//
//                    val data: MoviesData = response.body()
//                    //вывод данных с тэгом HTTP_MOVIE
//                    Log.i("HTTP_MOVIE", data[0].toString())
//                    //для работы с пользовательскким интерфейсом
//                    withContext(Dispatchers.Main) {
//                        //обновление данных adapter
//                        adapter.update(data)
//                    }
//                }
//                //ошибка
//            } catch (e: Exception) {
//                //в случае ошибки вывод в логи информации об ошибки с тэгом HTTP_MOVIE
//                Log.e("HTTP_MOVIE", e.stackTraceToString())
//            }
//        }
//        //возвращение root
//        return root
//    }
//
//}
//
//
////
////import android.os.Bundle
////import android.util.Log
////import android.view.Menu
////import android.widget.CheckBox
////import android.widget.SearchView
////import androidx.appcompat.app.AppCompatActivity
////import androidx.recyclerview.widget.GridLayoutManager
////import androidx.recyclerview.widget.LinearLayoutManager
////import kotlinx.coroutines.*
////import org.jsoup.Jsoup
////import org.jsoup.nodes.Document
////import ru.nhmt.authtestapp.databinding.ActivityMainBinding
////import ru.rehill.testappjsoupmanga.databinding.ActivityMainBinding
////
////@DelicateCoroutinesApi
////class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
////
////    //создание переменной с отложенной инициализацией
////    private lateinit var bind: ActivityMainBinding
////    //создание переменной с отложенной инициализацией
////    private lateinit var adapter: MangaListAdapter
////    //создание переменной searchText для запроса поиска
////    private var searchText: String = ""
////    //создание переменной pageText для последующей нумеровки страниц
////    private var pageText: Int = 0
////    //создание переменной maxPageText для максимального количсетва стравниц в документе
////    private var maxPageText: Int = 0
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        //привязка xml-данных из ActivityMain
////        bind = ActivityMainBinding.inflate(layoutInflater)
////        //передача корневого представления
////        setContentView(bind.root)
////
////        //присваивнаие переменной adapter заначение из MangaListAdapter
////        adapter = MangaListAdapter(this)
////        //обращение к объетку mangaList и привязка с adapter
////        bind.mangaList.adapter = adapter
////        adapterLoad("")
//////        listManga.observe(this, Observer { words ->
//////            words?.let { adapter.setList(it) }
//////        })
////        //обработка выбора пунтка bottomNav
////        bind.bottomNav.setOnItemSelectedListener{
////            //создание целочисленной переменной и приравнивание к значению переменной pageText
////            var newPageText:Int = pageText
////            when(it.title) {
////                //когда текст объетка (it) активности равен up
////                "up" ->  if (newPageText < maxPageText-1) newPageText++
////                //когда текст объетка (it) активности равен back
////                "back" -> if (newPageText > 0) newPageText--
////                //когда текст объетка (it) активности равен start
////                "start" -> newPageText = 0
////                //когда текст объетка (it) активности равен end
////                "end" -> newPageText = maxPageText-1
////            }
////            // если newPageText != pageText то выполянется поиск запрашиваемого текста
////            if (newPageText != pageText) {
////                pageText = newPageText
////                adapterLoad(searchText)
////            }
////            //возвращение true в  bind.bottomNav.setOnItemSelectedListener
////            return@setOnItemSelectedListener true
////        }
////
////    }
////
////    //создание функции adapterLoad
////    private fun adapterLoad(text: String){
////        GlobalScope.launch {
////            //создание списка в котором находится текст и номер страницы
////            val list = loadMangaList(text, pageText)
////            //вывод в логи страницы с тэгом load
////            Log.i("load", list.toString())
////            GlobalScope.launch(Dispatchers.Main){
////                //если список не пустой добавляет текст объетка (it)
////                list?.let { adapter.setList(it) }
////                //приравнивание тестка элемента активности pageText
////                bind.pageText.text = (pageText+1).toString()
////            }
////        }
////    }
////    //создание функции loadMangaList
////    private fun loadMangaList(text: String, page: Int): List<MangaItem>? {
////        //объявление переменной doc
////        val doc: Document
////        try {
////            doc = if ( text != "" ) {
////                //если в документе есть текст открывается ссылка поиска
////                Jsoup.connect("https://readmanga.io/search")
////                    .data("q", text)
////                    .data("offset", (page * 50).toString())
////                    .post()
////            }else{
////                //если в документе нет текста открывается ссылка со списком
////                Jsoup.connect("https://readmanga.io/list")
////                    .data("sortType", "DATE_CREATE")
////                    .data("offset", (page*70).toString())
////                    .get()
////            }
////            //в случае ошибки возвращается пустота
////        }catch (e: Exception){
////            Log.e("load", "jsoup error")
////            return null
////        }
////        try {
////            //преобразование номера последней страницы документа в целочисленное значение
////            maxPageText = doc.select(".pagination .step").last().text().toInt()
////            //преобразование номера текущей страницы документа в целочисленное значение
////            pageText = doc.select(".pagination .currentStep").last().text().toInt() -1
////            //в случае ошибки номера текущей и последней страницы документа в 0
////        }catch (e: Exception){
////            maxPageText = 0
////            pageText = 0
////        }
//////        Log.i("load",  doc.select(".pagination .step").last().text())
////        //объявление целочисленной переменной mangaId типа Long
////        var mangaId: Long = 0
////        //объявление списка list доступного только для чтения
////        val list = emptyList<MangaItem>().toMutableList()
////        //выбор элемнтов из докумета doc
////        doc.select(".tiles.row .tile.col-md-6")
////            //перебор элеметов из докумета doc и выбор нужных
////            .forEach { i ->
////                list += MangaItem(
////                    mangaId++,
////                    //выбор текста с заголовком h3
////                    i.select(".desc h3").text(),
////                    //выбор текста с заголовком h4
////                    i.select(".desc h4").text(),
////                    //выбор оригинального изображения
////                    i.select(".img img").attr("data-original"),
////                    //выбор рейтнига
////                    (i.select(".star-rate .rating")
////                        .attr("title")
////                        .substringBefore(" из ")
////                        .toFloatOrNull()?:0F)/2,
////                    //выбор информации
////                    i.select(".tile-info").html(),
////                    //выбор тэгов
////                    i.select(".tags").text(),
////                    //выбор ссылок
////                    i.select(".desc h3 a").attr("href")
////                )
////                //вывод в логи ссылки из документа
////                Log.i("loads", i.select(".desc h3 a").attr("href"))
////            }
////        //возвращение списка
////        return list
////    }
////    //создание функции onCreateOptionsMenu
////    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
////        //привязка XML-файлов из main_menu
////        menuInflater.inflate(R.menu.main_menu, menu)
////        //привязка к переменной search элемент с id app_bar_search
////        val search = menu?.findItem(R.id.app_bar_search)
////        //создание переменной в которую записывается значение переменной search, если оно не пустое
////        val searchView = search?.actionView as? SearchView
////        //добавление в аттрибут queryHint Поиск если он не пустой
////        searchView?.queryHint = "Поиск"
////        //Возвращает, true если включена кнопка отправки
////        searchView?.isSubmitButtonEnabled  = true
////        //наблюдение за иззмением в текмте
////        searchView?.setOnQueryTextListener(this)
////        //создание списка для запросов
////        val listToGrid = menu?.findItem(R.id.app_bar_switch)
////        //переключение по поискам
////        listToGrid?.actionView?.setOnClickListener() {
////            //измерение и позиционирование выделенных элементов
////            bind.mangaList.layoutManager =
////                //проверка на выделение 3 элементов списка в CheckBox
////                if ((it as CheckBox).isChecked)
////                    GridLayoutManager(this, 3)
////                //если нет выделения CheckBox добавляется в LinearLayout
////                else
////                    LinearLayoutManager(this)
////        }
////        //возвращенеи истины
////        return true
////    }
////
////    //создание функции onQueryTextSubmit
////    override fun onQueryTextSubmit(query: String?): Boolean {
////        query?.let {
////            //прсиваивание значения переменной query в searchText
////            searchText = query
////            //присваивание значения 0 переменной pageText
////            pageText = 0
////            //отправка текста поиска
////            adapterLoad(searchText)
////        }
////        //вывод в логи значение переменной query с тэгом query
////        Log.i("query", query.toString())
////        //возавращение значения false
////        return false
////    }
////    //создание функции onQueryTextChange
////    override fun onQueryTextChange(newText: String?): Boolean {
////        //возавращение значения false
////        return false
////    }
////
////
////}
////
