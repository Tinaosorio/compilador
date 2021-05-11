package co.edu.uniquindio.compiladores

class AnalizadorLexico (var codigoFuente:String) {
    var posicionActual= 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var listaErrores = ArrayList<Error>()
    var finCodigo = 0.toChar()
    var filaActual= 0
    var columnaActual= 0
    val MAX_LONG = 10

    fun almacenarToken(lexema:String, categoria: Categoria, fila:Int, columna:Int ) {
        listaTokens.add(Token(lexema, categoria, fila, columna))
    }

    fun almacenarError(lexema:String, errorMsg: String, fila:Int, columna:Int ) {
        listaErrores.add(Error(lexema, errorMsg, fila, columna))
    }

    fun analizar(){
        while(caracterActual != finCodigo ){
            if(caracterActual==' '  || caracterActual== '\t' || caracterActual == '\n') {
                obtenerSiguienteCaracter()
                continue
            }
            if (esOpIncremento())continue
            if (esOpDecremento())continue
            if (esCaracter())continue
            if (esDecimal())continue
            if (esMetodo()) continue
            if (esEntero()) continue
            if (esPalabraReservadaWhile()) continue
            if (esClase()) continue
            if (esCadena()) continue
            if (esTrue()) continue
            if (esEntero()) continue
            if (esPalabraReservadaInt()) continue
            if (esPalabraReservadaChar()) continue
            if (esPalabraReservadaDo()) continue
            if (esPalabraReservadaDouble()) continue
            if (esPalabraReservadaFloat()) continue
            if (esPalabraReservadaFor()) continue
            if (esPalabraReservadaIf()) continue
            if (esPalabraReservadaElse()) continue
            if (esPalabraReservadaReturn()) continue
            if (esPalabraReservadaClass()) continue
            if (esFalse()) continue
            if (esDosPuntos()) continue
            if (esComa()) continue
            if (esParentesisDr()) continue
            if (esParentesisIq()) continue
            if (esCorcheteDr()) continue
            if (esCorcheteIq()) continue
            if (esPalabraReservadaVoid()) continue
            if (esOpRelacional()) continue
            if (esOpAsignacion()) continue
            if (esOpRelacional2()) continue
            if (esOpAritmetico()) continue
            if (esAgrupadorLlave()) continue
            if (esOpConcatenacion()) continue
            if (esPalabraReservadaString()) continue
            if (esSeparador()) continue
            if (esPalabraReservadaPrivate()) continue
            if (esPalabraReservadaPublic()) continue
            if (esOperadorLogico1()) continue
            if (esOperadorLogico2()) continue
            if (esSaltoLinea()) continue
            if (esVariable()) continue
            if (esComentario()) continue
            if (esComentarioBloque()) continue
            if (esFinSentencia()) continue


            almacenarToken(lexema= ""+caracterActual, Categoria.DESCONOCIDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
        }
    }

    fun hacerBT(posicionInicial:Int, filaInicial:Int, columnaInicial:Int){
        posicionActual= posicionInicial
        filaActual = filaInicial
        columnaActual = columnaInicial

        caracterActual = codigoFuente[posicionActual]

    }
    fun esVariable(): Boolean {

        // Si no es �,no va ser una variable
        if (caracterActual != '°') {
            return false
        }
        /*
		 * Se almacena la posici�n Actual en posicionInicial en que
		 * inicia el recorrido para definir si es un entero. En caso de no
		 * serlo, debe iniciar el recorrido en esta posici�n para continuar con
		 * otro m�todo Predicado
		 */
        val posicionInicial = posicionActual

        // posicion para guardar los lexemas en la tabla de s�mbolos
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        // se almacena el caracterActual en la cadena lexema
        var lexema = caracterActual.toString() + ""
        obtenerSiguienteCaracter()
        var i = 0
        while (caracterActual != finCodigo && caracterActual.isLetter() && i < 10) {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            i ++
        }
        almacenarToken(lexema,Categoria.VARIABLE, filaInicial, columnaInicial)
        return true
    }
    private fun esSaltoLinea(): Boolean {
        if (caracterActual == '\n') {
            obtenerSiguienteCaracter()
            return true
        }
        return false
    }
    fun esOperadorLogico2(): Boolean {
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == '|') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '|') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial,
                    columnaInicial)
                return true
            }
        }
        return false
    }

    private fun esSeparador(): Boolean {
        if (!(caracterActual == ';' || caracterActual == ',')) {
            return false
        } else {
            if (caracterActual == ';') {
                val filaInicial = filaActual
                val columnaInicial = columnaActual
                val lexema = caracterActual.toString() + ""
                almacenarToken(lexema, Categoria.SEPARADOR, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }
            if (caracterActual == ',') {
                val filaInicial = filaActual
                val columnaInicial = columnaActual
                val lexema = caracterActual.toString() + ""
                almacenarToken(lexema, Categoria.SEPARADOR, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }
        }
        return false
    }
     fun esPalabraReservadaPublic(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
         if (caracterActual == 'p') {
            obtenerSiguienteCaracter()
            lexema += 'p'
            if (caracterActual == 'u') {
                obtenerSiguienteCaracter()
                lexema += 'u'
                if (caracterActual == 'b') {
                    obtenerSiguienteCaracter()
                    lexema += 'b'
                    if (caracterActual == 'l') {
                        obtenerSiguienteCaracter()
                        lexema += 'l'
                        if (caracterActual == 'i') {
                            obtenerSiguienteCaracter()
                            lexema += 'i'
                            if (caracterActual == 'c') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                              return  true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                              return  false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                          return  false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                      return  false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                   return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
               return false
            }
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
           return false
        }
    }
    fun esOpDecremento(): Boolean {
        val filaInicial = filaActual
        val posicionInicial = posicionActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == '-') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '-') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.DECREMENTO, filaInicial,
                    columnaInicial)
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return  false
            }
        }
        return false
    }
    fun esOpIncremento(): Boolean {
        val filaInicial = filaActual
        val posicionInicial = posicionActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == '+') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '+') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.INCREMENTO, filaInicial,
                    columnaInicial)
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return  false
            }
        }
        return false
    }

    fun esOperadorLogico1(): Boolean {
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == '&') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '&') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial,
                    columnaInicial)
                return true
            }
        }
        return false
    }
     fun esPalabraReservadaPrivate(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
         if (caracterActual == 'p') {
            obtenerSiguienteCaracter()
            lexema += 'p'
            if (caracterActual == 'r') {
                obtenerSiguienteCaracter()
                lexema += 'r'
                if (caracterActual == 'i') {
                    obtenerSiguienteCaracter()
                    lexema += 'i'
                    if (caracterActual == 'v') {
                        obtenerSiguienteCaracter()
                        lexema += 'v'
                        if (caracterActual == 'a') {
                            obtenerSiguienteCaracter()
                            lexema += 'a'
                            if (caracterActual == 't') {
                                lexema += "t"
                                obtenerSiguienteCaracter()
                                if (caracterActual == 'e') {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()
                                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                   return true
                                } else {
                                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                                  return  false
                                }
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                              return  false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                          return  false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                      return  false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                   return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
               return false
            }
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
           return false
        }
    }
     fun esPalabraReservadaString(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
         if (caracterActual == 'S') {
            obtenerSiguienteCaracter()
            lexema += 'S'
            if (caracterActual == 't') {
                obtenerSiguienteCaracter()
                lexema += 't'
                if (caracterActual == 'r') {
                    obtenerSiguienteCaracter()
                    lexema += 'r'
                    if (caracterActual == 'i') {
                        obtenerSiguienteCaracter()
                        lexema += 'i'
                        if (caracterActual == 'n') {
                            obtenerSiguienteCaracter()
                            lexema += 'n'
                            if (caracterActual == 'g') {
                                lexema += "g"
                                obtenerSiguienteCaracter()
                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                              return  true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                              return  false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                          return  false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                     return   false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                  return  false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
               return false
            }
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
           return false
        }
    }
    fun esOpRelacional(): Boolean {
        if (!(caracterActual == '>' || caracterActual == '<')) {
          return  false
        } else {
            val filaInicial = filaActual
            val columnaInicial = columnaActual
            var lexema = caracterActual.toString() + ""
            obtenerSiguienteCaracter()
            if (caracterActual != '=') {
                almacenarToken(lexema, Categoria.OP_RELACIONAL, filaInicial,
                    columnaInicial)
                return true
            } else {
                lexema += caracterActual
                almacenarToken(lexema, Categoria.OP_RELACIONAL, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }
        }
    }

     fun esOpRelacional2(): Boolean {
         if (caracterActual != '!') {
            return false
        } else {
            val filaInicial = filaActual
            val columnaInicial = columnaActual
            var lexema = caracterActual.toString() + ""
            obtenerSiguienteCaracter()
            if (caracterActual != '=') {
                return  false
            } else {
                lexema += caracterActual
                almacenarToken(lexema, Categoria.OP_RELACIONAL, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }
        }
    }

     fun esOpAsignacion(): Boolean {
         if (caracterActual != '=') {
             return false
        } else {
            val filaInicial = filaActual
            val columnaInicial = columnaActual
            var lexema = caracterActual.toString() + ""
            obtenerSiguienteCaracter()
            if (caracterActual != '=') {
                almacenarToken(lexema,Categoria.OP_ASIGNACION , filaInicial, columnaInicial)
                return true
            } else {
                lexema += caracterActual
                almacenarToken(lexema, Categoria.OP_RELACIONAL, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }
        }
    }

    fun esOpAritmetico(): Boolean {
        if (!(caracterActual == '+' || caracterActual == '*' || caracterActual == '/' || caracterActual == '-')) {
            return false
        } else {
            val filaInicial = filaActual
            val columnaInicial = columnaActual
            val lexema = caracterActual.toString() + ""
            almacenarToken(lexema, Categoria.OP_ARITMETICO, filaInicial, columnaInicial)
            obtenerSiguienteCaracter()
            return true
        }
    }

    private fun esOpConcatenacion(): Boolean {
        return if (caracterActual != '$') {
            false
        } else {
            val filaInicial = filaActual
            val columnaInicial = columnaActual
            val lexema = caracterActual.toString() + ""
            almacenarToken(lexema, Categoria.OP_CONCATENACION, filaInicial, columnaInicial)
            obtenerSiguienteCaracter()
           return true
        }
    }

    fun esDosPuntos(): Boolean {
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        val lexema = caracterActual.toString() + ""

        if (caracterActual != ':' ) {
            return false
        } else {
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.DOS_PUNTOS, filaInicial, columnaInicial)
            return true
        }
    }

    fun esFinSentencia(): Boolean {
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        val lexema = caracterActual.toString() + ""

        if (caracterActual != '.' || caracterActual != ';' ) {
            return false
        } else {
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.FIN_SENTENCIA, filaInicial, columnaInicial)
            return true
        }
    }

    fun esComa(): Boolean {
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        val lexema = caracterActual.toString() + ""

        if (caracterActual != ',' ) {
            return false
        } else {
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.COMA, filaInicial, columnaInicial)
            return true
        }
    }
    fun esParentesisIq(): Boolean {
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        val lexema = caracterActual.toString() + ""

        if (caracterActual != '(' ) {
            return false
        } else {
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.PARENTESISIQ, filaInicial, columnaInicial)
            return true
        }
    }
    fun esParentesisDr(): Boolean {
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        val lexema = caracterActual.toString() + ""

        if (caracterActual != ')' ) {
            return false
        } else {
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.PARENTESISDR, filaInicial, columnaInicial)
            return true
        }
    }
    fun esCorcheteIq(): Boolean {
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        val lexema = caracterActual.toString() + ""

        if (caracterActual != '[' ) {
            return false
        } else {
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.CORCHETEIQ, filaInicial, columnaInicial)
            return true
        }
    }
    fun esCorcheteDr(): Boolean {
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        val lexema = caracterActual.toString() + ""

        if (caracterActual != '[' ) {
            return false
        } else {
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.CORCHETEDR, filaInicial, columnaInicial)
            return true
        }
    }
     fun esAgrupadorLlave(): Boolean {
        if (!(caracterActual == '{' || caracterActual == '}')) {
            return false
        } else {
            if (caracterActual == '{') {
                val filaInicial = filaActual
                val columnaInicial = columnaActual
                val lexema = caracterActual.toString() + ""
                almacenarToken(lexema, Categoria.LLAVE, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }
            if (caracterActual == '}') {
                val filaInicial = filaActual
                val columnaInicial = columnaActual
                val lexema = caracterActual.toString() + ""
                almacenarToken(lexema, Categoria.LLAVE, filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }
        }
        return false
    }

    fun esMetodo(): Boolean{
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == '%') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            var i=0
            while (caracterActual != finCodigo && !caracterActual.isDigit() && caracterActual.isLetter()
                && i<MAX_LONG) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '%') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema,Categoria.METODO, filaInicial, columnaInicial)
                    return true
                }
                i++
            }
            almacenarError(lexema, "No se completo el metodo", filaInicial, columnaInicial)
            return false
        }
        return false
    }

    fun esFalse(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
         if (caracterActual == 'f') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'a') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'l') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 's') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'e') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                            return  true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
    }

    fun esPalabraReservadaVoid(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (Character.isDigit(caracterActual)) {
            return false
        }
        if (caracterActual == 'v') {
            obtenerSiguienteCaracter()
            lexema += 'v'
            if (caracterActual == 'o') {
                obtenerSiguienteCaracter()
                lexema += 'o'
                if (caracterActual == 'i') {
                    obtenerSiguienteCaracter()
                    lexema += 'i'
                    if (caracterActual == 'd') {
                        obtenerSiguienteCaracter()
                        lexema += 'd'
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return  true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return  false
            }
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
           return false
        }
    }

     fun esPalabraReservadaClass(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == 'c') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'l') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'a') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 's') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 's') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
           return false
        }
    }
    fun esPalabraReservadaInt(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == 'i') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'n') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 't') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
    }

    fun esPalabraReservadaChar(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == 'C') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'h') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'a') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'r') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return  false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return  false
            }
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
    }

    fun esPalabraReservadaDo(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == 'D') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'o') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema,Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                return  true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return  false
            }
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return  false
        }
    }

    fun esPalabraReservadaDouble(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == 'd') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'o') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'u') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'b') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'l') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'e') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                                return  true
                            } else {
                                hacerBT(posicionInicial, filaInicial, columnaInicial)
                                return   false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return  false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return  false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return  false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return  false
            }
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return  false
        }
    }

    fun esPalabraReservadaFloat(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == 'f') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'l') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'o') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'a') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 't') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                            return   true
                        } else {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return   false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return   false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return  false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return  false
            }
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return  false
        }
    }

    fun esPalabraReservadaFor(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == 'f') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'o') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'r') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                    return  true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return  false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        } else {
            hacerBT(posicionInicial, filaInicial,
                columnaInicial)
            return  false
        }
    }

    fun esPalabraReservadaIf(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == 'i') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'f') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                return   true
            } else {
                hacerBT(posicionInicial, filaInicial,
                    columnaInicial)
                return  false
            }
        } else {
            hacerBT(posicionInicial, filaInicial,
                columnaInicial)
            return  false
        }
    }

    fun esPalabraReservadaElse(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == 'e') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'l') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 's') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'e') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA,
                            filaInicial, columnaInicial)
                        return   true
                    } else {
                        hacerBT(posicionInicial, filaInicial,
                            columnaInicial)
                        return    false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial,
                        columnaInicial)
                    return    false
                }
            } else {
                hacerBT(posicionInicial, filaInicial,
                    columnaInicial)
                return  false
            }
        } else {
            hacerBT(posicionInicial, filaInicial,
                columnaInicial)
            return  false
        }
    }

    fun esPalabraReservadaReturn(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == 'r') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'e') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 't') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'u') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        if (caracterActual == 'r') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            if (caracterActual == 'n') {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()
                                almacenarToken(lexema,Categoria.PALABRA_RESERVADA ,
                                    filaInicial, columnaInicial)
                                return true
                            } else {
                                hacerBT(posicionInicial, filaInicial,
                                    columnaInicial)
                                return  false
                            }
                        } else {
                            hacerBT(posicionInicial, filaInicial,
                                columnaInicial)
                            return   false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial,
                            columnaInicial)
                        return   false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial,
                        columnaInicial)
                    return  false
                }
            } else {
                hacerBT(posicionInicial, filaInicial,
                    columnaInicial)
                return false
            }
        } else {
            hacerBT(posicionInicial, filaInicial,
                columnaInicial)
            return false
        }
    }

    companion object {
        // constante finCodigo para indicar fin del archivo
        const val finCodigo = 0.toChar()
    }

    fun esTrue(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == 't') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == 'r') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == 'u') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual == 'e') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                        almacenarToken(lexema,Categoria.TRUE, filaInicial, columnaInicial)
                        return true
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial,
                    columnaInicial)
                return false
            }
        } else {
            hacerBT(posicionInicial, filaInicial,
                columnaInicial)
            return false
        }
    }
    fun esCaracter(): Boolean {
        var posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == '\'') {
            lexema += caracterActual
            posicionInicial++
            obtenerSiguienteCaracter()

            if(caracterActual != finCodigo){
                lexema += caracterActual
                posicionInicial++
                obtenerSiguienteCaracter()

                if(caracterActual == '\'' && caracterActual != finCodigo){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.CARACTER, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
    }
    fun esCadena(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == '"') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '"') {
                lexema += caracterActual
                almacenarError(lexema, "No se completo la cadena", filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }
            while (caracterActual != finCodigo) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '"') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.CADENA,
                        filaInicial, columnaInicial)
                    return true
                }
            }
            almacenarError(lexema, "No se completo la cadena", filaActual, columnaInicial)
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }
    fun esClase(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual.isDigit()) {
            return false
        }
        if (caracterActual == '[') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == ']') {
                lexema += caracterActual
                almacenarError(lexema, "No se completo la clase", filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                println(lexema)
                return true
            }
            var i = 0
            while (caracterActual != finCodigo && !caracterActual.isDigit() && caracterActual.isLetter() &&
                    i < MAX_LONG) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == ']') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema,Categoria.CLASE, filaInicial, columnaInicial)
                    return true
                }
                i++
            }
            almacenarError(lexema, "No se completo la clase", filaInicial, columnaInicial)
            return true
        }
        return false
    }
    fun esPalabraReservadaWhile(): Boolean {
        val posicionInicial = posicionActual
        val filaInicial = filaActual
        val columnaInicial = columnaActual
        var lexema = ""
        if (Character.isDigit(caracterActual)) {
            return false
        }
        if (caracterActual == 'w') {
            obtenerSiguienteCaracter()
            lexema += 'w'
            if (caracterActual == 'h') {
                obtenerSiguienteCaracter()
                lexema += 'h'
                if (caracterActual == 'i') {
                    obtenerSiguienteCaracter()
                    lexema += 'i'
                    if (caracterActual == 'l') {
                        obtenerSiguienteCaracter()
                        lexema += 'l'
                        if (caracterActual == 'e') {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                            return true
                        } else {
                            hacerBT(posicionInicial, filaInicial,
                                columnaInicial)
                           return false
                        }
                    } else {
                        hacerBT(posicionInicial, filaInicial,
                            columnaInicial)
                        return false
                    }
                } else {
                    hacerBT(posicionInicial, filaInicial,
                        columnaInicial)
                    return false
                }
            } else {
                hacerBT(posicionInicial, filaInicial,
                    columnaInicial)
                return false
            }
        } else {
            hacerBT(posicionInicial, filaInicial,
                columnaInicial)
            return false
        }
    }

    fun  esComentario():Boolean{
        var lexema =""
        var filaInicial = filaActual
        var columnaInicial= columnaActual
        var posicionInicial= posicionActual

        if (Character.isDigit(caracterActual)) {
            return false
        }
        if (caracterActual == '#') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '#') {
                lexema += caracterActual
                almacenarError(lexema, "No se completo el comentario", filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }
            while (caracterActual != finCodigo) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '#') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.COMENTARIO, filaInicial, columnaInicial);
                    return true
                }
            }
            almacenarError(lexema, "No se completo el Comentario", filaInicial, columnaInicial)
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }

    fun  esComentarioBloque():Boolean{
        var lexema =""
        var filaInicial = filaActual
        var columnaInicial= columnaActual
        var posicionInicial= posicionActual

        if (Character.isDigit(caracterActual)) {
            return false
        }
        if (caracterActual == '~') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '~') {
                lexema += caracterActual
                almacenarError(lexema, "No se completo el comentario", filaInicial, columnaInicial)
                obtenerSiguienteCaracter()
                return true
            }
            while (caracterActual != finCodigo) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual == '~') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.COMENTARIO_BLOQUE, filaInicial, columnaInicial);
                    return true
                }
            }
            almacenarError(lexema, "No se completo el Comentario de bloque", filaInicial, columnaInicial)
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }

    fun esEntero():Boolean{
        if (caracterActual.isDigit()){

            var lexema =""
            var filaInicial = filaActual
            var columnaInicial= columnaActual
            var posicionInicial= posicionActual

            lexema+=caracterActual
            obtenerSiguienteCaracter()

            while( caracterActual.isDigit()){
                lexema+=caracterActual
                obtenerSiguienteCaracter()
            }
            if(caracterActual == '.'){
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
            almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaActual)
            return true
        }
        // rechaso inmediato
        return false
    }

    fun esDecimal(): Boolean {
        val posicionInicial = posicionActual
        if (caracterActual == '.' || caracterActual.isDigit()) {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            if (caracterActual == '.') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual.isDigit()) {
                    while (caracterActual.isDigit()) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                    almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)
                    return true
                }

            } else {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                if (caracterActual == '.') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual.isDigit()) {
                        while (caracterActual.isDigit()) {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }
                    }
                    almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial,
                        columnaInicial)
                    return false
                }
            }
            while (caracterActual.isDigit()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }

        }
        return false
    }


    fun obtenerSiguienteCaracter()
    {
        if (posicionActual== codigoFuente.length -1){
            caracterActual=finCodigo
        }
        else {
            if (caracterActual == '\n') {
                filaActual++
                columnaActual = 0
            } else {
                columnaActual++
            }
            posicionActual++
            caracterActual = codigoFuente[posicionActual]
        }
    }
}