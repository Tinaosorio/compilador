package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class UnidadDeCompilacion(var listaFunciones:ArrayList<Funcion>){

    override fun toString(): String {
        return "UnidadDeCompilacion(ListaFunciones=$listaFunciones)"
    }
    fun getArbolVisual():TreeItem<String>{
        var raiz = TreeItem("unidad de compilacion")
        for (f in listaFunciones){
            raiz.children.add(f.getArbolVisual())
        }
        return raiz
    }
}