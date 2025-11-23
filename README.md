# Prueba Técnica - Carga de Pedidos

Este repositorio contiene la prueba técnica que incluye el servicio de carga de pedidos, junto con migraciones de base de datos, configuración de build, y ejemplos para validar la funcionalidad.

---

## Contenido del Repositorio

- `src/` - Código fuente de la aplicación.
- `db/migrations/` - Migraciones Flyway para la base de datos.
- `samples/` - Archivos de ejemplo para validar la carga de pedidos.
- `postman/` - Colección de Postman para probar el endpoint `POST /pedidos/cargar`.
- `pom.xml` - Configuración de build.

---

## Requisitos

- Java 17+
- Maven
- PostgreSQL
- Postman (para pruebas)

---

## Instrucciones para ejecutar localmente

1. **Clonar el repositorio**:

```bash
git clone https://github.com/usuario/repositorio-prueba-tecnica.git
cd repositorio-prueba-tecnica
```

2. **Configuración base de datos**:
- Asegúrate de tener una base de datos creada y accesible (PostgreSQL o MySQL).  
- Configura la conexión en `application.yml`

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pedidos_db
    username: tu_usuario
    password: tu_contraseña
    driver-class-name: org.postgresql.Driver
  flyway:
    locations: classpath:db/migrations
```

3. **Ejecutar la aplicación**:

- Spring Boot iniciará la aplicación y Flyway aplicará automáticamente todas las migraciones en la base de datos.

```bash
# Con Maven
mvn spring-boot:run
```

## Uso de la Aplicación

Esta aplicación permite cargar pedidos de manera batch y gestionarlos a través de la API. A continuación se describen las principales características y endpoints disponibles:

### Características principales

- Carga de pedidos desde archivos CSV.
- Procesamiento por lotes (batch) para optimizar la inserción de datos.
- Validación de datos y manejo de errores por lote.
- Registro de logs de procesamiento.
- Documentación de la API disponible vía OpenAPI.

### Endpoints disponibles

#### `POST /api/carga-pedido/csv`

- **Descripción:** Permite cargar un archivo CSV con pedidos en la base de datos de manera batch.  
- **Headers requeridos:**  
  - `Authorization`: `Bearer <token>` — Token JWT obtenido previamente desde `POST /api/token`.
  - `Idempotency-Key`: Clave única para garantizar idempotencia en la carga.
- **Parámetros de formulario (multipart/form-data):**  
  - `file`: Archivo CSV con los pedidos a cargar.
- **Respuesta:** JSON con un resumen del procesamiento (`ResumenDto`), que incluye:
  - Número de registros cargados correctamente.
  - Número de errores y detalles de los registros inválidos.

- **Ejemplo de uso con Postman:**
  1. Ejecutar `POST /token` para obtener un token JWT válido.  
  2. Importar la colección `postman/Dinet - Prueba tecnica.postman_collection.json`.  
  3. Seleccionar el endpoint `POST /carga-pedido/csv`.  
  4. Agregar los headers:  
     - `Authorization: Bearer <token>`  
     - `Idempotency-Key: <valor-único>`  
  5. Adjuntar un archivo CSV desde `/samples`.  
  6. Ejecutar la solicitud y revisar el resumen de la carga.

#### `POST /api/token`

- **Descripción:** Genera un token JWT que se puede usar para autenticación o pruebas de la API.  
- **Headers requeridos:** Ninguno.  
- **Parámetros de entrada:** Ninguno.  
- **Respuesta:** Devuelve un string con el token JWT generado.

- **Ejemplo de uso con Postman:**
  1. Importar la colección `postman/Dinet - Prueba tecnica.postman_collection.json`.
  2. Seleccionar el endpoint `POST /api/token`.
  3. Ejecutar la solicitud.
  4. Copiar el token devuelto para usarlo en otros endpoints que requieran autenticación.

---

## Esquema OpenAPI

El esquema completo de la API está disponible en tiempo de ejecución:

- **JSON OpenAPI:**  
[http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

- **Swagger UI (interfaz web para explorar la API):**  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Estas URLs permiten:  
- Visualizar la documentación de todos los endpoints.  
- Probar solicitudes directamente desde la interfaz web.  
- Generar clientes de API si es necesario.
