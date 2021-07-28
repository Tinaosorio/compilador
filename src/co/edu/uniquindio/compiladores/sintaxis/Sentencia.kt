package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
import javafx.scene.control.TreeItem

open class Sentencia() {
  lateinit var contexto: Funcion
  open fun getArbolVisual(): TreeItem<String>{
   return TreeItem("sentencia")
  }
    open fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores:ArrayList<ErrorSemantico>, ambito:String){

    }
    open fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: String){

    }

    open fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String):String{
      return "string"
    }

    open fun getJavaCode():String{
      return ""
    }

}