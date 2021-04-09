package co.edu.uniquindio.compiladores

fun main(){
    val lexico = AnalizadorLexico(codigoFuente="a\$b" )
    lexico.analizar()
    println(lexico.listaTokens)
    println(lexico.listaErrores)
}
