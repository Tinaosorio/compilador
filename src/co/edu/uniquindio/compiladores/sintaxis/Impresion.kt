package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
import javafx.scene.control.TreeItem
import java.lang.Error

class Impresion(var expresion:Expresion) : Sentencia() {

    override fun toString(): String {
        return "Impresion(expresionLogica=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Impresion:")
        raiz.children.add(expresion.getArbolVisual())

        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: String) {
        expresion.analizarSemantica(tablaSimbolos, listaErrores, ambito)
    }
    override fun getJavaCode():String{
        return "JOptionPane.showMessageDialog(null,"+expresion.getJavaCode()+");"
    }
}