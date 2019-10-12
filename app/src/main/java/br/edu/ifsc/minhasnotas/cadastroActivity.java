package br.edu.ifsc.minhasnotas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class cadastroActivity extends AppCompatActivity {

    public static final int GALLERY_REQUEST_CODE = 1;

    private BaseDeDados dataBase;
    private Context context;
    private Boolean inserindo;
    private int idNota;
    private Bitmap imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        context = getBaseContext();
        dataBase = new BaseDeDados();
        dataBase.onCreate(context);
        inserindo = true;

        Bundle b = getIntent().getExtras();
        if(b != null) {
            this.idNota = b.getInt("id");

            if (this.idNota > 0) {

                Nota nota = dataBase.getNotaById(idNota);

                EditText edText = findViewById(R.id.editTexto);
                edText.setText(nota.getTexto());

                nota.setTexto(edText.getText().toString());
                if (nota.getImage() != null) {
                    ImageView img = findViewById(R.id.imagem);
                    img.setImageBitmap(nota.getImage());
                }



                inserindo = false;
            }

        }

        ImageView img = findViewById(R.id.imagem);
    }

    public void onClickGravar(View view){

        EditText edText = findViewById(R.id.editTexto);
        Nota nota;

        if (inserindo) {
            Log.i("update", "inserindo");

            if (!(edText.getText().toString().isEmpty())) {
                nota = new Nota();
                nota.setTexto(edText.getText().toString());
                nota.setImage(this.imagem);
                dataBase.insertDados(nota);
            } else {

            }
        }
        else{
            Log.i("update", "alterando");

            if (!(edText.getText().toString().isEmpty())) {
                Log.i("update", "alterando2");
                nota = dataBase.getNotaById(idNota);

                nota.setTexto(edText.getText().toString());
                nota.setImage(this.imagem);

                Log.i("update", "alterando3");

                dataBase.alterDados(nota);
            } else {

            }
        }

        Intent it = new Intent(cadastroActivity.this, MainActivity.class);
        startActivity(it);

    }

    public void selecionarImg(View view){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);

        Log.i("Resultado", "imagem");
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        ImageView imageview = findViewById(R.id.imagem);

        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    imageview.setImageURI(selectedImage);

                    Bitmap bitmap = ((BitmapDrawable)imageview.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageInByte = stream.toByteArray();
                    long lengthbmp = imageInByte.length;

                    if (lengthbmp > 500000){
                        Toast toast = Toast.makeText(context, "Imagem muito grande!", 5);
                        toast.show();

                        imageview.setImageResource(R.drawable.noimage);
                    }
                    else{
                        this.imagem = bitmap;
                    }

                    break;
            }

    }

}
