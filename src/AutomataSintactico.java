
import java.util.ArrayList;

public class AutomataSintactico {

    private ArrayList<Integer> identificadorToken;
    public ArrayList<String> tokens1;
    boolean estadoAceptacion;
    int tokenAnalizado, estado;

    public AutomataSintactico(ArrayList<Integer> tokens) {
        this.identificadorToken = tokens;
    }

    public void automata() {
        estadoAceptacion = false;
        tokenAnalizado = 0;
        estado = 0;
        boolean estadoError = false;

        for (int i = 0; i < identificadorToken.size(); i++) {
            tokenAnalizado = identificadorToken.get(i);

            if (!(estadoAceptacion) || !(estadoError)) {

                switch (estado) {
                    case 0:
                        //Si es "Iniciar"
                        if (tokenAnalizado == 100) {
                            estado = 1;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 1:
                        //Si es "Variables"
                        if (tokenAnalizado == 101) {
                            estado = 2;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 2:
                        //aqui vamos, en ver si sigue una declaracion de String o int
                        //Si es "String" o "int"
                        if (tokenAnalizado == 102 || tokenAnalizado == 103) {
                            estado = 3;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 3:
                        //Si un identificador"
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
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
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }

                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 5:
                        //Si es otra variable
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 6:
                        //Si es parentesis
                        if (tokenAnalizado == 501) {
                            estado = 7;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 7:
                        //Si comilla
                        if (tokenAnalizado == 503) {
                            estado = 9;
                        } else if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 8;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
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
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 9:
                        //Si cadena de caracteres
                        if (tokenAnalizado > 299 && tokenAnalizado < 401) {
                            estado = 10;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 10:
                        //Si comilla
                        if (tokenAnalizado == 503) {
                            estado = 11;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
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
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 12:
                        //Si termina con ;
                        if (tokenAnalizado == 504) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 13:
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 14;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }

                        break;

                    case 14:
                        if (tokenAnalizado == 504) {
                            estado = 4;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
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
                        }

                        break;

                    case 17:
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 18;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
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
                            estado = 37;

                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }

                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 21:
                        //Si es parentesis
                        if (tokenAnalizado == 501) {
                            estado = 22;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 22:
                        //Si comilla
                        if (tokenAnalizado == 503) {
                            estado = 24;
                        } else if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 23;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
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
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 24:
                        //Si cadena de caracteres
                        if (tokenAnalizado > 299 && tokenAnalizado < 401) {
                            estado = 25;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 25:
                        //Si comilla
                        if (tokenAnalizado == 503) {
                            estado = 26;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
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
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 27:
                        //Si termina con ;
                        if (tokenAnalizado == 504) {
                            estado = 20;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }
                        System.out.println("Paso por el estado " + estado);
                        break;

                    case 28:
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 29;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }

                        break;

                    case 29:
                        if (tokenAnalizado == 504) {
                            estado = 20;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }

                        break;
   
                    case 30:
                        if (tokenAnalizado == 505) {
                            estado = 21;
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
                        }

                        break;
//TERMINA EL DESMADRE

                    case 37:
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 38;
                        } else {
                            estado = 400;
                            estadoError = true;
                            imprimirError(i + 1);
                        }

                        break;

                    case 38:

                        break;

                    case 39:

                        break;

                    case 40:

                        break;

                    case 200:
                        //Estado de aceptacion
                        i = identificadorToken.size();
                        estadoAceptacion = true;
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

    }

    public void imprimirError(int posicion) {
        System.out.println("  Tienes un error en la posicion " + (posicion) + ", no se identifico la cadena " + tokens1.get(posicion - 1));

    }

    public void setTokens1(ArrayList<String> tokens1) {
        this.tokens1 = tokens1;
    }

}
