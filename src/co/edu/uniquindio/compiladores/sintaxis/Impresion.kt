package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Impresion(var expresion:Expresion) : Sentencia() {

    override fun toString(): String {
        return "Impresion(expresionLogica=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Impresion:")
        raiz.children.add(expresion.getArbolVisual())

        return raiz
    }
}