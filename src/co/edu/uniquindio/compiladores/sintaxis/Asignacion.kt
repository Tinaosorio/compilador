package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import javafx.scene.control.TreeItem

class Asignacion (var identificador: Token, var operadorAsignacion: Token, var expresion: Sentencia) : Sentencia() {

    override fun toString(): String {
        return "Asignacion(identificador=$identificador, operadorAsignacion=$operadorAsignacion, expresion=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Asignacion:")

        raiz.children.add( TreeItem("Identificador: ${identificador.lexema}"))
        raiz.children.add( TreeItem("OperadorAsignacion: ${operadorAsignacion.lexema}"))
        raiz.children.add(expresion.getArbolVisual())

        return raiz
    }

}