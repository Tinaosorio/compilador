package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
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
}