package br.ucs.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.ucs.mobile.R;
import br.ucs.mobile.model.ConteudoLido;

public class ConteudoAdapter extends RecyclerView.Adapter<ConteudoAdapter.MissionViewHolder> {
    private List<ConteudoLido> conteudos;
    private int rowLayout;
    private Context context;

    public class MissionViewHolder extends RecyclerView.ViewHolder {
        LinearLayout conteudosLayout;
        TextView conteudoCodigoBarra;
        TextView conteudoTipo;

        public MissionViewHolder(View v) {
            super(v);
            conteudosLayout = (LinearLayout) v.findViewById(R.id.conteudo_layout);
            conteudoCodigoBarra = (TextView) v.findViewById(R.id.conteudoCodigoBarras);
            conteudoTipo = (TextView) v.findViewById(R.id.conteudoTipo);
            conteudoCodigoBarra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Ao clicar
                }
            });
        }
    }

    public ConteudoAdapter(List<ConteudoLido> conteudos, int rowLayout, Context context) {
        this.conteudos = conteudos;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MissionViewHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ConteudoAdapter.MissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MissionViewHolder holder, final int position) {
        holder.conteudoCodigoBarra.setText(conteudos.get(position).getConteudoCodigoBarras());
        holder.conteudoTipo.setText(conteudos.get(position).getConteudoTipo());

    }

    @Override
    public int getItemCount() {
        return conteudos.size();
    }
}