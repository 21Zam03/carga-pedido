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
  - `Idempotency-Key`: Clave única para garantizar idempotencia en la carga.  
- **Parámetros de formulario (multipart/form-data):**  
  - `file`: Archivo CSV con los pedidos a cargar.  
- **Respuesta:** JSON con un resumen del procesamiento (`ResumenDto`), que incluye:
  - Número de registros cargados correctamente.
  - Número de errores y detalles de los registros inválidos.

- **Ejemplo de uso con Postman:**
  1. Importar la colección `postman/PruebaTecnica.postman_collection.json`.
  2. Seleccionar el endpoint `POST /carga-pedido/csv`.
  3. Agregar el header `Idempotency-Key` con un valor único (por ejemplo, un UUID).
  4. Adjuntar un archivo CSV desde `/samples`.
  5. Ejecutar la solicitud y revisar el resumen de la carga.

#### `GET /pedidos` (opcional, si existe)

- **Descripción:** Listar los pedidos cargados en la base de datos.  
- **Parámetros:** Puede incluir filtros opcionales como fecha, estado o cliente.  
- **Respuesta:** JSON con la lista de pedidos existentes.  

---

### Esquema OpenAPI

- El esquema completo de la API está disponible en tiempo de ejecución:

