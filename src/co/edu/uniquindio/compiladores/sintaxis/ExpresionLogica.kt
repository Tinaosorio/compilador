package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class ExpresionLogica : Expresion() {

    override open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Expresion Logica")
    }
}