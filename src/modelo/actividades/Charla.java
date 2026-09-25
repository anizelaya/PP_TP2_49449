package modelo.actividades;

import java.io.Serializable;

public class Charla extends Actividad implements Serializable {
    private String disertante;

   public Charla (int id, String titulo, int cupo, String disertante){
       super(id, titulo, cupo);
       this.disertante=disertante;
   }


    @Override
    public double calcularCostoMateriales() {
        return 0;
    }

    @Override
    public String getTipo() {
        return "Charla";
    }

    public String getDisertante() {
        return disertante;
    }
}
