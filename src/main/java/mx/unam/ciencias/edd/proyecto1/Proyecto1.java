package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Lista;

/**
 * <p>Clase principal para ordenamientos lexicograficos<p>
 */

 public class Proyecto1{
    
/**
 * Metodo para invertir los elementos del Arbol Ordenado
 * @param Roji El arbol ordenado a invertir
 * @return una Lista en orden inverso lista para ser impresa
 */
  private static Lista<CadenaStr> invierte(ArbolRojinegro<CadenaStr> Roji){

    Lista<CadenaStr> invertida = new Lista<>();
    for(CadenaStr cd : Roji){
      invertida.agregaInicio(cd);
    }

    return invertida;
  }

  /**
   * metodo para unir varios archivos en uno solo
   * @param args la entrada estandar
   * @return
   */
  private static ArbolRojinegro<CadenaStr> unionArchivos(String[] args, int in, int id){

      ArbolRojinegro<CadenaStr> Entrada = new ArbolRojinegro<CadenaStr>();
      ArbolRojinegro<CadenaStr> RojiOrdenado = new ArbolRojinegro<CadenaStr>();

      String NombreArchivo;
      EntradaEstandar lec = new EntradaEstandar();

        for(int i = in; i < args.length - id; i++){
            NombreArchivo = args[i];
            Entrada = lec.lectura(NombreArchivo);

            for(CadenaStr cd: Entrada)
              RojiOrdenado.agrega(cd);

            Entrada.limpia();
        }
        return RojiOrdenado;
    }

    /**
     * Muestra el uso del programa
     */
    public static void uso(){
      System.out.println("Uso: java -jar target/proyecto1.jar [-o |-r | ] <Archivo1> <Archivo2> ...  <identificador>");
      System.exit(1);
    }

  public static void main(String[] args){
    /**
     * Basura de las banderitas
     */
    boolean guardado = false;
    boolean reversa = false;

    //checar si hay ambas banderitas
    if(args.length > 1){
      guardado = (args[0].equals("-o") || args[1].equals("-o"));
      reversa = (args[0].equals("-r") || args[1].equals("-r"));
    }

    if(args.length == 1){
      guardado = (args[0].equals("-o"));
      reversa = (args[0].equals("-r"));
    }

    if(args.length == 0){
      EntradaEstandar in = new EntradaEstandar();
      ArbolRojinegro<CadenaStr> Ordenado = in.LecturaEstandar();
      for(CadenaStr ln: Ordenado)
        System.out.println(ln.getOriginal());
      }
    
    if(args.length == 1 && reversa){
      EntradaEstandar in = new EntradaEstandar();
      ArbolRojinegro<CadenaStr> Ordenado = in.LecturaEstandar();

      Lista<CadenaStr> alReves = invierte(Ordenado);

        for(CadenaStr ln: alReves)
        System.out.println(ln.getOriginal());
        if(guardado == true)
          uso();
        System.exit(1);
    }

    else if(args.length == 1 && guardado){
      uso();
    }

    else if(args.length == 2 && guardado){
      EntradaEstandar in = new EntradaEstandar();
      ArbolRojinegro<CadenaStr> Ordenado = in.LecturaEstandar();
      
      String identificador = args[args.length -1];

      for(CadenaStr ln: Ordenado)
        System.out.println(ln.getOriginal());

      in.escritura(identificador, Ordenado);
      System.exit(1);
    }

    else if(args.length == 3 && guardado && reversa){
      EntradaEstandar in = new EntradaEstandar();
      ArbolRojinegro<CadenaStr> Ordenado = in.LecturaEstandar();

      String identificador = args[args.length -1];

      Lista<CadenaStr> alReves = invierte(Ordenado);

        for(CadenaStr ln: alReves)
          System.out.println(ln.getOriginal());

      in.escritura(identificador, alReves);
      System.exit(1);
    }

    if(guardado == false && reversa == false){
      ArbolRojinegro<CadenaStr> ordenada;

      if(args.length != 1){
        ordenada = unionArchivos(args, 0, 0);
      }

      else{
        EntradaEstandar in = new EntradaEstandar();
        String NombreArchivo = args[0];
        ordenada = in.lectura(NombreArchivo);
      }

      for(CadenaStr ln: ordenada){
        System.out.println(ln.getOriginal());
      }
    }

    else if(guardado && reversa){
      EntradaEstandar in = new EntradaEstandar();
      ArbolRojinegro<CadenaStr> ordenada = unionArchivos(args, 2, 1);
      String identificador = args[args.length -1];


      Lista<CadenaStr> alReves = invierte(ordenada);

      for(CadenaStr ln: alReves)
        System.out.println(ln.getOriginal());

      in.escritura(identificador, alReves);
    }

    else if(guardado && reversa == false){
      EntradaEstandar in = new EntradaEstandar();
      ArbolRojinegro<CadenaStr> ordenada = unionArchivos(args, 1, 1);
      String identificador = args[args.length -1];

      for(CadenaStr ln: ordenada)
        System.out.println(ln.getOriginal());

      in.escritura(identificador, ordenada);
    }

    else if(guardado == false && reversa){
      ArbolRojinegro<CadenaStr> ordenada = unionArchivos(args, 1, 0);

      Lista<CadenaStr> alReves = invierte(ordenada);

      for(CadenaStr ln: alReves)
        System.out.println(ln.getOriginal());
    }

    else{
      uso();
    }
  }
}