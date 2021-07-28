package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
import javafx.scene.control.TreeItem
import java.lang.Error


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

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: String) {
        tablaSimbolos.guardarSimboloValor(variable.lexema, tipoDato.lexema, true, ambito, variable.fila, variable.columna )
    }


    override fun getJavaCode(): String {
        var codigo = tipoDato.getJavaCode()
        codigo += " "+ variable.getJavaCode() + ";"
        return codigo
    }
}