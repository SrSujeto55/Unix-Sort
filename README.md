# Ordenador lexicográfico 
Este ordenador lexicográfico intenta simular el comportamineto del comando **sort** de Unix.

# Requerimientos

Este proyecto fue desarrollado en Maven, por lo tanto, es necesario que tengas una versión funcional de Maven.

# Uso

## compilar el proyecto
Dirígete a la carpeta raiz del proyecto y ejecuta lo siguiente:

    mvn compile

## Instalar el proyecto
Dirígete a la carpeta raiz del proyecto y ejectua lo siguiente:

    mvn install

Acto seguido se empezará a instalar el programa.

## Ejecutar el programa

Para empezar a usar el programa tienes varias opciones, pero en un inicio deberás empezar con:

    java -jar target/proyecto1

acto seguido puedes pasarle como parámetro el nombre de algún archivo (o múltiples archivos) o ruta de éste para que el programa realice el ordenamiento correcpondiente, puedes usar la bandera:

    -o <Nombre_De_Archivo>

Para guardar la salida del programa en un archivo que usted mismo nombrará

o bien, puede usar la bandera

    -r

para realizar el ordenamiento de manera inversa.

Asímismo puede usar las 2 banderas, éstas deben ir al inicio del programa y si se usa la bandera -o el nombre del archivo donde se guardará la salida debe ir al final. Ejemplo:

    java -jar /target/proyecto1 -o -r Hombres.txt Salida.txt

También puede usar la entrada estandar para agregar las lineas de texto, ejecutando 

    java -jar target/proyecto1

sin parámetros.
