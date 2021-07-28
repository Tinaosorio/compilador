package co.edu.uniquindio.compiladores

import co.edu.uniquindio.compiladores.sintaxis.Simbolo

fun main(){
    val lexico = AnalizadorLexico(codigoFuente="int %sumar% {} " )
    lexico.analizar()
    println(lexico.listaTokens)
    println(lexico.listaErrores)
/**
    val sintaxis = Simbolo(lexico.listaTokens)
    print(sintaxis.esUnidadDeCompilacion())
    print( sintaxis.listaErrores)
    **/
}
