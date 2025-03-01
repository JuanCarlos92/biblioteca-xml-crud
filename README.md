# Gestión de Base de Datos Nativas XML para la Gestión de Bibliotecas

Este proyecto consiste en una aplicación desarrollada en Java que gestiona información almacenada en una base de datos nativa XML utilizando BaseX. El objetivo principal de esta aplicación es permitir la gestión de un catálogo de libros, incluyendo funcionalidades CRUD (Crear, Leer, Actualizar, Eliminar), la administración de colecciones, y la manipulación de documentos XML. La aplicación también incluye un análisis sobre las ventajas e inconvenientes de utilizar una base de datos nativa XML.

## Objetivos del Proyecto

- **Gestión de Catálogo de Libros:** La aplicación permite realizar consultas sobre un catálogo de libros de una biblioteca, utilizando tecnologías como XQuery o XPath para realizar búsquedas avanzadas.
- **Funcionalidades CRUD:** Se implementan las operaciones de crear, leer, actualizar y eliminar libros en el catálogo.
- **Administración de Colecciones:** La aplicación permite gestionar colecciones de libros (por ejemplo: ficción, no ficción, infantil, etc.) mediante la creación y eliminación de colecciones.
- **Base de Datos Nativa XML:** Utiliza BaseX como base de datos nativa XML para almacenar los datos.
- **Análisis de Base de Datos XML:** Incluye un análisis sobre las ventajas e inconvenientes de usar una base de datos nativa XML.

## Requisitos

- **Java 8 o superior**
- **BaseX (Base de Datos Nativa XML)**
- **XQuery o XPath para consultas**
- **Bibliotecas de BaseX para Java**

## Funcionalidades Implementadas

1. **CRUD de Libros:**
   - Permite agregar nuevos libros al catálogo.
   - Consultar libros por autor, género o año de publicación.
   - Actualizar los datos de los libros existentes.
   - Eliminar libros del catálogo.

2. **Gestión de Colecciones:**
   - Crear y eliminar colecciones de libros (por ejemplo: ficción, no ficción, infantil, etc.).
   - Administrar las colecciones a través de la base de datos XML.

3. **Consultas XQuery y XPath:**
   - Consultas para buscar libros por diferentes criterios.
   - Realización de operaciones complejas sobre los datos XML utilizando XQuery o XPath.

## Instalación

### Requisitos Previos

1. **Instalar Java:**
   - Asegúrate de tener Java 8 o superior instalado en tu máquina.
   - Puedes verificar la versión de Java ejecutando `java -version` en la terminal.

2. **Instalar BaseX:**
   - Descarga e instala BaseX desde su [sitio oficial](https://basex.org/download/).
   - Asegúrate de tener configurada la conexión de BaseX correctamente en tu entorno.

3. **Dependencias:**
   - Este proyecto usa las bibliotecas de BaseX para interactuar con la base de datos XML. Asegúrate de tenerlas configuradas correctamente en tu archivo `pom.xml` (si usas Maven) o descargadas manualmente.

### Clonación del Proyecto

```bash
  git clone https://github.com/tu_usuario/gestion-biblioteca-xml.git
  cd gestion-biblioteca-xml
  ```

### Compilación y Ejecución

1. **Compila y ejecuta el proyecto:**
   - Si estás usando Maven, puedes compilar el proyecto con el siguiente comando:
     
```bash
  mvn clean install
  ```

   - Para ejecutar el proyecto:
     
```bash
  mvn exec:java
  ```

2. **Configuración de BaseX:**
   - Asegúrate de tener una base de datos de BaseX configurada con el catálogo de libros.
   - Si no tienes un archivo XML de muestra, puedes crear uno con un formato como el siguiente:

```xml
  <catalog>
      <book>
          <title>Harry Potter y la Piedra Filosofal</title>
          <author>J.K. Rowling</author>
          <genre>Ficción</genre>
          <year>1997</year>
      </book>
      <!-- Otros libros -->
  </catalog>
  ```

## Análisis de Base de Datos XML
En este proyecto se incluye un análisis sobre las ventajas e inconvenientes de utilizar una base de datos nativa XML:

### Ventajas:
**Estructura flexible y escalable:** Las bases de datos XML permiten un modelo de datos jerárquico flexible.
**Consulta avanzada con XQuery y XPath:** Permite realizar consultas complejas sobre los datos XML.
**Fácil integración con aplicaciones que utilizan XML:** Ideal para aplicaciones que ya gestionan información en formato XML.

### Inconvenientes:
**Rendimiento:** Las bases de datos XML pueden no ser tan rápidas como las bases de datos relacionales en ciertos escenarios.
**Complejidad de consultas:** Las consultas en XQuery o XPath pueden ser complejas de escribir y entender, especialmente en bases de datos grandes.

## Contribuciones

Si deseas contribuir al proyecto, sigue los siguientes pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama:
   ```bash
     git checkout -b mi-rama
     ```
3. Realiza tus cambios y haz commit de tus modificaciones:
   ```bash
     git commit -am 'Añadir nueva funcionalidad'
     ```
4. Sube tus cambios a tu repositorio remoto:
   ```bash
     git push origin mi-rama
     ```
5. Abre un Pull Request para que tus cambios sean revisados e integrados al repositorio principal.
