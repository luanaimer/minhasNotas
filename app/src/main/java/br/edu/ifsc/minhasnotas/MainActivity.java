package br.edu.ifsc.minhasnotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BaseDeDados dataBase;
    private Context context;
    private ListView listViewNotas;
    private ArrayList<Nota> listaNotas;
    private NotaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Data de alteração");
        spinnerArray.add("Data de criação");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner);
        sItems.setAdapter(adapter);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String orderByClause;

                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Data de alteração"))
                {
                    orderByClause = "order by dtAlteracao desc ";
                }
                else
                {
                    orderByClause = "order by dtCriacao desc";
                }

                carregarRegistro(orderByClause);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public void onClickAdd(View view){
        Log.i("Resultado", "clickadd");
        Intent it = new Intent(MainActivity.this, cadastroActivity.class);
        startActivity(it);
    }

    public void carregarRegistro(String orderByClause){
        context = getBaseContext();
        dataBase = new BaseDeDados();
        dataBase.onCreate(context);
        dataBase.setOrderByClause(orderByClause);

        listViewNotas = findViewById(R.id.listView);
        listaNotas = dataBase.getListaNotas();

        if (listaNotas != null) {
            adapter = new NotaAdapter(this, listaNotas);
            listViewNotas.setAdapter(adapter);

            listViewNotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent it = new Intent(MainActivity.this, cadastroActivity.class);
                    Bundle b = new Bundle();
                    Nota nota = adapter.getItem(i);
                    b.putInt("id", nota.getId());
                    it.putExtras(b);
                    startActivity(it);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("Resultado", "teste77");

        carregarRegistro("");
    }
}
