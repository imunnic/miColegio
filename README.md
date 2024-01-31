# Definición de Proyecto Individual

## Descripción
MiColegioLugares es un componente de la aplicación MiColeg.io(Multiherramienta Innovadora para la COLaboración Educativa Global.io). Su finalidad es poder gestionar los lugares disponibles en el centro de forma colaborativa permitiendo a los profesores elegir los lugares para dar su asignatura de modo que estos se adapten lo mejor a sus necesidades dentro de las posibilidades del centro y con la mínima participación del equipo directivo.
La aplicación mostrará las reservas efectuadas para cada lugar. 

## Diagrama de clases de diseño
![Diagrama de Diseño MVP](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/miColegioDiagramaClases_Simplificado.svg)

**Cumplimiento de requisitos**

1. **Herencia**: Se realizará sobre `Lugar` y sus subtipos `Patio` y `Laboratorio`.
2. **Relación One-To-Many**: Relación entre `Lugar` y `Reserva`.
3. **Método personalizado**: `Reserva.asignarLugar(Lugar)` Asigna un lugar a una reserva de acuerdo con las restricciones del lugar (disponibilidad).
4. **Listado**: Se mostrarán las reservas para un lugar tal como aparece en la figura 1.
5. **CRUD**: En la misma pantalla que la anterior se usarán controles para añadir nuevos lugares y asignar reservas a lugares. El formulario para añadir un lugar tendrá dos derivaciones en función del tipo de lugar que se seleccione. Esto es debido a que en función del tipo de lugar éste tendrá unas propiedades u otras. Sólo se podrán reservar aquellos lugares disponibles (en función de su aforo).
6. **URLs** del proyecto:
   1. Repositorio proyecto: https://git.institutomilitar.com/RodrigoDG23/MiColegio
   2. Librería: https://git.institutomilitar.com/RodrigoDG23/MiColegio
7. **Despliegue** en Internet:
   1. API: TBD
   2. Web: TBD

## Interfaz de usuario
Figura 1:
![Figura1](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/DatosLugares.jpg)

Figura2:
![Figura2](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/insertarLugarSimplificado.jpg)

Figura3:
![foto](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/reservarLugarSimplificado.jpg)
