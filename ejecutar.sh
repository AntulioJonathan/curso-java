#!/bin/bash

# Script para compilar y ejecutar ejemplos de Java

JAVA_VERSION=$(java -version 2>&1 | grep -oP '(?<=")\d+' | head -1)
echo "Java versión detectada: $JAVA_VERSION"

if [ $JAVA_VERSION -lt 8 ]; then
    echo "Error: Se requiere Java 8 o superior"
    exit 1
fi

echo ""
echo "=================================================="
echo "  EJEMPLOS DE JAVA - Temario Completo"
echo "=================================================="
echo ""

# Función para ejecutar un ejemplo
ejecutar() {
    local paquete=$1
    local clase=$2
    local descripcion=$3
    
    echo ""
    echo "📝 $descripcion"
    echo "----------------------------------------"
    
    if [ -f "$paquete/$clase.java" ]; then
        javac "$paquete/$clase.java" 2>/dev/null
        java -cp . "$paquete.$clase"
    else
        echo "❌ Archivo no encontrado: $paquete/$clase.java"
    fi
}

# Menú interactivo
echo "Selecciona un ejemplo para ejecutar:"
echo ""
echo "1. Sintaxis Básica"
echo "2. Estructuras de Control"
echo "3. Arrays y Cadenas"
echo "4. Clases y Objetos"
echo "5. Herencia y Polimorfismo"
echo "6. Interfaces"
echo "7. Excepciones"
echo "8. Colecciones"
echo "9. Genéricos"
echo "10. I/O"
echo "11. Concurrencia"
echo "12. Streams y Lambdas"
echo "13. Fecha y Hora"
echo "14. Novedades"
echo "15. Proyecto Integrador (Biblioteca)"
echo "0. Salir"
echo ""
read -p "Opción: " opcion

case $opcion in
    1)
        ejecutar "01_fundamentos" "SintaxisBasica" "1️⃣ Sintaxis Básica"
        ;;
    2)
        ejecutar "01_fundamentos" "EstructurasControl" "2️⃣ Estructuras de Control"
        ;;
    3)
        ejecutar "01_fundamentos" "ArraysYCadenas" "3️⃣ Arrays y Cadenas"
        ;;
    4)
        ejecutar "02_poo" "Persona" "4️⃣ Clases y Objetos"
        ;;
    5)
        ejecutar "02_poo" "Animal" "5️⃣ Herencia y Polimorfismo"
        ;;
    6)
        ejecutar "02_poo" "Interfaz" "6️⃣ Interfaces"
        ;;
    7)
        ejecutar "03_excepciones" "EjemplosExcepciones" "7️⃣ Excepciones"
        ;;
    8)
        ejecutar "04_colecciones" "EjemplosColecciones" "8️⃣ Colecciones"
        ;;
    9)
        ejecutar "05_genericos" "EjemplosGenericos" "9️⃣ Genéricos"
        ;;
    10)
        ejecutar "06_io" "EjemplosIO" "🔟 I/O"
        ;;
    11)
        ejecutar "07_concurrencia" "EjemplosConcurrencia" "1️⃣1️⃣ Concurrencia"
        ;;
    12)
        ejecutar "08_streams_lambda" "EjemplosStreamsLambda" "1️⃣2️⃣ Streams y Lambdas"
        ;;
    13)
        ejecutar "09_fecha_hora" "EjemplosFechaHora" "1️⃣3️⃣ Fecha y Hora"
        ;;
    14)
        ejecutar "11_novedades" "NovedadesJava" "1️⃣4️⃣ Novedades Java"
        ;;
    15)
        echo ""
        echo "🏛️ Proyecto Integrador: Gestión de Biblioteca"
        echo "----------------------------------------"
        javac ProyectoIntegrador.java 2>/dev/null
        java -cp . proyecto_integrador.ProyectoIntegrador
        ;;
    0)
        echo "Goodbye! 👋"
        exit 0
        ;;
    *)
        echo "❌ Opción inválida"
        exit 1
        ;;
esac

echo ""
echo "✓ Ejecutado correctamente"
echo ""
