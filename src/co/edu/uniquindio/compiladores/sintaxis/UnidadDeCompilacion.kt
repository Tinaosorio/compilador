package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
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

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores:ArrayList<ErrorSemantico>){
        for (f in listaFunciones){
            f.llenarTablaSimbolos(tablaSimbolos, listaErrores, "unidadcompilacion")
        }
    }
    fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores:ArrayList<ErrorSemantico>){
        var funcionesUnicas = ArrayList<String>()
        for( f in listaFunciones){
            val nombreFuncion = f.nombreFuncion.lexema
            f.analizarSemantica(tablaSimbolos, listaErrores)
            if(nombreFuncion in funcionesUnicas){
                listaErrores.add(ErrorSemantico(
                    "nombre de funcion ${nombreFuncion} en uso",
                    f.nombreFuncion.fila,
                    f.nombreFuncion.columna)
                )
            } else {
                funcionesUnicas.add(nombreFuncion)
            }
        }
    }

    fun getJavaCode():String{
        var codigo = "import javax.swing.JOptionPane;\npublic class Principal{\n\t"
        for (f in listaFunciones) {
            codigo += f.getJavaCode()
        }
        codigo += "\n}"
        return codigo
    }

}