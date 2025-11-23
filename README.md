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

## Estrategia de Batch

La aplicación utiliza una estrategia de **procesamiento batch** para la carga masiva de pedidos desde archivos CSV, permitiendo manejar grandes volúmenes de datos (más de 100 registros) de manera eficiente y segura. A continuación se describe el flujo implementado:

### Flujo de procesamiento

1. **Validación de idempotencia**
   - Se calcula un hash SHA-256 del archivo recibido.
   - Se valida que la clave `Idempotency-Key` junto con el hash del archivo no hayan sido procesados anteriormente, evitando duplicados.

2. **Lectura inicial del archivo**
   - Se realiza un primer barrido del CSV para extraer:
     - IDs de clientes
     - IDs de zonas de entrega
     - Números de pedido
   - Esto permite hacer consultas a la base de datos de manera anticipada y reducir accesos repetidos.

3. **Carga de datos de referencia**
   - Se obtienen de la base de datos todos los pedidos existentes para evitar duplicados.
   - Se cargan en memoria los clientes y zonas involucradas para validaciones rápidas.

4. **Procesamiento por lotes (batch)**
   - Cada fila del CSV se valida:
     - Duplicidad de número de pedido
     - Existencia del cliente
     - Existencia y compatibilidad de la zona de entrega
     - Validaciones de negocio adicionales
   - Los pedidos válidos se agregan a un batch temporal (`List<PedidoModel>`).
   - Cuando el batch alcanza un tamaño configurado (`batchSize`), se persisten todos los pedidos de golpe con `saveAll()`.
   - El batch se limpia y se continúa con el siguiente grupo de pedidos.

5. **Registro de errores**
   - Los pedidos que no cumplen validaciones se registran en una lista de errores con:
     - Fila procesada
     - Mensaje de error
     - Clasificación del tipo de error
   - Se mantiene un conteo total de registros procesados, guardados y con errores.

6. **Persistencia final y cierre**
   - Si quedan registros pendientes en el batch al finalizar la lectura, se guardan en la base de datos.
   - Se registra la carga en la tabla de idempotencia con el hash del archivo y la clave de idempotencia.

7. **Resumen final**
   - Se genera un objeto `ResumenDto` que incluye:
     - Total de registros procesados
     - Total de registros guardados
     - Total de registros con error
     - Detalle de errores por fila
     - Conteo de errores por tipo
---

### Beneficios de esta estrategia

- Minimiza el número de accesos a la base de datos.
- Permite procesar archivos grandes de manera eficiente.
- Evita duplicados mediante idempotencia.
- Permite un control detallado de errores y seguimiento por tipo de fallo.
- Garantiza integridad y consistencia en la base de datos.

## Pruebas Unitarias

La aplicación cuenta con **pruebas unitarias en la capa de dominio**, logrando una cobertura de **81%**.  
Estas pruebas validan la lógica de negocio de los pedidos y aseguran que las reglas del dominio se cumplan correctamente antes de persistir los datos.

<img width="1090" height="214" alt="image" src="https://github.com/user-attachments/assets/f7067241-f201-4996-bd63-3c5ee4b2bb83" />


