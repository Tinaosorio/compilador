package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Decision (var expresionLogica: ExpresionLogica, var listaSentencia:ArrayList<Sentencia>, var listaSentenciaElse:ArrayList<Sentencia>?) : Sentencia(){
     override fun toString(): String {
        return "Decision(expresionLogica=$expresionLogica, listaSentencia=$listaSentencia, listaSentenciaElse=$listaSentenciaElse)"
    }
    override fun getArbolVisual(): TreeItem<String>{

        var raiz = TreeItem("Decision")
        raiz.children.add(expresionLogica.getArbolVisual())

        var raizTrue = TreeItem("Sentencias verdaderas")
        for( s in listaSentencia){
            raizTrue.children.add( s.getArbolVisual())
        }
        raiz.children.add(raizTrue)

        if (listaSentenciaElse!=null){
            var raizFalse = TreeItem("Sentencias Falsas")
            for( s in listaSentenciaElse !!){
                raizFalse.children.add( s.getArbolVisual())
            }
            raiz.children.add(raizFalse)
        }

        return raiz
    }
}