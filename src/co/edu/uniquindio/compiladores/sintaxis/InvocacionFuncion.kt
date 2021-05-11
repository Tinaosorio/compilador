package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token

class InvocacionFuncion(var nombreFuncion:Token, var listaArgumentos:ArrayList<Expresion >): Sentencia() {
}