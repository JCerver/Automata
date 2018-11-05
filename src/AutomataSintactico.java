
import java.util.ArrayList;

public class AutomataSintactico {

    boolean estadoAceptacion;
    int tokenAnalizado, estado;

    public ArrayList<Cadena> objetosCadenas = new ArrayList();
    ArrayList<SimbologiaToken> simbologia;

    public AutomataSintactico() {

    }

    public AutomataSintactico(ArrayList<SimbologiaToken> simbologia) { 

        this.simbologia = new ArrayList<SimbologiaToken>(simbologia);

    }

    public void automata() {
        estadoAceptacion = false;
        tokenAnalizado = 0;
        estado = 0;
        boolean estadoError = false;
        boolean terminadoEnFinalizar = false;
        final String MENSAJE_ERROR_PALABRA_RESERVADA = "Se esperaba la palabra reservada: ";
        final String MENSAJE_ERROR_SIMBOLO = "Se esperaba el simbolo: ";
        final String MENSAJE_ERROR_IDENTIFICADOR = "Se esperaba un identificador";
        final String MENSAJE_ERROR_CADENA_CARACTERES = "Se esperaba una cadena de constantes";

        for (int i = 0; i < objetosCadenas.size(); i++) {
            tokenAnalizado = objetosCadenas.get(i).getTokenAsignado();

            if (!(estadoAceptacion) || !(estadoError)) {

                switch (estado) {
                    case 0:
                        //Si es "Iniciar"
                        if (tokenAnalizado == obtenerTokenn("Iniciar")) {
                            estado = 1;

                        } else if (tokenAnalizado == obtenerTokenn("Finalizar")) {
                            estado = 200;
                            //   System.out.println("Paso por el estado " + estado);
                            System.out.println("Programa compilado correctamente");
                            if (i == objetosCadenas.size() - 1) {
                                terminadoEnFinalizar = true;
                            }

                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_PALABRA_RESERVADA, "Inicial");
                        }
                        //  System.out.println("Paso por el estado " + estado);
                        break;

                    case 1:
                        //Si es "Variables"
                        if (tokenAnalizado == obtenerTokenn("Variables")) {
                            estado = 2;
                        } else if (tokenAnalizado == obtenerTokenn("Finalizar")) {
                            estado = 200;
                            //  System.out.println("Paso por el estado " + estado);
                            System.out.println("Programa compilado correctamente");
                            if (i == objetosCadenas.size() - 1) {
                                terminadoEnFinalizar = true;
                            }

                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_PALABRA_RESERVADA, "Variables");
                        }
                        //  System.out.println("Paso por el estado " + estado);
                        break;

                    case 2:
                        //aqui vamos, en ver si sigue una declaracion de String o int
                        //Si es "String" o "int"
                        if (tokenAnalizado == obtenerTokenn("int") || tokenAnalizado == obtenerTokenn("String")) {
                            estado = 3;
                        } else if (tokenAnalizado == obtenerTokenn("Finalizar")) {
                            estado = 200;
                            //    System.out.println("Paso por el estado " + estado);
                            System.out.println("Programa compilado correctamente");
                            if (i == objetosCadenas.size() - 1) {
                                terminadoEnFinalizar = true;
                            }

                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_PALABRA_RESERVADA, "del tipo de dato String o int");
                        }
                        //System.out.println("Paso por el estado " + estado);
                        break;

                    case 3:
                        //Si un identificador"
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_IDENTIFICADOR, "");
                        }
                        //System.out.println("Paso por el estado " + estado);
                        break;

                    case 4:
                        //Si es una coma 
                        if (tokenAnalizado == obtenerTokenn(",")) {
                            estado = 5;
                        } else if (tokenAnalizado == obtenerTokenn("int") || tokenAnalizado == obtenerTokenn("String")) {
                            estado = 3;
                        } else if (tokenAnalizado == obtenerTokenn("printf")) {
                            estado = 6;

                        } else if (tokenAnalizado == obtenerTokenn("captura")) {
                            estado = 13;

                        } else if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 16;

                        } else if (tokenAnalizado == obtenerTokenn("do")) {
                            estado = 20;

                        } else if (tokenAnalizado == obtenerTokenn("Finalizar")) {
                            estado = 200;
                            //    System.out.println("Paso por el estado " + estado);
                            System.out.println("Programa compilado correctamente");
                            if (i == objetosCadenas.size() - 1) {
                                terminadoEnFinalizar = true;
                            }

                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje("Se esperaba un inicio de sentencia o declaración de variables", "");
                        }

                        //System.out.println("Paso por el estado " + estado);
                        break;

                    case 5:
                        //Si es otra variable
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_IDENTIFICADOR, "");
                        }
                        //System.out.println("Paso por el estado " + estado);
                        break;

                    case 6:
                        //Si es parentesis
                        if (tokenAnalizado == obtenerTokenn("(")) {
                            estado = 7;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, " ( ");
                        }
                        //System.out.println("Paso por el estado " + estado);
                        break;

                    case 7:
                        //Si comilla
                        if (tokenAnalizado == obtenerTokenn("\"")) {
                            estado = 9;
                        } else if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 8;
                        } else if (tokenAnalizado == obtenerTokenn(")")) {
                            estado = 12;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_IDENTIFICADOR, "  o se esteraba " + MENSAJE_ERROR_SIMBOLO + "\"");
                        }
                        //System.out.println("Paso por el estado " + estado);
                        break;

                    case 8:
                        //Si concatenacion de cadenas osea un +
                        if (tokenAnalizado == obtenerTokenn("+")) {
                            estado = 7;
                        } else if (tokenAnalizado == obtenerTokenn(")")) {
                            estado = 12;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "+");
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "(");
                        }
                        //System.out.println("Paso por el estado " + estado);
                        break;

                    case 9:
                        //Si cadena de caracteres
                        if (tokenAnalizado > 299 && tokenAnalizado < 401) {
                            estado = 10;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_CADENA_CARACTERES, "");
                        }
                        //System.out.println("Paso por el estado " + estado);
                        break;

                    case 10:
                        //Si comilla
                        if (tokenAnalizado == obtenerTokenn("\"")) {
                            estado = 11;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "\"");
                        }
                        //System.out.println("Paso por el estado " + estado);
                        break;

                    case 11:
                        //Si concatenacion de cadenas osea un +
                        if (tokenAnalizado == obtenerTokenn("+")) {
                            estado = 7;
                        } else if (tokenAnalizado == obtenerTokenn(")")) {
                            estado = 12;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "+");
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "(");
                        }
                        // System.out.println("Paso por el estado " + estado);
                        break;

                    case 12:
                        //Si termina con ;
                        if (tokenAnalizado == obtenerTokenn(";")) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, ";" + " es decir fin de sentencia");

                        }
                        // System.out.println("Paso por el estado " + estado);
                        break;

                    case 13:
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 14;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_IDENTIFICADOR, " ");
                        }

                        break;

                    case 14:
                        if (tokenAnalizado == obtenerTokenn(";")) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, ";" + " es decir fin de sentencia");

                        }

                        break;

                    case 15:

                        break;

                    case 16:
                        if (tokenAnalizado == obtenerTokenn("=")) {
                            estado = 17;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "=");

                        }

                        break;

                    case 17:
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 18;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_IDENTIFICADOR, " ");
                        }

                        break;

                    case 18:
                        if (tokenAnalizado == obtenerTokenn("+") || tokenAnalizado == obtenerTokenn("-")) {
                            estado = 17;
                        } else if (tokenAnalizado == obtenerTokenn(";")) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, ";" + " es decir fin de sentencia o se esperaba un operador");
                        }

                        break;

                    case 19:

                        break;
