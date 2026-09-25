import modelo.Certificacion.Certificable;
import modelo.Inscripcion;
import modelo.actividades.Actividad;
import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Sala;
import excepciones.CupoExcedidoException;
import modelo.actividades.Charla;
import modelo.actividades.Curso;
import modelo.actividades.Taller;

import java.util.List;


public class App {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE ADMINISTRACION DE EVENTOS UNIVERSITARIOS ===\n");

        // 1. Instanciación básica
        EventoUniversitario evento = new EventoUniversitario("E-101", "Jornadas de Programación", 2000.0, false);

        Sala sala = new Sala(1, "Auditorio Central");
        evento.asignarSala(sala);

        Estudiante e1 = new Estudiante("LEG-001", "Ana López");
        Estudiante e2 = new Estudiante("LEG-002", "Juan Pérez");


        //Construccion de distintas actividades
        Charla charla = new Charla(201, "Charla sobre IA", 50, "Dr. Pedro Perez");
        Taller taller1 = new Taller(202, "Taller de Spring Boot", 20, true);
        Taller taller2 = new Taller(204, "Taller de CSS", 10, true);
        Curso curso = new Curso(203, "Curso de Java para Desarrolladores", 30, 2);

        evento.getActividades().add(charla);
        evento.getActividades().add(taller1);
        evento.getActividades().add(taller2);
        evento.getActividades().add(curso);

        //e)Inscribir estudiantes
        try {
            taller1.inscribir(e1);
            taller2.inscribir(e2);
            charla.inscribir(e1);
            charla.inscribir(e2);
            curso.inscribir(e1);

        } catch (CupoExcedidoException e) {
            System.out.println("Error de inscripción: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Excepción Encontrada" + e.getMessage());
        } finally {
            System.out.println("Finalizó prueba de inscripciones");
        }

        //Prueba de persistencia
        System.out.println("Guardando el archivo...");
        if (evento.persistirEvento()) {
            System.out.println("Evento guardado con éxito");

            System.out.println("\nRecuperando el evento desde el archivo...");
            EventoUniversitario eventoRecuperado = EventoUniversitario.recuperarEvento("E-101");

            if (eventoRecuperado != null) {
                System.out.println("Objeto recuperado");
                eventoRecuperado.mostrarDatos();
            }
        }

        System.out.println("            EMISIÓN DE CERTIFICADOS              ");
        for (Actividad act : evento.getActividades())
            if (act instanceof Certificable) {
                Certificable certificable = (Certificable) act;
                System.out.println("--- Certificados para: " + act.getTitulo() + " (" + act.getTipo() + ") ---");

                for (Inscripcion ins : act.getInscripciones()) {
                    // Generamos e imprimimos el certificado del estudiante
                    String textoCertificado = certificable.generarCertificado(ins.getEstudiante());
                    System.out.println(textoCertificado);
                    System.out.println(); // Espacio entre certificados
                }
            } else {
                System.out.println("--> Actividad: " + act.getTitulo() + " (" + act.getTipo() + ") - NO EMITE CERTIFICADOS.\n");
            }
        //g) mostrar el total de eventos creados
        System.out.println("Cantidad total de eventos cargados en memoria: " + EventoUniversitario.getCantidadEventos() + " eventos.");

        //filtrado List<charla>
        List<Charla> charlas = evento.filtrarActividadesPorTipo(Charla.class);
        System.out.println("Cantidad de Charlas encontradas: " + charlas.size());

        // Filtrado explícito tipado: List<Taller>
        List<Taller> talleres = evento.filtrarActividadesPorTipo(Taller.class);
        System.out.println("Cantidad de Talleres encontrados: " + talleres.size());

        // Filtrado explícito tipado: List<Curso>
        List<Curso> cursos = evento.filtrarActividadesPorTipo(Curso.class);
        System.out.println("Cantidad de Cursos encontrados: " + cursos.size());

        // f y g. Cálculo del costo de materiales operando sobre subtipos mediante Wildcards
        System.out.println("--- CÁLCULO DE COSTO DE MATERIALES---\n");

        double costoCharlas = evento.calcularCostoMateriales(charlas);
        System.out.println("Costo total materiales Charlas: $" + costoCharlas);

        double costoTalleres = evento.calcularCostoMateriales(talleres);
        System.out.println("Costo total materiales Talleres: $" + costoTalleres);

        double costoCursos = evento.calcularCostoMateriales(cursos);
        System.out.println("Costo total materiales Cursos: $" + costoCursos);

        double costoTodas = evento.calcularCostoMateriales(evento.getActividades());
        System.out.println("Costo total materiales General (Todas las Actividades): $" + costoTodas);
    }

}



