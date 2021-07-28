package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import javafx.scene.control.TreeItem

class ExpresionAritmetica() : Expresion() {
    var expresionAritmetica1:ExpresionAritmetica? = null
    var expresionAritmetica2:ExpresionAritmetica? = null
    var operador: Token? = null
    var valorNumerico: ValorNumerico? = null

    constructor( expresionAritmetica1: ExpresionAritmetica?, operador:Token?, expresionAritmetica2: ExpresionAritmetica?):this(){
        this.expresionAritmetica1= expresionAritmetica1
        this.operador = operador
        this.expresionAritmetica2= expresionAritmetica2
    }
    constructor(expresionAritmetica1: ExpresionAritmetica?): this(){
        this.expresionAritmetica1 = expresionAritmetica1
    }
    constructor( valorNumerico: ValorNumerico?, operador: Token?, expresionAritmetica2: ExpresionAritmetica?): this(){
        this.valorNumerico = valorNumerico
        this.operador = operador
        this.expresionAritmetica2 = expresionAritmetica2
    }
    constructor(valorNumerico:ValorNumerico?): this(){
        this.valorNumerico = valorNumerico
    }

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem("Expresión Aritmética")
        if( expresionAritmetica1!=null && operador!=null && expresionAritmetica2!=null ){
            raiz.children.add( expresionAritmetica1!!.getArbolVisual() )
            raiz.children.add( TreeItem( "Operador: ${operador!!.lexema}" ) )
            raiz.children.add( expresionAritmetica2!!.getArbolVisual() )
        }else if( expresionAritmetica1!=null && operador==null && expresionAritmetica2==null && valorNumerico==null ){
            raiz.children.add( expresionAritmetica1!!.getArbolVisual() )
        }else if( valorNumerico!=null && operador!=null && expresionAritmetica2!=null ){
            raiz.children.add( valorNumerico!!.getArbolVisual() )
            raiz.children.add( TreeItem("Operador: ${operador!!.lexema}" ) )
            raiz.children.add( expresionAritmetica2!!.getArbolVisual() )

        }else{
            raiz.children.add( valorNumerico!!.getArbolVisual() )
        }
        return raiz
    }

    override fun toString(): String {
        return "ExpresionAritmetica(expresionAritmetica1=$expresionAritmetica1, expresionAritmetica2=$expresionAritmetica2, operador=$operador, valorNumerico=$valorNumerico)"
    }

    override fun getJavaCode(): String {
        if( expresionAritmetica1!=null && operador!=null && expresionAritmetica2!=null){
            return "("+expresionAritmetica1!!.getJavaCode()+")"+operador+ "" + expresionAritmetica2!!.getJavaCode()
        }else if( expresionAritmetica1!=null && operador==null && expresionAritmetica2==null && valorNumerico==null ){
            return "("+expresionAritmetica1!!.getJavaCode()+")"
        }else if( valorNumerico!=null && operador!=null && expresionAritmetica2!=null){
            return valorNumerico!!.getJavaCode() + operador!!.getJavaCode() + expresionAritmetica2!!.getJavaCode()
        }else{
            return  valorNumerico!!.getJavaCode()
        }

    }
}