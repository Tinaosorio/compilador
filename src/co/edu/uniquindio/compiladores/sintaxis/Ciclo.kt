package co.edu.uniquindio.compiladores.sintaxis

class Ciclo (var expresionLogica:ExpresionLogica,var litaSentencia: ArrayList<Sentencia>) : Sentencia() {

    override fun toString(): String {
        return "Ciclo(expresionLogica=$expresionLogica, litaSentencia=$litaSentencia)"
    }
}