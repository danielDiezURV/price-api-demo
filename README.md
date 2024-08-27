# Price API Demo

Este proyecto es una aplicación de demostración de Spring Boot para gestionar y recuperar precios de productos. Incluye una API REST para consultar precios basados en la fecha de aplicación, el ID del producto y el ID de la cadena.

## Tabla de Contenidos

- [Enunciado](#enunciado)
- [Tecnologías](#tecnologías)
- [Configuración](#configuración)
- [Ejecución de la Aplicación](#ejecución-de-la-aplicación)
- [Ejecución de Tests](#ejecución-de-tests)
- [Resultados](#resultados)

## Enunciado

En la base de datos de comercio electrónico de la compañía disponemos de la tabla PRICES que refleja el precio final (pvp) y la tarifa que aplica a un producto de una cadena entre unas fechas determinadas. A continuación se muestra un ejemplo de la tabla con los campos relevantes:

### PRICES

| BRAND_ID | START_DATE          | END_DATE            | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE | CURR |
|----------|----------------------|---------------------|------------|------------|----------|-------|------|
| 1        | 2020-06-14-00.00.00  | 2020-12-31-23.59.59 | 1          | 35455      | 0        | 35.50 | EUR  |
| 1        | 2020-06-14-15.00.00  | 2020-06-14-18.30.00 | 2          | 35455      | 1        | 25.45 | EUR  |
| 1        | 2020-06-15-00.00.00  | 2020-06-15-11.00.00 | 3          | 35455      | 1        | 30.50 | EUR  |
| 1        | 2020-06-15-16.00.00  | 2020-12-31-23.59.59 | 4          | 35455      | 1        | 38.95 | EUR  |

### Campos:

- **BRAND_ID**: foreign key de la cadena del grupo (1 = ZARA).
- **START_DATE**, **END_DATE**: rango de fechas en el que aplica el precio tarifa indicado.
- **PRICE_LIST**: Identificador de la tarifa de precios aplicable.
- **PRODUCT_ID**: Identificador código de producto.
- **PRIORITY**: Desambiguador de aplicación de precios. Si dos tarifas coinciden en un rango de fechas se aplica la de mayor prioridad (mayor valor numérico).
- **PRICE**: precio final de venta.
- **CURR**: iso de la moneda.

### Requisitos:

Construir una aplicación/servicio en SpringBoot que provea un endpoint REST de consulta tal que:

- Acepte como parámetros de entrada: fecha de aplicación, identificador de producto, identificador de cadena.
- Devuelva como datos de salida: identificador de producto, identificador de cadena, tarifa a aplicar, fechas de aplicación y precio final a aplicar.

Se debe utilizar una base de datos en memoria (tipo H2) e inicializar con los datos del ejemplo, (se pueden cambiar el nombre de los campos y añadir otros nuevos si se quiere, elegir el tipo de dato que se considere adecuado para los mismos).

Desarrollar unos test al endpoint REST que validen las siguientes peticiones al servicio con los datos del ejemplo:

- Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)
- Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)
- Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)
- Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)
- Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)

## Tecnologías

Este proyecto utiliza las siguientes tecnologías:

- **Java 17**: Lenguaje de programación utilizado para desarrollar la aplicación.
- **Spring Boot**: Framework para construir aplicaciones Java basadas en Spring.
- **H2 Database**: Base de datos en memoria utilizada para almacenar los datos de ejemplo.
- **Mockito**: Framework de pruebas unitarias para Java.
- **Maven**: Herramienta de gestión de proyectos y dependencias.

## Configuración

### Prerrequisitos

- **Java 17** o superior
- **Maven 3.6.0** o superior

### Instalación

1. Clona el repositorio:

    ```sh
    git clone https://github.com/danielDiezURV/price-api-demo.git
    cd price-api-demo
    ```

2. Instala las dependencias y construye el proyecto:

    ```sh
    mvn clean package
    ```

## Ejecución de la Aplicación

### Opción 1: Spring Boot

1. Inicia la aplicación Spring Boot:

    ```sh
    mvn spring-boot:run
    ```

2. La aplicación estará disponible en `http://localhost:8080/swagger-ui/index.html`.

### Opción 2: Docker

1. Construye la imagen Docker:

    ```sh
    docker build -t price-api-demo .
    ```

2. Ejecuta el contenedor Docker:

    ```sh
    docker run -p 8080:8080 price-api-demo
    ```

3. La aplicación estará disponible en `http://localhost:8080/swagger-ui/index.html`.

## Ejecución de Tests

### Tests Unitarios

Para ejecutar los tests unitarios, usa el siguiente comando:

```sh
mvn test
``` 
### Tests de Integración

Para ejecutar los tests de integración usando Postman, sigue estos pasos:

1. Abre Postman.
2. Importa la colección de pruebas de integración desde el archivo `postman_collection.json` que se encuentra en el directorio raíz del proyecto.
3. Ejecuta la colección de pruebas.

Alternativamente, puedes usar la línea de comandos para ejecutar la colección de Postman usando Newman:

1. Instala Newman si no lo tienes instalado:

    ```sh
    npm install -g newman
    ```

2. Ejecuta la colección de Postman:

    ```sh
    newman run priceTests.postman_collection.json
    ```

Esto ejecutará todas las pruebas definidas en la colección de Postman y mostrará los resultados en la terminal.

## Resultados

### Test 1: Petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```
### Test 2: Petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45,
  "currency": "EUR"
}
```
### Test 3: Petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```
### Test 4: Petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 3,
  "startDate": "2020-06-15T00:00:00",
  "endDate": "2020-06-15T11:00:00",
  "price": 30.50,
  "currency": "EUR"
}
```
### Test 5: Petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 4,
  "startDate": "2020-06-15T16:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 38.95,
  "currency": "EUR"
}
```
