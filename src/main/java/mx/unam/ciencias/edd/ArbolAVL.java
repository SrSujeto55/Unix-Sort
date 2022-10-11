package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            // Aquí va su código.
            super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.
            if(this.derecho == null && this.izquierdo == null)
                return altura = 0;
            if(this.derecho == null)
                return altura = 1 + ((VerticeAVL)this.izquierdo).altura();
            if(this.izquierdo == null)
                return 1 + ((VerticeAVL)this.derecho).altura();
            else{
                return 1 + Math.max(((VerticeAVL)this.izquierdo).altura(),
                ((VerticeAVL)this.derecho).altura());
            }
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            // Aquí va su código.
            return super.toString() + " " + this.altura() + "/" + 
            (balanceV(this));
        }


        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            // Aquí va su código.
            return (altura == vertice.altura && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        // Aquí va su código.
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            return;
        super.agrega(elemento);
        rebalanceoAVL((VerticeAVL)ultimoAgregado.padre);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(elemento == null)
            return;
        VerticeAVL del = (VerticeAVL)busca(elemento);
        if(del == null)
            return;
        elementos--;
        if(del.izquierdo != null && del.derecho != null)
            del = (VerticeAVL)intercambiaEliminable(del);

        eliminaVertice(del);

        rebalanceoAVL((VerticeAVL)del.padre);
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }

    private int balanceV(VerticeAVL v){
        int altIzq = v.hayIzquierdo() ?
        v.izquierdo.altura() : -1;
        int altDer = v.hayDerecho() ?
        v.derecho.altura() : -1;

        return altIzq - altDer;
    }

    private void rebalanceoAVL(VerticeAVL v){
        //clausula de escape
        if(v == null)
            return;
        
        //Recalcular la altura de v
        v.altura();
        int balV = balanceV(v);

        //caso negativo
        if(balV == -2){
            VerticeAVL q = (VerticeAVL)v.derecho;
            if(balanceV(q) == 1){
                super.giraDerecha(q);
                q.altura();
                q.padre.altura();
            }
            
            if(balanceV(q) <= 0){
                super.giraIzquierda(v);
                v.altura();
                v.padre.altura();
            }
                
        }

        //caso positivo espejo
        if(balV == 2){
            VerticeAVL p = (VerticeAVL)v.izquierdo;
            if(balanceV(p) == -1){
                super.giraIzquierda(p);
                p.altura();
                p.padre.altura();
            }
            
            if(balanceV(p) >= 0){
                super.giraDerecha(v);
                v.altura();
                v.padre.altura();
            }
                
        }

        // recursion sobre el padre
        rebalanceoAVL((VerticeAVL)v.padre);
    }
}