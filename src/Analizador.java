//NOTA: FALTA CORREGIR QUE NOS REGRESA UN NUMERO CON EL IDENTIFICADOR 1q
/*

 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Analizador {

    ArrayList<String> caracteresAnalizados;

    ArrayList<String> palabrasReservadasAnalizadas;
    ArrayList<String> identificadoresAnalizados;
    ArrayList<String> numerosAnalizados;
    ArrayList<String> simbolosAnalizados;
    ArrayList<String> operadoresAnalizados;
    ArrayList<String> cadenasDeConstantesAnalizadas;
    ArrayList<SimbologiaToken> simbologia;

    ArrayList<String> errores;

    boolean isError;
    String cadenasPrograma = "";

    AutomataSintactico automataSintactico;
    AnalizadorSemantico analizadorSemantico;

    public ArrayList<Cadena> objetosCadenas = new ArrayList();
    
    boolean lexicoCorrecto=false ;
    boolean sintacticoCorrecto =false ;
    boolean semanticoCorrecto =false;
    

    public Analizador() {
        simbologia = new ArrayList<SimbologiaToken>();
        generarSimbologia();
    }

    public void generarSimbologia() {
        simbologia.add(new SimbologiaToken("Palabras reservadas", "Iniciar", 100));
        simbologia.add(new SimbologiaToken("Palabras reservadas", "Variables", 101));
        simbologia.add(new SimbologiaToken("Palabras reservadas", "int", 102));
        simbologia.add(new SimbologiaToken("Palabras reservadas", "String", 103));
        simbologia.add(new SimbologiaToken("Palabras reservadas", "do", 104));
        simbologia.add(new SimbologiaToken("Palabras reservadas", "printf", 105));
        simbologia.add(new SimbologiaToken("Palabras reservadas", "captura", 106));
        simbologia.add(new SimbologiaToken("Palabras reservadas", "while", 107));
        simbologia.add(new SimbologiaToken("Palabras reservadas", "Finalizar", 108));

        simbologia.add(new SimbologiaToken("Simbolos", ",", 500));
        simbologia.add(new SimbologiaToken("Simbolos", "(", 501));
        simbologia.add(new SimbologiaToken("Simbolos", ")", 502));
        simbologia.add(new SimbologiaToken("Simbolos", "\"", 503));
        simbologia.add(new SimbologiaToken("Simbolos", ";", 504));
        simbologia.add(new SimbologiaToken("Simbolos", "=", 505));
        simbologia.add(new SimbologiaToken("Simbolos", "==", 506));
        simbologia.add(new SimbologiaToken("Simbolos", "'", 507));  
        
        simbologia.add(new SimbologiaToken("Operador", "+", 800));
        simbologia.add(new SimbologiaToken("Operador", "-", 801));
        simbologia.add(new SimbologiaToken("Operador", "+", 802));
        
        
    }

    public void leer() {
        separarCadenasConAutomataLexico(this.cadenasPrograma);
        
        if(lexicoCorrecto){
            llamarAnalizadorSintactico();
            
            if(sintacticoCorrecto){
                
                llamaAnalizadorSemantico();
                if(semanticoCorrecto){
                    
                }
            }
            
        }
        imprimirPalabras();
        
        
        
    }

    
    public void leerPrograma() {
 
        String aux = "";
        try {
            JFileChooser jfc = new JFileChooser();
            jfc.setCurrentDirectory(new File("src/Archivos/"));
            jfc.showOpenDialog(null);

            File archivo = jfc.getSelectedFile();

            if (archivo != null) {
                FileReader archivos = new FileReader(archivo);
                BufferedReader lee = new BufferedReader(archivos);
                while ((aux = lee.readLine()) != null) {
                    cadenasPrograma += aux;
                    cadenasPrograma += " ";

                }
                lee.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex + ""
                    + "\nNo se ha encontrado el archivo",
                    "ADVERTENCIA!!!", JOptionPane.WARNING_MESSAGE);
        }

    }

    void separarCadenasConAutomataLexico(String cadenasProgramas) {//con un automata
        AutomataLexico automatalexico = new AutomataLexico(cadenasPrograma,simbologia);
        
        automatalexico.analizar();
        automatalexico.colocarTipo();
        automatalexico.colocarLosDemasTipos();
        
        this.caracteresAnalizados = new ArrayList<String>(automatalexico.getCaracteresAnalizados());

        this.palabrasReservadasAnalizadas = new ArrayList<String>(automatalexico.getPalabrasReservadasAnalizadas());
        this.identificadoresAnalizados = new ArrayList<String>(automatalexico.getIdentificadoresAnalizados());
        this.numerosAnalizados = new ArrayList<String>(automatalexico.getNumerosAnalizados());
        this.simbolosAnalizados = new ArrayList<String>(automatalexico.getSimbolosAnalizados());
        this.operadoresAnalizados = new ArrayList<String>(automatalexico.getOperadoresAnalizados());
        this.cadenasDeConstantesAnalizadas = new ArrayList<String>(automatalexico.getCadenasDeConstantesAnalizadas());

        this.objetosCadenas = new ArrayList<Cadena>(automatalexico.getObjetosCadenas());
        
        
        
        if(automatalexico.isLexicoCorrecto()){
            lexicoCorrecto=true;
        }

    }

    void imprimirPalabras() {
        

        
        System.out.println("Cadenas del programa: "+cadenasPrograma);

        for (int i = 0; i < palabrasReservadasAnalizadas.size(); i++) {
            System.out.println(palabrasReservadasAnalizadas.get(i) + "        es palabra reservada");
        }
        for (int i = 0; i < identificadoresAnalizados.size(); i++) {
            System.out.println(identificadoresAnalizados.get(i) + "        es identificador");
        }
        for (int i = 0; i < numerosAnalizados.size(); i++) {
            System.out.println(numerosAnalizados.get(i) + "        es numero");
        }
        for (int i = 0; i < simbolosAnalizados.size(); i++) {
            System.out.println(simbolosAnalizados.get(i) + "        es simbolo");
        }
        for (int i = 0; i < operadoresAnalizados.size(); i++) {
            System.out.println(operadoresAnalizados.get(i) + "        es operador");
        }

        for (int i = 0; i < cadenasDeConstantesAnalizadas.size(); i++) {
            System.out.println(cadenasDeConstantesAnalizadas.get(i) + "        es cadena de constantes");
        }

        /*  for (int i = 0; i < caracteresAnalizados.size(); i++) {
            System.out.println(caracteresAnalizados.get(i)+"        es cada caracter del programa leido");
        }*/
        System.out.println("Posicion      Cadena leida               Token asignado            metadato                      tipo");
        for (int i = 0; i < objetosCadenas.size(); i++) {
            System.out.println(i + "            " + objetosCadenas.get(i).getLexema()
                    + "                      " + objetosCadenas.get(i).getTokenAsignado() 
                    + "                          " + objetosCadenas.get(i).getMetaDato()
                    + "                    " + objetosCadenas.get(i).getTipoDato());
        }

    }

    /* public boolean programa(String cadena){
        if(cadena.eq uals("Iniciar")){ 
            return true; //se lerá palabra por palabra de un archivo hasta que finalice
        }
    }*/
    public void llamarAnalizadorSintactico() {
        automataSintactico = new AutomataSintactico();
        
        automataSintactico.setObjetosCadenas(new ArrayList<Cadena>(this.objetosCadenas));
        automataSintactico.setSimbologia(simbologia);
        automataSintactico.automata();
        //System.out.println("Probando github");
        
        if(automataSintactico.isSintacticoCorrecto()){
            sintacticoCorrecto=true;
        }

    }
    
    private void llamaAnalizadorSemantico(){
        analizadorSemantico=new AnalizadorSemantico();
        analizadorSemantico.setObjetosCadenas(new ArrayList<Cadena>(this.objetosCadenas));
        
        analizadorSemantico.iniciar();
   
    }

    public static void main(String[] args) {
        Analizador test = new Analizador();
        test.leerPrograma();
        test.leer();

    }
}
