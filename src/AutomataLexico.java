
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jcerver
 */
public class AutomataLexico {

    private String cadena = "";
    private int estado;
    private char simbolo;
    private boolean cadenaIncorrecta = false;
    private boolean estadoAceptacion = true;
    private String palabraAnalizada = "";

    private String palabrasReservadasDelLenguaje[] = {"Iniciar", "Variables", "int", "String", "do", "printf", "captura", "while", "Finalizar"};
    private String simbolosLenguaje[] = {"(", ")", ",", ";"};
    private String operadoresLenguaje[] = {"+", "-"};

    private ArrayList<String> caracteresAnalizados = new ArrayList<String>();

    private ArrayList<String> palabrasReservadasAnalizadas = new ArrayList<String>();
    private ArrayList<String> cadenasDeConstantesAnalizadas = new ArrayList<String>();
    private ArrayList<String> identificadoresAnalizados = new ArrayList<String>();
    private ArrayList<String> numerosAnalizados = new ArrayList<String>();
    private ArrayList<String> simbolosAnalizados = new ArrayList<String>();
    private ArrayList<String> operadoresAnalizados = new ArrayList<String>();
    private ArrayList<String> erroresAnalizados = new ArrayList<String>();

    //Aqui estamos guardando los tokens, es decir 1 Iniciar Palabra_reservada, 2 Iniciar Palabra_Reservada, etc
    // el numero del token es la posicion del arreglo, la palbra leida estará en tokens1, lo que es esa palabra leida estara en tokens2
    private ArrayList<String> tokens1 = new ArrayList<String>();
    private ArrayList<String> tokens2 = new ArrayList<String>();
    private ArrayList<Integer> identificadorToken = new ArrayList<Integer>();

    public ArrayList <Cadena> cadenas = new ArrayList();
    
    
    int contadorGlobal;
    
    public AutomataLexico(String cadena) {
        this.cadena = cadena;
        this.estado = 0;
    }

    public void analizar() {
        boolean puedeSerCadena = false;
        boolean puedeSerCaracter = false;
        
        estadoAceptacion = false;
        palabraAnalizada = "";

        String simbolo = "";
        String simboloSig = "";

        String expresionPalabrasReservadas = "[A-Za-z]";
        String expresionIdentificador = "[A-Za-z0-9]";
        String expresionNumero = "[0-9]";

        for (int i = 0; i < cadena.length(); i++) {
            if (!(estado == 50)) {

                simbolo = String.valueOf(cadena.charAt(i));
                if (i < (cadena.length() - 1)) {
                    simboloSig = String.valueOf(cadena.charAt(i + 1));
                   // System.out.println("Si verifico sig caracter ");
                }

                if (!(simbolo.equals(" "))) {
                    caracteresAnalizados.add(simbolo);
                }

                switch (estado) {
                    case 0:
                     //   System.out.println("entro al caso 0");

                        if (simbolo.equals(" ")) {
                            estado = 0;
                        } else if (simbolo.matches(expresionPalabrasReservadas)) {
                            if (!simboloSig.matches(expresionIdentificador)) {
                                palabraAnalizada += simbolo;
                                analizarIdentificador();
                                estado = 0;
                            } else {
                                estado = 1;
                                palabraAnalizada += simbolo;
                                //System.out.println("como que es identificador");
                            }
                        } else if (simbolo.matches(expresionNumero)) {
                            estado = 2;
                            palabraAnalizada += simbolo;
                        } else if (isSimbolo(simbolo)) {

                            palabraAnalizada += simbolo;
                            analizarSimbolo();
                            estado = 0;
                        } else if (isOperador(simbolo)) {

                            palabraAnalizada += simbolo;
                            analizarOperador();
                            estado = 0;
                        } else if (simbolo.equals("\"")) {
                            palabraAnalizada += simbolo;
                            analizarSimbolo();
                            puedeSerCadena = true;

                            if (puedeSerCadena) {
                                estado = 10;
                                //puedeSerCadena=false;
                            } else {

                                estado = 0;
                            }
                            
                        }else if (simbolo.equals("'")) {
                            palabraAnalizada += simbolo;
                            analizarSimbolo();
                            puedeSerCaracter = true;

                            if (puedeSerCaracter) {
                                estado = 20;
                                //puedeSerCadena=false;
                            } else {

                                estado = 0;
                            }

                        } else if (simbolo.equals("=")) {
                            palabraAnalizada += simbolo;

                            if (!simboloSig.equals("=")) {

                                analizarSimbolo();//ya le esta pasando el simbolo ==
                                estado = 0;

                            } else {
                                estado = 7;
                            }

                        }

                        break;
                    case 1:

                        if (simboloSig.matches(expresionIdentificador)) {

                            estado = 1;
                            palabraAnalizada += simbolo;
                        } else {
                            palabraAnalizada += simbolo;
                            analizarIdentificador();
                            estado = 0;
                        }

                        break;

                    case 2:
                        /* Pattern patnum = Pattern.compile(expresionNumero);
                          Matcher matnum = patnum.matcher(cadena);*/
                        if (simbolo.matches(expresionPalabrasReservadas)) {
                            estado = 2;
                            palabraAnalizada += simbolo;
                        } else {
                            estado = 31;
                        }
                        break;
                    case 3:
                        //   analizarSimbolo();

                        break;
                    case 4:
                        // analizarOperador();
                        break;
                    case 5:

                        break;
                    case 6:
//                 
                        break;
                    case 7:
                        palabraAnalizada += simbolo;
                        analizarSimbolo();//ya le esta pasando el simbolo ==
                        estado = 0;

                        break;
                    case 8:

                        break;
                    case 9:

                        break;
                    case 10:
                        if (simbolo.equals("\"")) {
                        
                            analizarCadenaConstantes();

                            puedeSerCadena = false;
                            palabraAnalizada += simbolo;
                            analizarSimbolo();
                            estado = 0;
                        } else if(i==cadena.length()-1){
                            analizarCadenaConstantes();

                            puedeSerCadena = false;
                            estado = 0;
                        }
                        else {
                            palabraAnalizada += simbolo;
                            estado = 10;
                        }
                        break;
                    
           
                    case 20:
                        if (simbolo.equals("'")) {
                            analizarCadenaConstantes();

                            puedeSerCadena = false;
                            palabraAnalizada += simbolo;
                            analizarSimbolo();
                            estado = 0;
                        } else {
                            palabraAnalizada += simbolo;
                            estado = 20;
                        }
                        
                        break;
                        
                    case 30:
                        analizarIdentificador();
                        estado = 0;
                        break;
                    case 31:
                        analizarNumero();
                        estado = 0;
                        break;
                    case 50:
                        erroresAnalizados.add(palabraAnalizada);
                        palabraAnalizada = "";
                        estado = 0;
                        break;

                    default:
                        throw new AssertionError();
                }

            }

        }
    }