//COMIENZA EL DESMADRE
                    case 20:

                        if (tokenAnalizado == obtenerTokenn("printf")) {
                            estado = 21;

                        } else if (tokenAnalizado == obtenerTokenn("captura")) {
                            estado = 28;

                        } else if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 30;
                        } else if (tokenAnalizado == obtenerTokenn("while")) {
                            estado = 38;

                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje("Se esperaba un inicio de sentencia o declaración de variables", "");
                            imprimirErrorMensaje(MENSAJE_ERROR_PALABRA_RESERVADA, "while");
                        }

                        //  System.out.println("Paso por el estado " + estado);
                        break;

                    case 21:
                        //Si es parentesis
                        if (tokenAnalizado == obtenerTokenn("(")) {
                            estado = 22;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, " ( ");
                        }
                        //  System.out.println("Paso por el estado " + estado);
                        break;

                    case 22:
                        //Si comilla
                        if (tokenAnalizado == obtenerTokenn("\"")) {
                            estado = 24;
                        } else if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 23;
                        } else if (tokenAnalizado == obtenerTokenn(")")) {
                            estado = 27;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_IDENTIFICADOR, "  o se esteraba " + MENSAJE_ERROR_SIMBOLO + "\"");
                        }
                        // System.out.println("Paso por el estado " + estado);
                        break;

                    case 23:
                        //Si concatenacion de cadenas osea un +
                        if (tokenAnalizado == obtenerTokenn("+")) {
                            estado = 22;
                        } else if (tokenAnalizado == obtenerTokenn(")")) {
                            estado = 27;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "+");
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "(");
                        }
                        //  System.out.println("Paso por el estado " + estado);
                        break;

                    case 24:
                        //Si cadena de caracteres
                        if (tokenAnalizado > 299 && tokenAnalizado < 401) {
                            estado = 25;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_CADENA_CARACTERES, "");
                        }
                        // System.out.println("Paso por el estado " + estado);
                        break;

                    case 25:
                        //Si comilla
                        if (tokenAnalizado == obtenerTokenn("\"")) {
                            estado = 26;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "\"");
                        }
                        //  System.out.println("Paso por el estado " + estado);
                        break;

                    case 26:
                        //Si concatenacion de cadenas osea un +
                        if (tokenAnalizado == obtenerTokenn("+")) {
                            estado = 22;
                        } else if (tokenAnalizado == obtenerTokenn(")")) {
                            estado = 27;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "+");
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "(");
                        }
                        //   System.out.println("Paso por el estado " + estado);
                        break;

                    case 27:
                        //Si termina con ;
                        if (tokenAnalizado == obtenerTokenn(";")) {
                            estado = 20;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, ";" + " es decir fin de sentencia");
                        }
                        //   System.out.println("Paso por el estado " + estado);
                        break;

                    case 28:
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 29;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_IDENTIFICADOR, " ");
                        }

                        break;

                    case 29:
                        if (tokenAnalizado == obtenerTokenn(";")) {
                            estado = 20;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, ";" + " es decir fin de sentencia");
                        }

                        break;

                    case 30:
                        if (tokenAnalizado == obtenerTokenn("=")) {
                            estado = 31;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }

                        break;

                    case 31:
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 32;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }

                        break;

                    case 32:
                        if (tokenAnalizado == obtenerTokenn("+") || tokenAnalizado == obtenerTokenn("-")) {
                            estado = 31;
                        } else if (tokenAnalizado == obtenerTokenn(";")) {
                            estado = 20;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, ";" + " es decir fin de sentencia o se esperaba un operador");
                        }

                        break;
