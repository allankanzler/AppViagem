package edu.fameg.helloworld.domain;

import android.util.Log;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Allan on 19/02/2017.
 */
public class Viagem {

    private int id;
    private String destino;
    private TipoViagem tipoViagem;
    private Calendar dataChegada;
    private Calendar dataPartida;
    private double orcamento;
    private int QuantidadePessoas;
    private ArrayList<Gasto> gastos;
    private String imagem;
    private double jaGasto=0;

    public Viagem() {
        this.gastos = new ArrayList<>();
    }


    public Viagem(int id, String destino, TipoViagem tipoViagem, Calendar dataChegada, Calendar dataPartida, double orcamento, int quantidadePessoas) {
        this.id = id;
        this.destino = destino;
        this.tipoViagem = tipoViagem;
        this.dataChegada = dataChegada;
        this.dataPartida = dataPartida;
        this.orcamento = orcamento;
        QuantidadePessoas = quantidadePessoas;
        this.gastos = new ArrayList<>();
    }

    public Viagem(String destino, TipoViagem tipoViagem, Calendar dataChegada, double orcamento, int quantidadePessoas) {
        this.destino = destino;
        this.tipoViagem = tipoViagem;
        this.dataChegada = dataChegada;
        this.orcamento = orcamento;
        QuantidadePessoas = quantidadePessoas;
        gastos = new ArrayList<>();
    }

    public double getJaGasto() {
        return jaGasto;
    }

    public void setJaGasto(double jaGasto) {
        this.jaGasto = jaGasto;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setGastos(ArrayList<Gasto> gastos) {
        this.gastos = gastos;
    }


    public void setDataPartida(Calendar dataPartida) {
        this.dataPartida = dataPartida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public TipoViagem getTipoViagem() {
        return tipoViagem;
    }

    public void setTipoViagem(TipoViagem tipoViagem) {
        this.tipoViagem = tipoViagem;
    }

    public Calendar getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(Calendar dataChegada) {
        this.dataChegada = dataChegada;
    }
    public void setDataChegada(String dataChe){
        Log.d("vamo vê",dataChe);

        try {
            String pattern = "dd-MM-yyyy";
            Date date = new SimpleDateFormat(pattern).parse(dataChe);
            this.dataChegada = Calendar.getInstance();
            this.dataChegada.setTime(date);
        }catch (Exception ex){
            ex.printStackTrace();
            Log.d("Deu ruim","deu ruim");
        }
    }

    public void setDataPartida(String dataPar){
        Log.d("vamo vê",dataPar);

        try {
            String pattern = "dd-MM-yyyy";
            Date date = new SimpleDateFormat(pattern).parse(dataPar);
            this.dataPartida = Calendar.getInstance();
            this.dataPartida.setTime(date);
        }catch (Exception ex){
            ex.printStackTrace();
            Log.d("Deu ruim","deu ruim");
        }
    }

    public Calendar getDataPartida() {
        return dataPartida;
    }

    public ArrayList<Gasto> getGastos() {
        return gastos;
    }

    public double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(double orcamento) {
        this.orcamento = orcamento;
    }

    public int getQuantidadePessoas() {
        return QuantidadePessoas;
    }

    public void setQuantidadePessoas(int quantidadePessoas) {
        QuantidadePessoas = quantidadePessoas;
    }

    @Override
    public String toString() {
        return "Viagem{" +
                "id=" + id +
                ", destino='" + destino + '\'' +
                ", tipoViagem=" + tipoViagem +
                ", dataChegada=" + dataChegada.get(Calendar.DAY_OF_MONTH)+"/"+ (dataChegada.get(Calendar.MONTH)+1)+"/"+ dataChegada.get(Calendar.YEAR) +
                ", dataPartida=" + dataPartida.get(Calendar.DAY_OF_MONTH)+"/"+ (dataPartida.get(Calendar.MONTH)+1)+"/"+ dataPartida.get(Calendar.YEAR) +
                ", orcamento=" + orcamento +
                ", QuantidadePessoas=" + QuantidadePessoas +
                ", Imagem=" +imagem +
                '}';
    }
}
