# Definición de Proyecto Individual

## Descripción
MiColegioLugares es un componente de la aplicación MiColeg.io(Multiherramienta Innovadora para la COLaboración Educativa Global.io). Su finalidad es poder gestionar los lugares disponibles en el centro de forma colaborativa permitiendo a los profesores elegir los lugares para dar su asignatura que mejor se adapten a sus necesidades dentro de las posibilidades del centro y con la mínima participación del equipo directivo.
La aplicación mostrará las reservas efectuadas para cada lugar 

## Diagrama de clases de diseño
![Diagrama de Diseño MVP](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/miColegioDiagramaClases_Simplificado.svg)

**Cumplimiento de requisitos**

1. **Herencia**: Se realizará sobre `Lugar` y sus subtipos.
2. **Relación One-To-Many**: elación entre `Lugar` y `Reserva`.
3. **Método personalizado**: `Reserva.asignarLugar(Lugar)` Asigna un lugar a una reserva de acuerdo con las restricciones de asignatura.
4. **Listado**: Se mostrarán las reservas para un lugar tal como aparece en la figura 1.
5. **CRUD**: En la misma pantalla que la anterior se usarán


* : 
  El CRUD se realizará en cada una de las pantallas de visualización de listados. La inserción se realizará a través de un botón que mostrará el correspondiente formulario. Véaseen el apartado Pantallas de Interfaz gráfica.
  * Insertar lugar (con diferenciación entre Patio, Aula o Laboratorio)
  * Asignatura
  * Franja horaria
  * Reservar franja horaria

URLs del proyecto: https://git.institutomilitar.com/RodrigoDG23/MiColegio

Repositorio proyecto: https://git.institutomilitar.com/RodrigoDG23/MiColegio
Librería: https://git.institutomilitar.com/RodrigoDG23/MiColegio

Despliegue en Internet:

API: pendiente despliegue en servidor propio, alternativa de mercado pendiente.

Web: pendiente despliegue en servidor propio, alternativa de mercado pendiente.





# 4. Pantallas de interfaz gráfica
### 4.0.1. Asignaturas
![foto](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/horarioSimplificado.jpg)
![foto](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/insertarAsignaturasSimplificado.jpg)
![foto](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/insertarLugarSimplificado.jpg)
4.1. ![foto](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/reservarFranjaHorariaSimplificado.jpg)
----
