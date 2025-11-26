# 🚀 Configuración de Neon PostgreSQL para GastroReview

Este proyecto usa **Neon** (PostgreSQL serverless en la nube) como base de datos.

---

## 📋 Requisitos Previos

- Cuenta de Neon (gratuita)
- Java 21 instalado
- Maven instalado

---

## 🛠️ Paso a Paso

### 1️⃣ Crear cuenta en Neon

1. Ve a: https://console.neon.tech/signup
2. Regístrate con GitHub, Google o email
3. Verifica tu correo electrónico

### 2️⃣ Crear proyecto en Neon

1. Inicia sesión en https://console.neon.tech
2. Haz clic en **"Create a project"**
3. Configura:
   - **Project name**: `GastroReview`
   - **Database name**: `gastroreview` (déjalo por defecto)
   - **Region**: Elige el más cercano (ej: US East Ohio, US West Oregon, etc.)
   - **PostgreSQL version**: 16 (por defecto)
4. Haz clic en **"Create project"**

### 3️⃣ Obtener credenciales de conexión

Después de crear el proyecto, Neon te mostrará las credenciales:

```
Host: ep-xxxxx-xxxxx-xxxxx.us-east-2.aws.neon.tech
Database: gastroreview
User: gastroreview_owner
Password: npg_xxxxxxxxxxxxxxxxxxxxx
```

**⚠️ IMPORTANTE**: Guarda tu password, solo se muestra una vez.

Si perdiste el password:
1. Ve a tu proyecto en Neon
2. Click en **"Settings"** → **"Reset password"**

### 4️⃣ Configurar application.properties

Abre: `src/main/resources/application.properties`

**Actualiza la línea 10** con tu host (si es diferente):
```properties
spring.datasource.url=jdbc:postgresql://TU-HOST-AQUI.aws.neon.tech:5432/gastroreview?sslmode=require
```

**Actualiza la línea 11** con tu username (si es diferente):
```properties
spring.datasource.username=gastroreview_owner
```

**Actualiza la línea 12** con tu password real:
```properties
spring.datasource.password=npg_xxxxxxxxxxxxxxxxxxxxx
```

### 5️⃣ Compilar y ejecutar

```bash
# Compilar
./mvnw.cmd clean compile

# Ejecutar
./mvnw.cmd spring-boot:run
```

Si todo está configurado correctamente, verás:
```
HikariPool-1 - Starting...
HikariPool-1 - Start completed.
Hibernate: create table if not exists users (...)
Tomcat started on port(s): 8083
```

---

## 🔒 Buenas Prácticas de Seguridad

### ❌ NO subas tus credenciales a Git

El archivo `.gitignore` ya está configurado para ignorar:
- `.env`
- `application-local.properties`
- `application-prod.properties`

### ✅ Usa variables de entorno (Recomendado)

1. Crea un archivo `.env` (basado en `.env.example`):
```bash
copy .env.example .env
```

2. Edita `.env` con tus credenciales reales
3. Nunca subas `.env` a Git

### 🔐 Para producción

Cambia el JWT secret en `application.properties` línea 45:
```properties
jwt.secret=UN_SECRET_MUY_SEGURO_Y_LARGO_PARA_PRODUCCION
```

---

## 🌐 URLs del Proyecto

Una vez ejecutando:

- **Swagger UI**: http://localhost:8083/swagger-ui/index.html
- **GraphiQL**: http://localhost:8083/graphiql
- **API Base**: http://localhost:8083/api/v1/
- **Health Check**: http://localhost:8083/api/v1/auth/test

---

## 🐛 Troubleshooting

### Error: "Connection refused"
- Verifica que tu host de Neon sea correcto
- Asegúrate de incluir `?sslmode=require` al final de la URL

### Error: "password authentication failed"
- Revisa que tu username y password sean correctos
- Resetea tu password en Neon si es necesario

### Error: "SSL connection required"
- Asegúrate de que tu URL incluya: `?sslmode=require`

### Error: "HikariPool - Connection is not available"
- Tu base de datos en Neon puede estar "dormida" (sleep mode)
- Espera unos segundos y vuelve a intentar
- El plan gratuito de Neon hiberna después de 5 minutos de inactividad

---

## 📊 Plan Gratuito de Neon

El plan gratuito incluye:
- ✅ 0.5 GB de almacenamiento
- ✅ 10 branches
- ✅ PostgreSQL 16
- ✅ Auto-scaling
- ✅ Backups automáticos
- ⚠️ Hibernación después de 5 minutos de inactividad
- ⚠️ Máximo 100 horas de compute time al mes

---

## 🔄 Cambiar entre Local y Neon

### Para usar Neon (por defecto):
Deja las líneas 10-12 sin comentar

### Para usar PostgreSQL local:
1. Comenta las líneas 10-12
2. Descomenta las líneas 17-19

---

## 📞 Soporte

- **Neon Docs**: https://neon.tech/docs/introduction
- **Neon Discord**: https://discord.gg/neon
- **GitHub Issues**: Reporta problemas del proyecto aquí

---

## ✅ Checklist Final

Antes de ejecutar el proyecto, verifica:

- [ ] Cuenta de Neon creada
- [ ] Proyecto creado en Neon
- [ ] Credenciales copiadas (host, username, password)
- [ ] `application.properties` actualizado con tus credenciales
- [ ] Proyecto compilado sin errores
- [ ] Aplicación ejecutándose en puerto 8083
- [ ] Swagger accesible en http://localhost:8083/swagger-ui/index.html

---

¡Listo! 🎉 Tu proyecto GastroReview ahora está conectado a Neon PostgreSQL en la nube.
