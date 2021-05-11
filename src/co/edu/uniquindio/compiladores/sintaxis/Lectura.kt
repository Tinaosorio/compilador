package co.edu.uniquindio.compiladores.sintaxis

class Lectura (var expresionLogica:ExpresionLogica, var litaSentencia: ArrayList<Sentencia>) : Sentencia() {

    override fun toString(): String {
        return "Lectura(expresionLogica=$expresionLogica, litaSentencia=$litaSentencia)"
    }
}