package main.java.edu.jm.sportlife.model;

public class Instalacion {

    private int idInstalacion;
    private String codigo;
    private String descripcion;
    private String deporte;
    private boolean techada;
    private double precioHora;

    public Instalacion() {
    }

    public Instalacion(int idInstalacion, String codigo, String descripcion, String deporte, boolean techada, double precioHora) {
        this.idInstalacion = idInstalacion;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.deporte = deporte;
        this.techada = techada;
        this.precioHora = precioHora;
    }

    public Instalacion(String codigo, String descripcion, String deporte, boolean techada, double precioHora) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.deporte = deporte;
        this.techada = techada;
        this.precioHora = precioHora;
    }

    public int getIdInstalacion() {
        return idInstalacion;
    }

    public void setIdInstalacion(int idInstalacion) {
        this.idInstalacion = idInstalacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public boolean isTechada() {
        return techada;
    }

    public void setTechada(boolean techada) {
        this.techada = techada;
    }

    public double getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(double precioHora) {
        this.precioHora = precioHora;
    }

    public String getTechadaTexto() {
        return techada ? "Sí" : "No";
    }
}