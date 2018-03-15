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
    var prog:ProgressBar?=null
    var loadtxt : TextView?=null
    var searchBar: EditText?= null
    var pokmon = Pokemon("","","","","","")
    var submit:Button?=null
    var pbar: ProgressBar?=null
    var defensebar:ProgressBar?=null
    var sabar:ProgressBar?=null
    var sdbar:ProgressBar?=null
    var speedbar:ProgressBar?=null
    var attackbar:ProgressBar?=null
    var hpbar : ProgressBar?=null
    var txtv2 :TextView?=null
    var txtv:TextView?=null
    var txtv3 :TextView?=null
    var txtv4 :TextView?=null
    var txtv5 :TextView?=null
    var txtv6 :TextView?=null
    var txtv7 :TextView?=null
    var download : downloader? = null
  var pkm : ArrayList<Pokemon> = ArrayList()
    var imageView : ImageView?=null
    var imageView2:ImageView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        initializer()

download = downloader()
        download!!.execute()





    }














    //--------------------------------------//
    inner class Loader : AsyncTask<Void,Pokemon,Void>(){
        var SingleParse = ""
        var Statst = ""
     var refresher = 0
        var ImageUrl =""
        var ImageUrlback =""
        var counter =0
        override fun onPreExecute() {
            super.onPreExecute()
            searchBar!!.isEnabled = false
            submit!!.isEnabled = false
            pbar!!.visibility = View.VISIBLE


            hpbar!!.visibility = View.GONE
            attackbar!!.visibility = View.GONE
            defensebar!!.visibility = View.GONE
            sabar!!.visibility = View.GONE
            sdbar!!.visibility = View.GONE
            speedbar!!.visibility = View.GONE

            var toast = Toast.makeText(this@MainActivity,"Loading....",Toast.LENGTH_LONG)

            toast.show()
        }
        override fun doInBackground(vararg p0: Void?): Void? {

            var searchvalue = searchBar!!.text.toString()


            while(counter != 100) {


                 var pokemonholder = pkm[counter]
                SingleParse = pokemonholder.name.toLowerCase()
                if(SingleParse.equals(searchvalue.toLowerCase())){
                    ImageUrl = pokemonholder.imageurl
                    ImageUrlback = pokemonholder.imageurlback
                    var data = pokemonholder.url
                    var c = 0
                    while(c !=6 ){
                        var value = pokepower(data, c)
                        if(c == 0) pokemonholder.setspeed("Speed : "+value.toString())
                        if(c == 1) pokemonholder.setsa("Special Attack : " + value.toString())
                        if(c == 2) pokemonholder.setsd("Special Defense : "+value.toString())
                        if(c == 3) pokemonholder.setdefense("Defense : " + value.toString())
                        if(c == 4) pokemonholder.setattack("Attack : " + value.toString())
                        if(c== 5) pokemonholder.sethp("Health : " + value.toString())
                        c++
                    }
                    publishProgress(pokemonholder)

                    refresher = 1

                    break

                }
                else{
                    counter ++
                }


            }
            return null
        }

        override fun onProgressUpdate(vararg values: Pokemon) {
            super.onProgressUpdate(*values)
            var url = values[0].url
            var counter = 0
            while(counter !=6) {
                var value = pokepower(url, counter)

                if(counter == 5){ hpbar!!.setProgress(value)}
                if(counter == 0){speedbar!!.setProgress(value)}
                if(counter == 1) {sabar!!.setProgress(value)}
                if(counter == 2) {sdbar!!.setProgress(value)}
                if(counter == 3) {attackbar!!.setProgress(value)}
                if(counter == 4) {defensebar!!.setProgress(value)}




                setpokestats(counter,values[0])
               counter++
            }

        }
        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            hpbar!!.visibility = View.VISIBLE
            attackbar!!.visibility = View.VISIBLE
            defensebar!!.visibility = View.VISIBLE
            sabar!!.visibility = View.VISIBLE
            sdbar!!.visibility = View.VISIBLE
            speedbar!!.visibility = View.VISIBLE
            pbar!!.visibility = View.GONE
            searchBar!!.setText("")
            submit!!.isEnabled = true
            searchBar!!.isEnabled = true
if(refresher == 1) {
    refresher = setImageBitmap(Statst, ImageUrl, ImageUrlback, SingleParse)
}
            else{
    var toast = Toast.makeText(this@MainActivity,"Pokemon Not Found",Toast.LENGTH_LONG)

    toast.show()
}


        }

    }
    //-----------------------------------//




