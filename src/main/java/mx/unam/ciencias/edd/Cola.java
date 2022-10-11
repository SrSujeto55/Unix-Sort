package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
        // Aquí va su código.
        if(esVacia())
            return "";
        Nodo n = cabeza;
        String s = "";

        while(n != null){
            s += n.elemento + ",";
            n = n.siguiente;
        }
        return s;
    }

    /**
     * Agrega un elemento al final de la cola.
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
            rabo.siguiente = h;
            rabo = h;
        }
        
    }
}