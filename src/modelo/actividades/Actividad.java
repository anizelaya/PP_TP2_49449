package modelo.actividades;

import excepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.Inscripcion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Actividad implements Serializable {
    private int id;
    private String titulo;
    private int cupoMaximo;
    public static final int CUPO_MINIMO = 5;
    //Clase asociativa
    protected List<Inscripcion> inscripciones;

    public Actividad(int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;
        this.inscripciones = new ArrayList<>();//se inicializa
    }

    //modelo.Inscripcion de estudiantes
    public Inscripcion inscribir (Estudiante estudiante) throws CupoExcedidoException {
        if (inscripciones.size() >= cupoMaximo) {
            throw new CupoExcedidoException("Error: No hay más cupos disponibles");
        }

        Inscripcion nuevaInscripcion = new Inscripcion(LocalDate.now(), "Inscripcion Confirmada", estudiante);
                inscripciones.add(nuevaInscripcion); //agrega inscripcion dentro de la lista
                return nuevaInscripcion;
    }

    public void mostrarInscripciones() {
        System.out.println("Inscripciones de " +titulo + ":");
        for (Inscripcion ins : inscripciones) {
            System.out.println(" - " + ins);
        }
    }

    public final void mostrarIdentificacion(){
        System.out.println("Nombre Actividad : " +titulo +" | ID: "+id + " | Tipo de Actividad: "+getTipo() + " | Cupo máximo: " + cupoMaximo);
    }

    public abstract double calcularCostoMateriales();
    public abstract String getTipo();

    //GETTERS Y SETTERS
    public int getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }
    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

}


