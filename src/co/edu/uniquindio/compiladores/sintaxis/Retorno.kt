package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
import javafx.scene.control.TreeItem
import java.lang.Error

class Retorno (var expresion:Expresion) : Sentencia() {

    override fun toString(): String {
        return "Retorno(expresionLogica=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Retorno:")
        raiz.children.add(expresion.getArbolVisual())
        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: String) {
        expresion.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        /**
         * comprovar que el tipo de la expresion sea el mismo tipo de retorno de la funcion
         * a la que pertenece la sentencia retorno
         */
    }

    override fun getJavaCode(): String {
        return "return " + expresion.getJavaCode() + ";"
    }
}