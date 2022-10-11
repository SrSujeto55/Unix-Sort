package mx.unam.ciencias.edd.proyecto1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Lista;

/**
 * <p> clase para conseguir elementos por entrada estandar, esta clase tambien se usa para guardar 
 * en archivos
 */

 public class EntradaEstandar{

    /**
     * Constructor sin parametros
     */
    public EntradaEstandar(){}


    /**
     * metodo para lectura estandar
     * @return el arbol binario con las cadenas ordenadas
     */
    public ArbolRojinegro<CadenaStr> LecturaEstandar(){
        String linea;
        ArbolRojinegro<CadenaStr> cargada = new ArbolRojinegro<CadenaStr>();
        try{
          BufferedReader br = new BufferedReader(
                          new InputStreamReader(System.in));
                  while( (linea = br.readLine()) != null){
                      CadenaStr nuevaCadena = new CadenaStr(linea);
                    cargada.agrega(nuevaCadena);
                  }

        } catch(IOException e){}
          return cargada;
      }
            

      /**
       * Lectura de un archivo
       * @param archivo el archivo a leer
       * @return el arbol binario con las lineas ordenadas
       */
      public ArbolRojinegro<CadenaStr> lectura(String nombreArchivo){

        ArbolRojinegro<CadenaStr> archivoCargado = new ArbolRojinegro<CadenaStr>();
        File doc = new File(nombreArchivo);
        BufferedReader in;
        
        if(doc.exists()){
            try{
                in = new BufferedReader(new FileReader(doc));
                try{
                archivoCargado = carga(in);
                in.close();
                }catch(IOException e){
                    throw new FileNotFoundException();
                }
    
            }catch(FileNotFoundException F){
                System.out.printf("cannot read: \"%s\": No such path or directory.\n",
                nombreArchivo);
                System.exit(1);
            }

            return archivoCargado;
        }
  
        try {
            FileInputStream fileIn = new FileInputStream(nombreArchivo);
            InputStreamReader isIn = new InputStreamReader(fileIn);
            in = new BufferedReader(isIn);
            archivoCargado = carga(in);
            in.close();
        } catch (IOException IEe) {
            System.out.printf("cannot read: \"%s\": No such file or directory.\n",
                              nombreArchivo);
            System.exit(1);
        }
          return archivoCargado;
      }

    /**
     * Metodo para la escritura de datos
     */

    public void escritura(String nombreArchivo, ArbolRojinegro<CadenaStr> AB) {

        try {
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            OutputStreamWriter osOut = new OutputStreamWriter(fileOut);
            BufferedWriter out = new BufferedWriter(osOut);
            guarda(out, AB);
            out.close();
        } catch (IOException ioe) {System.exit(1);}
        System.out.printf("\n Texto guardado exitosamente en: \"%s\".\n", nombreArchivo);

    }

    public void escritura(String nombreArchivo, Lista<CadenaStr> ls) {

        try {
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            OutputStreamWriter osOut = new OutputStreamWriter(fileOut);
            BufferedWriter out = new BufferedWriter(osOut);
            guarda(out, ls);
            out.close();
        } catch (IOException ioe) {System.exit(1);}
        System.out.printf("\n Texto guardado exitosamente en: \"%s\".\n", nombreArchivo);

    }

    public void guarda(BufferedWriter out, ArbolRojinegro<CadenaStr> ordenado) 
    throws IOException{

        String lineaExtensa = "";
          for(CadenaStr cadena : ordenado)
            lineaExtensa += cadena.getOriginal() + "\n";
          out.write(lineaExtensa);
    }
    
    public void guarda(BufferedWriter out, Lista<CadenaStr> ls) 
    throws IOException{

        String lineaExtensa = "";
          for(CadenaStr cadena : ls)
            lineaExtensa += cadena.getOriginal() + "\n";
          out.write(lineaExtensa);
      }   
      
    

    private static ArbolRojinegro<CadenaStr> carga(BufferedReader in) 
    throws IOException{

    ArbolRojinegro<CadenaStr> cargada = new ArbolRojinegro<CadenaStr>();
        String linea;
        while( (linea = in.readLine()) != null){
            CadenaStr nuevaCadena = new CadenaStr(linea);
            cargada.agrega(nuevaCadena);
        }

        return cargada;
    }  

 }

    