package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import javafx.scene.control.TreeItem

class ExpresionLogica() : Expresion() {
    var expresionRelacional1:ExpresionRelacional? = null
    var expresionRelacional2:ExpresionRelacional? = null
    var operador: Token? = null


    constructor(expresionRelacional1: ExpresionRelacional?, operador: Token?, expresionRelacional2: ExpresionRelacional?):this(){
        this.expresionRelacional1= expresionRelacional1
        this.operador = operador
        this.expresionRelacional2= expresionRelacional2
    }
    constructor(expresionRelacional: ExpresionRelacional?): this(){
        this.expresionRelacional1 = expresionRelacional
    }

    override fun getArbolVisual(): TreeItem<String> {
        val raiz = TreeItem("Expresión Logica")
        if( expresionRelacional1!=null && operador!=null && expresionRelacional2!=null ){
            raiz.children.add( expresionRelacional1!!.getArbolVisual() )
            raiz.children.add( TreeItem( "Operador: ${operador!!.lexema}" ) )
            raiz.children.add( expresionRelacional2!!.getArbolVisual() )
        }else{
            raiz.children.add(expresionRelacional1!!.getArbolVisual())
        }
        return raiz
    }

    override fun getJavaCode(): String {
        if(expresionRelacional1!=null && expresionRelacional2!=null){
            return expresionRelacional1!!.getJavaCode()+operador!!.getJavaCode()+expresionRelacional2!!.getJavaCode()
        }else if(operador!=null && expresionRelacional1!=null && expresionRelacional2==null){
            return  operador!!.getJavaCode()+expresionRelacional1!!.getJavaCode()
        }else{
            return expresionRelacional1!!.getJavaCode()
        }
    }
}