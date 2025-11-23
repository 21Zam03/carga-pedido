# Prueba Técnica - Carga de Pedidos

Este repositorio contiene la prueba técnica que incluye el servicio de carga de pedidos, junto con migraciones de base de datos, configuración de build, y ejemplos para validar la funcionalidad.

---

## Contenido del Repositorio

- `src/` - Código fuente de la aplicación.
- `db/migrations/` - Migraciones Flyway para la base de datos.
- `samples/` - Archivos de ejemplo para validar la carga de pedidos.
- `postman/` - Colección de Postman para probar el endpoint `POST /pedidos/cargar`.
- `build.gradle` / `pom.xml` - Configuración de build.

---

## Requisitos

- Java 17+
- Maven o Gradle (según configuración del proyecto)
- PostgreSQL/MySQL (según la configuración de tu aplicación)
- Postman (para pruebas)

---

## Instrucciones para ejecutar localmente

1. **Clonar el repositorio**:

```bash
git clone https://github.com/usuario/repositorio-prueba-tecnica.git
cd repositorio-prueba-tecnica