    private boolean isSimbolo(String s) {
        boolean simbolo = false;
        for (int i = 0; i < simbolosLenguaje.length; i++) {
            if (s.equals(simbolosLenguaje[i])) {
                simbolo = true;
                break;
            }
        }
        return simbolo;
    }

    private boolean isOperador(String s) {
        boolean operador = false;
        for (int i = 0; i < operadoresLenguaje.length; i++) {
            if (s.equals(operadoresLenguaje[i])) {
                operador = true;
                break;
            }
        }
        return operador;
    }

    private void analizarIdentificador() {
        boolean isPalabraReservada = false;
        for (int i = 0; i < palabrasReservadasDelLenguaje.length; i++) {
            if (palabraAnalizada.equals(palabrasReservadasDelLenguaje[i])) {
                isPalabraReservada = true;
                break;
            }
        }

        if (isPalabraReservada) {
            palabrasReservadasAnalizadas.add(palabraAnalizada);
            Cadena cadena=new Cadena ();
            cadena.setCadenaLeida(palabraAnalizada);
            cadena.setTipoCadena("Palabra Reservada");
            
            cadenas.add(cadena);
            
            //tokens1.add(palabraAnalizada);
            //tokens2.add("Palabra Reservada");
            guardarTokenCorrespondiente();
        } else {
            boolean existe;
            int valorToken = 0;

            int tamaño = identificadoresAnalizados.size();

            if (tamaño == 0) {
                System.out.println("tamaño del arreglo identificadores Analizados: " + tamaño);
                identificadoresAnalizados.add(palabraAnalizada);
            } else {
                //comparar lo que hay en el arreglo con lo que se que se quiere agregar
                existe = false;
                valorToken = 0;

                for (int i = 0; i < identificadoresAnalizados.size(); i++) {
                    if ((identificadoresAnalizados.get(i)).equals(palabraAnalizada)) {
                        existe = true;
                        valorToken = i;
                        break;
                    }
                }

                if (!existe) {
                    identificadoresAnalizados.add(palabraAnalizada);
                    valorToken = identificadoresAnalizados.size() - 1;
                }
            }

            tokens1.add(palabraAnalizada);
            tokens2.add("Identificador");

            identificadorToken.add(valorToken + 1);

        }
        palabraAnalizada = "";
    }

    private void analizarNumero() {
        numerosAnalizados.add(palabraAnalizada);
        tokens1.add(palabraAnalizada);
        tokens2.add("Numero");
        identificadorToken.add(3);
        palabraAnalizada = "";
    }

