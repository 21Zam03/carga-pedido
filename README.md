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
