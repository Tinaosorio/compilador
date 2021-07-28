package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Categoria
import co.edu.uniquindio.compiladores.Token
import co.edu.uniquindio.compiladores.semantica.ErrorSemantico

class AnalizadorSemantico{

    private lateinit var unidadDeCompilacion: UnidadDeCompilacion
    val listaErrores: ArrayList<ErrorSemantico> = ArrayList()
    val tablaSimbolo: TablaSimbolos = TablaSimbolos(listaErrores)

    /**
     * se encarga de conocer el arbol sintactico para llenar la tabla con simbolos de declaracin de variables
     * constantes parametros arreglos y funciones
     */
    fun llenarTablaSimbolos() {
        unidadDeCompilacion.llenarTablaSimbolos(tablaSimbolo, listaErrores)
    }

    /**
     * una ves llenada la tabla esta funcion permite analizar la sentencia del
     * codigo fuente
     */
    fun analizarSemantica() {
        unidadDeCompilacion.analizarSemantica(tablaSimbolo, listaErrores)
    }

    fun inicializar(unidadDeCompilacion: UnidadDeCompilacion) {
        this.unidadDeCompilacion = unidadDeCompilacion
        listaErrores.clear()
        tablaSimbolo.listaSimbolos.clear()
    }

}
