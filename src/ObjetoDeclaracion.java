
import java.util.ArrayList;


public class ObjetoDeclaracion {
    
    
    private ArrayList<Cadena> listaObjetosCadenas;
    private boolean declaracionCorrecta;

    public ObjetoDeclaracion(ArrayList<Cadena> listaObjetosCadenas) {
        this.listaObjetosCadenas = listaObjetosCadenas;
    }

    public ObjetoDeclaracion() {
    }

    public ArrayList<Cadena> getListaObjetosCadenas() {
        return listaObjetosCadenas;
    }

    public void setListaObjetosCadenas(ArrayList<Cadena> listaObjetosCadenas) {
        this.listaObjetosCadenas = listaObjetosCadenas;
    }

    public boolean isDeclaracionCorrecta() {
        return declaracionCorrecta;
    }

    public void setDeclaracionCorrecta(boolean declaracionCorrecta) {
        this.declaracionCorrecta = declaracionCorrecta;
    }

    
    
    

    
    
    
}
