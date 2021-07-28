package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Categoria
import co.edu.uniquindio.compiladores.Token


class Simbolo() {

    var nombre:String = ""
    var tipo:String = ""
    var mutable:Boolean = false
    var arreglo:Boolean = false
    var ambito:Any? = ""
    var fila:Int = 0
    var columna:Int = 0
    var tiposParametros:ArrayList<String>? = null

    /**
     * constructor para crear un simbolo de tipo valor
     */
    constructor(nombre:String, tipoDato:String, mutableIterable:Boolean, ambito:Any, fila:Int, columna:Int, arreglo: Boolean):this(){
        this.nombre = nombre
        this.tipo = tipoDato
        this.mutable = mutable
        this.ambito = ambito
        this.fila = fila
        this.columna = columna
        this.arreglo = arreglo
    }
    /**
     * constructor para crear un simbolo de metodo (funcion)
     */
    constructor(nombre: String, tipoDato: String, tiposParametro: ArrayList<String>, ambito: Any? ):this(){
        this.nombre = nombre
        this.tipo = tipoDato
        this.tiposParametros = tiposParametros
        this.ambito = ambito
    }

    override fun toString(): String{
        return if(tiposParametros== null){
             "Simbolo(nombre='$nombre',tipo='$tipo', modificable=$mutable, ambito=$ambito, fila=$fila, columa=$columna)"
        }else{
             "Simbolo(nomre='$nombre', tipo='$tipo', ambito=$ambito, tiposParametros=$tiposParametros"
        }

    }

}