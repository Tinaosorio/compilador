package co.edu.uniquindio.compiladores.sintaxis

class Retorno (var expresionLogica:ExpresionLogica, var litaSentencia: ArrayList<Sentencia>) : Sentencia() {

    override fun toString(): String {
        return "Retorno(expresionLogica=$expresionLogica, litaSentencia=$litaSentencia)"
    }
}