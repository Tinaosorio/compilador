package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
import javafx.scene.control.TreeItem

class Decision (var expresionLogica: ExpresionLogica, var listaSentencia:ArrayList<Sentencia>, var listaSentenciaElse:ArrayList<Sentencia>?) : Sentencia() {
    override fun toString(): String {
        return "Decision(expresionLogica=$expresionLogica, listaSentencia=$listaSentencia, listaSentenciaElse=$listaSentenciaElse)"
    }

    override fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Decision")
        raiz.children.add(expresionLogica.getArbolVisual())

        var raizTrue = TreeItem("Sentencias verdaderas")
        for (s in listaSentencia) {
            raizTrue.children.add(s.getArbolVisual())
        }
        raiz.children.add(raizTrue)

        if (listaSentenciaElse != null) {
            var raizFalse = TreeItem("Sentencias Falsas")
            for (s in listaSentenciaElse!!) {
                raizFalse.children.add(s.getArbolVisual())
            }
            raiz.children.add(raizFalse)
        }

        return raiz
    }

    /**
    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: String) {
    for (s in listaSentencia){
    s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
    }
    if (listaSentenciaElse != null){
    for (s in listaSentenciaElse!!){
    s.llenarTablaSimbolos(tablaSimbolos, listaErrores,ambito)
    }
    }
    }
     **/

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: String) {
        if (expresionLogica != null) {
            expresionLogica!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
        for (s in listaSentencia) {
            s.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
        if (listaSentenciaElse != null) {
            for (s in listaSentenciaElse!!) {
                s.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            }
        }
    }

    override fun getJavaCode(): String {
        var codigo = "if ("+expresionLogica!!.getJavaCode()+"){"
        for( s in listaSentencia){
            codigo += s.getJavaCode()
        }
        codigo += "}"
        if (listaSentenciaElse != null) {
            codigo += "else{"
            for( s in listaSentencia){
                codigo += s.getJavaCode()
            }
            codigo += "}"
        }
        return codigo
    }
}