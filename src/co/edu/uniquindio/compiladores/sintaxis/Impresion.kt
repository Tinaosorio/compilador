package co.edu.uniquindio.compiladores.sintaxis

class Impresion (var expresionLogica:ExpresionLogica, var litaSentencia: ArrayList<Sentencia>) : Sentencia() {

    override fun toString(): String {
        return "Impresion(expresionLogica=$expresionLogica, litaSentencia=$litaSentencia)"
    }
}