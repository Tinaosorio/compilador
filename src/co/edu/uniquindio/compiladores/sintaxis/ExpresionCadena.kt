package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
import javafx.scene.control.TreeItem

class ExpresionCadena() : Expresion() {
    var cadena: Token? = null
    var expresion: Expresion? = null
    var identificador: Token? = null

    constructor(cadena: Token, expresion: Expresion?): this(){
        this.cadena = cadena
        this.expresion = expresion
    }
    constructor(identificador: Token): this(){
        this.identificador = identificador
    }

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem("Expresión Cadena")
        if( cadena!=null && expresion!=null){
            raiz.children.add( TreeItem( "Cadena: ${cadena!!.lexema}" ) )
            raiz.children.add( expresion!!.getArbolVisual() )
        }else if(cadena != null) {
            raiz.children.add( TreeItem( "Cadena: ${cadena!!.lexema}" ) )
        } else {
            raiz.children.add( TreeItem( "Identificador: ${identificador!!.lexema}" ))
        }

        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String):String{
        return "string"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: String){
        if(expresion!=null){
            expresion!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun getJavaCode(): String {
        var codigo = ""
        if (cadena != null && expresion != null) {
            codigo += cadena!!.getJavaCode() + "+" + expresion!!.getJavaCode()
        } else if (cadena != null) {
            codigo += cadena!!.getJavaCode()
        } else {
            codigo += identificador!!.getJavaCode()
        }
        return codigo
    }

}