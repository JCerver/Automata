
import java.util.ArrayList;

public class AnalizadorSemantico {

    boolean semanticoCorrecto = false;

    private ArrayList<Cadena> objetosCadenas = new ArrayList();
    private ArrayList<Cadena> objetosCadenasIdentificadoresNoDeclarados = new ArrayList();
    private ArrayList<SimbologiaToken> simbologia;

    public ArrayList<Cadena> declaracion = new ArrayList();
    private ArrayList<ObjetoDeclaracion> listaObjetosDeclaraciones = new ArrayList<>();

    boolean estadoAceptacion;
    int tokenAnalizado, estado;

    public AnalizadorSemantico() {

    }

    public void iniciar() {
        verificarIdentificadoresNoDeclarados();
        automataSemantico();
    }

    public void automataSemantico() {
        estadoAceptacion = false;
        tokenAnalizado = 0;
        estado = 0;
        boolean estadoError = false;
        boolean terminadoEnFinalizar = false;

        for (int i = 0; i < objetosCadenas.size(); i++) {
            tokenAnalizado = objetosCadenas.get(i).getTokenAsignado();

            if (!(estadoAceptacion) || !(estadoError)) {

                switch (estado) {
                    case 0:
                        //Si es "Iniciar"
                        if (tokenAnalizado == obtenerTokenn("Iniciar")) {
                            estado = 1;

                        } else {
                            estado = 0;
                            estadoError = true;
                            //imprimirError(i + 1);
                            //imprimirErrorMensaje(MENSAJE_ERROR_PALABRA_RESERVADA, "Inicial");
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
                            //System.out.println("Programa compilado correctamente");
                            if (i == objetosCadenas.size() - 1) {
                                terminadoEnFinalizar = true;
                            }

                        } else {
                            estado = 1;
                            estadoError = true;
                            //imprimirError(i + 1);
                            //imprimirErrorMensaje(MENSAJE_ERROR_PALABRA_RESERVADA, "Inicial");
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
                            //System.out.println("Programa compilado correctamente");
                            if (i == objetosCadenas.size() - 1) {
                                terminadoEnFinalizar = true;
                            }

                        } else {
                            estado = 2;
                            estadoError = true;
                            //imprimirError(i + 1);
                            //imprimirErrorMensaje(MENSAJE_ERROR_PALABRA_RESERVADA, "Inicial");
                        }
                        //System.out.println("Paso por el estado " + estado);
                        break;

                    case 3:
                        //Si un identificador"
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 4;
                        } else {
                            estado = 3;
                            estadoError = true;
                            //imprimirError(i + 1);
                            //imprimirErrorMensaje(MENSAJE_ERROR_PALABRA_RESERVADA, "Inicial");
                        }
                        //System.out.println("Paso por el estado " + estado);
                        break;

                    case 4:

                        //Si es una coma 
                        if (tokenAnalizado == obtenerTokenn(",")) {
                            estado = 5;
                        } else if (tokenAnalizado == obtenerTokenn("int") || tokenAnalizado == obtenerTokenn("String")) {
                            estado = 3;
                        } else if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 16;
                            declaracion.add(objetosCadenas.get(i));
                            //System.out.println(objetosCadenas.get(i).getLexema());
                        } else if (tokenAnalizado == obtenerTokenn("Finalizar")) {
                            estado = 200;
                            //    System.out.println("Paso por el estado " + estado);
                            //System.out.println("Programa compilado correctamente");
                            if (i == objetosCadenas.size() - 1) {
                                terminadoEnFinalizar = true;
                                //sintacticoCorrecto =true;
                            }
                            declaracion = new ArrayList<Cadena>();
                        } else {
                            estado = 4;
                            estadoError = true;
                            //imprimirError(i + 1);
                            //imprimirErrorMensaje(MENSAJE_ERROR_PALABRA_RESERVADA, "Inicial");
                            declaracion = new ArrayList<Cadena>();
                        }

                        //System.out.println("Paso por el estado " + estado);
                        break;

                    case 5:
                        //Si es otra variable
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 4;
                        } else {
                            estado = 4;
                            estadoError = true;
                            //imprimirError(i + 1);
                            //imprimirErrorMensaje(MENSAJE_ERROR_PALABRA_RESERVADA, "Inicial");
                            declaracion = new ArrayList<Cadena>();
                        }
                        //System.out.println("Paso por el estado " + estado);
                        break;

                    case 16:
                        if (tokenAnalizado == obtenerTokenn("=")) {
                            estado = 17;
                            declaracion.add(objetosCadenas.get(i));
                            // System.out.println(objetosCadenas.get(i).getLexema());
                        } else {
                            estado = 4;
                            estadoError = true;
                            //imprimirError(i + 1);
                            //imprimirErrorMensaje(MENSAJE_ERROR_PALABRA_RESERVADA, "Inicial");
                            declaracion = new ArrayList<Cadena>();
                        }

                        break;

                    case 17:
                        if (tokenAnalizado > 0 && tokenAnalizado < 100) {
                            estado = 18;
                            declaracion.add(objetosCadenas.get(i));
                            //System.out.println(objetosCadenas.get(i).getLexema());
                        } else {
                            estado = 4;
                            estadoError = true;
                            //imprimirError(i + 1);
                            //imprimirErrorMensaje(MENSAJE_ERROR_PALABRA_RESERVADA, "Inicial");
                            declaracion = new ArrayList<Cadena>();
                        }

                        break;

                    case 18:
                        if (tokenAnalizado == obtenerTokenn("+") || tokenAnalizado == obtenerTokenn("-")) {
                            estado = 17;
                            declaracion.add(objetosCadenas.get(i));
                            //System.out.println(objetosCadenas.get(i).getLexema());
                        } else if (tokenAnalizado == obtenerTokenn(";")) {
                            estado = 4;
                            declaracion.add(objetosCadenas.get(i));

                            ObjetoDeclaracion od = new ObjetoDeclaracion(new ArrayList<Cadena>(declaracion));
                            //ObjetoDeclaracion od = new ObjetoDeclaracion(declaracion);

                            listaObjetosDeclaraciones.add(od);

                            //System.out.println(objetosCadenas.get(i).getLexema());
                            //imprimirPrueba();
                            //---------------------------------------------------------------
                            int tamaño = od.getListaObjetosCadenas().size();

                            //System.out.print("Si de guardo el ObjetoDeclaracion       ");
                            for (int cont = 0; cont < tamaño; cont++) {

                                //System.out.print(od.getObjetosCadenas().get(cont).getLexema());
                            }
                            System.out.println("");

                            //---------------------------------------------------------------------
                            declaracion = new ArrayList<Cadena>();
                        } else {
                            estado = 4;
                            estadoError = true;
                            //imprimirError(i + 1);
                            //imprimirErrorMensaje(MENSAJE_ERROR_PALABRA_RESERVADA, "Inicial");
                            declaracion = new ArrayList<Cadena>();
                        }

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
            if (simbologia.get(i).getLexema() == lexema) {
                valorToken = simbologia.get(i).getToken();
            }
        }

