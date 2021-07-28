package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
import javafx.scene.control.TreeItem
import java.lang.Error

class InvocacionFuncion(var nombreFuncion:Token, var listaArgumentos:ArrayList<Expresion>): Sentencia() {
    override fun toString(): String {
        return "InvocacionFuncion(nombreFuncion=$nombreFuncion, listaArgumentos=$listaArgumentos)"
    }

    override fun getArbolVisual(): TreeItem<String>{
        var raiz = TreeItem("InvocacionFuncion")

        raiz.children.add( TreeItem("NombreFuncion: ${nombreFuncion.lexema}"))

        var raizArgumentos = TreeItem("Argumentos: ")

        for(p in listaArgumentos){
            raizArgumentos.children.add (p.getArbolVisual())
        }
        raiz.children.add( raizArgumentos )

        return raiz
    }

    fun obtenerTiposArgumentos(tablaSimbolos: TablaSimbolos, ambito: String):ArrayList<String> {
        var listaArgs = ArrayList<String>()
        for (s in listaArgumentos) {
            listaArgs.add(s.obtenerTipo(tablaSimbolos, ambito))
        }
        return listaArgs
    }
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos,listaErrores: ArrayList<ErrorSemantico>, ambito: String){
        var listaTiposArgumetos= obtenerTiposArgumentos(tablaSimbolos, ambito)
        var s = tablaSimbolos.buscarSimboloFuncion(nombreFuncion.lexema, listaTiposArgumetos)
        if (s==null){
            listaErrores.add(ErrorSemantico("la funcion ${nombreFuncion.lexema} $listaTiposArgumetos no existe", nombreFuncion.fila, nombreFuncion.columna ))
        }
    }
}