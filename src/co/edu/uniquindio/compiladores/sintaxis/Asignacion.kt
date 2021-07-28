package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Token
import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
import javafx.scene.control.TreeItem


class Asignacion (var identificador: Token, var operadorAsignacion: Token, var expresion: Sentencia) : Sentencia() {

    override fun toString(): String {
        return "Asignacion(identificador=$identificador, operadorAsignacion=$operadorAsignacion, expresion=$expresion)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Asignacion:")

        raiz.children.add( TreeItem("Identificador: ${identificador.lexema}"))
        raiz.children.add( TreeItem("OperadorAsignacion: ${operadorAsignacion.lexema}"))
        raiz.children.add(expresion.getArbolVisual())

        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<ErrorSemantico>, ambito: String) {
        var s = tablaSimbolos.buscarSimboloValor(identificador.lexema, ambito, identificador.fila, identificador.columna)
        if (s==null){
            listaErrores.add(ErrorSemantico("el campo(${identificador.lexema}) no existe dentro del ambito ($ambito)", identificador.fila, identificador.columna ))
        }else{
            var tipo = s.tipo
            if(expresion!=null){
                expresion!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
                var tipoExp= expresion!!.obtenerTipo(tablaSimbolos, ambito)
                if (tipoExp != tipo) {
                    listaErrores.add(
                        ErrorSemantico(
                            "no se puede asignar un valor tipo ($tipoExp) al campo (${identificador.lexema})",
                            identificador.fila,
                            identificador.columna
                        )
                    )
                }
/*                }else if (tipoExp != null){
                    tipoExp!!.analizarSemantica(tablaSimbolos,listaErrores,ambito)
                    //comprobar tipos
                }*/
            }
        }
    }

    override fun getJavaCode(): String {
        var codigo = "${identificador.getJavaCode()} ${operadorAsignacion.getJavaCode()} ${expresion.getJavaCode()};"
        return codigo
    }
}