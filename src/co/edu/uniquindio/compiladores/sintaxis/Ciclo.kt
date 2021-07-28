package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
import javafx.scene.control.TreeItem

class Ciclo (var expresionLogica:ExpresionLogica,var listaSentencias: ArrayList<Sentencia>) : Sentencia() {

    override fun toString(): String {
        return "Ciclo(expresionLogica=$expresionLogica, listaSentencia=$listaSentencias)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Ciclo:")
        raiz.children.add(expresionLogica.getArbolVisual())
        var raizSentencias = TreeItem("Sentencias")

        for(sentencia in listaSentencias) {
            raizSentencias.children.add(sentencia.getArbolVisual())
        }
        raiz.children.add(raizSentencias)
        return raiz
    }


    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: String) {
        for (s in listaSentencias ){
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
        }
    }
    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: String) {
        expresionLogica.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        for (s in listaSentencias ){
            s.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun getJavaCode(): String{
        var codigo = "while("+expresionLogica.getJavaCode()+"){"
        for( s in listaSentencias ) {
            codigo += s.getJavaCode()
        }
        codigo += "}"
        return codigo
    }
}