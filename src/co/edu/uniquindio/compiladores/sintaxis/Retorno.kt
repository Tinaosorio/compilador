package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Retorno (var expresion:Expresion) : Sentencia() {

    override fun toString(): String {
        return "Retorno(expresionLogica=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Retorno:")
        raiz.children.add(expresion.getArbolVisual())
        return raiz
    }
}