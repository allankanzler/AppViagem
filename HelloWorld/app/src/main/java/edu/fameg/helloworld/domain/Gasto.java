package edu.fameg.helloworld.domain;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Allan on 22/02/2017.
 */
public class Gasto {

    private String tipo;
    private double valor;
    private Calendar data;
    private String descricao;
    private String local;

    public Gasto(String tipo, double valor, Calendar data, String descricao, String local) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
        this.local = local;
    }

    public Gasto() {
    }

    @Override
    public String toString() {
        return "Gasto{" +
                "tipo='" + tipo + '\'' +
                ", valor=" + valor +
                ", data=" + data.get(Calendar.DAY_OF_MONTH)+"-"+(data.get(Calendar.MONTH)+1)+"-"+data.get(Calendar.YEAR)+
                ", descricao='" + descricao + '\'' +
                ", local='" + local + '\'' +
                '}';
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }
    public void setData(String data){
        Log.d("vamo vê",data);

        try {
            String pattern = "dd-MM-yyyy";
            Date date = new SimpleDateFormat(pattern).parse(data);
            this.data = Calendar.getInstance();
            this.data.setTime(date);
        }catch (Exception ex){
            ex.printStackTrace();
            Log.d("Deu ruim","deu ruim");
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
