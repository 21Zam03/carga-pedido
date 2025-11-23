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

2. **Crear la base de datos vacía**:
   Crear una base de datos postgres local y configurar las crendenciales en el archivo application.yml

   datasource:
    url: jdbc:postgresql://{host}:{port}/{database}
    username: {username}
    password: {password}

