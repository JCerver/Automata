
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

    boolean lexicoCorrecto = true;

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
    public ArrayList<Cadena> objetosCadenas = new ArrayList();
    public ArrayList<Cadena> arregloComodin = new ArrayList();
    public ArrayList<Cadena> objetosCadenasErrores = new ArrayList();
    
    ArrayList<SimbologiaToken> simbologia;

    int contadorGlobal;

    public AutomataLexico(String cadena, ArrayList<SimbologiaToken> simbologia) {
        this.cadena = cadena;
        this.estado = 0;
        this.simbologia = new ArrayList<SimbologiaToken>(simbologia);
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

                        } else if (simbolo.equals("'")) {
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

                        }else{
                            estado=0;
                            palabraAnalizada += simbolo;
                            //System.out.println("si soy error en estado 0"+palabraAnalizada);
                            analizarError();
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
                        } else if (i == cadena.length() - 1) {
                            analizarCadenaConstantes();

                            puedeSerCadena = false;
                            estado = 0;
                        } else {
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

                            if (i == (cadena.length() - 1)) {
                                analizarCadenaConstantes();
                            }
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
                        //analizarError();
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
            Cadena cadena = new Cadena();
            cadena.setLexema(palabraAnalizada);
            cadena.setMetaDato("Palabra Reservada");

            objetosCadenas.add(cadena);

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

            Cadena cadena = new Cadena();
            cadena.setLexema(palabraAnalizada);
            cadena.setMetaDato("Identificador");
            cadena.setTokenAsignado(valorToken + 1);
            objetosCadenas.add(cadena);

        }
        palabraAnalizada = "";
    }

    private void analizarNumero() {
        numerosAnalizados.add(palabraAnalizada);

        Cadena cadena = new Cadena();
        cadena.setLexema(palabraAnalizada);
        cadena.setMetaDato("Número");
        cadena.setTokenAsignado(3);
        objetosCadenas.add(cadena);
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

        Cadena cadena = new Cadena();
        cadena.setLexema(palabraAnalizada);
        cadena.setMetaDato("Cadena de Constantes");
        cadena.setTokenAsignado(valorToken + 300);
        objetosCadenas.add(cadena);

//        for (int i =  i < cadenasDeConstantesAnalizadas.size(); i++) {
//            if (cadenasDeConstantesAnalizadas.get(i).equals(palabraAnalizada)) {
//                identificadorToken.add(i + 300);
//            }
//        }
        palabraAnalizada = "";
    }

    private void analizarSimbolo() {
        simbolosAnalizados.add(palabraAnalizada);

        Cadena cadena = new Cadena();
        cadena.setLexema(palabraAnalizada);
        cadena.setMetaDato("Simbolo");
        objetosCadenas.add(cadena);

        guardarTokenCorrespondiente();
        palabraAnalizada = "";
    }

    private void analizarOperador() {
        operadoresAnalizados.add(palabraAnalizada);

        Cadena cadena = new Cadena();
        cadena.setLexema(palabraAnalizada);
        cadena.setMetaDato("Operador");
        objetosCadenas.add(cadena);

        guardarTokenDeOperador();
        palabraAnalizada = "";
    }
    
    private void analizarError() {
        //System.out.println("Encontro Error"+palabraAnalizada);
        Cadena cadena = new Cadena();
        cadena.setLexema(palabraAnalizada);
        cadena.setMetaDato("ERROR");
        cadena.setTokenAsignado(99999);
        objetosCadenasErrores.add(cadena);

        guardarTokenDeOperador();
        palabraAnalizada = "";
    }

    public void guardarTokenCorrespondiente() {
        objetosCadenas.size();
        int valorToken = 0;

        switch (palabraAnalizada) {
            case "Iniciar":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("Iniciar"));

                //identificadorToken.add(100);
                break;
            case "Variables":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("Variables"));
                //identificadorToken.add(101);
                break;
            case "int":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("int"));
                //identificadorToken.add(102);
                break;
            case "String":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("String"));
                //identificadorToken.add(103);
                break;
            case "do":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("do"));
                //identificadorToken.add(104);
                break;
            case "printf":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("printf"));
                //identificadorToken.add(105);
                break;
            case "captura":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("captura"));
                //identificadorToken.add(106);
                break;
            case "while":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("while"));
                //identificadorToken.add(107);
                break;
            case "Finalizar":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("Finalizar"));
                //identificadorToken.add(108);
                break;
            case ",":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn(","));
                //identificadorToken.add(500);
                break;
            case "(":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("("));
                //identificadorToken.add(501);
                break;
            case ")":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn(")"));
                //identificadorToken.add(502);
                break;
            case "\"":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("\""));
                //identificadorToken.add(503);
                break;
            case ";":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn(";"));
                // identificadorToken.add(504);
                break;
            case "=":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("="));
                //identificadorToken.add(505);
                break;
            case "==":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("=="));
                // identificadorToken.add(507);
                break;
            case "'":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("'"));
                //identificadorToken.add(508);
                break;

        }

    }

    private int obtenerTokenn(String lexema) {
        int valorToken = 0;
        for (int i = 0; i < simbologia.size(); i++) {
            if (simbologia.get(i).getLexema() == lexema) {
                valorToken = simbologia.get(i).getToken();
            }
        }

        return valorToken;
    }

    private void guardarTokenDeOperador() {
        switch (palabraAnalizada) {
            case "+":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("+"));
                break;
            case "-":
                objetosCadenas.get(objetosCadenas.size() - 1).setTokenAsignado(obtenerTokenn("-"));
                break;

        }
    }

    public String[] getPalabrasReservadasDelLenguaje() {
        return palabrasReservadasDelLenguaje;
    }

    public void setPalabrasReservadasDelLenguaje(String[] palabrasReservadasDelLenguaje) {
        this.palabrasReservadasDelLenguaje = palabrasReservadasDelLenguaje;
    }

    public String[] getSimbolosLenguaje() {
        return simbolosLenguaje;
    }

    public void setSimbolosLenguaje(String[] simbolosLenguaje) {
        this.simbolosLenguaje = simbolosLenguaje;
    }

    public ArrayList<String> getCaracteresAnalizados() {
        return caracteresAnalizados;
    }

    public void setCaracteresAnalizados(ArrayList<String> caracteresAnalizados) {
        this.caracteresAnalizados = caracteresAnalizados;
    }

    public ArrayList<String> getPalabrasReservadasAnalizadas() {
        return palabrasReservadasAnalizadas;
    }

    public void setPalabrasReservadasAnalizadas(ArrayList<String> palabrasReservadasAnalizadas) {
        this.palabrasReservadasAnalizadas = palabrasReservadasAnalizadas;
    }

    public ArrayList<String> getCadenasDeConstantesAnalizadas() {
        return cadenasDeConstantesAnalizadas;
    }

    public void setCadenasDeConstantesAnalizadas(ArrayList<String> cadenasDeConstantesAnalizadas) {
        this.cadenasDeConstantesAnalizadas = cadenasDeConstantesAnalizadas;
    }

    public ArrayList<String> getIdentificadoresAnalizados() {
        return identificadoresAnalizados;
    }

    public void setIdentificadoresAnalizados(ArrayList<String> identificadoresAnalizados) {
        this.identificadoresAnalizados = identificadoresAnalizados;
    }

    public ArrayList<String> getNumerosAnalizados() {
        return numerosAnalizados;
    }

    public void setNumerosAnalizados(ArrayList<String> numerosAnalizados) {
        this.numerosAnalizados = numerosAnalizados;
    }

    public ArrayList<String> getSimbolosAnalizados() {
        return simbolosAnalizados;
    }

    public void setSimbolosAnalizados(ArrayList<String> simbolosAnalizados) {
        this.simbolosAnalizados = simbolosAnalizados;
    }

    public ArrayList<String> getOperadoresAnalizados() {
        return operadoresAnalizados;
    }

    public void setOperadoresAnalizados(ArrayList<String> operadoresAnalizados) {
        this.operadoresAnalizados = operadoresAnalizados;
    }

    public ArrayList<String> getErroresAnalizados() {
        return erroresAnalizados;
    }

    public void setErroresAnalizados(ArrayList<String> erroresAnalizados) {
        this.erroresAnalizados = erroresAnalizados;
    }

    public ArrayList<Cadena> getObjetosCadenas() {
        return objetosCadenas;
    }

    public void setObjetosCadenas(ArrayList<Cadena> objetosCadenas) {
        this.objetosCadenas = objetosCadenas;
    }

    void colocarTipo() {
        boolean puedeSerInt = false;
        boolean puedeSerString = false;
        boolean finDeclaracionVariables = false;

        for (int i = 0; i < objetosCadenas.size(); i++) {

            if (!(objetosCadenas.get(i).getTokenAsignado() == obtenerTokenn("Iniciar"))
                    && !(objetosCadenas.get(i).getTokenAsignado() == obtenerTokenn("Variables"))
                    && !(objetosCadenas.get(i).getTokenAsignado() == obtenerTokenn("int"))
                    && !(objetosCadenas.get(i).getTokenAsignado() == obtenerTokenn("String"))
                    && !(objetosCadenas.get(i).getMetaDato().equals("Identificador"))
                    && !(objetosCadenas.get(i).getTokenAsignado() == obtenerTokenn(","))) {
                break;
            }

            if (objetosCadenas.get(i).getTokenAsignado() == obtenerTokenn("int")) {
                puedeSerInt = true;
                puedeSerString = false;
            } else if (objetosCadenas.get(i).getTokenAsignado() == obtenerTokenn("String")) {
                puedeSerInt = false;
                puedeSerString = true;
            }

            if (puedeSerInt) {
                if (objetosCadenas.get(i).getMetaDato().equals("Identificador")) {
                    objetosCadenas.get(i).setTipoDato("int");

                    Cadena c = new Cadena();
                    c.setLexema(objetosCadenas.get(i).getLexema());
                    c.setTokenAsignado(objetosCadenas.get(i).getTokenAsignado());
                    c.setMetaDato(objetosCadenas.get(i).getMetaDato());
                    c.setTipoDato("int");
                    arregloComodin.add(c);
                }
            } else if (puedeSerString) {
                if (objetosCadenas.get(i).getMetaDato().equals("Identificador")) {
                    objetosCadenas.get(i).setTipoDato("String");

                    Cadena c = new Cadena();
                    c.setLexema(objetosCadenas.get(i).getLexema());
                    c.setTokenAsignado(objetosCadenas.get(i).getTokenAsignado());
                    c.setMetaDato(objetosCadenas.get(i).getMetaDato());
                    c.setTipoDato("String");
                    arregloComodin.add(c);

                }
            }

        }
    }

    void colocarLosDemasTipos() {

        colocarValoresDefault();
        /* for (int i = 0; i < arregloComodin.size(); i++) {
            System.out.println(i + "            " + arregloComodin.get(i).getLexema()
                    + "                      " + arregloComodin.get(i).getTokenAsignado() 
                    + "                          " + arregloComodin.get(i).getMetaDato()
                    + "                    " + arregloComodin.get(i).getTipoDato());
            
        }
        
         */

        for (int i = 0; i < objetosCadenas.size(); i++) {
            if (objetosCadenas.get(i).getMetaDato().equals("Identificador")) {

                for (int j = 0; j < arregloComodin.size(); j++) {
                    if (objetosCadenas.get(i).getTokenAsignado() == arregloComodin.get(j).getTokenAsignado()) {
                        objetosCadenas.get(i).setTipoDato(arregloComodin.get(j).getTipoDato());
                        break;
                    }
                }
            }
        }
    }

    void colocarValoresDefault() {
        for (int i = 0; i < objetosCadenas.size(); i++) {
            objetosCadenas.get(i).setTipoDato("null");
            objetosCadenas.get(i).setValorInicial("null");
        }
    }

    public boolean isLexicoCorrecto() {
        return lexicoCorrecto;
    }

    public void setLexicoCorrecto(boolean lexicoCorrecto) {
        this.lexicoCorrecto = lexicoCorrecto;
    }

    public ArrayList<Cadena> getObjetosCadenasErrores() {
        return objetosCadenasErrores;
    }

    public void setObjetosCadenasErrores(ArrayList<Cadena> objetosCadenasErrores) {
        this.objetosCadenasErrores = objetosCadenasErrores;
    }

    
}