package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        private Iterador() {
            // Aquí va su código.
            pila = new Pila<Vertice>();
            Vertice v = raiz;
            while(v != null){
                pila.mete(v);
                v = v.izquierdo;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            // Aquí va su código.
            Vertice next = pila.saca();
            Vertice d = next.derecho;
            while(d != null){
                pila.mete(d);
                d = d.izquierdo;
            }
            return next.elemento;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            throw new IllegalArgumentException();
        Vertice v = nuevoVertice(elemento);
        ultimoAgregado = v;
        elementos++;
        if(raiz == null){
            raiz = v;
            return;
        }
        agrega(raiz, v);
    }

    private void agrega(Vertice actual, Vertice v){
        if(actual.elemento.compareTo(v.elemento) >= 0){
            if(actual.izquierdo == null){
                actual.izquierdo = v;
                v.padre = actual;
            }
            else{
                agrega(actual.izquierdo, v);
            }
        }
        else{
            if(actual.derecho == null){
                actual.derecho = v;
                v.padre = actual;
            }
            else{
                agrega(actual.derecho, v);
            }
        }
    }
    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            throw new IllegalArgumentException();

        Vertice b = vertice(busca(elemento));
        if(b == null)
            return;
        elementos--;

        if(elementos == 0){
            raiz = null;
            return;
        }
        if(b.izquierdo != null && b.derecho != null){
            Vertice e = intercambiaEliminable(b);
            eliminaVertice(e);
        }else{
                eliminaVertice(b);
        }
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        // Aquí va su código.

        Vertice verticeMaximoSubarbol = MaximoEnSubArbol(vertice.izquierdo);

        T elementoMaximo = verticeMaximoSubarbol.elemento;
        vertice.elemento = elementoMaximo;

        return verticeMaximoSubarbol;

    }

    private Vertice MaximoEnSubArbol(Vertice vertice){
        if(vertice.derecho == null)
            return vertice;
        return MaximoEnSubArbol(vertice.derecho);
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        // Aquí va su código.
        Vertice hijo = vertice.izquierdo == null ? vertice.derecho : vertice.izquierdo;

                Vertice padre = vertice.padre;

                if (padre != null) {
                    if (vertice == padre.izquierdo) {
                        padre.izquierdo = hijo;
                    } else {
                        padre.derecho = hijo;
                    }
                } else {
                    raiz = hijo;
                }

                if (hijo != null) {
                    hijo.padre = padre;
                }
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        // Aquí va su código.
        return buscaAux(raiz, elemento);
    }

    private VerticeArbolBinario<T> buscaAux(Vertice v, T elemento){
        if(v == null)
            return null;
        if(v.elemento.equals(elemento))
            return v;
        if(v.elemento.compareTo(elemento) >= 0)
            return buscaAux(v.izquierdo, elemento);
        else{
            return buscaAux(v.derecho, elemento);
        }

    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        // Aquí va su código.
        return this.ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        if(vertice == null || !vertice.hayIzquierdo() || esVacia())
            return;
        Vertice AG = vertice(vertice);
        Vertice IZ = AG.izquierdo;
        IZ.padre = AG.padre;
        if(!AG.equals(raiz)){
            if(AG.equals(AG.padre.izquierdo))
                IZ.padre.izquierdo = IZ;
            else
                IZ.padre.derecho = IZ;
        }else{
            raiz = IZ;
        }
        AG.izquierdo = IZ.derecho;
        if(IZ.hayDerecho()){
            IZ.derecho.padre = AG;
        }
        IZ.derecho = AG;
        AG.padre = IZ;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        if(vertice == null || !vertice.hayDerecho() || esVacia())
            return;
        Vertice AG = vertice(vertice);
        Vertice DE = AG.derecho;
        DE.padre = AG.padre;
        if(!AG.equals(raiz)){
            if(AG.equals(AG.padre.izquierdo)){
                DE.padre.izquierdo = DE;
            }else{
                DE.padre.derecho = DE;
            }
        }else{
            raiz = DE;
        }
        AG.derecho = DE.izquierdo;
        if(DE.hayIzquierdo())
            DE.izquierdo.padre = AG;
        DE.izquierdo = AG;
        AG.padre = DE;
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsPrO(raiz, accion);
    }

    private void dfsPrO(Vertice v, AccionVerticeArbolBinario<T> a){
        if(v == null)
            return;
        a.actua(v);
        dfsPrO(v.izquierdo, a);
        dfsPrO(v.derecho, a);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsIO(raiz, accion);
    }

    private void dfsIO(Vertice v, AccionVerticeArbolBinario<T> a){
        if(v == null)
            return;
        dfsIO(v.izquierdo, a);
        a.actua(v);
        dfsIO(v.derecho, a);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsPoO(raiz, accion);
    }

    private void dfsPoO(Vertice v, AccionVerticeArbolBinario<T> a){
        if(v == null)
            return;
        dfsPoO(v.izquierdo, a);
        dfsPoO(v.derecho, a);
        a.actua(v);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}