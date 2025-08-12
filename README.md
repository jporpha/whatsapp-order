# 📦 WhatsApp Order

Sistema **RESTful** en Java + Spring Boot para gestionar un catálogo de productos y generar pedidos listos para enviar por WhatsApp.  
Diseñado para ser **simple, genérico y reutilizable** para cualquier negocio que venda por redes sociales.

---

## 🚀 Tecnologías Utilizadas
- **Java 17**
- **Spring Boot 3**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **MapStruct** (DTO ↔ Entity)
- **Lombok**
- **Swagger/OpenAPI**
- **H2 Database** (modo memoria por defecto)
- **HTML estático + Bootstrap** para panel admin y vista pública

---

## 📂 Estructura del Proyecto
```
src
 ├─ main
 │   ├─ java/com/jp/orpha/whatsapp_order
 │   │   ├─ controller     # Controladores REST
 │   │   ├─ service        # Lógica de negocio
 │   │   ├─ repository     # Acceso a datos
 │   │   ├─ entity         # Entidades JPA
 │   │   ├─ dto            # Objetos de transferencia
 │   │   ├─ mapper         # MapStruct mappers
 │   │   └─ exception      # Manejo de errores
 │   └─ resources
 │       ├─ static         # HTML estáticos (admin.html, index.html)
 │       ├─ application.properties
 │       └─ data.sql       # Datos iniciales (opcional)
```

---

## ⚙️ Configuración y Ejecución

### 1️⃣ Requisitos previos
- Java 17 instalado  
- Maven 3.8+ instalado  

### 2️⃣ Clonar el repositorio
```bash
git clone https://github.com/TU_USUARIO/whatsapp-order.git
cd whatsapp-order
```

### 3️⃣ Ejecutar
```bash
mvn spring-boot:run
```
La API estará disponible en:  
`http://localhost:8080`

---

## 📖 Documentación API
Swagger UI disponible en:
```
http://localhost:8080/swagger-ui.html
```

Endpoints principales:
- **Catálogo público** → `GET /api/v1/public/catalog`
- **Generar pedido** → `POST /api/v1/public/order/generate`
- **CRUD Productos** → `/api/v1/products`
- **Configuración** → `/api/v1/admin/settings`
- **Plantillas de mensaje** → `/api/v1/admin/templates`

---

## 🖥 Paneles Web
- **Vista pública (pedido)** → [http://localhost:8080/index.html](http://localhost:8080/index.html)  
- **Panel administrador** → [http://localhost:8080/admin.html](http://localhost:8080/admin.html)  

---

## 🗄 Base de Datos (H2)
Por defecto se usa **H2 en memoria** (se reinicia al apagar la app).  
Consola H2:  
[http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
- JDBC URL: `jdbc:h2:mem:waorder`
- User: `sa`
- Pass: *(vacío)*

Para persistir datos entre reinicios, cambiar en `application.properties`:
```properties
spring.datasource.url=jdbc:h2:file:./data/waorder;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1
```

---

## 📜 Licencia
Este proyecto está bajo licencia MIT. Libre para uso y modificación.
