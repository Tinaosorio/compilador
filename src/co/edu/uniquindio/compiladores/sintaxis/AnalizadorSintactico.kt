package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.Categoria
import co.edu.uniquindio.compiladores.Token

class AnalizadorSintactico(var listaTokens:ArrayList<Token>) {
    var posicionActual = 0
    var tokenActual = listaTokens[0]
    var listaErrores = ArrayList<ErrorSintactico>()

    /**
     * controla el avance del analisis de los token
     */
    fun obtenerSiguienteToken() {
        posicionActual++
        if (posicionActual < listaTokens.size) {
            tokenActual = listaTokens[posicionActual]
        }
    }

    /**
     * analisa los posibles errores sintacticos
     */
    fun reportarError(mensaje: String) {
        listaErrores.add(ErrorSintactico(mensaje, tokenActual.fila, tokenActual.columna))

    }

    fun hacerBacktraking(posInicial: Int) {
        posicionActual = posInicial
        tokenActual = listaTokens[posicionActual]
    }

    /**
     * <UnidadDeCompilacion> ::= <ListaFunciones>
     */
    fun esUnidadDeCompilacion(): UnidadDeCompilacion? {
        val listaFunciones: ArrayList<Funcion> = esListaFunciones()
        return if (listaFunciones.size > 0) {
            UnidadDeCompilacion(listaFunciones)
        } else null
    }

    /**
     * ArrayList<funcion> ::=<Funcion>(<ListaFunciones>)
     */
    fun esListaFunciones(): ArrayList<Funcion> {
        var listaFunciones = ArrayList<Funcion>()
        var funcion = esFuncion()

        while (funcion != null) {
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }
        return listaFunciones
    }

