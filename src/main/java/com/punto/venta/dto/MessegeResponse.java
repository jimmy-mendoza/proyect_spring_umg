package com.punto.venta.dto;

public class MessegeResponse {
    private String mensaje;

    public MessegeResponse(String mensjae){
        this.mensaje = mensjae;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String msj) {
        this.mensaje = msj;
    }
}
