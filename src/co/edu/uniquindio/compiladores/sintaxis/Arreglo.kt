package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
import javafx.scene.control.TreeItem

class Arreglo (var nombre:Token, var tipoDato: Token, var listaExpresiones: ArrayList<Expresion> ): Sentencia() {


    override fun getArbolVisual(): TreeItem<String>{
        var raiz = TreeItem("Arreglo ")

        raiz.children.add( TreeItem("Nombre: ${nombre.lexema}"))
        raiz.children.add( TreeItem("TipoDato: ${tipoDato.lexema}"))

        var raizExpresiones = TreeItem("Expresiones: ")

        for(p in listaExpresiones){
            raizExpresiones.children.add (p.getArbolVisual())
        }
        raiz.children.add( raizExpresiones )

        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: String) {
       tablaSimbolos.guardarSimboloValor(nombre.lexema, tipoDato.lexema, true, ambito, nombre.fila, nombre.columna)
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: String) {

        for (e in listaExpresiones){
            e!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            var tipo = e.obtenerTipo(tablaSimbolos, ambito)
            if( tipo != tipoDato.lexema ) {
                listaErrores.add( ErrorSemantico( "El tipo de dato de la expresion ($tipo) no coincide con el tipo de dato", tipoDato.fila, tipoDato.columna))
            }
        }
    }

    override fun getJavaCode(): String {
        var codigo = tipoDato.getJavaCode()+"[] "+nombre.getJavaCode();
        if (listaExpresiones!=null){
            codigo += "{"
            for( e in listaExpresiones ){
                codigo += e.getJavaCode()+","
            }
            codigo = codigo.substring(0, codigo.length-1)
            codigo += "}"
        }
        codigo += ";"
        return codigo
    }
}