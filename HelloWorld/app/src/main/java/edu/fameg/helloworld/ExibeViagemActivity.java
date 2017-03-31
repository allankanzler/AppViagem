package edu.fameg.helloworld;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import edu.fameg.helloworld.banco.Banco;

public class ExibeViagemActivity extends AppCompatActivity {

    private Banco banco;
    private GridView gridView;
    private static ArrayList<String> lista;
    private long idViagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibe_viagem);
        getIdViagem();
        exibirViagem();
    }

    private void getIdViagem(){
        Intent intent = getIntent();
        idViagem = intent.getLongExtra(NovoGastoActivity.ID_VIAGEM,0);
        Log.d("ID",idViagem+"");
    }

    private void exibirViagem() {
        lista = new ArrayList<>();
        try{
            banco = new Banco(this,"bd3",1);
            Cursor cr = banco.getWritableDatabase().rawQuery("SELECT * FROM viagens where id_viagem = "+idViagem,null);
            if (cr != null) {
                if (cr.moveToFirst()) {
                    do {
                        String imagem = cr.getString(cr.getColumnIndex("imagem"));
                        showImage(imagem);
                        String destino = "Destino: "+cr.getString(cr.getColumnIndex("destino"));
                        String orcamento = "Orçamento: R$"+cr.getString(cr.getColumnIndex("orcamento"));
                        String dataChegada = "Data Chegada: "+cr.getString(cr.getColumnIndex("data_chegada"));
                        String dataPartida = "Data Partida: "+cr.getString(cr.getColumnIndex("data_partida"));
                        String qtdPessoes = "Quantidade de Pessoas: "+cr.getString(cr.getColumnIndex("qtd_pessoas"));
                        String tipoViagem = "Tipo de Viagem: "+cr.getString(cr.getColumnIndex("tipo_viagem"));

                        String id = "id: "+cr.getString(cr.getColumnIndex("id_viagem"));
                        lista.add(id);
                        lista.add(destino);
                        lista.add(orcamento);
                        lista.add(dataChegada);
                        lista.add(dataPartida);
                        lista.add(qtdPessoes);
                        lista.add(tipoViagem);
                        lista.add(imagem);

                    } while (cr.moveToNext());
                } else {
                    Toast.makeText(getApplicationContext(), "No data to show",
                            Toast.LENGTH_LONG).show();
                }
            }
            cr.close();


        }
        catch (Exception ex){ex.printStackTrace();}
        try{
            Cursor cr = banco.getWritableDatabase().rawQuery("SELECT * FROM gastos where id_viagem = "+ idViagem,null);
            if(cr!=null){
                if (cr.moveToFirst()) {
                    do {
                        String tipoGasto = "Tipo de gasto: " + cr.getString(cr.getColumnIndex("tipo_gasto"));
                        String valor = "Valor: R$"+cr.getString(cr.getColumnIndex("valor"));
                        String data = "Data: "+ cr.getString(cr.getColumnIndex("data"));
                        String descricao = "Descrição: "+ cr.getString(cr.getColumnIndex("descricao"));
                        String local = "Local: "+ cr.getString(cr.getColumnIndex("local"));

                        lista.add("--");
                        lista.add(tipoGasto);
                        lista.add(valor);
                        lista.add(data);
                        lista.add(descricao);
                        lista.add(local);


                    } while (cr.moveToNext());
                }
            }cr.close();
        }catch (Exception e){e.printStackTrace();}

        gridView = (GridView) findViewById(R.id.gridView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,lista);
        gridView.setAdapter(adapter);

    }

    private void showImage(String imagem) {

        ImageView myImage = (ImageView) findViewById(R.id.result);
        Bitmap yourSelectedImage =
                BitmapFactory.decodeFile(imagem);

        myImage.setImageBitmap(yourSelectedImage);

       /*
        File imgFile = new  File(imagem);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.result);
            try{
                myImage.setImageBitmap(myBitmap);
            }catch (Exception e){e.printStackTrace();}
        }
        */
    }
}
