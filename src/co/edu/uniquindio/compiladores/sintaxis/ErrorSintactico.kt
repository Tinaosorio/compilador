package co.edu.uniquindio.compiladores.sintaxis

class ErrorSintactico (var errorMsg:String, var fila:Int, var columna:Int) {
    override fun toString(): String {
        return "Error(errorMsg=$errorMsg, fila=$fila, columna=$columna)"
    }
}