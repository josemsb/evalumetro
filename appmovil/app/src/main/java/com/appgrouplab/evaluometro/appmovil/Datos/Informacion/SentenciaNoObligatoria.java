package com.appgrouplab.evaluometro.appmovil.Datos.Informacion;

public class SentenciaNoObligatoria {
    private String dni;
    private String parammateriasentencia;
    private String expedienteobliga;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getParammateriasentencia() {
        return parammateriasentencia;
    }

    public void setParammateriasentencia(String parammateriasentencia) {
        this.parammateriasentencia = parammateriasentencia;
    }

    public String getExpedienteobliga() {
        return expedienteobliga;
    }

    public void setExpedienteobliga(String expedienteobliga) {
        this.expedienteobliga = expedienteobliga;
    }

    public String getOrganojuridicionalobliga() {
        return organojuridicionalobliga;
    }

    public void setOrganojuridicionalobliga(String organojuridicionalobliga) {
        this.organojuridicionalobliga = organojuridicionalobliga;
    }

    public String getFalloobliga() {
        return falloobliga;
    }

    public void setFalloobliga(String falloobliga) {
        this.falloobliga = falloobliga;
    }

    private String organojuridicionalobliga;
    private String falloobliga;
}
