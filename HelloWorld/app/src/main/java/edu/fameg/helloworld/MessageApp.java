package edu.fameg.helloworld;

import android.app.Application;

import edu.fameg.helloworld.domain.Viagem;

/**
 * Created by Allan on 27/02/2017.
 */
public class MessageApp extends Application {

    /*
    private DomainObject domainObject;

    public DomainObject getDomainObject() {
        return domainObject;
    }

    public void setDomainObject(DomainObject domainObject) {
        this.domainObject = domainObject;
    }

     */
    private Viagem viagem;
    public Viagem getViagem(){
        return viagem;
    }
    public void setViagem(Viagem viagem){
        this.viagem=viagem;
    }
}
