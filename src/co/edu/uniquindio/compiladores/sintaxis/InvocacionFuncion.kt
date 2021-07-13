package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import javafx.scene.control.TreeItem

class InvocacionFuncion(var nombreFuncion:Token, var listaArgumentos:ArrayList<Expresion>): Sentencia() {
    override fun toString(): String {
        return "InvocacionFuncion(nombreFuncion=$nombreFuncion, listaArgumentos=$listaArgumentos)"
    }

    override fun getArbolVisual(): TreeItem<String>{
        var raiz = TreeItem("InvocacionFuncion")

        raiz.children.add( TreeItem("NombreFuncion: ${nombreFuncion.lexema}"))

        var raizArgumentos = TreeItem("Argumentos: ")

        for(p in listaArgumentos){
            raizArgumentos.children.add (p.getArbolVisual())
        }
        raiz.children.add( raizArgumentos )

        return raiz
    }
}