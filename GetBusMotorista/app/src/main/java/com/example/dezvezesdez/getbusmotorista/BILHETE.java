package com.example.dezvezesdez.getbusmotorista;

/**
 * Created by dezvezesdez on 24/05/16.
 */
public class BILHETE {

    protected String ID;
    protected String IP_CLIENT;
    protected String NUM_ID_CLIENT;
    protected String NAME_CLIENT;
    protected String PHONE_CLIENT;
    protected String EMAIL_CLIENT;
    protected String TYPE_TRIP;
    protected String DESTINATION_TRIP;
    protected String SOURCE_TRIP;
    protected String NUM_CHILD;
    protected String NUM_ADULT;
    protected String DATE_GO;
    protected String DATE_COME;
    protected String TYPE_PAY;
    protected String DATE_PAY;
    protected String TOTAL_PAY;

    protected String VALIDATOR_ID;
    protected String VALIDATOR_IDA;
    protected String VALIDATOR_VOLTA;


    //construtores
    public BILHETE(String ID, String IP_CLIENT, String NUM_ID_CLIENT, String PHONE_CLIENT, String NAME_CLIENT,
                   String EMAIL_CLIENT, String TYPE_TRIP, String DESTINATION_TRIP,
                   String SOURCE_TRIP, String NUM_CHILD, String NUM_ADULT, String DATE_GO,
                   String DATE_COME, String TYPE_PAY, String DATE_PAY, String TOTAL_PAY) {

        this.ID = ID;
        this.IP_CLIENT = IP_CLIENT;
        this.NUM_ID_CLIENT = NUM_ID_CLIENT;
        this.PHONE_CLIENT = PHONE_CLIENT;
        this.NAME_CLIENT = NAME_CLIENT;
        this.EMAIL_CLIENT = EMAIL_CLIENT;
        this.TYPE_TRIP = TYPE_TRIP;
        this.DESTINATION_TRIP = DESTINATION_TRIP;
        this.SOURCE_TRIP = SOURCE_TRIP;
        this.NUM_CHILD = NUM_CHILD;
        this.NUM_ADULT = NUM_ADULT;
        this.DATE_GO = DATE_GO;
        this.DATE_COME = DATE_COME;
        this.TYPE_PAY = TYPE_PAY;
        this.DATE_PAY = DATE_PAY;
        this.TOTAL_PAY = TOTAL_PAY;
    }

    public BILHETE(String VALIDATOR_ID, String VALIDATOR_IDA, String VALIDATOR_VOLTA, String ID){
        this.ID = ID;
        this.VALIDATOR_ID = VALIDATOR_ID;
        this.VALIDATOR_IDA = VALIDATOR_IDA;
        this.VALIDATOR_VOLTA = VALIDATOR_VOLTA;

    }


    //gets
    public String getID() {
        return ID;
    }

    public String getIP_CLIENT() {
        return IP_CLIENT;
    }

    public String getNAME_CLIENT() {
        return NAME_CLIENT;
    }

    public String getPHONE_CLIENT() {
        return PHONE_CLIENT;
    }

    public String getEMAIL_CLIENT() {
        return EMAIL_CLIENT;
    }

    public String getTYPE_TRIP() {
        return TYPE_TRIP;
    }

    public String getDESTINATION_TRIP() {
        return DESTINATION_TRIP;
    }

    public String getSOURCE_TRIP() {
        return SOURCE_TRIP;
    }

    public String getNUM_CHILD() {
        return NUM_CHILD;
    }

    public String getNUM_ADULT() {
        return NUM_ADULT;
    }

    public String getDATE_GO() {
        return DATE_GO;
    }

    public String getTYPE_PAY() {
        return TYPE_PAY;
    }

    public String getDATE_COME() {
        return DATE_COME;
    }

    public String getDATE_PAY() {
        return DATE_PAY;
    }

    public String getTOTAL_PAY() {
        return TOTAL_PAY;
    }

    public String getNUM_ID_CLIENT() {
        return NUM_ID_CLIENT;
    }

    public String getVALIDATOR_ID() {
        return VALIDATOR_ID;
    }

    public String getVALIDATOR_IDA() {
        return VALIDATOR_IDA;
    }

    public String getVALIDATOR_VOLTA() {
        return VALIDATOR_VOLTA;
    }

    //sets
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setIP_CLIENT(String IP_CLIENT) {
        this.IP_CLIENT = IP_CLIENT;
    }

    public void setNAME_CLIENT(String NAME_CLIENT) {
        this.NAME_CLIENT = NAME_CLIENT;
    }

    public void setPHONE_CLIENT(String PHONE_CLIENT) {
        this.PHONE_CLIENT = PHONE_CLIENT;
    }

    public void setEMAIL_CLIENT(String EMAIL_CLIENT) {
        this.EMAIL_CLIENT = EMAIL_CLIENT;
    }

    public void setTYPE_TRIP(String TYPE_TRIP) {
        this.TYPE_TRIP = TYPE_TRIP;
    }

    public void setDESTINATION_TRIP(String DESTINATION_TRIP) {
        this.DESTINATION_TRIP = DESTINATION_TRIP;
    }

    public void setSOURCE_TRIP(String SOURCE_TRIP) {
        this.SOURCE_TRIP = SOURCE_TRIP;
    }

    public void setNUM_CHILD(String NUM_CHILD) {
        this.NUM_CHILD = NUM_CHILD;
    }

    public void setDATE_GO(String DATE_GO) {
        this.DATE_GO = DATE_GO;
    }

    public void setNUM_ADULT(String NUM_ADULT) {
        this.NUM_ADULT = NUM_ADULT;
    }

    public void setDATE_COME(String DATE_COME) {
        this.DATE_COME = DATE_COME;
    }

    public void setTYPE_PAY(String TYPE_PAY) {
        this.TYPE_PAY = TYPE_PAY;
    }

    public void setDATE_PAY(String DATE_PAY) {
        this.DATE_PAY = DATE_PAY;
    }

    public void setTOTAL_PAY(String TOTAL_PAY) {
        this.TOTAL_PAY = TOTAL_PAY;
    }

    public void setNUM_ID_CLIENT(String NUM_ID_CLIENT) {
        this.NUM_ID_CLIENT = NUM_ID_CLIENT;
    }

    public void setVALIDATOR_ID(String VALIDATOR_ID) {
        this.VALIDATOR_ID = VALIDATOR_ID;
    }

    public void setVALIDATOR_IDA(String VALIDATOR_IDA) {
        this.VALIDATOR_IDA = VALIDATOR_IDA;
    }

    public void setVALIDATOR_VOLTA(String VALIDATOR_VOLTA) {
        this.VALIDATOR_VOLTA = VALIDATOR_VOLTA;
    }
}
