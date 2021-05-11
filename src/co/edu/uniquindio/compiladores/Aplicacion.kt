package co.edu.uniquindio.compiladores

import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico

fun main(){
    val lexico = AnalizadorLexico(codigoFuente="int %sumar% {} " )
    lexico.analizar()
    println(lexico.listaTokens)
    println(lexico.listaErrores)

    val sintaxis = AnalizadorSintactico(lexico.listaTokens)
    print(sintaxis.esUnidadDeCompilacion())
    print( sintaxis.listaErrores)
}
