package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token

class IncrementoDecremento (var identificador:Token, var operador:Token) : Sentencia() {

    override fun toString(): String {
        return "IncrementoDecremento(identificador=$identificador, operador=$operador)"
    }
}