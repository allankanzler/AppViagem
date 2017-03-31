package edu.fameg.helloworld;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.fameg.helloworld.banco.Banco;

public class MostraViagensBancoActivity extends AppCompatActivity {

    private Banco banco;
    private GridView gridView;
    public static ArrayList<String> lista = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_viagens_banco);
        listarUsuarios();
    }
    private void listarUsuarios() {
        try{
            banco = new Banco(this,"bd3",1);
            Cursor cr = banco.getWritableDatabase().rawQuery("SELECT * FROM viagens",null);
            if (cr != null) {
                if (cr.moveToFirst()) {
                    do {
                        String usuario = cr.getString(cr.getColumnIndex("destino"));
                        String nomeCompleto = cr.getString(cr.getColumnIndex("orcamento"));
                        String dataChegada = cr.getString(cr.getColumnIndex("data_chegada"));
                        String imagem = cr.getString(cr.getColumnIndex("imagem"));
                        String id = "id: "+cr.getString(cr.getColumnIndex("id_viagem"));
                        lista.add(id);
                        lista.add(usuario);
                        lista.add(nomeCompleto);
                        lista.add(dataChegada);
                        lista.add(imagem);
                    } while (cr.moveToNext());
                } else {
                    Toast.makeText(getApplicationContext(), "Noo data to show",
                            Toast.LENGTH_LONG).show();
                }
            }
            cr.close();
        }
        catch (Exception ex){ex.printStackTrace();}

        gridView = (GridView) findViewById(R.id.gridView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,lista);
        gridView.setAdapter(adapter);


    }

}
