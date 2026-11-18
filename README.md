# OrangePlane Arsenal — Forge 1.20.1

Proyecto fuente para Minecraft Java 1.20.1 + Forge 47.x. Requiere Java 17.

## Qué incluye
- Tecla **K** para solicitar un cofre frente al jugador.
- El servidor solo crea el cofre si el nombre autenticado es `OrangePlane`.
- El cofre queda marcado con el UUID del propietario; el mod bloquea la apertura/interacción y el rompimiento por otros jugadores.
- Cofre con armadura Netherite, arco, espada, caña, herramientas, escudo y flechas.
- Encantamientos vanilla a niveles altos y etiqueta Unbreakable para evitar desgaste.

## Importante: alcance de esta primera versión
Este es el **proyecto fuente**, no un `.jar` ya compilado. No se ha compilado ni probado en un servidor real. La espada aún no fuerza 200 puntos de daño efectivo: esa parte requiere lógica adicional de combate y pruebas. Los +40 de armadura/resistencia y +0.25 de resistencia al retroceso tampoco están aplicados todavía. La protección del cofre cubre interacción y rotura normal; operadores/mods de terceros pueden tener otras vías de acceso. El escudo tiene Irrompibilidad 255 y Unbreakable, pero las resistencias especiales contra Wither/Creeper no están implementadas.

## Compilar
1. Instala Java 17 (JDK).
2. Instala Gradle o usa Gradle Wrapper generado localmente.
3. Desde esta carpeta ejecuta `gradle genIntellijRuns` (opcional) y `gradle build`.
4. El JAR aparecerá en `build/libs/`.
5. Copia el JAR a la carpeta `mods` del cliente y del servidor Forge 1.20.1.

La tecla predeterminada es **K** y se puede cambiar en Opciones → Controles.
El nombre `OrangePlane` se valida en el servidor; en servidores con autenticación desactivada los nombres pueden suplantarse.