    private void analizarCadenaConstantes() {
        boolean existe;
        int valorToken = 0;

        int tamaño = cadenasDeConstantesAnalizadas.size();

        if (tamaño == 0) {
            System.out.println("tamaño del arreglo cadenasDeConstantesAnalizadas: " + tamaño);
            cadenasDeConstantesAnalizadas.add(palabraAnalizada);
        } else {
            //comparar lo que hay en el arreglo con lo que se que se quiere agregar
            existe = false;
            valorToken = 0;

            for (int i = 0; i < cadenasDeConstantesAnalizadas.size(); i++) {
                if ((cadenasDeConstantesAnalizadas.get(i)).equals(palabraAnalizada)) {
                    existe = true;
                    valorToken = i;
                    break;
                }
            }

            if (!existe) {
                cadenasDeConstantesAnalizadas.add(palabraAnalizada);
                valorToken = cadenasDeConstantesAnalizadas.size() - 1;
            }
        }

        tokens1.add(palabraAnalizada);
        tokens2.add("Cadena de Constantes");

        
        identificadorToken.add(valorToken + 300);

//        for (int i =  i < cadenasDeConstantesAnalizadas.size(); i++) {
//            if (cadenasDeConstantesAnalizadas.get(i).equals(palabraAnalizada)) {
//                identificadorToken.add(i + 300);
//            }
//        }
        palabraAnalizada = "";
    }

    private void analizarSimbolo() {
        simbolosAnalizados.add(palabraAnalizada);
        tokens1.add(palabraAnalizada);
        tokens2.add("Simbolo");
        guardarTokenCorrespondiente();
        palabraAnalizada = "";
    }

    private void analizarOperador() {
        operadoresAnalizados.add(palabraAnalizada);
        tokens1.add(palabraAnalizada);
        tokens2.add("Operador");
        guardarTokenDeOperador();
        palabraAnalizada = "";
    }

    public void guardarTokenCorrespondiente() {
        cadenas.size();
        
        switch (palabraAnalizada) {
            case "Iniciar":
                
                cadenas.get(cadenas.size()-1).setTokenAsignado(100);
                
                //identificadorToken.add(100);
                break;
            case "Variables":
                cadenas.get(cadenas.size()-1).setTokenAsignado(101);
                //identificadorToken.add(101);
                break;
            case "int":
                cadenas.get(cadenas.size()-1).setTokenAsignado(102);
                //identificadorToken.add(102);
                break;
            case "String":
                cadenas.get(cadenas.size()-1).setTokenAsignado(103);
                //identificadorToken.add(103);
                break;
            case "do":
                cadenas.get(cadenas.size()-1).setTokenAsignado(104);
                //identificadorToken.add(104);
                break;
            case "printf":
                cadenas.get(cadenas.size()-1).setTokenAsignado(105);
                //identificadorToken.add(105);
                break;
            case "captura":
                cadenas.get(cadenas.size()-1).setTokenAsignado(106);
                //identificadorToken.add(106);
                break;
            case "while":
                cadenas.get(cadenas.size()-1).setTokenAsignado(107);
                //identificadorToken.add(107);
                break;
            case "Finalizar":
                cadenas.get(cadenas.size()-1).setTokenAsignado(108);
                //identificadorToken.add(108);
                break;
            case ",":
                cadenas.get(cadenas.size()-1).setTokenAsignado(500);
                //identificadorToken.add(500);
                break;
            case "(":
                cadenas.get(cadenas.size()-1).setTokenAsignado(501);
                //identificadorToken.add(501);
                break;
            case ")":
                cadenas.get(cadenas.size()-1).setTokenAsignado(502);
                //identificadorToken.add(502);
                break;
            case "\"":
                cadenas.get(cadenas.size()-1).setTokenAsignado(503);
                //identificadorToken.add(503);
                break;
            case ";":
                cadenas.get(cadenas.size()-1).setTokenAsignado(504);
               // identificadorToken.add(504);
                break;
            case "=":
                cadenas.get(cadenas.size()-1).setTokenAsignado(505);
                //identificadorToken.add(505);
                break;
            case "==":
                cadenas.get(cadenas.size()-1).setTokenAsignado(507);
               // identificadorToken.add(507);
                break;
            case "'":
                cadenas.get(cadenas.size()-1).setTokenAsignado(508);
                //identificadorToken.add(508);
                break;

        }

    }

    private void guardarTokenDeOperador() {
        switch (palabraAnalizada) {
            case "+":
                identificadorToken.add(800);
                break;
            case "-":
                identificadorToken.add(801);
                break;

        }
    }

    public ArrayList<String> getCaracteresAnalizados() {
        return caracteresAnalizados;
    }

    public ArrayList<String> getPalabrasReservadasAnalizadas() {
        return palabrasReservadasAnalizadas;
    }

    public ArrayList<String> getIdentificadoresAnalizados() {
        return identificadoresAnalizados;
    }

    public ArrayList<String> getNumerosAnalizados() {
        return numerosAnalizados;
    }

    public ArrayList<String> getSimbolosAnalizados() {
        return simbolosAnalizados;
    }

    public ArrayList<String> getOperadoresAnalizados() {
        return operadoresAnalizados;
    }

    public ArrayList<String> getCadenasDeConstantesAnalizadas() {
        return cadenasDeConstantesAnalizadas;
    }

    public ArrayList<String> getTokens1() {
        return tokens1;
    }

    public ArrayList<String> getTokens2() {
        return tokens2;
    }

    public ArrayList<Integer> getIdentificadorToken() {
        return identificadorToken;
    }

}
