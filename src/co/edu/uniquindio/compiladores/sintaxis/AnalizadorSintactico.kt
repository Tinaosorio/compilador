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

        if (listaFunciones.size > 0) {
            return UnidadDeCompilacion(listaFunciones)
        }
        return null
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
                            var bloqueSentencia = esBloqueSentencias()
                            if (bloqueSentencia != null) {
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
                    reportarError(mensaje = "falta el parentesis izquierdo")
                }
            } else {
                reportarError(mensaje = "falta el nombre de la funcion")
            }

        }
        return null
    }

    /**
     * <ListaArgumentos> ::= <Argumento>["," <ListaArgumentos>]
     *       <Argumento> ::= <Expresion>
     */
    fun esListaArgumentos(): ArrayList<Expresion> {
        val lista = ArrayList<Expresion>()
        var param = esExpresion()
        while (param != null) {
            lista.add(param)
            if (tokenActual.categoria == Categoria.COMA) {
                obtenerSiguienteToken()
                param = esExpresion()
            } else {
                param = null
            }
        }
        return lista
    }


    /**
     * <Arreglo> ::= list identificadorVariable ":" <TipoDato> [ "=" "[" <ListaArgumentos>"]"] ";"
     */
    fun esArreglo(): Arreglo? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "list") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                val nombre = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                    obtenerSiguienteToken()
                    val tipoDato = esTipoDato()
                    if (tipoDato != null) {
                        obtenerSiguienteToken()
                        var listaExpresion = ArrayList<Expresion>()
                        if (tokenActual.categoria == Categoria.OP_ASIGNACION && tokenActual.lexema == "=") {
                            obtenerSiguienteToken()
                            if (tokenActual.categoria == Categoria.LLAVE)
                                obtenerSiguienteToken()
                            listaExpresion = esListaArgumentos()
                            if (tokenActual.categoria == Categoria.LLAVE) {
                                return Arreglo(nombre, tipoDato, listaExpresion)
                                obtenerSiguienteToken()
                            } else {
                                reportarError("falta corchete derecho del arrelo")
                            }
                        } else {
                            reportarError("falta corchete izquierdo del arreglo")
                        }
                    }
                    if (tokenActual.categoria == Categoria.FIN_SENTENCIA) {
                        obtenerSiguienteToken()
                    } else {
                        reportarError("falta fin de sentencia")
                    }
                } else {
                    reportarError("falata tipo de dato en el arreglo")
                }
            } else {
                reportarError("falta tipo de dato en el arreglo")
            }
        } else {
            reportarError("falta definir el nombre del arreglo")
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
        s = esDecision()
        if (s != null) {
            return s
        }
        s = esDeclaracionVariable()
        if (s != null) {
            return s
        }
        s = esCiclo()
        if (s != null) {
            return s
        }
        s = esImpresion()
        if (s != null) {
            return s
        }
        s = esAsignacion()
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
        s = esIncrementoDecremento()
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
    fun esRetorno(): Retorno? {
        if (tokenActual.lexema == "return") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                obtenerSiguienteToken()
                val expresion = esExpresion()
                if (expresion != null) {
                    return Retorno(expresion)
                } else {
                    reportarError("Error en la expresion")
                }
            } else {
                reportarError("Falta dos puntos del return")
            }
        }
        return null
    }

    /**
    <IncrementoDecremento> ::=  IdentificadorVariable ++ | IdentificadorVariable --
     */
    fun esIncrementoDecremento(): IncrementoDecremento? {
        val posInicial = posicionActual
        if (tokenActual.categoria == Categoria.VARIABLE) {
            val variable = tokenActual
            obtenerSiguienteToken()
            val incremento = tokenActual
            if (tokenActual.categoria == Categoria.INCREMENTO) {
                return IncrementoDecremento(variable, incremento)
            } else if (tokenActual.categoria == Categoria.DECREMENTO) {
                return IncrementoDecremento(variable, incremento)
            } else {
                reportarError("falta operador incremento/decremento")
            }
        } else {
            hacerBacktraking(posInicial)
        }
        return null
    }

    /**
     * <MutableInmutable> ::=  const | let
     */
    fun esMutableInmutable(): Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "const" || tokenActual.lexema == "let"
            ) {
                return tokenActual
            }
        }
        return null
    }

    /**
     *<esTipoDato> ::= int|float|chat|boolean|string
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
     * <esParametro> ::= identificadorVariable":"<TipoDato>]
     */

    fun esParametro(): Parametro? {

        if (tokenActual.categoria == Categoria.VARIABLE) {
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
                break
            }

        }
        return listaParametros
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
     * <Expresion> ::= <ExpresionAritmetica> | <ExpresionRelacional> | <ExpresionLogica> | <ExpresionCadena
     */
    fun esExpresion(): Expresion? {
        var e: Expresion? = esExpresionLogica()
        if (e != null) {
            return e
        }
        e = esExpresionAritmetica()
        if (e != null) {
            return e
        }
        e = esExpresionRelacional()
        if (e != null) {
            return e
        }
        e = esExpresionCadena()
        if (e != null) {
            return e
        }
        return null
    }

    /**
     * <ExpresionAritmetica> ::= "("<ExpAritmetica>")" | <ExpAritmetica> [operadorAritmetico <ExpAritmetica>]|<ValorNumerico>| <InvocacionFuncion>
     *     | <Identificador>
     */

    fun esExpresionAritmetica(): ExpresionAritmetica? {
        if (tokenActual.categoria == Categoria.PARENTESISIQ) {
            obtenerSiguienteToken()
            var expresion1 = esExpresionAritmetica()
            if (expresion1 != null) {
                if (tokenActual.categoria == Categoria.PARENTESISDR) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.OP_ARITMETICO) {
                        val operador = tokenActual
                        obtenerSiguienteToken()
                        val expresion2 = esExpresionAritmetica()
                        if (expresion2 != null) {
                            return ExpresionAritmetica(expresion1, operador, expresion2)
                        }
                    } else {
                        return ExpresionAritmetica(expresion1)
                    }
                }
            }
        } else {
            val valor = esValorNumerico()
            if (valor != null) {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.OP_ARITMETICO) {
                    val operador = tokenActual
                    obtenerSiguienteToken()
                    val expresion = esExpresionAritmetica()
                    if (expresion != null) {
                        return ExpresionAritmetica(valor, operador, expresion)
                    }
                } else {
                    return ExpresionAritmetica(valor)
                }
            }
        }
        return null
    }


    /**
     * ExpresionLogica> ::= "!" <ExpresionRelacional> | <ExpresionRelacional> || <ExpresionRelacional> |
     *                                                  <ExpresionRelacional> && <ExpresionRelacional> |
     *                          <ExpresionRelacional>
     */
    fun esExpresionLogica(): ExpresionLogica? {
        if (tokenActual.categoria == Categoria.NEGACION) {
            obtenerSiguienteToken()
            var expresion1 = esExpresionRelacional()
            if (expresion1 != null) {
                return ExpresionLogica(expresion1)
            } else {
                reportarError("Falta expresion relacional")
            }
        } else {
            var expresion1 = esExpresionRelacional()
            if (expresion1 != null) {
                if (tokenActual.categoria == Categoria.OPERADOR_LOGICO) {
                    val operador = tokenActual
                    obtenerSiguienteToken()
                    var expresion2 = esExpresionRelacional()
                    if (expresion2 != null) {
                        return ExpresionLogica(expresion1, operador, expresion2)
                    } else {
                        reportarError("Falto expresion relacional derecha")
                    }
                } else {
                    return ExpresionLogica(expresion1)
                }
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
                val bloqueV = esBloqueSentencias()
                if (bloqueV != null) {
                    if (tokenActual.lexema == "else") {
                        obtenerSiguienteToken()
                        val bloqueF = esBloqueSentencias()
                        if (bloqueF != null) {
                            return Decision(expresion, bloqueV, bloqueF)
                        } else {
                            reportarError("Falto contenido else")
                        }
                    } else {
                        return Decision(expresion, bloqueV, null)
                    }
                }
            }
        }
        return null
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
            reportarError("no es tipo de dato valido:")
        }
        return null
    }

    /**
     * <Impresion> ::= print ":" <Expresion>
     */
    fun esImpresion(): Impresion? {
        if (tokenActual.lexema == "print") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                obtenerSiguienteToken()
                val expresion = esExpresion()
                if (expresion != null) {
                    return Impresion(expresion)
                }
            } else {
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
                    obtenerSiguienteToken()
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
     * <InvocacionFuncion> ::= identificador "("<ListaArgumentos>")" ";"
     */
    fun esInvocacionFuncion(): InvocacionFuncion? {
        val posInicial: Int = posicionActual
        if (tokenActual.categoria == Categoria.METODO) {
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
                if (tokenActual.categoria == Categoria.OP_ASIGNACION) {
                    hacerBacktraking(posInicial)
                } else {
                    reportarError("falta parentesis izquierdo")
                }
            }
        }
        return null
    }

    /**
     * <ValorNumerico> ::= [<signo>] decimal | [<signo>] entero | [<signo>] identificador
     */
    fun esValorNumerico(): ValorNumerico? {
        var signo: Token? = null
        if (tokenActual.categoria == Categoria.OP_ARITMETICO && (tokenActual.lexema == "+" || tokenActual.lexema == "-")) {
            signo = tokenActual
            obtenerSiguienteToken()
        }
        if (tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL || tokenActual.categoria == Categoria.VARIABLE) {
            val termino = tokenActual
            return ValorNumerico(signo, termino)
        }
        return null
    }

    /**
     * <Asignacion> ::= <IdVariable> <OperadorAsignacion> <Expresion>
     */
    fun esAsignacion(): Asignacion? {
        val posInicial = posicionActual
        if (tokenActual.categoria == Categoria.VARIABLE) {
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
                } else {
                    reportarError("falta un operador de asignacion")
                }
            }
        }
        return null
    }

    /**
     * <ExpresionRelacional> ::= <ExpresionAritmetica> operacionRelacional <ExpresionAritmetica> | false | true
     */
    fun esExpresionRelacional(): ExpresionRelacional? {
        var posInicial = posicionActual
        var expresion1 = esExpresionAritmetica()
        if (expresion1 != null) {
            if (tokenActual.categoria == Categoria.OP_RELACIONAL) {
                val operador = tokenActual
                obtenerSiguienteToken()
                val expresion2 = esExpresionAritmetica()
                if (expresion2 != null) {
                    return ExpresionRelacional(expresion1, operador, expresion2)
                } else {
                    reportarError("Falto expresion derecha")
                }
            } else {
                hacerBacktraking(posInicial)
            }
        } else {
            if (tokenActual.lexema == "true" || tokenActual.lexema == "false") {
                val valorVerdad = tokenActual
                return ExpresionRelacional(valorVerdad)
            }
        }
        return null
    }

    /**
     * <Lectura> ::= input":" cadena
     */
    fun esLectura(): Lectura? {
        if (tokenActual.lexema == "input") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.CADENA) {
                    val cadena = tokenActual
                    obtenerSiguienteToken()
                    return Lectura(cadena)
                } else {
                    reportarError("Falta identificador")
                }
            }
        }
        return null
    }

    /**
     *<ExpresionCadena> ::= cadena["+" <Expresion>] | identificadors
     */
    fun esExpresionCadena(): ExpresionCadena? {
        if (tokenActual.categoria == Categoria.CADENA) {
            val cadena = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.lexema == "+") {
                obtenerSiguienteToken()
                var expresion = esExpresion()
                if (expresion != null) {
                    return ExpresionCadena(cadena, expresion)
                }
            } else {
                return ExpresionCadena(cadena)
            }
        } else {
            if (tokenActual.categoria == Categoria.VARIABLE) {
                val variable = tokenActual
                return ExpresionCadena(variable)
            }
        }
        return null
    }

    /**
     * <Variable> ::= identificador ["=" <Expresion>]
     */
    fun esVariable(): Variable? {
        if (tokenActual.categoria == Categoria.VARIABLE) {
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

}