package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import javafx.scene.control.TreeItem

class Arreglo (var nombre:Token, var tipoDato: Token, var listaExpresion: ArrayList<Expresion> ): Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        return super.getArbolVisual()
    }
}