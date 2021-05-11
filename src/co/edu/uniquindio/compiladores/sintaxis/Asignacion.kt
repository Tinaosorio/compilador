package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token

class Asignacion (var identificador:Token, var operadorAsignacion: Token, var expresion: Sentencia) : Sentencia() {
}