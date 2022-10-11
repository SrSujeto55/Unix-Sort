package mx.unam.ciencias.edd;

import org.hamcrest.core.IsInstanceOf;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            // Aquí va su código.
            super(elemento);
            this.color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        @Override public String toString() {
            // Aquí va su código.
            if(this.color.equals(Color.ROJO)){
                return "R{" + super.toString() + "}";
            }
            else{
                return "N{" + super.toString() + "}";
            }
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            // Aquí va su código.
            return (color == vertice.color && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        return ((VerticeRojinegro)vertice).color;

    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);
        VerticeRojinegro v = (VerticeRojinegro)ultimoAgregado;
        v.color = Color.ROJO;
        rebalanceoAdd(v);
    }
    private void rebalanceoAdd(VerticeRojinegro v){
        //caso 1
        if(v.padre == null){
            v.color = Color.NEGRO;
            return;
        }

        //caso 2
        VerticeRojinegro pa =  (VerticeRojinegro)v.padre;
        if(esNegro(pa))
            return;

        //caso 3
        VerticeRojinegro ti;
        VerticeRojinegro ab = (VerticeRojinegro)pa.padre;
        if(v.padre == ab.izquierdo)
            ti = (VerticeRojinegro)ab.derecho;
        else
            ti = (VerticeRojinegro)ab.izquierdo;

        if(esRojo(ti)){
            ti.color = Color.NEGRO;
            pa.color = Color.NEGRO;
            ab.color = Color.ROJO;
            rebalanceoAdd(ab);
        }else{
            VerticeRojinegro paux = pa;

            //Caso 4
            if(ab.izquierdo == pa &&
                pa.derecho == v){
                super.giraIzquierda(pa);
                pa = v;
                v = paux;
            }else if(ab.derecho == pa && pa.izquierdo == v){
                super.giraDerecha(pa);
                pa = v;
                v = paux;
            }

            // Caso 5
            pa.color = Color.NEGRO;
            ab.color = Color.ROJO;
            if(v == pa.izquierdo)
                super.giraDerecha(ab);
            else
                super.giraIzquierda(ab);
        }
    }


    private boolean esNegro(VerticeRojinegro vertice){
        if(vertice == null) return true;
        return (vertice.color == Color.NEGRO);
    }


    private boolean esRojo(VerticeRojinegro vertice){
        return (vertice != null && vertice.color == Color.ROJO);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */

     @Override
     public void elimina(T elemento) {
         // Aquí va su código.
         VerticeRojinegro del = (VerticeRojinegro) busca(elemento);

         if (del == null)
             return;

         VerticeRojinegro Phantom = null;

         if (del.hayIzquierdo() && del.hayDerecho())
             del = (VerticeRojinegro) intercambiaEliminable(del);

         if (del.izquierdo == null && del.derecho == null) {
             Phantom = (VerticeRojinegro) nuevoVertice(null);
             Phantom.color = Color.NEGRO;
             del.izquierdo = Phantom;
             Phantom.padre = del;
         }

         VerticeRojinegro sh;

         if(del.izquierdo != null)
            sh = (VerticeRojinegro)del.izquierdo;
          else{
            sh = (VerticeRojinegro)del.derecho;
          }

         eliminaVertice(del);
         elementos--;

         if (esRojo(sh)) {
             sh.color = Color.NEGRO;
             return;
         }

         if (esNegro(del) && esNegro(sh))
             rebalanceoDel(sh);

         if (Phantom != null)
             eliminaVertice(Phantom);
     }

    //Rebalanceo por casos para eliminar
    private void rebalanceoDel(VerticeRojinegro v){
        //caso 1
        if(v.padre == null)
            return;

            //actuaizacion de referencias
        VerticeRojinegro pa = (VerticeRojinegro)v.padre;
        VerticeRojinegro he;
        if(v == pa.derecho)
            he = (VerticeRojinegro)pa.izquierdo;
        else
            he = (VerticeRojinegro)pa.derecho;

        //caso 2
        if(esRojo(he)){
            pa.color = Color.ROJO;
            he.color = Color.NEGRO;

            if(v == pa.derecho)
                super.giraDerecha(pa);
            else
                super.giraIzquierda(pa);

                //actuaizacion de referencias
            pa = (VerticeRojinegro)v.padre;
            if(v == pa.derecho)
                he = (VerticeRojinegro)pa.izquierdo;
            else
                he = (VerticeRojinegro)pa.derecho;
        }

            //actuaizacion de referencias
        VerticeRojinegro hei = (VerticeRojinegro)he.izquierdo;
        VerticeRojinegro hed = (VerticeRojinegro)he.derecho;

        //caso 3
        if(esNegro(pa) && esNegro(he) && esNegro(hei) && esNegro(hed)){
            he.color = Color.ROJO;
            rebalanceoDel(pa);
            return;
        }

        //caso 4
        if(esNegro(he) && esNegro(hei) && esNegro(hed) && esRojo(pa)){
            he.color = Color.ROJO;
            pa.color = Color.NEGRO;
            return;
        }

        //caso 5
        if((pa.izquierdo == v && esRojo(hei) && esNegro(hed))){
            he.color = Color.ROJO;
            hei.color = Color.NEGRO;
            super.giraDerecha(he);
            pa = (VerticeRojinegro)v.padre;
            he = (VerticeRojinegro)pa.derecho;
        }
        if((pa.derecho == v && esNegro(hei) && esRojo(hed))){
            he.color = Color.ROJO;
            hed.color = Color.NEGRO;
            super.giraIzquierda(he);
            pa = (VerticeRojinegro)v.padre;
            he = (VerticeRojinegro)pa.izquierdo;
        }

                //actuaizacion de referencias
            hei = (VerticeRojinegro)he.izquierdo;
            hed = (VerticeRojinegro)he.derecho;

        //caso 6
            he.color = pa.color;
            pa.color = Color.NEGRO;
            if(pa.derecho == v && esRojo(hei)){
                hei.color = Color.NEGRO;
                super.giraDerecha(pa);
            }
            else{
                hed.color = Color.NEGRO;
                super.giraIzquierda(pa);
            }
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}