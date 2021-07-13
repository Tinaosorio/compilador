package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import javafx.scene.control.TreeItem

class Variable(var nombreVariable: Token, var expresion:Expresion?) : Sentencia() {

    override fun toString(): String {
        return "Variable(nombreVariable=$nombreVariable, expresion=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Variable:")
        raiz.children.add(TreeItem("Variable: ${nombreVariable.lexema}"))
        raiz.children.add(expresion!!.getArbolVisual())

        return raiz
    }

}