//TERMINA EL DESMADRE

                    case 37:

                        break;

                    case 38:
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 39;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_IDENTIFICADOR, " ");

                        }

                        break;

                    case 39:
                        if (tokenAnalizado == obtenerTokenn("==")) {
                            estado = 40;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, " == ");
                        }

                        break;

                    case 40:
                        if (tokenAnalizado == obtenerTokenn("\"")) {
                            estado = 41;
                        } else if (tokenAnalizado == obtenerTokenn("'")) {
                            estado = 43;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "\"  o '");
                        }

                        break;

                    case 41:
                        if (tokenAnalizado > 299 && tokenAnalizado < 401) {
                            estado = 42;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_CADENA_CARACTERES, "");
                        }
                        //  System.out.println("Paso por el estado " + estado);
                        break;

                    case 42:
                        if (tokenAnalizado == obtenerTokenn("\"")) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "\" ");
                        }

                        break;

                    case 43:
                        if (tokenAnalizado > 299 && tokenAnalizado < 401) {
                            estado = 44;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_CADENA_CARACTERES, "");
                            
                        }
                        //   System.out.println("Paso por el estado " + estado);
                        break;

                    case 44:
                        if (tokenAnalizado == obtenerTokenn("'")) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, " '");
                        }

                        break;

                    case 200:
                        //Estado de aceptacion
                        i = objetosCadenas.size();
                        estadoAceptacion = true;
                        if (i == objetosCadenas.size()) {
                            terminadoEnFinalizar = true;
                        }

                        break;

                    case 400:
                        //Estado de error 
                        estadoError = true;
                        estadoAceptacion = false;

                        break;

                    default:
                        throw new AssertionError();
                }

            }

        }

        if (!terminadoEnFinalizar) {
            //System.out.println("Programa inconcluso.   Se esperaba la llave de cierre Finalizar,  error general ");
            System.out.println("Programa inconcluso,  error general ");

        }

    }
    
    private int obtenerTokenn(String lexema) {
        int valorToken = 0;
        for (int i = 0; i < simbologia.size(); i++) {
            if (simbologia.get(i).getLexema()== lexema) {
                valorToken = simbologia.get(i).getToken();
            }
        }
        
        return valorToken;
    }
    

    public void imprimirError(int posicion) {
        System.out.println("  Tienes un error en la posicion " + (posicion) + ", no se identifico la cadena " + objetosCadenas.get(posicion - 1).getLexema());

    }

    public void imprimirErrorMensaje(String mensaje, String simbolo) {
        System.out.println(mensaje + simbolo);
    }

    public ArrayList<Cadena> getObjetosCadenas() {
        return objetosCadenas;
    }

    public void setObjetosCadenas(ArrayList<Cadena> objetosCadenas) {
        this.objetosCadenas = objetosCadenas;
    }

    public ArrayList<SimbologiaToken> getSimbologia() {
        return simbologia;
    }

    public void setSimbologia(ArrayList<SimbologiaToken> simbologia) {
        this.simbologia = simbologia;
    }

}
