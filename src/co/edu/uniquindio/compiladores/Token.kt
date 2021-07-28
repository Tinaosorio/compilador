package co.edu.uniquindio.compiladores

class Token  (var lexema:String, var categoria:Categoria, var fila:Int, var columna:Int) {
    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }
    fun getJavaCode():String{
        if( categoria == Categoria.PALABRA_RESERVADA){
            if( lexema == "decimal" ){
                return "double"
            }else if( lexema == "string"){
                return "String"
            }else if( lexema == "bool"){
                return "boolean"
            }
        } else if(categoria == Categoria.METODO) {
            return lexema.replace("%", "")
        } else if(categoria == Categoria.VARIABLE) {
            return lexema.replace("°", "")
        }
        return lexema
    }
}