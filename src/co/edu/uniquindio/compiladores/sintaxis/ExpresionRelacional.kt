package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import javafx.scene.control.TreeItem

class ExpresionRelacional() : Expresion() {
    var expresionAritmetica1:ExpresionAritmetica? = null
    var expresionAritmetica2:ExpresionAritmetica? = null
    var operador: Token? = null
    var valorVerdad: Token? = null

    constructor(expresionAritmetica1: ExpresionAritmetica?, operador: Token?, expresionAritmetica2: ExpresionAritmetica?):this(){
        this.expresionAritmetica1= expresionAritmetica1
        this.operador = operador
        this.expresionAritmetica2= expresionAritmetica2
    }

    constructor(valorVerdad:Token?): this(){
        this.valorVerdad = valorVerdad
    }

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem("Expresión Relacional")
        if( expresionAritmetica1!=null && operador!=null && expresionAritmetica2!=null ){
            raiz.children.add( expresionAritmetica1!!.getArbolVisual() )
            raiz.children.add( TreeItem( "Operador: ${operador!!.lexema}" ) )
            raiz.children.add( expresionAritmetica2!!.getArbolVisual() )
        }else{
            raiz.children.add( TreeItem( "ValorVerdad: ${valorVerdad!!.lexema}" ))
        }
        return raiz
    }

    override fun toString(): String {
        return "ExpresionRelacional(expresionAritmetica1=$expresionAritmetica1, expresionAritmetica2=$expresionAritmetica2, operador=$operador, valorVerdad=$valorVerdad)"
    }
    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String): String {
        return "bool"
    }

    override fun getJavaCode(): String {
        if(expresionAritmetica1!=null && expresionAritmetica2!=null){
            return expresionAritmetica1!!.getJavaCode()+operador!!.getJavaCode()+expresionAritmetica2!!.getJavaCode()
        }else{
            return valorVerdad!!.getJavaCode()
        }
    }
}