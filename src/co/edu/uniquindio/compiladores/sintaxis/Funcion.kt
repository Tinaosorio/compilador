package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
import javafx.scene.control.TreeItem

class Funcion (var nombreFuncion:Token, var tipoRetorno: Token, var listaParametros:ArrayList<Parametro>, var listaSentencias:ArrayList<Sentencia>) {

    lateinit var contexto: UnidadDeCompilacion
    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, listaSentencias=$listaSentencias)"
    }

    fun getArbolVisual(): TreeItem<String>{
        var raiz = TreeItem("Funcion ")

        raiz.children.add( TreeItem("Nombre: ${nombreFuncion.lexema}"))
        raiz.children.add( TreeItem("TipoRetorno: ${tipoRetorno.lexema}"))

        var raizParametros = TreeItem("Parametros: ")

        for(p in listaParametros){
            raizParametros.children.add (p.getArbolVisual())
        }
        raiz.children.add( raizParametros )

        var raizSentencias = TreeItem("Sentencias: ")

        for(p in listaSentencias){
            raizSentencias.children.add (p.getArbolVisual())
        }
        raiz.children.add( raizSentencias)

        return raiz
    }

    fun obtenerTiposParametros():ArrayList<String>{
        var lista = ArrayList<String>()
        for ( p in listaParametros){
            lista.add(p.tipoDato.lexema)
        }
        return lista
    }

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores:ArrayList<ErrorSemantico>, ambito:String){
        tablaSimbolos.guardarSimboloFuncion(nombreFuncion.lexema, tipoRetorno.lexema, obtenerTiposParametros(), ambito, nombreFuncion.fila, nombreFuncion.columna)

        for (p in listaParametros){
            tablaSimbolos.guardarSimboloValor(p.nombre.lexema, p.tipoDato.lexema, true,ambito, p.nombre.fila, p.nombre.columna)
        }
        for(s in listaSentencias){
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, nombreFuncion.lexema)
        }
    }

    fun analizarSemantica(tablaSimbolos:TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>){
        for (s in listaSentencias){
            s.analizarSemantica(tablaSimbolos, listaErrores,nombreFuncion.lexema)
        }
    }

    fun getJavaCode() :String {
        var codigo = ""
        //main de Java
        if (nombreFuncion.lexema == "%principal%") {
            codigo = "public static void main(String[] args){"
        } else {
            codigo = "static " + tipoRetorno.getJavaCode() + " " + nombreFuncion.getJavaCode() + "("
            if (listaParametros.isNotEmpty()) {
                for (p in listaParametros) {
                    codigo += p.getJavaCode() + ","
                }
                codigo = codigo.substring(0, codigo.length - 1)
            }
            codigo += "){\n"
        }
        var i = 0
        for (s in listaSentencias) {
            codigo += if(i >= 1) {
                s.getJavaCode() + "\n"
            } else {
                "\t\t" + s.getJavaCode() + "\n"
            }
            i+=1

        }
        codigo += "\n\t}"
        return codigo
    }

}
