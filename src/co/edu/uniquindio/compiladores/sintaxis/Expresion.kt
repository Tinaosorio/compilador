package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import javafx.scene.control.TreeItem

open class Expresion: Sentencia() {

   override fun getArbolVisual(): TreeItem<String>{
        return TreeItem("Expresion ")
    }

}