package modelo;

import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Curso;
import modelo.actividades.Taller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventoUniversitario implements Serializable {
    private final String ID;
    private String titulo;
    private double costoBase;
    private boolean gratuito;
    private static int cantidadEventos =0;

    //Agregacion ejercicio2
    private Sala sala;
    // Composicion ejercicio2 las modelo.modelo.actividades dependen del evento
    private final List<Actividad> actividades;

    public EventoUniversitario (String ID, String titulo, double costoBase, boolean gratuito){
        this.ID=ID;
        this.titulo=titulo;
        this.costoBase= costoBase;
        this.gratuito= gratuito;
        this.actividades=new ArrayList<>(); //se inicializa la coleccion de la composición
        cantidadEventos++;
    }

    public EventoUniversitario (EventoUniversitario otroEvento) {
        this.ID = otroEvento.ID + "-COPIA";
        this.titulo = otroEvento.titulo;
        this.costoBase = otroEvento.costoBase;
        this.gratuito = otroEvento.gratuito;
        this.actividades=new ArrayList<>(otroEvento.actividades);
        cantidadEventos++;
    }

    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    public void crearActividad(int id, String titulo, int cupo, String tipo) {
        if ("Charla".equalsIgnoreCase(tipo)) {
            actividades.add(new Charla(id, titulo, cupo, "Disertante a definir"));
        } else if ("Taller".equalsIgnoreCase(tipo)) {
            actividades.add(new Taller(id, titulo, cupo, true));
        } else if ("Curso".equalsIgnoreCase(tipo)){
            actividades.add(new Curso(id, titulo, cupo, 1));
        }
    }


    public double calcularCostoEstimado() {
        if (this.gratuito) {
            return 0.0;
        }

        double costoTotal = costoBase;

        for (Actividad actividad : actividades) {
            costoTotal += actividad.calcularCostoMateriales();
        }

        return costoTotal * 1.21;
    }

    public void mostrarDatos() {
        System.out.println("__________________________________________");
        System.out.println("Evento código: " + ID);
        System.out.println("Título: " + titulo);
        System.out.println("Costo: $" + costoBase);
        System.out.println("Costo Estimado (con 21% IVA si aplica): $" + String.format("%.2f", calcularCostoEstimado()));
        if (this.sala != null) {
            System.out.println("Sala Asignada: " + sala.getNombre() + " (ID: " + sala.getId() + ")");
        } else {
            System.out.println("Pendiente de asignación");
        }
        //charlas = gratuitas
        //talleres $5000 si usan notebook o $2000 si no usan
        //Info de acividades e inscripciones
        System.out.println("Agenda de Actividades:");
        if (actividades.isEmpty()) {
            System.out.println("No hay actividades asignadas");
        } else {
            for (Actividad act : actividades) {
                act.mostrarIdentificacion();
                act.mostrarInscripciones();
            }
        }
        System.out.println("___________________________________________");
    }
    public boolean persistirEvento() {
            try (ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("evento_"+this.ID+ ".dat"))){
                oos.writeObject(this);
                return true;
            } catch (IOException e) {
                System.out.println("Error de E/S al intentar persistir el evento: " + e.getMessage());
                return false;
            }

    }

    public static EventoUniversitario recuperarEvento(String ID) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("evento_" + ID + ".dat"))) {
            return (EventoUniversitario) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado para el evento ID: " + ID);
        } catch (IOException e) {
            System.out.println("Error de lectura/escritura al recuperar: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada al recuperar el evento.");
        }
        return null;
    }

    public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo){
        List<T> resultado = new ArrayList<>();
        for (Actividad act : this.actividades) {
            //isInstance verifica si el objeto pertenece a la clase o subclase
            if (tipo.isInstance(act)){
                resultado.add(tipo.cast(act)); //Cast seguro generico
            }
        }
        return resultado;
    }

    //Metodo con comodin
    public double calcularCostoMateriales(List<? extends Actividad> listaActividades){
        double costoTotal=0.0;
        for (Actividad act: listaActividades){
            costoTotal += act.calcularCostoMateriales();
        }
        return costoTotal;
    }


    public static int getCantidadEventos() {
        return cantidadEventos;
    }

    //Getters y setters

    public String getID() {
        return ID;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) { this.titulo = titulo;}

    public double getCostoBase() {
        return costoBase;
    }
    public void setCostoBase(double costoBase) { this.costoBase = costoBase;}

    public boolean isGratuito(boolean gratuito) {
        return gratuito;
    }
    public void setGratuito(boolean gratuito) {
        this.gratuito = gratuito;
    }

    public Sala getSala() { return sala;}

    public List<Actividad> getActividades() { return actividades; }
}

