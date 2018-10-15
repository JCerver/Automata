//NOTA: FALTA CORREGIR QUE NOS REGRESA UN NUMERO CON EL IDENTIFICADOR 1q
/*
+++++++++++++++++++++++++++++++++++++
Palabras reservadas
    Iniciar=100
    Variables=101
    int=102
    String=103
    do=104
    printf=105
    captura=106
    while=107
    Finalizar=108
)
Identificador=1-99 
Cadena de caracteres= 300-400
numero=3

caracteres especiales
    ,=500
    (=501   
    )=502
    "=503
    ;=504
    = =505
    == = 507
    ' = 508
   

    +=800
 -  = 801
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
    
    private ArrayList<String> tokens1;
    private ArrayList<String> tokens2;
    private ArrayList<Integer> valoresToken;
    
    
    ArrayList<String> palabrasReservadas;
    ArrayList<String> simbolos;
    ArrayList<String> errores;

    boolean isError;
    String cadenasPrograma = "";

    AutomataSintactico automataSintactico;
    
    public ArrayList <Cadena> cadenas = new ArrayList();
    
    public Analizador() {
        palabrasReservadas = new ArrayList<String>();
        simbolos = new ArrayList<String>();

    }

    public void leer() {
        abrirArchivo();
        separarCadenas(this.cadenasPrograma);
        imprimirPalabras();
        llamarAnalizadorSintactico();
    }

    void abrirArchivo() {
        palabrasReservadas.clear();
        String aux = "";
        try {
            File archivo = new File("src/Archivos/PalabrasReservadas.txt");

            if (archivo != null) {
                FileReader archivos = new FileReader(archivo);
                BufferedReader lee = new BufferedReader(archivos);
                while ((aux = lee.readLine()) != null) {
                    palabrasReservadas.add(aux);
                }
                lee.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex + ""
                    + "\nNo se ha encontrado el archivo",
                    "ADVERTENCIA!!!", JOptionPane.WARNING_MESSAGE);
        }
 
        System.out.println("");
        simbolos.clear();
        String linea = "";
        try {
            File archivo = new File("src/Archivos/simbolos.txt");

            if (archivo != null) {
                FileReader archivos = new FileReader(archivo);
                BufferedReader lee = new BufferedReader(archivos);
                while ((linea = lee.readLine()) != null) {
                    simbolos.add(linea);
                }
                lee.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex + ""
                    + "\nNo se ha encontrado el archivo",
                    "ADVERTENCIA!!!", JOptionPane.WARNING_MESSAGE);
        }

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

    void separarCadenas(String cadenasProgramas) {//con un automata
        AutomataLexico automata1=new AutomataLexico(cadenasPrograma);
        automata1.analizar();
        
        this.caracteresAnalizados = new ArrayList<String>(automata1.getCaracteresAnalizados());

    this.palabrasReservadasAnalizadas = new ArrayList<String>(automata1.getPalabrasReservadasAnalizadas());
   this.identificadoresAnalizados = new ArrayList<String>(automata1.getIdentificadoresAnalizados());
    this.numerosAnalizados = new ArrayList<String>(automata1.getNumerosAnalizados());
    this.simbolosAnalizados = new ArrayList<String>(automata1.getSimbolosAnalizados());
    this.operadoresAnalizados = new ArrayList<String>(automata1.getOperadoresAnalizados());
    this.cadenasDeConstantesAnalizadas=new ArrayList<String>(automata1.getCadenasDeConstantesAnalizadas());
    this.tokens1=new ArrayList<String>(automata1.getTokens1());
    this.tokens2=new ArrayList<String>(automata1.getTokens2());    
    this.valoresToken=new ArrayList<Integer>(automata1.getIdentificadorToken()); 
    
        
    }

    void imprimirPalabras() {
        for (int i = 0; i < palabrasReservadas.size(); i++) {
            System.out.println(palabrasReservadas.get(i));
        }

        for (int i = 0; i < simbolos.size(); i++) {
            System.out.println(simbolos.get(i));
        }
        System.out.println(cadenasPrograma);
        
        
        
            
        for (int i = 0; i < palabrasReservadasAnalizadas.size(); i++) {
            System.out.println(palabrasReservadasAnalizadas.get(i)+"        es palabra reservada");
        }
        for (int i = 0; i < identificadoresAnalizados.size(); i++) {
            System.out.println(identificadoresAnalizados.get(i)+"        es identificador");
        }
        for (int i = 0; i < numerosAnalizados.size(); i++) {
            System.out.println(numerosAnalizados.get(i)+"        es numero");
        }
        for (int i = 0; i < simbolosAnalizados.size(); i++) {
            System.out.println(simbolosAnalizados.get(i)+"        es simbolo");
        }
        for (int i = 0; i < operadoresAnalizados.size(); i++) {
            System.out.println(operadoresAnalizados.get(i)+"        es operador");
        }
        
        
        for (int i = 0; i < cadenasDeConstantesAnalizadas.size(); i++) {
            System.out.println(cadenasDeConstantesAnalizadas.get(i)+"        es cadena de constantes");
        }
        
      /*  for (int i = 0; i < caracteresAnalizados.size(); i++) {
            System.out.println(caracteresAnalizados.get(i)+"        es cada caracter del programa leido");
        }*/
        
         
        
        for (int i = 0; i < tokens1.size(); i++) {
            System.out.println(tokens1.get(i)+"        es   "+tokens2.get(i)+ "      en la pocision "+ i);
        }
        
        for (int i = 0; i < valoresToken.size(); i++) {
            System.out.println(valoresToken.get(i));
        }
        
        
        System.out.println("El arreglo tokens1.size() es de tamaño "+tokens1.size());
        System.out.println("El arreglo identificadorToken.size() es de tamaño "+valoresToken.size());
        
    }
    
   

    /* public boolean programa(String cadena){
        if(cadena.equals("Iniciar")){
            return true; //se lerá palabra por palabra de un archivo hasta que finalice
        }
    }*/
    
    public void llamarAnalizadorSintactico(){
        automataSintactico=new AutomataSintactico(valoresToken);
        automataSintactico.setTokens1(new ArrayList<String>(this.tokens1));
        automataSintactico.automata();
        System.out.println("Probando github");
        
        
    }
    
    public static void main(String[] args) {
        Analizador test = new Analizador();
        test.leerPrograma();
        test.leer();
     

    }
}
