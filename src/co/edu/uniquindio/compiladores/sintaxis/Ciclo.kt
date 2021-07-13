package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Ciclo (var expresionLogica:ExpresionLogica,var listaSentencias: ArrayList<Sentencia>) : Sentencia() {

    override fun toString(): String {
        return "Ciclo(expresionLogica=$expresionLogica, listaSentencia=$listaSentencias)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Ciclo:")
        raiz.children.add(expresionLogica.getArbolVisual())
        var raizSentencias = TreeItem("Sentencias")

        for(sentencia in listaSentencias) {
            raizSentencias.children.add(sentencia.getArbolVisual())
        }
        raiz.children.add(raizSentencias)
        return raiz
    }
}