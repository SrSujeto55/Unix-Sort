package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        // Aquí va su código.
        if(esVacia())
            return "";
        String s = "";
        Nodo n = cabeza;

        
        while(n != null){
            s += n.elemento + "\n";
            n = n.siguiente;
        }
        return s;
    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            throw new IllegalArgumentException();
        Nodo h = new Nodo(elemento);
        if(esVacia())
        cabeza = rabo = h;
        else{
            Nodo temp = cabeza;
            h.siguiente = temp;
            cabeza = h;   
        }
        
    }
}