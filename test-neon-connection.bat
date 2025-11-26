@echo off
echo ================================================
echo GastroReview - Test de Conexion a Neon
echo ================================================
echo.

echo [1/3] Compilando proyecto...
call mvnw.cmd clean compile -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Falló la compilación
    echo Verifica que Java 21 esté instalado
    pause
    exit /b 1
)

echo.
echo [2/3] Verificando conexión a base de datos...
echo (Esto puede tardar unos segundos si Neon está hibernando)
echo.

call mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=8083" -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Falló la conexión a Neon
    echo.
    echo Posibles causas:
    echo 1. Credenciales incorrectas en application.properties
    echo 2. Neon está hibernando (espera 30 segundos y reintenta)
    echo 3. Firewall bloqueando conexión
    echo.
    echo Consulta NEON_SETUP.md para más ayuda
    pause
    exit /b 1
)

echo.
echo ================================================
echo EXITO! Conexión establecida
echo ================================================
echo.
echo Accede a:
echo - Swagger UI: http://localhost:8083/swagger-ui/index.html
echo - GraphiQL: http://localhost:8083/graphiql
echo.
pause
