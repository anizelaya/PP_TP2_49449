package modelo.actividades;

import modelo.Certificacion.Certificable;
import modelo.Estudiante;


public class Curso extends Actividad implements Certificable {
    private int nivel;

    public Curso (int id, String titulo, int cupo, int nivel ){
        super(id, titulo, cupo);
        this.nivel=nivel;
    }

    @Override
    public double calcularCostoMateriales() {
        return 3000.0 * nivel;
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "CERTIFICADO DE APROBACIÓN -" +ENTIDAD_EMISORA+ "-\n" +
                "Alumno: " +estudiante.getNombre()+ "Legajo: " +estudiante.getLegajo() + ")\n" +
                "Completó exitosamente el Curso: " +this.getTitulo() +" |Nivel: " +nivel;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
