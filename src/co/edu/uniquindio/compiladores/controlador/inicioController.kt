package co.edu.uniquindio.compiladores.controlador

import co.edu.uniquindio.compiladores.AnalizadorLexico
import co.edu.uniquindio.compiladores.Error
import co.edu.uniquindio.compiladores.Token
import co.edu.uniquindio.compiladores.semantica.ErrorSemantico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSemantico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico
import co.edu.uniquindio.compiladores.sintaxis.ErrorSintactico
import co.edu.uniquindio.compiladores.sintaxis.UnidadDeCompilacion
import javafx.collections.FXCollections
import javafx.concurrent.Task
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.io.File


class inicioController {
    @FXML
    var tablaLexica: TableView<Token>? = null

    @FXML
    var lexema: TableColumn<Token, String>? = null

    @FXML
    var token: TableColumn<Token, String>? = null

    @FXML
    var fila: TableColumn<Token, Int>? = null

    @FXML
    var columna: TableColumn<Token, Int>? = null

    @FXML lateinit var TxtCodigo: TextArea

    @FXML lateinit var arbolVisual: TreeView<String>


    //tabla para los errores lexicos
    @FXML
    var tablaError: TableView<Error>? = null

    @FXML
    var LexemaError: TableColumn<Error, String>? = null

    @FXML
    var ErrorError: TableColumn<Error, String>? = null

    @FXML
    var FilaError: TableColumn<Error, Int>? = null

    @FXML
    var ColumnaError: TableColumn<Error, Int>? = null

    //tabla errores sintacticos

    @FXML
    var tablaErroresSintacticos: TableView<ErrorSintactico>? = null

    @FXML
    var Mensaje: TableColumn<ErrorSintactico, String>? = null

    @FXML
    var FilaErrorSintactico: TableColumn<ErrorSintactico, Int>? = null

    @FXML
    var ColumnaErrorSintactico: TableColumn<ErrorSintactico, Int>? = null


    //tabla errores semanticos

    @FXML
    var tablaErroresSemanticos: TableView<ErrorSemantico>? = null

    @FXML
    var MensajeSemantico: TableColumn<ErrorSemantico, String>? = null

    @FXML
    var FilaErrorSemantico: TableColumn<ErrorSemantico, Int>? = null

    @FXML
    var ColumnaErrorSemantico: TableColumn<ErrorSemantico, Int>? = null

    var uc: UnidadDeCompilacion? = null


    @FXML
    fun AnalizarCodigo(e : ActionEvent) {
        if (TxtCodigo.text.isNotEmpty()) {

            val lexico = AnalizadorLexico(TxtCodigo.text)
            lexico.analizar()
            refresh(lexico.listaTokens)
            refreshTablaError(lexico.listaErrores)
            val msgOK = Alert(Alert.AlertType.INFORMATION)
            msgOK.headerText = "Codigo analizado"
            msgOK.showAndWait()

            if (lexico.listaErrores.isEmpty()) {

                val sintaxis = AnalizadorSintactico(lexico.listaTokens)
                uc = sintaxis.esUnidadDeCompilacion()
                if (uc != null) {
                    arbolVisual.root = uc!!.getArbolVisual()
                    val semantica = AnalizadorSemantico()
                    semantica.inicializar(uc!!)
                    semantica.llenarTablaSimbolos()
                    semantica.analizarSemantica()
                    traducirCodigo()
                    refreshTablaErrorSemantico(semantica.listaErrores)
                }
                refreshTablaErrorSintactico(sintaxis.listaErrores)
            } else {
                var alerta = Alert(Alert.AlertType.WARNING)
                alerta.headerText = "mensaje"
                alerta.contentText = "hay errorews lexicos en el codigo fuente"
            }
        }
    }

    @FXML
    fun BorrarEditor(){
        TxtCodigo.clear()
    }

    fun initialize() {

        lexema?.cellValueFactory = PropertyValueFactory<Token, String>("lexema")
        token?.cellValueFactory = PropertyValueFactory<Token, String>("categoria")
        fila?.cellValueFactory = PropertyValueFactory<Token, Int>("fila")
        columna?.cellValueFactory = PropertyValueFactory<Token, Int>("columna")

        // tabla Errores lexicos

        LexemaError?.cellValueFactory = PropertyValueFactory<Error, String>("lexema")
        ErrorError?.cellValueFactory = PropertyValueFactory<Error, String>("errorMsg")
        FilaError?.cellValueFactory = PropertyValueFactory<Error, Int>("fila")
        ColumnaError?.cellValueFactory = PropertyValueFactory<Error, Int>("columna")

        //tabla errores sintacticos

        Mensaje?.cellValueFactory = PropertyValueFactory<ErrorSintactico, String>("errorMsg")
        FilaErrorSintactico?.cellValueFactory = PropertyValueFactory<ErrorSintactico, Int>("fila")
        ColumnaErrorSintactico?.cellValueFactory = PropertyValueFactory<ErrorSintactico, Int>("columna")

        //tabla errores semanticos

        MensajeSemantico?.cellValueFactory = PropertyValueFactory<ErrorSemantico, String>("errorMsg")
        FilaErrorSemantico?.cellValueFactory = PropertyValueFactory<ErrorSemantico, Int>("fila")
        ColumnaErrorSemantico?.cellValueFactory = PropertyValueFactory<ErrorSemantico, Int>("columna")

    }

    fun refresh(listaTokens: List<Token>) {
        val task = object : Task<List<Token>> () {
            override fun call(): List<Token> {
                return listaTokens
            }

            override fun succeeded() {
                tablaLexica?.items?.clear()
                tablaLexica?.items?.addAll( value )
            }
        }

        Thread(task).start()
    }
    fun refreshTablaError(listaError: List<Error>) {
        val task = object : Task<List<Error>> () {
            override fun call(): List<Error> {
                return listaError
            }

            override fun succeeded() {
                tablaError?.items?.clear()
                tablaError?.items?.addAll( value )
            }
        }

        Thread(task).start()
    }

    fun refreshTablaErrorSintactico(listaErrorSintactico: List<ErrorSintactico>) {
        val task = object : Task<List<ErrorSintactico>> () {
            override fun call(): List<ErrorSintactico> {
                return listaErrorSintactico
            }

            override fun succeeded() {
                tablaErroresSintacticos?.items?.clear()
                tablaErroresSintacticos?.items?.addAll( value )
            }
        }

        Thread(task).start()
    }

    fun refreshTablaErrorSemantico(listaErrorSemantico: List<ErrorSemantico>) {
        val task = object : Task<List<ErrorSemantico>>() {
            override fun call(): List<ErrorSemantico> {
                return listaErrorSemantico
            }

            override fun succeeded() {
                tablaErroresSemanticos?.items?.clear()
                tablaErroresSemanticos?.items?.addAll( value )
            }
        }

        Thread(task).start()
    }


    @FXML
    fun traducirCodigo() {
        if (uc != null) {
            val codigo = uc!!.getJavaCode()
            File("src/Principal.java").writeText(codigo)

            val runtime = Runtime.getRuntime().exec("java src/Principal.java")
            runtime.waitFor()
            Runtime.getRuntime().exec("java Principal", null, File("src"))
        } else {
            val alerta = Alert(Alert.AlertType.ERROR)
            alerta.headerText = null
            alerta.contentText = "el codigo no se puede traducir porque tiene errores"
            alerta.show()
        }

        println(uc!!.getJavaCode())
    }
}