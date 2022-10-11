package mx.unam.ciencias.edd.proyecto1;

import java.text.Collator;

/**
 * <p>Clase para Cadenas String, por algun razon generica. Las cadenasStr deben pode almacenaar dentro de si mismas su 
 * cadena original y su cadena modificada, implementa {@link Object} por lo que se debe poder 
 * actualizar a otra cadena simplemente pasandole esta </p>
 * 
 */

 public class CadenaStr implements Comparable<CadenaStr>{
     
    /*String privada para la cadena original*/
    private String Original;

    /**
     * String privada sin espacios finales ni iniciales
     */
    private String Modified;


    // /*Collator privado para la comaprar cadenas pero sin caracteres especiales*/ 
    // private Collator Comparador;

    /*Constructor unico para asociarle una string al original */
    public CadenaStr(String s){
        this.Original = s;
        Modified = Original.replaceAll("\\P{L}+", "");
    }

    /**
     * Metodo para regresar el string original
     * @return el String original
     */
    public String getOriginal(){
        return Original;
    }


    /*Metodo para actualizar los valores de la cadenaStr */
    public void actualiza(Object object){
        if(object.getClass() != getClass())
            throw new IllegalArgumentException();

        CadenaStr newStr = (CadenaStr)object;

        this.Original = newStr.Original;
    }


    /*Metodo para comparar 2 strings implementado de Comparable */
    @Override
    public int compareTo(CadenaStr str) {
        Collator Comparador = Collator.getInstance();
        Comparador.setStrength(Collator.PRIMARY);
        return Comparador.compare(Modified, str.Modified);
    }
 }