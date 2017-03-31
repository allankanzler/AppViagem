package edu.fameg.helloworld;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

import edu.fameg.helloworld.banco.Banco;
import edu.fameg.helloworld.domain.DatePickerFragment;
import edu.fameg.helloworld.domain.TipoViagem;
import edu.fameg.helloworld.domain.Viagem;

public class ViagemActivity extends AppCompatActivity {

    private boolean chegadaPartida;
    Button btn;
    int yearX,monthX,dayX;
    static final int DIALOG_ID = 0;
    static final int DIALOG_ID2 = 1;
    private String dataChegada="0/0/0";
    private String dataPartida="0/0/0";
    private TextView dataChegadaTextView;
    private TextView dataPartidaTextView;
    private TipoViagem tipoViagem;
    private EditText destino;
    private EditText orcamento;
    private EditText qntPessoas;
    private Uri uri;
    private static final int CAPTURAR_IMAGEM = 1;
    private Banco banco;
    private String imagem ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagem);
        onDataChegadaClick();
        onPartidadaClick();

        final Calendar cal = Calendar.getInstance();
        yearX=cal.get(Calendar.YEAR);
        monthX=cal.get(Calendar.MONTH);
        dayX=cal.get(Calendar.DAY_OF_MONTH);
        this.dataChegadaTextView = (TextView)findViewById(R.id.dataChegadaTextView);
        this.dataPartidaTextView = (TextView)findViewById(R.id.dataPartidaTextView);

        this.destino = (EditText)findViewById(R.id.destinoEditText);
        this.orcamento = (EditText)findViewById(R.id.orcamentoEditText);
        this.qntPessoas = (EditText)findViewById(R.id.QuantidadePessoasEditText);



    }

    //data partida
    public void onPartidadaClick(){
        chegadaPartida = false;
        btn = (Button)findViewById((R.id.buttonPartida));
        btn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID2);
                    }
                }
        );
    }
    protected Dialog onCreateDialog1(int id){
        if(id==DIALOG_ID2){
            return new DatePickerDialog(this, dpickerListner1,yearX,monthX,dayX);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListner1 = new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            yearX=year;
            monthX = monthOfYear;
            dayX = dayOfMonth;
            dataPartida = dayX+"-"+(monthX+1)+"-"+yearX;
            dataPartidaTextView.setText(dataPartida);

        }
    };


    //Data chegada
    public void onDataChegadaClick(){
        chegadaPartida = true;
        btn = (Button)findViewById(R.id.buttonChegada);

        btn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if(id== DIALOG_ID){
            return new DatePickerDialog(this, dpickerListner,yearX,monthX,dayX);
        }else
        if(id== DIALOG_ID2){
            return new DatePickerDialog(this, dpickerListner1,yearX,monthX,dayX);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListner =  new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            yearX=year;
            monthX = monthOfYear;
            dayX = dayOfMonth;
            dataChegada = dayX+"-"+(monthX+1)+"-"+yearX;
            Log.d("AlouAlou",chegadaPartida+"");
            dataChegadaTextView.setText(dataChegada);

        }
    };

    //Radio button
    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.radioLazer:
                if (checked){
                    tipoViagem = TipoViagem.LAZER;
                }
                break;
            case R.id.radioNegocios:
                if (checked){
                 tipoViagem = TipoViagem.NEGOCIOS;
                }
                break;
        }
    }

    public void criarViagem(View view){


        Viagem v = new Viagem();

        try {
            Editable qntPessoas = this.qntPessoas.getText();

            Editable destino = this.destino.getText();
            Editable orcamento = this.orcamento.getText();

            v.setDataChegada(dataChegada);
            v.setDataPartida(dataPartida);
            v.setTipoViagem(tipoViagem);
            v.setQuantidadePessoas(Integer.parseInt(qntPessoas.toString()));
            v.setDestino(destino.toString());
            v.setOrcamento(Double.parseDouble(orcamento.toString()));
            v.setImagem(imagem);
            Log.d("Viagem", v.toString());
         //   criarBanco();
            abreGastoActivity(v);


        }catch (Exception ex){ex.printStackTrace();}

    }

    public void abreGastoActivity(Viagem v){

        Intent intent = new Intent(this,NovoGastoActivity.class);
      //  Intent intent = new Intent(this,MostraViagensBancoActivity.class);

        MessageApp ma = (MessageApp) getApplicationContext();
        ma.setViagem(v);

        startActivity(intent);
    }

    public void tirarFoto(View v){
        boolean temCamera = getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA);
        if(temCamera){
           // File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File diretorio = getAlbumStorageDir();
            String nomeImagem = diretorio.getPath() + "/" +
                    System.currentTimeMillis() +
                    ".jpg";
            this.imagem = nomeImagem;
            uri = Uri.fromFile(new File(nomeImagem));
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            startActivityForResult(intent, CAPTURAR_IMAGEM);
        }
    }

    public File getAlbumStorageDir() {

        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "appViagem12");
        if (!file.mkdirs()) {
            Log.e("ERROR", "Directory not created");
        }
        return file;
    }

    private void criarBanco() {

        banco = new Banco (this, "bd3",1);
        ContentValues contentValues = new ContentValues();
        contentValues.put("destino",destino.getText().toString());
        contentValues.put("tipo_viagem",tipoViagem.toString());
        contentValues.put("data_chegada", dataChegada);
        contentValues.put("data_partida", dataPartida);
        contentValues.put("orcamento",orcamento.getText().toString());
        contentValues.put("qtd_pessoas",qntPessoas.getText().toString());
       contentValues.put("imagem",imagem);
        banco.getWritableDatabase().insert("viagens",null,contentValues);
        Log.d("BANCO", "criou o banco");
    }

}