# Definición de Proyecto Individual

## Descripción
MiColegioHorario es un componente de la aplicación MiColeg.io(Multiherramienta Innovadora para la COLaboración Educativa Global.io). Su finalidad es poder gestionar la planificación del horario del centro de forma colaborativa permitiendo a los profesores elegir los lugares para dar su asignatura de modo que estos se adapten lo mejor a sus necesidades dentro de las posibilidades del centro y con la mínima participación del equipo directivo.
La aplicación mostrará las reservas efectuadas por cada profesor permitiendo añadir nuevos lugares. 

## Diagrama de clases de diseño
![Diagrama de Diseño MVP](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/miColegioDiagramaClasesSimplificado.jpg)

**Cumplimiento de requisitos**

1. **Herencia**: Se realizará sobre `Lugar` y sus subtipos `Patio` y `Aula`.
2. **Relación One-To-Many**: Relación entre `Lugar` y `Reserva`.
3. **Método personalizado**: `consultarDisponible(Profesor,fechaHoraInicio, fechaHoraFin)` Dado un profesor y un periodo de tiempo, devuelve una lista con todas las reservas que estén relacionadas con los lugares o grupos de alumnos de las asignaturas que él imparte. Esta relación de lugares y grupos se obtendrá a partir de  la propiedad asignatura e la Reserva.
4. **Listado**: Se mostrarán para cada profesor las reservas que tiene efectuadas (cada reserva contendrá información sobre el Lugar, la Franja Horaria y la Asignatura) tal como aparece en la figura 1.
5. **CRUD**: Existirá un formulario para añadir o modificar un lugar y tendrá dos derivaciones en función del tipo de lugar que se seleccione. Esto es debido a que en función del tipo de lugar éste tendrá unas propiedades u otras. Sólo se podrán reservar aquellos lugares disponibles (en función de su aforo).
6. **URLs** del proyecto:
   1. Repositorio proyecto: https://git.institutomilitar.com/RodrigoDG23/MiColegio
   2. Librería: TBD
7. **Despliegue** en Internet:
   1. API: https://micolegio-c6e07df12596.herokuapp.com/api/
   2. Web: https://micolegio.netlify.app/#/login

## Interfaz de usuario
Figura 1: Listado de reservas
![Figura1](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/horarioSimplificado.jpg)

Figura2: Formulario CRUD Lugar
![Figura2](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/insertarLugarSimplificado.jpg)


