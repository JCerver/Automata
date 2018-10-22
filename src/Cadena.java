/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Cadena {

    int posicion;
    String lexema;
    int tokenAsignado;
    String tipoCadena;
    String tipoDato;
     
     
    public Cadena() {
    }

    public Cadena(int posicion, String cadenaLeida, int tokenAsignado, String tipoCadena, String tipoDato) {
        this.posicion = posicion;
        this.lexema = cadenaLeida;
        this.tokenAsignado = tokenAsignado;
        this.tipoCadena = tipoCadena;
        this.tipoDato = tipoDato;
    } 

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) { 
        this.lexema = lexema;
    }

    public int getTokenAsignado() {
        return tokenAsignado;
    }

    public void setTokenAsignado(int tokenAsignado) {
        this.tokenAsignado = tokenAsignado;
    }

    public String getTipoCadena() {
        return tipoCadena;
    }

    public void setTipoCadena(String tipoCadena) {
        this.tipoCadena = tipoCadena;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }
    
    
    
    
}