    /**
     * <Funcion> ::= <TipoRetorno> identificadorMetodo "("[<ListaParametros>]")" <LlaveApertura> <BloqueSentencias> <LlaveCierre>
     */
    fun esFuncion(): Funcion? {

        var tipoRetorno = esTipoRetorno()
        if (tipoRetorno != null) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.METODO) {
                var nombreFuncion = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.PARENTESISIQ) {
                    obtenerSiguienteToken()
                    var listaParametros = esListaParametros()
                    if (tokenActual.categoria == Categoria.PARENTESISDR) {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.LLAVE) {
                            obtenerSiguienteToken()
                            var bloqueSentencia = esBloqueSentencias()
                            if (bloqueSentencia != null) {
                                //funcion esta bien escrito
                                obtenerSiguienteToken()
                                if (tokenActual.categoria == Categoria.LLAVE) {
                                    return Funcion(nombreFuncion, tipoRetorno, listaParametros, bloqueSentencia)
                                } else {
                                    reportarError(mensaje = "el bloque de sentencia esta vacio")
                                }

                            } else {
                                reportarError(mensaje = "el bloque de sentencia esta vacio")
                            }
                        } else {
                            reportarError(mensaje = "el bloque de sentencia esta vacio")
                        }

                    } else {
                        reportarError(mensaje = "el bloque de sentencia esta vacio")
                    }
                } else {
                    reportarError(mensaje = "falta el parentesis izquierdo")
                }
            } else {
                reportarError(mensaje = "falta el nombre de la funcion")
            }

        }
        return null
    }

    /**
     * <Arreglo> ::= arr identificador ":" <TipoDato> [ "=" "[" <ListaArgumentos>"]"] ";"
     */
    fun esArreglo(): Arreglo? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema=="arr"){
            obtenerSiguienteToken()
            if (tokenActual.categoria==Categoria.IDENTIFICADOR){
                val nombre=tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria==Categoria.DOS_PUNTOS){
                    obtenerSiguienteToken()
                    val tipoDato = esTipoDato()
                    if (tipoDato != null){
                        obtenerSiguienteToken()
                        var listaExpresion = ArrayList<Expresion>()
                        if (tokenActual.categoria == Categoria.OP_ASIGNACION && tokenActual.lexema == "="){
                            obtenerSiguienteToken()
                            if (tokenActual.categoria == Categoria.LLAVE){
                                obtenerSiguienteToken()
                                listaExpresion = eslistaArgumentos()
                                if (tokenActual.categoria == Categoria.LLAVE){
                                    obtenerSiguienteToken()
                                }else{
                                    reportarError("falta corchete derecho del arrelo")
                                }
                            }else{
                                reportarError("falta corchete izquierdo del arreglo")
                            }
                        }
                        if (tokenActual.categoria == Categoria.FIN_SENTENCIA){
                            obtenerSiguienteToken()
                            return Arreglo(nombre, tipoDato, listaExpresion)
                        }else{
                            reportarError("falta fin de sentencia")
                        }
                    }else{
                        reportarError("falata tipo de dato en el arreglo")
                    }
                }else{
                    reportarError("falta tipo de dato en el arreglo")
                }
            }else{
                reportarError("falta definir el nombre del arreglo")
            }
        }
        return null
    }

    /**
     * <sentencia> ::= <Desicion< | <Ciclo> | <Impresion> | <Lectura> | >
     */
    fun esSentencia(): Sentencia? {
        var s: Sentencia? = esArreglo()
        if (s != null) {
            return s
        }
        s= esAsignacion()
        if (s != null) {
            return s
        }
        s = esCiclo()
        if (s != null) {
            return s
        }
        s = esDecision()
        if (s != null) {
            return s
        }
        s = esDeclaracionVariable()
        if (s != null) {
            return s
        }

        s = esImpresion()
        if (s != null) {
            return s
        }
        s = esLectura()
        if (s != null) {
            return s
        }
        s = esRetorno()
        if (s != null) {
            return s
         }
        return null
    }

    /**
     *<tipoRetorno> ::= int| decimal| string|char|bool|void
     */
    fun esTipoRetorno(): Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "int" || tokenActual.lexema == "float" || tokenActual.lexema == "string" ||
                tokenActual.lexema == "char" || tokenActual.lexema == "boolean" || tokenActual.lexema == "void"
            ) {
                return tokenActual
            }
        }
        return null
    }

    /**
     * <Retorno> ::= return":" <Expresion>
     */
  fun esRetorno(): Retorno?{
    if (tokenActual.lexema == "return"){
        obtenerSiguienteToken()
        if (tokenActual.categoria == Categoria.DOS_PUNTOS){
            obtenerSiguienteToken()
            val expresion = esExpresion()
            if (expresion != null){
                if (tokenActual.categoria == Categoria.CADENA.FIN_SENTECIA){
                    obtenerSiguienteToken()
                    return Retorno(expresion)
                }else{
                    reportarError("falta fin de sentencia retorno")
                }
            }else{
                reportarError("Error en la expresion")
            }
        }else{
            reportarError("Falta dos puentos del return")
        }
         return null
    }

}

    /**
     *
     */

    fun esTipoDato(): Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "int" || tokenActual.lexema == "float" || tokenActual.lexema == "string" ||
                tokenActual.lexema == "char" || tokenActual.lexema == "boolean"
            ) {
                return tokenActual
            }
        }
        return null
    }

    /**
     * <ListaParametros> ::=<Parametro>[","<ListaParametros>]
     */

    fun esListaParametros(): ArrayList<Parametro> {
        var listaParametros = ArrayList<Parametro>()
        var parametro = esParametro()

        while (parametro != null) {
            listaParametros.add(parametro)

            if (tokenActual.categoria == Categoria.COMA) {
                obtenerSiguienteToken()
                parametro = esParametro()
            } else {
                if (tokenActual.categoria != Categoria.AGRUPADOR) {
                    reportarError(mensaje = "falta una coma n la lista de parametro")
                }
                reportarError(mensaje = "falta una coma n la lista de parametro")
                break
            }

        }
        return listaParametros
    }
    /**
     * <DeclaracionVariable>::= <TipoDato><IdentificadorVariable>
     */
    fun esDeclaracionVariable(): DeclaracionVariable? {
        val tipoDato = esTipoDato()
        if (tipoDato != null) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.VARIABLE) {
                val variable = tokenActual
                return DeclaracionVariable(tipoDato, variable)
            } else {
                reportarError("no es una variable:")
            }
        } else {
            reportarError("no es topo de dato valido:")
        }
        return null
    }

    /**
     * <esParametro> ::= identificador":"<TipoDato>]
     */

    fun esParametro(): Parametro? {

        if (tokenActual.categoria == Categoria.VARIABLE )  {
            val nombre = tokenActual
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                obtenerSiguienteToken()
                val tipoDato = esTipoDato()

                if (tipoDato != null) {
                    obtenerSiguienteToken()
                    return Parametro(nombre, tipoDato)
                } else {
                    reportarError(mensaje = "Falta tipo de retorno en el parametro")
                }
            } else {
                reportarError(mensaje = "Falta el operador:")
            }

        }
        return null
    }
    /**
     * <ListaArgumentos> ::= <Argumento>["," <ListaArgumentos>]
     *       <Argumento> ::= <Expresion>
     */
        fun esListaArgumentos(): ArrayList<Expresion>{
            val lista = ArrayList<Expresion>()
            var param = esExpresion()
            while (param != null){
                lista.add(param)
                if(tokenActual.categoria == Categoria.COMA){
                    obtenerSiguienteToken()
                    param = esExpresion()
                }else{
                    param = null
                }
            }
        return lista
        }

    /**
     * <BloqueSentencias> ::= "{" [<ListaSentencia>] "}"
     */

    fun esBloqueSentencias(): ArrayList<Sentencia>? {
        if (tokenActual.categoria == Categoria.LLAVE) {
            obtenerSiguienteToken()
            var listaSentencias = esListaSentencias()
            if (tokenActual.categoria == Categoria.LLAVE) {
                obtenerSiguienteToken()
                return listaSentencias
            } else {
                reportarError(mensaje = "falta la llave derecha")
            }
        } else {
            reportarError(mensaje = "falta la llave izquierdo")
        }
        return null
    }

    /**
     * <ListaSentencias> ::= <Sentencia>[<ListaSentencias>]
     */
    fun esListaSentencias(): ArrayList<Sentencia> {
        val lista = ArrayList<Sentencia>()
        var s = esSentencia()
        while (s != null) {
            lista.add(s)
            s = esSentencia()
        }
        return lista
    }

    fun esExpresionLogica(): ExpresionLogica? {
        return null
    }

    /**
     * <Impresion> ::= print ":" <Expresion>
     */
    fun esImpresion(): Impresion?{
        if(tokenActual.lexema == "print"){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.DOS_PUNTOS){
                obtenerSiguienteToken()
                val expresion = esExpresion()
                if(expresion!= null){
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA){
                        obtenerSiguienteToken()
                        return Impresion(expresion)
                    }else{
                        reportarError("falta fin sentencia")
                    }
                }
            }else{
                reportarError("falta dos puntos en la expresion")
            }
        }
        return null
    }
    /**
     * <Ciclo> ::= while <ExpresionLogica> do <Bloque>
     */
    fun esCiclo(): Ciclo? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "while") {
            obtenerSiguienteToken()
            val expresion = esExpresionLogica()
            if (expresion != null) {
                if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "do") {
                    val bloque = esBloqueSentencias()
                    if (bloque != null) {
                        return Ciclo(expresion, bloque)
                    }
                } else {
                    reportarError(mensaje = "falta la palabra do")
                }
            } else {
                reportarError(mensaje = "hay un error en la condicion")
            }
        }
        return null
    }

    /**
     * <Decision> ::= if <ExpresionLogica> <Bloque> [else <Bloque>]
     */
    fun esDecision(): Decision? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "if") {
            obtenerSiguienteToken()
            val expresion = esExpresionLogica()
            if (expresion != null) {
                obtenerSiguienteToken()
                val bloqueV = esBloqueSentencias()
                if (bloqueV != null) {
                    obtenerSiguienteToken()
                    val bloqueF = esBloqueSentencias()
                    if (bloqueF != null) {
                        return Decision(expresion, bloqueV, bloqueF)
                    } else {
                        return Decision(expresion, bloqueV, null)
                    }
                } else {
                    return null
                }
            }
        }
        return null
    }

    /**
     * <InvocacionFuncion> ::= identificador "("<ListaArgumentos>")" ";"
     */
    fun esInvocacionFuncion(): InvocacionFuncion? {
        val posInicial: Int = posicionActual
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val nombreFuncion = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PARENTESISIQ) {
                obtenerSiguienteToken()
                val argumentos = esListaArgumentos()
                if (tokenActual.categoria == Categoria.PARENTESISDR) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                        obtenerSiguienteToken()
                        return InvocacionFuncion(nombreFuncion, argumentos)
                    } else {
                        reportarError("falta fin Sentencia")
                    }
                } else {
                    reportarError("falta parentesis derecho")
                }
            } else {
                if (tokenActual.categoria == Categoria.OP_ASIGNACION || tokenActual.categoria == Categoria.)
                    hacerBacktraking(posInicial)
            }else{
                reportarError("falta parentesis izquierdo")
            }
        }
     }
        return null
    }

    /**
     * <Asignacion> ::= <IdVariable> <OperadorAsignacion> <Expresion>
     */
    fun esAsignacion(): Asignacion? {
        val posInicial:Int = posicionActual
        if (tokenActual.categoria == Categoria.VARIABLE ) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OP_ASIGNACION) {
                val operadorAsignacion = tokenActual
                obtenerSiguienteToken()

                val invocacionFuncion = esInvocacionFuncion()
                if (invocacionFuncion != null) {
                    return Asignacion(identificador, operadorAsignacion, invocacionFuncion)
                } else {
                    val expresion = esExpresion()
                    if (expresion != null) {
                        return Asignacion(identificador, operadorAsignacion, expresion)
                    } else {
                            reportarError("falta fin de sentencia asignacion")
                    }
                }
            } else {
                if (tokenActual.categoria == Categoria.PARENTESISIQ) {
                    hacerBacktraking(posInicial)
                } else  {
                    reportarError("falta un operador de asignacion")
                }
            }
        }
        return null
        }
    /**
     * <Variable> ::= identificador ["=" <Expresion>]
     */
    fun esVariable():Variable? {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR){
        val nombreVariable = tokenActual
        obtenerSiguienteToken()
        if (tokenActual.lexema == "=") {
            obtenerSiguienteToken()
            val expresion = esExpresion()
            if (expresion != null) {
                return Variable(nombreVariable, expresion)
            } else {
                reportarError("falta expresion")
            }
        } else {
            return Variable(nombreVariable, null)
        }
    }
    return null

    }
    /**
     * <DeclaracionVariable> ::= var <listaVariables>":"<TipoDato>";"
     */
    fun esDeclaracionVariable(): DeclaracionVariable? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "var"){
            obtenerSiguienteToken()
            val listaVariable = eslistaVariable()
            if (listaVariable != null){
                if (tokenActual.categoria == Categoria.DOS_PUNTOS){
                    obtenerSiguienteToken()
                    val tipoDato= esTipoDato()
                    if (tipoDato != null){
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.FIN_SENTENCIA){
                            obtenerSiguienteToken()
                            return DeclaracionVariable(tipoDato, listaVariable)
                        }else{
                            reportarError("falta fin de sentencia")
                        }
                    }else{
                        reportarError("falta el tipo de dato")
                    }
                }else{
                    reportarError("falta dos puntos")
                }
            }else{
                reportarError("falta las variables ")
            }
        }
        return null
    }
    /**
     * <Parametro> ::= identificador":"<TipoDato>
     */
    fun esParametro():Parametro? {
        if (tokenActual.categoria== Categoria.IDENTIFICADOR){
            val nombere= tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.DOS_PUNTOS){
                obtenerSiguienteToken()
                val tipoDato = esTipoDato()
                if (tipoDato = null){
                    obtenerSiguienteToken()
                    return Parametro(nombere,tipoDato)
                }else{
                    reportarError("falta tipo retorno en el paraetro")
                }
            }else{
                reportarError("falta el operador")
            }
        }
        return null
    }
    /**
     * <Retorno> ::= return ":" <Expresion> ";"
     */
    fun esRetorno(): Retorno? {
        if (tokenActual.lexema == "return"){
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.DOS_PUNTOS){
                obtenerSiguienteToken()
                val expresion = esExpresion()
                if (expresion!=null){
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA){
                        obtenerSiguienteToken()
                        return Retorno(expresion)
                    }else{
                        reportarError("falta fin de sentencia retorno")
                    }
                } else{
                    reportarError("Error en la expresion")
                 }
            }else{
                reportarError("falta dos puntos de retorno")
            }
        }
        return null
    }
    /**
     * <Lectura> ::= imput":" identificador ";"
     */
    fun esLectura(): Lectura? {
        if (tokenActual.lexema == "input"){
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.DOS_PUNTOS){
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.IDENTIFICADOR){
                    val nombreVariable = tokenActual
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA){
                        obtenerSiguienteToken()
                        return Lectura(nombreVariable)
                    }else{
                        reportarError("falta fin sentencia")
                    }
                }else{
                    reportarError("Falta identificador")
                }
            }
        }
        return null
    }
    /**
     * <ExpresionCadena> ::= cadena
     */

    /**
     * <Expresion> ::= <ExpresionAritmetica> | <ExpresionRelacional> | <ExpresionLogica>
     */
    fun esExpresion(): Expresion? {
        var e: Expresion? = esExpresionLogica()
        if(e != null){
            return e
        }
        e = esExpresionAritmetica()
        return e

    }

    /**
     * <ExpresionAritmetica> ::= "("<ExpAritmetica>")" [operadorAritmetica <ExpAritmetica>]|<ValorNumerico>| <InvocacionFuncion>
     *     | <Identificador>
     *
     */

    fun esExpresionAritmetica(): ExpresionAritmetica? {
        return null
    }
    /**
     * <ExpresionRelacional> ::= <ExpresionAritmetica> operacionRelacional <ExpresionAritmetica> | false | true
     */
    fun esExpresionRelacional(): ExpresionRelacional? {
        return null
    }

    /**
     * ExpresionLogica> ::= "!" <ExpresionRelacional> | <ExpresionRelacional> || <ExpresionRelacional> |
     *                                                  <ExpresionRelacional> && <ExpresionRelacional> |
     *                          <ExpresionRelacional>
     */
    fun esExpresionLogica(): ExpresionLogica? {
        return null
    }
    /**
     * <ValorNumerico> ::= [<signo>] decimal | [<signo>] entero | [<signo>] identificador
     */
    fun esValorNumerico(): ValorNumerico? {
        var signo: Token? = null
        if (tokenActual.categoria == Categoria.OP_ARITMETICO && (tokenActual.lexema == "+" || tokenActual.lexema == "-"){
            signo = tokenActual
            obtenerSiguienteToken()
            }
            if (tokenActual.categoria== Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL || tokenActual.categoria == Categoria.IDENTIFICADOR) {
                val termino = tokenActual

            }
    }

}