        return valorToken;
    }

    /*
    public void imprimirError(int posicion) {
        System.out.println("  Tienes un error en la posicion " + (posicion) + ", no se identifico la cadena " + objetosCadenas.get(posicion - 1).getLexema());

    }

    public void imprimirErrorMensaje(String mensaje, String simbolo) {
        System.out.println(mensaje + simbolo);
    }
    
     */
    public void imprimirValoresRecibidos() {
        System.out.println("Posicion      Lexema               Token asignado            metadato                      tipo                       valor");
        for (int i = 0; i < objetosCadenas.size(); i++) {
            System.out.println(i + "            " + objetosCadenas.get(i).getLexema()
                    + "                      " + objetosCadenas.get(i).getTokenAsignado()
                    + "                          " + objetosCadenas.get(i).getMetaDato()
                    + "                    " + objetosCadenas.get(i).getTipoDato()
                    + "                    " + objetosCadenas.get(i).getValorInicial());
        }
    }

    public void imprimirIdentificadoresNoDeclarados() {
        System.out.println("Posicion      Lexema               Token asignado            metadato                      tipo                       valor");
        for (int i = 0; i < objetosCadenasIdentificadoresNoDeclarados.size(); i++) {
            System.out.println(i + "            " + objetosCadenasIdentificadoresNoDeclarados.get(i).getLexema()
                    + "                      " + objetosCadenasIdentificadoresNoDeclarados.get(i).getTokenAsignado()
                    + "                          " + objetosCadenasIdentificadoresNoDeclarados.get(i).getMetaDato()
                    + "                    " + objetosCadenasIdentificadoresNoDeclarados.get(i).getTipoDato()
                    + "                    " + objetosCadenasIdentificadoresNoDeclarados.get(i).getValorInicial());
        }
    }

    /*
    void imprimirDeclaraciones() {
        for (int i = 0; i < listaObjetosDeclaraciones.size(); i++) {

            ObjetoDeclaracion od = listaObjetosDeclaraciones.get(i);
            int tamaño = od.getObjetosCadenas().size();

            for (int cont = 0; cont< tamaño; cont++) {
                System.out.print(od.getObjetosCadenas().get(cont).getLexema());
            }

            System.out.println("");
        }

    }*/
    void imprimirPrueba() {
        for (int i = 0; i < declaracion.size(); i++) {
            System.out.print(declaracion.get(i).getLexema());
        }
        System.out.println("");
    }

    public void verificarIdentificadoresNoDeclarados() {
        for (int i = 0; i < objetosCadenas.size(); i++) {
            //System.out.println("Si entro");
            String MetaDato = objetosCadenas.get(i).getMetaDato();
            String TipoDato = objetosCadenas.get(i).getTipoDato();

            if (MetaDato.equals("Identificador")) {
                System.out.println("Si entro otra vez");

                if (TipoDato.equals("null")) {
                    System.out.println("Si entro otra vez mas");
                    objetosCadenasIdentificadoresNoDeclarados.add(objetosCadenas.get(i));
                }
            }
        }
    }

    void analizarDeclaraciones() {
        //analizar cada declaracion
        
        //borrar aquellas declaracones con identificadores no declarados
        borrarDeclaracionesConIdentificadoresNoDeclarados();
        
        boolean isCorrecto = false;

        boolean isEntero = false;
        boolean isString = false;
        for (int i = 0; i < listaObjetosDeclaraciones.size(); i++) {

            isEntero = false;
            isString = false;
            isCorrecto = false;

            ObjetoDeclaracion objeto_declaracion = new ObjetoDeclaracion();
            objeto_declaracion.setListaObjetosCadenas(new ArrayList<Cadena>(listaObjetosDeclaraciones.get(i).getListaObjetosCadenas()));

            for (int j = 0; j < objeto_declaracion.getListaObjetosCadenas().size(); j++) {

                int posicion = objeto_declaracion.getListaObjetosCadenas().get(j).getPosicion();
                String lexema = objeto_declaracion.getListaObjetosCadenas().get(j).getLexema();
                int tokenAsignado = objeto_declaracion.getListaObjetosCadenas().get(j).getTokenAsignado();
                String metaDato = objeto_declaracion.getListaObjetosCadenas().get(j).getMetaDato();
                String tipoDato = objeto_declaracion.getListaObjetosCadenas().get(j).getTipoDato();
                String valorInicial = objeto_declaracion.getListaObjetosCadenas().get(j).getValorInicial();

                Cadena objeto_cadena = new Cadena(posicion, lexema, tokenAsignado, metaDato, tipoDato, valorInicial);

                if (objeto_cadena.getMetaDato().equals("Identificador")) {
                    if (objeto_cadena.getTipoDato().equals("int")) {
                        isEntero = true;
                        //System.out.println("Contiene identificadores de tipo int");

                    } else if (objeto_cadena.getTipoDato().equals("String")) {
                        isString = true;
                        //System.out.println("Contiene identificadores de tipo String");
                    } else {
                        //System.out.println("Contiene identificadores de tipo null");
                    }
                }
            }
            
            if (isEntero==true) {
                
                if(isString==true){
                    isCorrecto = false;
                }else if(isString==false) {
                    isCorrecto = true;
                }
            } else if (isEntero==false) {
                
                if(isString==true){
                    isCorrecto = true;
                }else if(isString==false){
                    isCorrecto = false;
                }
            }
            
            

            
            if (isCorrecto ==true) {
                listaObjetosDeclaraciones.get(i).setDeclaracionCorrecta(true);
            } else if (isCorrecto ==false) {
                listaObjetosDeclaraciones.get(i).setDeclaracionCorrecta(false);
            }
        }

    }
    
    void borrarDeclaracionesConIdentificadoresNoDeclarados(){
        ArrayList <ObjetoDeclaracion> listaObjetoDeclaracionesComodin1=new ArrayList<ObjetoDeclaracion>(this.listaObjetosDeclaraciones);
        
        int tamañoDeListaObjetosDeclaraciones=listaObjetosDeclaraciones.size();
        boolean contieneIndentificadoresSinDeclarar=false;
        
        for (int i = (tamañoDeListaObjetosDeclaraciones-1); i >= 0; i--) {
            contieneIndentificadoresSinDeclarar=false;
            
            ObjetoDeclaracion objeto_declaracion = new ObjetoDeclaracion();
            objeto_declaracion.setListaObjetosCadenas(new ArrayList<Cadena>(listaObjetosDeclaraciones.get(i).getListaObjetosCadenas()));

            for (int j = 0; j < (objeto_declaracion.getListaObjetosCadenas().size()); j++) {

                int posicion = objeto_declaracion.getListaObjetosCadenas().get(j).getPosicion();
                String lexema = objeto_declaracion.getListaObjetosCadenas().get(j).getLexema();
                int tokenAsignado = objeto_declaracion.getListaObjetosCadenas().get(j).getTokenAsignado();
                String metaDato = objeto_declaracion.getListaObjetosCadenas().get(j).getMetaDato();
                String tipoDato = objeto_declaracion.getListaObjetosCadenas().get(j).getTipoDato();
                String valorInicial = objeto_declaracion.getListaObjetosCadenas().get(j).getValorInicial();

                Cadena objeto_cadena = new Cadena(posicion, lexema, tokenAsignado, metaDato, tipoDato, valorInicial);

                if (objeto_cadena.getMetaDato().equals("Identificador")) {
                    if (objeto_cadena.getTipoDato().equals("int")) {
                        
                    } else if (objeto_cadena.getTipoDato().equals("String")) {
                       
                    } else {
                        contieneIndentificadoresSinDeclarar=true;
                    }
                }
            }
            
            if (contieneIndentificadoresSinDeclarar==true) {
                listaObjetosDeclaraciones.remove(i);
            } else if (contieneIndentificadoresSinDeclarar==false) {
                
            }
        }
        
        
        
        
        
    }
            

    public ArrayList<SimbologiaToken> getSimbologia() {
        return simbologia;
    }

    public void setSimbologia(ArrayList<SimbologiaToken> simbologia) {
        this.simbologia = simbologia;
    }

    public ArrayList<Cadena> getObjetosCadenas() {
        return objetosCadenas;
    }

    public void setObjetosCadenas(ArrayList<Cadena> objetosCadenas) {
        this.objetosCadenas = objetosCadenas;
    }

    public ArrayList<ObjetoDeclaracion> getListaObjetosDeclaraciones() {
        return listaObjetosDeclaraciones;
    }

    public void setListaObjetosDeclaraciones(ArrayList<ObjetoDeclaracion> listaObjetosDeclaraciones) {
        this.listaObjetosDeclaraciones = listaObjetosDeclaraciones;
    }

}
