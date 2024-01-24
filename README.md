<h1 style="text-align:center;">MiColegioHorario</h1>

# 1. Introdución y alcance
MiColegioHorario es un componente de la aplicación MiColegio. Su finalidad es poder gestionar los horarios de un centro educativo de forma colaborativa. Su finalidad es poder permitir a los profesores elegir las franjas horarias que mejor se adapten a sus necesidades dentro de las posibilidades del centro y con la mínima participación del equipo directivo, actuando la aplicación como mediadora y elemento de propuesta de soluciones a conflictos.
La aplicación mostrará un horario en el que los profesores podrán reservar franjas horarias y lugares para su asignatura. 

# 2. Cumplimiento de requisitos

* **Herencia**: 
  * Existe herencia dentro de la clase Lugar para especificar distintos lugares con propiedades y comportamientos diferentes.
* **Relaciones entre clases**:
  Nótese que tanto las relaciones One-To-Many como las Many-To-Many son direccionales por lo que sólo hay que generar objetos de una de las entidades en la otra (de acuerdo con la dirección, se crea en el objeto origen de la flecha)
  * **Relación One-To-Many**: 
    * Relación entre Reserva y Lugar
    * Relación entre Reserva y Asignatura
    * Relación entre reserva y FranjaHorario
* **Método personalizado**: 
  1. Reserva:
        * asignarFranjaHoaria(FranjaHoraria): Asigna una franja horaria a una reserva cumpliendo con las restricciones de lugar, y grupo (a través de asignatura)
        * asignarLugar(Lugar): Asigna un lugar a una reserva de acuerdo con las restricciones de asignatura.
  2. Asignatura:
        * compruebaLugar(Lugar): comprueba si un lugar está autorizado para una asignatura.

* **Listados a mostrar**: 
  * Horario con reservas
  * Asignaturas
  * Lugares
    * Patio
    * Aula
    * Laboratorio
  * Franjas Horarias


* **CRUD**: 
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



# 3. Diagrama de clases
![foto](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/miColegioDiagramaClases_Simplificado.svg)

# 4. Pantallas de interfaz gráfica
### 4.0.1. Asignaturas
![foto](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/horarioSimplificado.jpg)
![foto](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/insertarAsignaturasSimplificado.jpg)
![foto](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/insertarLugarSimplificado.jpg)
4.1. ![foto](https://git.institutomilitar.com/RodrigoDG23/MiColegio/-/wikis/InterfazGrafica/Simplificado/reservarFranjaHorariaSimplificado.jpg)
----
