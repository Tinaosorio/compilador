package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
import javafx.scene.control.TreeItem
import java.lang.Error

class Lectura (var mensaje: Token) : Sentencia() {

    override fun toString(): String {
        return "Lectura(mensaje=$mensaje)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Lectura:")
        raiz.children.add(TreeItem("Mensaje: ${mensaje.lexema}"))

        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: String) {

    }
    override fun getJavaCode():String{
        return ""
    }
}