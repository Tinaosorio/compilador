package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import javafx.scene.control.TreeItem


class DeclaracionVariable(var tipoDato: Token, var variable: Token): Sentencia() {
    override fun toString(): String {
        return "DeclaracionVariable(tipoDato=$tipoDato, variable=$variable)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("DeclaracionVariable:")
        raiz.children.add(TreeItem("Tipo de dato: ${tipoDato.lexema}"))
        raiz.children.add(TreeItem("Variable: ${variable.lexema}"))
        return raiz
    }
}