package edu.fameg.helloworld;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

import edu.fameg.helloworld.banco.Banco;
import edu.fameg.helloworld.domain.Gasto;
import edu.fameg.helloworld.domain.Viagem;

public class NovoGastoActivity extends AppCompatActivity {

    private String data="0/0/0";
    private final int DIALOG_ID=0;
    private int yearX,monthX,dayX;
    private TextView dataGastoTextView;
    private Button btn;
    private EditText valor;
    private EditText descricao;
    private EditText local;
    Spinner spinner;
    private Viagem viagem;
    private Banco banco;
    private long idViagem;
    public final static String ID_VIAGEM = "ID_VIAGEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_gasto);
        spinner = (Spinner) findViewById(R.id.tipoGastoComobo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tiposDeGastos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        onDataGastoClick();

        MessageApp ma = (MessageApp) getApplication();
        viagem = new Viagem();
        viagem = ma.getViagem();
        Log.d("Viagem no gasto",viagem.toString());

        final Calendar cal = Calendar.getInstance();
        yearX=cal.get(Calendar.YEAR);
        monthX=cal.get(Calendar.MONTH);
        dayX=cal.get(Calendar.DAY_OF_MONTH);
        this.dataGastoTextView = (TextView)findViewById(R.id.dataGasto);
        //this.destino = (EditText)findViewById(R.id.destinoEditText);
        this.valor = (EditText)findViewById(R.id.valorGastoEditText);
        this.descricao = (EditText)findViewById(R.id.descricaoGastoEditText);
        this.local = (EditText)findViewById(R.id.localizacaoGastoEditText);

    }

    public void gostarGasto(View view){
        Gasto g = new Gasto();
        Context context = getApplicationContext();
        CharSequence text = "gravou";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context,text,duration);

        try{
            Editable local = this.local.getText();
            Editable descricao = this.descricao.getText();
            Editable valor = this.valor.getText();

            g.setLocal(local.toString());
            g.setDescricao(descricao.toString());
            g.setValor(Double.parseDouble(valor.toString()));
            g.setData(data);
            g.setTipo(spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString());
            viagem.getGastos().add(g);
            Log.d("oi",g.toString());
            Log.d("Gastos",viagem.getGastos().toString());
            cleanCampos();

            toast.show();

        }catch (Exception ex){
            ex.printStackTrace();
        }

        viagem.setJaGasto(viagem.getJaGasto()+g.getValor());
        Log.d("Quanto foi gasto", viagem.getJaGasto()+"||"+(viagem.getOrcamento()*0.75));

        if(viagem.getJaGasto()>(viagem.getOrcamento()*0.75)){
            Toast.makeText(context,"Os gastos já passaram de 75% do total da viagem!",duration).show();
        }

    }
    private void cleanCampos(){
        valor.setText("");
        descricao.setText("");
        local.setText("");
    }


    public void onDataGastoClick(){
        btn = (Button)findViewById(R.id.buttonData);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID){
            return new DatePickerDialog(this, dpickerListner,yearX,monthX,dayX);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            yearX = year;
            monthX = monthOfYear;
            dayX= dayOfMonth;
            data = dayX+"-"+(monthX+1)+"-"+yearX;
            dataGastoTextView.setText(data);
        }
    };

    public void salvarTudo(View view){
        try{
            criarBanco();
            exibirViagem();
        }catch (Exception e){e.printStackTrace();}
    }

    private void exibirViagem() {
        Intent intent = new Intent(this,ExibeViagemActivity.class);
        intent.putExtra(ID_VIAGEM,idViagem);
        startActivity(intent);
    }

    private void criarBanco(){

       // this.deleteDatabase("bd3");
        banco = new Banco(this, "bd3",1);

        ContentValues contentValues = new ContentValues();
        contentValues.put("destino",viagem.getDestino());
        contentValues.put("tipo_viagem",viagem.getTipoViagem().toString());
        contentValues.put("data_chegada", calendarToString(viagem.getDataChegada()));
        contentValues.put("data_partida", calendarToString(viagem.getDataPartida()));
        contentValues.put("orcamento",viagem.getOrcamento());
        contentValues.put("qtd_pessoas",viagem.getQuantidadePessoas());
        contentValues.put("imagem",viagem.getImagem());
        banco.getWritableDatabase().insert("viagens",null,contentValues);

        idViagem = banco.getReadableDatabase().insert("viagens","",contentValues);
        Log.d("ID VIAGEM",idViagem+"");

        Log.d("BANCO", "criou o banco com viagens");

        ContentValues contentViagens = new ContentValues();
        for(Gasto g : viagem.getGastos()){

            contentViagens.put("tipo_gasto",g.getTipo());
            contentViagens.put("valor",g.getValor());
            contentViagens.put("data",calendarToString(g.getData()));
            contentViagens.put("descricao",g.getDescricao());
            contentViagens.put("local",g.getLocal());
            contentViagens.put("id_viagem",idViagem);
            banco.getWritableDatabase().insert("gastos",null,contentViagens);
        }
    }

 /*
    private String tipo;
    private double valor;
    private Calendar data;
    private String descricao;
    private String local;
     */


    private String calendarToString(Calendar cal){
        //dataChegada.get(Calendar.DAY_OF_MONTH)+"/"+ (dataChegada.get(Calendar.MONTH)+1)+"/"+ dataChegada.get(Calendar.YEAR)
        return cal.get(Calendar.DAY_OF_MONTH)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);
    }


}

