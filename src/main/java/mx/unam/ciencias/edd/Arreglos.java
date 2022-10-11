package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    private static <T> void intercambia(T[] arreglo, int i, int m){
        T temp = arreglo[i];
        arreglo[i] = arreglo[m];
        arreglo[m] = temp;
      }

      private static <T> void quickAux(T[] arreglo, int a, int b, Comparator<T> comparador){
        if(b <= a)
          return;
        int i = a + 1;
        int j = b;
          while(i<j){
            if(comparador.compare(arreglo[i], arreglo[a]) > 0 && comparador.compare(arreglo[j], arreglo[a]) <= 0){
              intercambia(arreglo, i, j);
              i = i+1;
              j = j-1;
            }
            else if(comparador.compare(arreglo[i], arreglo[a]) <= 0)
              i = i+1;
            else
            j = j-1;
          }
          if(comparador.compare(arreglo[i], arreglo[a]) > 0)
            i = i-1;
          intercambia(arreglo, a, i);
          quickAux(arreglo, a, i-1, comparador);
          quickAux(arreglo, i+1, b, comparador);
  
      }

      private static <T> int busquedaAux(T[] arreglo, T elemento, int a, int b, Comparator<T> comparador){
        if(b < a)
          return -1;
  
          int m = a+((b-a)/2);
            if(comparador.compare(elemento, arreglo[m]) == 0){
              return m;
            }
            else if(comparador.compare(elemento, arreglo[m]) < 0){
              return busquedaAux(arreglo, elemento, a, m-1, comparador);
            }
            else {
              return busquedaAux(arreglo, elemento, m+1, b, comparador);
            }
  
      }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
        quickAux(arreglo, 0, arreglo.length -1, comparador);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
        for(int i = 0; i<arreglo.length; i++){
            int m = i;
            for(int j = i+1; j<arreglo.length; j++){
                if(comparador.compare(arreglo[j], arreglo[m]) < 0)
                m = j;
            }
            intercambia(arreglo, i, m);
          }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        // Aquí va su código.
        return busquedaAux(arreglo, elemento, 0, arreglo.length -1, comparador);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}