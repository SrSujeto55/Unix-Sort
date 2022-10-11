package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
            // Aquí va su código.
            start();
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return this.siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            // Aquí va su código.
            if(this.siguiente == null)
                throw new NoSuchElementException();
            Nodo n = this.siguiente;
            this.siguiente = n.siguiente;
            this.anterior = n;
            return n.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            // Aquí va su código.
            return this.anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            // Aquí va su código.
            if(this.anterior == null)
                throw new NoSuchElementException();
            Nodo n = this.anterior;
            this.anterior = n.anterior;
            this.siguiente = n;
            return n.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            // Aquí va su código.
            this.anterior = null;
            this.siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            // Aquí va su código.
            this.siguiente = null;
            this.anterior = rabo;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return cabeza == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento == null)
        throw new IllegalArgumentException();
        longitud++;
        Nodo n = new Nodo(elemento);
        if(esVacia())
          rabo = cabeza = n;
        else{
            rabo.siguiente = n;
            n.anterior = rabo;
            rabo = n;
        }
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        // Aquí va su código.
        if(elemento == null)
        throw new IllegalArgumentException();
        longitud++;
        Nodo n = new Nodo(elemento);
        if(esVacia())
          rabo = cabeza = n;
        else{
            rabo.siguiente = n;
            n.anterior = rabo;
            rabo = n;
        }
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        // Aquí va su código.
        if(elemento == null)
        throw new IllegalArgumentException();
        longitud++;
        Nodo n = new Nodo(elemento);
        if(esVacia())
          rabo = cabeza = n;
        else{
            cabeza.anterior = n;
            n.siguiente = cabeza;
            cabeza = n;
        }
    }
    int indiceNodo;

    //Metodo para buscar un Nodo de un T
    private Nodo BuscarNodo(T elemento){
        Nodo h = cabeza;
        indiceNodo = 0;
        while(h != null){
            if(elemento.equals(h.elemento))
            return h;
            else{
                h = h.siguiente;
                indiceNodo++;
            }
        }
        return null;
    }

    //metodo para conseguir un Nodo por indice
    private Nodo nodoPorIndex(int ind, Nodo n, int cont){
        if(cont < ind && n != null){
          n = n.siguiente;
          cont++;
          return nodoPorIndex(ind, n, cont);
        }
        return n;
      }

      //metodo para eliminar un nodo
    private void quitaNodo(Nodo eliminado){

        if(rabo == cabeza){ 
            rabo = cabeza = null;
            longitud--;
            return;
        }
        else if(eliminado == cabeza){
          cabeza.siguiente.anterior = null;
          cabeza = cabeza.siguiente;
          longitud--;

        }else if(eliminado == rabo){
          rabo.anterior.siguiente = null;
          rabo = rabo.anterior;
          longitud--;

        }
        else{
          eliminado.siguiente.anterior = eliminado.anterior;
          eliminado.anterior.siguiente = eliminado.siguiente;
          longitud--;
        }
  
      }


    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        // Aquí va su código.
        if(elemento == null)
        throw new IllegalArgumentException();

        if(i < 1){
          agregaInicio(elemento);
        }
        else if(i > longitud -1){
          agregaFinal(elemento);
        }
        else{
          Nodo n = new Nodo(elemento);
          Nodo s = nodoPorIndex(i, cabeza, 0);
          Nodo a = s.anterior;
          a.siguiente = n;
          n.anterior = a;
          s.anterior = n;
          n.siguiente = s;
          longitud++;
        }
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
         if(elemento == null || esVacia())
            return;
        quitaNodo(BuscarNodo(elemento));
           
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        // Aquí va su código.
        if(esVacia())
        throw new NoSuchElementException();

        else{
         Nodo Temp = cabeza;
         quitaNodo(Temp);
         return Temp.elemento;
        }
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        // Aquí va su código.
        if(esVacia())
        throw new NoSuchElementException();

        else{
         Nodo Temp = rabo;
         quitaNodo(Temp);
         return Temp.elemento;
        }
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        return BuscarNodo(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        // Aquí va su código.
        Lista<T> reversa = new Lista<T>();
        Nodo n = rabo;
        while(n != null){
            reversa.agregaFinal(n.elemento);
            n = n.anterior;
        }
        return reversa;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        // Aquí va su código.
        Lista<T> copia = new Lista<T>();
        Nodo n = cabeza;
        while(n != null){
            copia.agregaFinal(n.elemento);
            n = n.siguiente;
        }
        return copia;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        // Aquí va su código.
        if(esVacia())
        throw new NoSuchElementException();
        else{
            return cabeza.elemento;
        }
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        // Aquí va su código.
        if(esVacia())
            throw new NoSuchElementException();
        else{
            return rabo.elemento;
        }
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        // Aquí va su código.
        if(i < 0 || i >= longitud)
            throw new ExcepcionIndiceInvalido();
        else {
            return nodoPorIndex(i, cabeza, 0).elemento;
    }
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        // Aquí va su código.
        Nodo temp = BuscarNodo(elemento);
        if(temp != null){
          return indiceNodo;
        }
        else{
          return -1;
        }
    }

    private String stringAux(String a, Nodo n){
        if(n != rabo){
            a += String.format("%s, ", n.elemento);
            return stringAux(a, n.siguiente);
        }
        return a;
     }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        // Aquí va su código.
        if(esVacia()){
            return "[]";
          }
  
            String s = "[";
            s = stringAux(s, cabeza);
            s += String.format("%s]", rabo.elemento);
              return s;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        // Aquí va su código.
        if (lista == null || longitud != lista.getLongitud()){
            return false;
          }
        Nodo a = cabeza;
        Nodo b = lista.cabeza;
        while(a != null){
            if(a.elemento.equals(b.elemento)){
                a = a.siguiente;
                b = b.siguiente;
            }
            else{
                return false;
            }
        }
        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    private Lista<T> merge(Lista<T> p1, Lista<T> p2, Comparator<T> comparador){
        Lista<T> mezclada = new Lista<T>();
        Nodo i = p1.cabeza;
        Nodo j = p2.cabeza;
          while(i != null && j != null){
              if(comparador.compare(i.elemento, j.elemento) <= 0){
                mezclada.agregaFinal(i.elemento);
                i = i.siguiente;
              }
              else{
                mezclada.agregaFinal(j.elemento);
                j = j.siguiente;
              }
            }
              if(j == null) {
                while(i != null){
                    mezclada.agregaFinal(i.elemento);
                    i = i.siguiente;
                }
              }
              else {
                while(j != null){
                    mezclada.agregaFinal(j.elemento);
                    j = j.siguiente;
                }
              }
  
        return mezclada;
      }

      private Lista<T> mergeAux(Lista<T> copy, Comparator<T> comparador){
        if(copy.longitud <= 1)
          return copy;
        else{
          int m;
          Nodo h = copy.cabeza;
          Lista<T> p1 = new Lista<T>();
          Lista<T> p2 = new Lista<T>();
            if(copy.longitud % 2 == 0)
              m = copy.longitud/2;
            else{
              m = (copy.longitud-1)/2;
            }
  
          int i = 1;
          while(h != null){
                if(i <= m)
                  p1.agregaFinal(h.elemento);
                else
                  p2.agregaFinal(h.elemento);
  
                    h = h.siguiente;
                    i++;
          }
          return merge(mergeAux(p1, comparador), mergeAux(p2, comparador), comparador);
        }
  
      }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        // Aquí va su código.
        if(longitud <= 1)
            return this.copia();
        else
            return mergeAux(this.copia(), comparador);
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        // Aquí va su código.
        if(comparador.compare(elemento, rabo.elemento) > 0){
            return false;
          }   
        else if(indiceDe(elemento) != -1){
            return true;
        }
        else{
            return false;
        }
          
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}