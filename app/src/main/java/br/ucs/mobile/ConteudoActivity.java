package br.ucs.mobile;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.ucs.mobile.adapter.ConteudoAdapter;
import br.ucs.mobile.database.BDSQLite;
import br.ucs.mobile.model.ConteudoLido;

public class ConteudoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteudo);
        setTitle("Conteúdos Lidos");

        //soluciona bug da task
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        recyclerView = (RecyclerView) findViewById(R.id.missions_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.atualizarListMissions();
    }

    public void atualizarListMissions(){
        BDSQLite bd = new BDSQLite(this);
        List<ConteudoLido> conteudos = bd.getAllConteudoLido();
        recyclerView.setAdapter(new ConteudoAdapter(conteudos, R.layout.lista_item_conteudo, this));
    }

    @Override
    public void onResume()
    {
        this.atualizarListMissions();
        super.onResume();
    }
}