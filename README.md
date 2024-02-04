# Definición de Proyecto Individual

## Descripción
MiColegioHorario es un componente de la aplicación MiColeg.io(Multiherramienta Innovadora para la COLaboración Educativa Global.io). Su finalidad es poder gestionar la planificación del horario del centro de forma colaborativa permitiendo a los profesores elegir los lugares para dar su asignatura de modo que estos se adapten lo mejor a sus necesidades dentro de las posibilidades del centro y con la mínima participación del equipo directivo.
La aplicación mostrará las reservas efectuadas por cada profesor permitiendo añadir nuevos lugares. 

## Diagrama de clases de diseño
![Diagrama de Diseño MVP](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/miColegioDiagramaClasesSimplificado.jpg)

**Cumplimiento de requisitos**

1. **Herencia**: Se realizará sobre `Lugar` y sus subtipos `Patio` y `Aula`.
2. **Relación One-To-Many**: Relación entre `Lugar` y `Reserva`.
3. **Método personalizado**: `consultarDisponible(Profesor)` Dado un profesor devuelve una matriz que contiene para cada franja horaria (especificada como un String) una matriz de las asignaturas impartidas por el profesor que a su vez contienen una lista de los posibles lugares que están disponibles en dicha franja (para filtrar los lugares se tiene en cuenta el aforo del lugar y los posibles lugares que son compatibles para cada asignatura, y para filtrar la asignatura se tiene en cuenta si el grupo asociado ya tiene una asignatura en esa franja). Si el profesor ya tiene una reserva en esa franja el contenido para ese elemento de la matriz será null.
4. **Listado**: Se mostrarán para cada profesor las reservas que tiene efectuadas (cada reserva contendrá información sobre el Lugar, la Franja Horaria y la Asignatura) tal como aparece en la figura 1.
5. **CRUD**: Existirá un formulario para añadir o modificar un lugar y tendrá dos derivaciones en función del tipo de lugar que se seleccione. Esto es debido a que en función del tipo de lugar éste tendrá unas propiedades u otras. Sólo se podrán reservar aquellos lugares disponibles (en función de su aforo).
6. **URLs** del proyecto:
   1. Repositorio proyecto: https://git.institutomilitar.com/RodrigoDG23/MiColegio
   2. Librería: https://git.institutomilitar.com/RodrigoDG23/MiColegio
7. **Despliegue** en Internet:
   1. API: TBD
   2. Web: TBD

## Interfaz de usuario
Figura 1: Listado de reservas
![Figura1](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/horarioSimplificado.jpg)

Figura2: Formulario CRUD Lugar
![Figura2](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/insertarLugarSimplificado.jpg)


