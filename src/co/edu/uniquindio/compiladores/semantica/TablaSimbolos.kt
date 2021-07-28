package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Categoria
import co.edu.uniquindio.compiladores.Token
import co.edu.uniquindio.compiladores.semantica.ErrorSemantico

class TablaSimbolos(var listaErrores: ArrayList<ErrorSemantico>) {

    var listaSimbolos: ArrayList<Simbolo> = ArrayList()
    /**
     * permite guardar un sombolo que represente una variable dentreo de un arreglo
     */

    fun guardarSimboloValor(nombre: String, tipoDato: String, mutable: Boolean, ambito: Any, fila: Int, columna: Int,arreglo: Boolean = false ){
        val s = buscarSimboloValor(nombre, ambito, fila, columna)
        if (s==null){
            listaSimbolos.add(Simbolo(nombre, tipoDato, mutable, ambito, fila, columna, arreglo))
        }else{
            listaErrores.add(ErrorSemantico("El campo $nombre ya existe dentro del ambito $ambito", fila, columna))
        }
    }

    /**
     * permite guardar un simbolo que representa una funcion (o metodo)
     */
    fun guardarSimboloFuncion(nombre: String, tipoRetorno: String, tiposParametros:ArrayList<String>, ambito: Any?, fila: Int,columna: Int){
        val S = buscarSimboloFuncion(nombre, tiposParametros)
        if (S== null){
            listaSimbolos.add(Simbolo(nombre, tipoRetorno, tiposParametros, ambito))
        }else{
            listaErrores.add(ErrorSemantico("la funcion $nombre ya existe dentro del ambito  $ambito", fila, columna))
        }
    }

    /**
     * permite buscar  un valor dentro de las tablas de sumbolos
     */
    fun buscarSimboloValor(nombre: String, ambito: Any, fila: Int, columna: Int): Simbolo? {
        for( s in listaSimbolos) {
            if (s.tiposParametros==null && s.nombre == nombre){
                if (s.ambito == ambito){
                    if (ambito is UnidadDeCompilacion || existe(s, fila, columna)){
                        return s
                    }
                }else {
                    val simbolo = when(ambito){
                        is Sentencia -> buscarSimboloValor(nombre, ambito.contexto, fila, columna)
                        is Funcion -> buscarSimboloValor(nombre, ambito.contexto,fila, columna)
                        else -> null
                    }
                    if (simbolo != null){
                        return simbolo
                    }
                }
            }
        }
        return null
    }


    /**
     * verifica a travez de su posicion si un campo fue declarado antes de usarse
     */
    private fun existe(s:Simbolo, filaCampo:Int, columnaCampo:Int):Boolean{
        if(s.fila <= filaCampo){
            if (s.fila == filaCampo){
                if (s.columna < columnaCampo){
                    return true
                }
            }else{
                return true
            }
        }
        return false
    }



        /**
         * permite buscar una funcion dentro de la tabla de simbolos
         */
        fun buscarSimboloFuncion(nombre: String, tiposParametros:ArrayList<String>):Simbolo?{
            for (s in listaSimbolos){
                if (s.tiposParametros != null){
                    if (s.nombre == nombre && s.tiposParametros == tiposParametros){
                        return s
                    }
                }
            }
            return null
        }


    override fun toString(): String {
        return "TablaSimbolo(listaSimbolo=$listaSimbolos)"
    }

}