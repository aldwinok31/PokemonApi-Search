package aldwin.tablante.com.pokemonsearch

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
var searchBar: EditText?= null



var submit:Button?=null
var pbar: ProgressBar?=null
    var hpbar : ProgressBar?=null
    var txtv :TextView?=null
    var txtv2 :TextView?=null
    var imageView : ImageView?=null
var imageView2:ImageView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        initializer()


    }

    //--------------------------------------//
    inner class Loader : AsyncTask<Void,String,Void>(){
        var SingleParse = ""
        var Statst = ""
     var refresher = 0
        var ImageUrl =""
        var ImageUrlback =""
        var counter =1
        override fun onPreExecute() {
            super.onPreExecute()
           // searchBar!!.visibility = View.GONE
           // submit!!.visibility = View.GONE

            var toast = Toast.makeText(this@MainActivity,"Loading....",Toast.LENGTH_LONG)

            toast.show()
        }
        override fun doInBackground(vararg p0: Void?): Void? {

            var searchvalue = searchBar!!.text.toString()


            while(counter != 803) {

                var data = StringUrl(counter)
                var JO = JSONObject(data)
                SingleParse = JO.getString("name")
                if(SingleParse.equals(searchvalue.toLowerCase())){
                    Statst =Pokinformation(data,JO)
                   ImageUrl = ImageUrl(data)
                    ImageUrlback = ImageUrlback(data)
                   publishProgress(data)
refresher = 1
    break }
                else{ counter ++ }


            }
            return null
        }

        override fun onProgressUpdate(vararg values: String) {
            super.onProgressUpdate(*values)
            var url = values[0]
            var counter = 1
            var value = pokepower(url,counter)
            hpbar!!.setProgress(value)

        }
        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            pbar = findViewById(R.id.progressBar)
            pbar!!.visibility = View.GONE
            searchBar!!.setText("")

if(refresher == 1) {
    txtv!!.setText(Statst)
    txtv2!!.setText(SingleParse.capitalize())
    var ins = URL(ImageUrl).openStream()
    var mIcon = BitmapFactory.decodeStream(ins)
    imageView!!.setImageBitmap(mIcon)

    ins = URL(ImageUrlback).openStream()
    mIcon = BitmapFactory.decodeStream(ins)
    imageView2!!.setImageBitmap(mIcon)
    refresher = 0
}


        }

    }
    //-----------------------------------//



































    fun initializer(){

        imageView2= findViewById(R.id.imageView2)
        imageView = findViewById(R.id.imageView)
        searchBar = findViewById(R.id.editText)
        submit = findViewById(R.id.button)
        txtv=findViewById(R.id.textView)
        hpbar = findViewById(R.id.progressBar2)
        txtv2=findViewById(R.id.textView2)

        submit!!.setOnClickListener {
            var process = Loader()
            process.execute()


        }

    }









    fun StringUrl ( counter:Int): String {
        var url = URL("https://pokeapi.co/api/v2/pokemon/"+counter.toString()+"/")
        var httpURLConnection = url.openConnection()
        var inputStream = httpURLConnection.getInputStream()
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = ""
        var data :String =""
        while (line != null) {
            line = bufferedReader.readLine()
            data += line
        }
        return  data
    }

    fun Pokinformation(data : String,JO:JSONObject) : String{
        var counter = 0
        var Statst = ""
        var SingleParse = ""
        var allvalue = ""
        while(counter != 6) {
            var JA = JSONObject(data).getJSONArray("stats").getJSONObject(counter).getJSONObject("stat")
            var jArray: JSONArray = JO.getJSONArray("stats")

            var stst = jArray.get(counter) as JSONObject
            Statst = stst.getString("base_stat").toString()

            stst = JA as JSONObject
             SingleParse = stst.getString("name")
            allvalue = allvalue + SingleParse + ": "+ Statst + "\n"
            counter++
        }
        return allvalue
    }

    fun ImageUrl (url:String) : String{
        var JO = JSONObject(url).getJSONObject("sprites")
        var SingleParse = JO.getString("front_default")

        return SingleParse
    }
    fun ImageUrlback (url:String) : String{
        var JO = JSONObject(url).getJSONObject("sprites")
        var SingleParse = JO.getString("back_default")

        return SingleParse
    }


    fun pokepower(data:String,index:Int):Int{
        var JO = JSONObject(data)
        var jArray: JSONArray = JO.getJSONArray("stats")

        var stst = jArray.get(index) as JSONObject
       var  Statst = stst.getString("base_stat").toInt()
        return Statst
    }
}


