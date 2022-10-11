package mx.unam.ciencias.edd;

import java.sql.Ref;
import java.util.Iterator;

import javax.lang.model.element.Element;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
            // Aquí va su código.
            cola = new Cola<Vertice>();
            if(raiz != null)
                cola.mete(raiz);
            else{
                return;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            // Aquí va su código.
            Vertice v = cola.saca();
            if(v.izquierdo != null)
                cola.mete(v.izquierdo);
            if(v.derecho != null)
                cola.mete(v.derecho);
            return v.elemento;   
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            throw new IllegalArgumentException();
        Vertice v = nuevoVertice(elemento); 
        elementos++;
        if(raiz == null){
            raiz = v;
            return;
        }
        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(raiz);
        while(!cola.esVacia()){
            Vertice u = cola.saca();
            if(u.izquierdo == null){
                u.izquierdo = v;
                v.padre = u;
                return;
            }
            cola.mete(u.izquierdo);
            if(u.derecho == null){
                u.derecho = v;
                v.padre = u;
                return;
            }
            cola.mete(u.derecho);
        }
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            return;
    
        Vertice e = vertice(busca(elemento));
        if(e == null)
            return;
        elementos--;
        if(elementos == 0){
            raiz = null;
            return;
        }
        Vertice u = getUltimoVertice();
        Intercambia(u, e);
        Vertice padre = u.padre;
        if(padre.izquierdo.elemento.equals(elemento))
            padre.izquierdo = null;
        else{
            padre.derecho = null;
        }  
        u.padre = null;
    }

    private void Intercambia(Vertice V, Vertice U){
        Vertice temporal = nuevoVertice(V.elemento);
        V.elemento = U.elemento;
        U.elemento = temporal.elemento;
    }

    private Vertice getUltimoVertice(){
        Cola<Vertice> q = new Cola<Vertice>();
        Vertice ult = raiz;
        q.mete(ult);
        while(!q.esVacia()){
            ult = q.saca();
            if(ult.izquierdo != null)
                q.mete(ult.izquierdo);
            if(ult.derecho != null)
                q.mete(ult.derecho);
        }
        return ult;
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        // Aquí va su código.
        if(esVacia())
            return -1;
        
        Double he = (Math.log10(elementos))/(Math.log10(2));
        int h = he.intValue();
        return h;
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        if(raiz == null)
            return;
        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(raiz);
        while(!cola.esVacia()){
            Vertice u = cola.saca();
            accion.actua(u);
            if(u.izquierdo != null)
                cola.mete(u.izquierdo);
            if(u.derecho != null)
                cola.mete(u.derecho);
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}