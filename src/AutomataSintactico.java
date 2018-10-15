
import java.util.ArrayList;

public class AutomataSintactico {

    private ArrayList<Integer> identificadorToken;
    public ArrayList<String> tokens1;
    boolean estadoAceptacion;
    int tokenAnalizado, estado;
    
    public ArrayList <Cadena> cadenas = new ArrayList();

    public AutomataSintactico(ArrayList<Integer> tokens) {
        this.identificadorToken = tokens; 
          
        
    }
  
    public void automata() {
        estadoAceptacion = false;
        tokenAnalizado = 0;
        estado = 0; 
        boolean estadoError = false;
        boolean terminadoEnFinalizar = false;
        final String MENSAJE_ERROR_PALABRA_RESERVADA="Se esperaba la palabra reservada: "  ;
        final String MENSAJE_ERROR_SIMBOLO="Se esperaba el simbolo: "  ;
        final String MENSAJE_ERROR_IDENTIFICADOR="Se esperaba un identificador"  ;
        final String MENSAJE_ERROR_CADENA_CARACTERES="Se esperaba una cadena de constantes"  ;

        for (int i = 0; i < identificadorToken.size(); i++) {
            tokenAnalizado = identificadorToken.get(i);

            if (!(estadoAceptacion) || !(estadoError)) {

                switch (estado) {
                    case 0:
                        //Si es "Iniciar"
                        if (tokenAnalizado == 100) {
                            estado = 1;

                        } else if (tokenAnalizado == 108) {
                            estado = 200;
                         //   System.out.println("Paso por el estado " + estado);
                            System.out.println("Programa compilado correctamente");
                            if (i == identificadorToken.size()-1) {
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
                        if (tokenAnalizado == 101) {
                            estado = 2;
                        } else if (tokenAnalizado == 108) {
                            estado = 200;
                          //  System.out.println("Paso por el estado " + estado);
                            System.out.println("Programa compilado correctamente");
                            if (i == identificadorToken.size()-1) {
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
                        if (tokenAnalizado == 102 || tokenAnalizado == 103) {
                            estado = 3;
                        } else if (tokenAnalizado == 108) {
                            estado = 200;
                         //    System.out.println("Paso por el estado " + estado);
                            System.out.println("Programa compilado correctamente");
                            if (i == identificadorToken.size()-1) {
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
                        if (tokenAnalizado == 500) {
                            estado = 5;
                        } else if (tokenAnalizado == 102 || tokenAnalizado == 103) {
                            estado = 3;
                        } else if (tokenAnalizado == 105) {
                            estado = 6;

                        } else if (tokenAnalizado == 106) {
                            estado = 13;

                        } else if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 16;

                        } else if (tokenAnalizado == 104) {
                            estado = 20;

                        } else if (tokenAnalizado == 108) {
                            estado = 200;
                         //    System.out.println("Paso por el estado " + estado);
                            System.out.println("Programa compilado correctamente");
                            if (i == identificadorToken.size()-1) {
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
                        if (tokenAnalizado == 501) {
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
                        if (tokenAnalizado == 503) {
                            estado = 9;
                        } else if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 8;
                        } else if (tokenAnalizado ==502) {
                            estado = 12;
                        }
                        else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                             imprimirErrorMensaje(MENSAJE_ERROR_IDENTIFICADOR, "  o se esteraba "+MENSAJE_ERROR_SIMBOLO + "\"");
                        }
                         //System.out.println("Paso por el estado " + estado);
                        break;

                    case 8:
                        //Si concatenacion de cadenas osea un +
                        if (tokenAnalizado == 800) {
                            estado = 7;
                        } else if (tokenAnalizado == 502) {
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
                        if (tokenAnalizado == 503) {
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
                        if (tokenAnalizado == 800) {
                            estado = 7;
                        } else if (tokenAnalizado == 502) {
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
                        if (tokenAnalizado == 504) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                                  imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, ";"+" es decir fin de sentencia");
                             
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
                        if (tokenAnalizado == 504) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, ";"+" es decir fin de sentencia");
                            
                        }

                        break;

                    case 15:

                        break;

                    case 16:
                        if (tokenAnalizado == 505) {
                            estado = 17;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                             imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "=" );
                            
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
                        if (tokenAnalizado == 800 || tokenAnalizado == 801) {
                            estado = 17;
                        } else if (tokenAnalizado == 504) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, ";"+" es decir fin de sentencia o se esperaba un operador" );
                        }

                        break;

                    case 19:

                        break;
//COMIENZA EL DESMADRE
                    case 20:

                        if (tokenAnalizado == 105) {
                            estado = 21;

                        } else if (tokenAnalizado == 106) {
                            estado = 28;

                        } else if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 30;
                        } else if (tokenAnalizado == 107) {
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
                        if (tokenAnalizado == 501) {
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
                        if (tokenAnalizado == 503) {
                            estado = 24;
                        } else if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 23;
                        } else if (tokenAnalizado ==502) {
                            estado = 27;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                             imprimirErrorMensaje(MENSAJE_ERROR_IDENTIFICADOR, "  o se esteraba "+MENSAJE_ERROR_SIMBOLO + "\"");
                        }
                        // System.out.println("Paso por el estado " + estado);
                        break;

                    case 23:
                        //Si concatenacion de cadenas osea un +
                        if (tokenAnalizado == 800) {
                            estado = 22;
                        } else if (tokenAnalizado == 502) {
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
                        if (tokenAnalizado == 503) {
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
                        if (tokenAnalizado == 800) {
                            estado = 22;
                        } else if (tokenAnalizado == 502) {
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
                        if (tokenAnalizado == 504) {
                            estado = 20;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                                imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, ";"+" es decir fin de sentencia");
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
                        if (tokenAnalizado == 504) {
                            estado = 20;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, ";"+" es decir fin de sentencia");
                        }

                        break;

                    case 30:
                        if (tokenAnalizado == 505) {
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
                        if (tokenAnalizado == 800 || tokenAnalizado == 801) {
                            estado = 31;
                        } else if (tokenAnalizado == 504) {
                            estado = 20;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                              imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, ";"+" es decir fin de sentencia o se esperaba un operador" );
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
                        if (tokenAnalizado == 507) {
                            estado = 40;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, " == ");
                        }

                        break;

                    case 40:
                        if (tokenAnalizado == 503) {
                            estado = 41;
                        } else if (tokenAnalizado == 508) {
                            estado = 43;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "\"  o '" );
                        }

                        break;

                    case 41:
                        if (tokenAnalizado > 299 && tokenAnalizado < 401) {
                            estado = 42;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_CADENA_CARACTERES, "" );
                        }
                       //  System.out.println("Paso por el estado " + estado);
                        break;

                    case 42:
                        if (tokenAnalizado == 503) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                            imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, "\" " );
                        }

                        break;

                    case 43:
                        if (tokenAnalizado > 299 && tokenAnalizado < 401) {
                            estado = 44;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                             imprimirErrorMensaje(MENSAJE_ERROR_CADENA_CARACTERES, "" );
                        }
                      //   System.out.println("Paso por el estado " + estado);
                        break;

                    case 44:
                        if (tokenAnalizado == 508) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                             imprimirErrorMensaje(MENSAJE_ERROR_SIMBOLO, " '" );
                        }

                        break;

                    case 200:
                        //Estado de aceptacion
                        i = identificadorToken.size();
                        estadoAceptacion = true;
                        if (i == identificadorToken.size()) {
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

    public void imprimirError(int posicion) {
        System.out.println("  Tienes un error en la posicion " + (posicion) + ", no se identifico la cadena " + tokens1.get(posicion - 1));

    }

    public void setTokens1(ArrayList<String> tokens1) {
        this.tokens1 = tokens1;
    }
    
    public void imprimirErrorMensaje(String mensaje,String simbolo){
        System.out.println(mensaje + simbolo);
    }

}