inner class downloader : AsyncTask<Void,Int,Void>(){
    var l1 = findViewById<LinearLayout>(R.id.linearLayout)
    var l2 = findViewById<LinearLayout>(R.id.linearLayout2)
    var l3 = findViewById<LinearLayout>(R.id.linearLayout3)
    var ImageUrl = ""
    var ImageUrlback = ""
    override fun onPreExecute() {
        super.onPreExecute()
        var toast = Toast.makeText(this@MainActivity,"FETCHING POKEMONS",Toast.LENGTH_LONG)

        toast.show()

        l2.visibility = View.GONE

        submit!!.isEnabled = false
        searchBar!!.isEnabled = false

    }
    override fun doInBackground(vararg p0: Void?): Void? {


try {
    var counter = 1
    var SingleParse = ""
    prog = findViewById<ProgressBar>(R.id.progressBar8)
    var pmon = Pokemon("", "", "", "", "", "")
    while (counter != 101) {


        prog!!.setProgress(counter)
        pmon = Pokemon("", "", "", "", "", "")
        var data = StringUrl(counter)
        var JO = JSONObject(data)
        SingleParse = JO.getString("name")

        //Pokinformation(data,JO)
        ImageUrl = ImageUrl(data)
        ImageUrlback = ImageUrlback(data)
        pmon.url = data
        pmon.imageurl = ImageUrl
        pmon.imageurlback = ImageUrlback
        pmon.name = SingleParse.toLowerCase()
        pkm.add(pmon)
        publishProgress(counter)
        counter++


    }
}
catch(e:InterruptedException){

    e.printStackTrace()
}
        return null
    }
    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        var ins = URL(ImageUrl).openStream()
        var mIcon = BitmapFactory.decodeStream(ins)
        imageView!!.setImageBitmap(mIcon)

        ins = URL(ImageUrlback).openStream()
        mIcon = BitmapFactory.decodeStream(ins)
        imageView2!!.setImageBitmap(mIcon)
        loadtxt!!.setText(values[0]!!.toString() + " : Available Pokemons " + " \n" + "Downloading more...")
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        l1.visibility = View.VISIBLE
        l2.visibility = View.VISIBLE
        l3.visibility = View.VISIBLE
        pbar!!.visibility = View.GONE
        prog!!.visibility = View.GONE
        loadtxt!!.visibility = View.GONE
        submit!!.isEnabled = true
        searchBar!!.isEnabled = true
    }

}































































    fun initializer(){

        imageView2= findViewById(R.id.imageView2)
        imageView = findViewById(R.id.imageView)
        searchBar = findViewById(R.id.editText)
        submit = findViewById(R.id.button)
        txtv=findViewById(R.id.textView)
        hpbar = findViewById(R.id.progressBar2)
        txtv2=findViewById(R.id.textView2)
        txtv3=findViewById(R.id.textView3)
        txtv4=findViewById(R.id.textView4)
        txtv5=findViewById(R.id.textView5)
        txtv6=findViewById(R.id.textView6)
        txtv7=findViewById(R.id.textView7)
        speedbar = findViewById(R.id.speed)
        sabar = findViewById(R.id.sa)
        sdbar = findViewById(R.id.sd)
        defensebar = findViewById(R.id.defense)
        attackbar = findViewById(R.id.attack)
        pbar = findViewById(R.id.progressBar)
        loadtxt = findViewById<TextView>(R.id.textView8)
        submit!!.setOnClickListener {
            var process = Loader()
            process.execute()


        }

    }




 fun setImageBitmap(Statst:String,ImageUrl:String,ImageUrlback: String,SingleParse:String):Int{
     //txtv!!.setText(Statst)
     txtv2!!.setText(SingleParse.capitalize())
     var ins = URL(ImageUrl).openStream()
     var mIcon = BitmapFactory.decodeStream(ins)
     imageView!!.setImageBitmap(mIcon)

     ins = URL(ImageUrlback).openStream()
     mIcon = BitmapFactory.decodeStream(ins)
     imageView2!!.setImageBitmap(mIcon)
     return 0
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




    fun setpokestats(counter:Int,pokmon : Pokemon){
        if(counter == 0) txtv!!.setText(pokmon.getspeed())
        if(counter == 1) txtv3!!.setText(pokmon.getsa())
        if(counter == 2) txtv4!!.setText(pokmon.getsd())
        if(counter == 3) txtv5!!.setText(pokmon.getdefense())
        if(counter == 4) txtv6!!.setText(pokmon.getattack())
        if(counter == 5) txtv7!!.setText(pokmon.gethp())

    }
}


