package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import javafx.scene.control.TreeItem

class Funcion (var nombreFuncion:Token, var tipoRetorno: Token, var listaParametros:ArrayList<Parametro>, var listaSentencias:ArrayList<Sentencia>) {

    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, listaSentencias=$listaSentencias)"
    }

    fun getArbolVisual(): TreeItem<String>{
        var raiz = TreeItem("Funcion ")

        raiz.children.add( TreeItem("Nombre: ${nombreFuncion.lexema}"))
        raiz.children.add( TreeItem("TipoRetorno: ${tipoRetorno.lexema}"))

        var raizParametros = TreeItem("Parametros: ")

        for(p in listaParametros){
            raizParametros.children.add (p.getArbolVisual())
        }
        raiz.children.add( raizParametros )

        var raizSentencias = TreeItem("Sentencias: ")

        for(p in listaSentencias){
            raizSentencias.children.add (p.getArbolVisual())
        }
        raiz.children.add( raizSentencias)

        return raiz
    }

}