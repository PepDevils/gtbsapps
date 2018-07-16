package com.example.dezvezesdez.getbus_android;

/**
 * Created by dezvezesdez on 05/05/16.
 */
public class Bilhete {

    protected String ticket_id;
    protected String ticket_price;

    protected String data_ida;
    protected String data_volta;

    protected String hora_ida;
    protected String hora_volta;


    public Bilhete(String ticket_id, String ticket_price){
        this.ticket_id=ticket_id;
        this.ticket_price=ticket_price;

    }





    public String getTicket_price() {
        return ticket_price;
    }

    public String getTicket_id() {
        return ticket_id;
    }

}
