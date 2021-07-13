package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import javafx.scene.control.TreeItem

class ValorNumerico(var signo: Token?, var termino: Token){
    override fun toString(): String {
        return "ValorNumerico(signo=$signo, termino=$termino)"
    }

    fun getArbolVisual(): TreeItem<String>{
        var raiz = TreeItem("ValorNumerico ")

        if(signo != null){
            raiz.children.add( TreeItem("signo: ${signo?.lexema}"))
        }
        raiz.children.add( TreeItem("termino: ${termino.lexema}"))

        return raiz
    }

}
