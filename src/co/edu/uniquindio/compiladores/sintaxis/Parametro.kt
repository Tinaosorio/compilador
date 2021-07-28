package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import javafx.scene.control.TreeItem

class Parametro (var nombre:Token, var tipoDato:Token) {

    override fun toString(): String {
        return "Parametro(nombre=$nombre, tipoDato=$tipoDato)"
    }

    fun getArbolVisual(): TreeItem<String>{
        var raiz = TreeItem("Parametro ")

        raiz.children.add( TreeItem("Nombre: ${nombre.lexema}"))
        raiz.children.add( TreeItem("TipoDato: ${tipoDato.lexema}"))

        return raiz
    }

    fun getJavaCode() :String{
        return tipoDato.getJavaCode()+" "+nombre.getJavaCode()
    }

}