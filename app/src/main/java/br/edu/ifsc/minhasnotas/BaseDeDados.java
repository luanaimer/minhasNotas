package br.edu.ifsc.minhasnotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BaseDeDados {

    private SQLiteDatabase bd;
    private Cursor cursor;
    private Context context;

    private String orderByClause;

    public void onCreate(Context aContext) {
        context = aContext;
        bd = context.openOrCreateDatabase("dbMinhasNotas", context.MODE_PRIVATE, null);

        bd.execSQL("CREATE TABLE IF NOT EXISTS notas ( id INTEGER PRIMARY KEY AUTOINCREMENT , texto VARCHAR, dtCriacao DATETIME, dtAlteracao DATETIME, imagem BLOB) ");
        this.cursor = bd.rawQuery("SELECT id, texto, dtCriacao, dtAlteracao, imagem  FROM  notas ", null);
    }

    public void insertDados(Nota nota) {
        Log.i("Resultado", "InsertDados");
        ContentValues novaNota = new ContentValues();

        String texto = nota.getTexto().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        Log.i("Resultado", dateFormat.format(date));

        novaNota.put("dtCriacao", dateFormat.format(date));
        novaNota.put("texto", texto);

        Bitmap imagem = nota.getImage();

        if (imagem != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imagem .compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] img = bos.toByteArray();
            novaNota.put("imagem", img);
        }

        try{
            bd.insert("notas", null, novaNota);
        }
        catch (SQLException ex){
        }
    }

    public void alterDados(Nota nota){
        ContentValues novaNota = new ContentValues();

        String texto = nota.getTexto().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        novaNota.put("dtAlteracao", dateFormat.format(date));
        novaNota.put("texto", texto);

        Bitmap imagem = nota.getImage();

        if (imagem != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imagem .compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] img = bos.toByteArray();
            novaNota.put("imagem", img);
        }

        String where = "id=" + nota.getId();

        bd.update("notas", novaNota, where, null);

    }

    public void setOrderByClause(String orderByClause){
        this.orderByClause = orderByClause;
    }

    public ArrayList<Nota> getListaNotas() {
        Nota nota;
        String encodedImage;

        ArrayList<Nota> listaNotas = null;

        this.cursor = bd.rawQuery("SELECT id, texto, dtAlteracao, dtCriacao, imagem FROM  notas " + orderByClause, null);

        if ((this.cursor != null) && (this.cursor.getCount() > 0)){
            this.cursor.moveToFirst();

            listaNotas = new ArrayList<Nota>();

            do {
                nota = new Nota();

                nota.setId(this.cursor.getInt(this.cursor.getColumnIndex("id")));
                nota.setTexto(this.cursor.getString(this.cursor.getColumnIndex("texto")));
                nota.setDataAlteracao(this.cursor.getString(this.cursor.getColumnIndex("dtAlteracao")));
                nota.setDataCriacao(this.cursor.getString(this.cursor.getColumnIndex("dtCriacao")));

                byte[] imagem = this.cursor.getBlob(this.cursor.getColumnIndex("imagem"));

                if (imagem != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
                    nota.setImage(bitmap);
                }

                listaNotas.add(nota);

            } while (this.cursor.moveToNext());
        }
        return listaNotas;
    }

    public Nota getNotaById(int Id){

        String where = "id=" + Id;

        this.cursor = bd.rawQuery("SELECT id, texto, dtAlteracao, dtCriacao, imagem  FROM  notas WHERE " + where, null);

        Nota nota = null;

        if ((this.cursor != null) && (this.cursor.getCount() > 0)) {
            this.cursor.moveToFirst();
            nota = new Nota();

            nota.setId(this.cursor.getInt(this.cursor.getColumnIndex("id")));
            nota.setDataCriacao(this.cursor.getString(this.cursor.getColumnIndex("dtCriacao")));
            nota.setDataAlteracao(this.cursor.getString(this.cursor.getColumnIndex("dtAlteracao")));
            nota.setTexto(this.cursor.getString(this.cursor.getColumnIndex("texto")));

            byte[] imagem = this.cursor.getBlob(this.cursor.getColumnIndex("imagem"));

            if (imagem != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
                nota.setImage(bitmap);
            }
        }

        return nota;
    }
}


