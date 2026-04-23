# COMPILAR TODOS LOS EJEMPLOS EN WINDOWS

@echo off
REM Script para compilar todos los ejemplos de Java en Windows

echo.
echo ==================================================
echo   COMPILANDO EJEMPLOS DE JAVA
echo ==================================================
echo.

setlocal enabledelayedexpansion

set base_dir=%cd%

REM Crear directorio bin si no existe
if not exist bin mkdir bin

REM Compilar todos los archivos .java
echo Compilando archivos...

for /r %%F in (*.java) do (
    echo Compilando: %%~nF
    javac -d bin "%%F" 2>nul
)

echo.
echo ================================================== 
echo   COMPILACION COMPLETADA
echo ==================================================
echo.
echo Archivos compilados en: %base_dir%\bin
echo.
echo Para ejecutar un ejemplo:
echo   java -cp bin fundamentos.SintaxisBasica
echo   java -cp bin poo.Persona
echo   java -cp bin proyecto_integrador.ProyectoIntegrador
echo.
