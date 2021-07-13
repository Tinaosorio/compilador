package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import javafx.scene.control.TreeItem

class Lectura (var mensaje: Token) : Sentencia() {

    override fun toString(): String {
        return "Lectura(mensaje=$mensaje)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Lectura:")
        raiz.children.add(TreeItem("Mensaje: ${mensaje.lexema}"))

        return raiz
    }
}