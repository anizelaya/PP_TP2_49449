package modelo.actividades;

import modelo.Certificacion.Certificable;
import modelo.Estudiante;

import java.io.Serializable;

public class Taller extends Actividad implements Certificable {
    private boolean requiereNotebook;

    public Taller (int id, String titulo, int cupo, boolean requiereNotebook) {
        super(id, titulo, cupo);
        this.requiereNotebook = requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales(){
        if (requiereNotebook){
            return 5000.0;
        } else {
            return 2000.0;
        }
    }

    @Override
    public String getTipo(){
        return "Taller";
    }

    public boolean isRequiereNotebook(){
        return requiereNotebook;
    }


    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "CERTIFICADO DE ASISTENCIA [" + ENTIDAD_EMISORA + "]\n" +
                "Alumno: " + estudiante.getNombre() + " (Legajo: " + estudiante.getLegajo() + ")\n" +
                "Por su participación en el Taller: " + this.getTitulo();
    }

}
