package co.edu.uniquindio.compiladores.controlador

import co.edu.uniquindio.compiladores.AnalizadorLexico
import co.edu.uniquindio.compiladores.Error
import co.edu.uniquindio.compiladores.Token
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico
import javafx.concurrent.Task
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory

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


//tabla para los errores
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
                val uc = sintaxis.esUnidadDeCompilacion()
                if (uc != null) {
                    arbolVisual.root = uc.getArbolVisual ()
                }
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

        // tabla Errores

        LexemaError?.cellValueFactory = PropertyValueFactory<Error, String>("lexema")
        ErrorError?.cellValueFactory = PropertyValueFactory<Error, String>("errorMsg")
        FilaError?.cellValueFactory = PropertyValueFactory<Error, Int>("fila")
        ColumnaError?.cellValueFactory = PropertyValueFactory<Error, Int>("columna")
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


}