package aldwin.tablante.com.pokemonsearch

/**
 * Created by Bobby on 12/03/2018.
 */
class Pokemon(speed:String,sa:String,sd:String,defense:String,attack:String,hp:String) {


var speed : String = speed
    var sa : String = sa
    var sd:String= sd
    var defense:String= defense
    var attack:String= attack
    var hp:String = hp
    var imageurl = ""
    var imageurlback = ""
    var name = ""
    var url = ""
    fun setspeed(speed:String){

        this.speed = speed

    }
    fun setsd(sd:String){

        this.sd = sd

    }

    fun setdefense(defense:String){

        this.defense = defense

    }

    fun setattack(attack:String){

        this.attack = attack

    }

    fun sethp(hp:String){

        this.hp = hp

    }

    fun setsa(sa:String){

        this.sa = sa

    }


    fun getspeed():String{

        return this.speed

    }
    fun getsa():String{

        return this.sa

    }

    fun getsd():String{

        return this.sd

    }
    fun getdefense():String{

        return this.defense

    }
    fun getattack():String{

        return this.attack

    }
    fun gethp():String{

        return this.hp

    }



}