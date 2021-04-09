package co.edu.uniquindio.compiladores

class Error (var lexema:String, var errorMsg:String, var fila:Int, var columna:Int) {
    override fun toString(): String {
        return "Error(lexema='$lexema', errorMsg=$errorMsg, fila=$fila, columna=$columna)"
    }
}