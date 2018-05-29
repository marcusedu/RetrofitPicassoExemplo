package info.marcussoftware.retrofitepicassoexemplo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 29/05/2018.
 */
public class AlunosAdapter extends RecyclerView.Adapter<AlunosAdapter.AlunoHolder> {
    private ArrayList<Aluno> mAlunos = new ArrayList<>();
    private Locale br = new Locale("pt", "br");

    @NonNull
    @Override
    public AlunoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Cria um objeto AlunoHolder que recebe o Layout que será exibido na lista
        return new AlunoHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.aluno_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoHolder holder, int position) {
        holder.definirAluno(mAlunos.get(position));
    }

    @Override
    public int getItemCount() {
        return mAlunos.size();
    }

    public void mostrarAlunos(ArrayList<Aluno> alunos) {
        mAlunos = alunos;
        notifyDataSetChanged();
    }

    class AlunoHolder extends RecyclerView.ViewHolder {
        private final ImageView fotoAluno;
        private final TextView nome, email, telefone, nota;

        AlunoHolder(View itemView) {
            super(itemView);
            fotoAluno = itemView.findViewById(R.id.fotoAluno);
            nome = itemView.findViewById(R.id.nome);
            email = itemView.findViewById(R.id.email);
            telefone = itemView.findViewById(R.id.telefone);
            nota = itemView.findViewById(R.id.nota);
        }

        /**
         * Exibe as informações do aluno na view
         *
         * @param aluno
         */
        void definirAluno(Aluno aluno) {
            //Aqui o picassa ele baixa a imagem que está na url informada e exibe dentro da ImageView
            Picasso.get().load(aluno.getFoto()).into(fotoAluno);
            nome.setText(aluno.getNome());
            email.setText(aluno.getEmail());
            telefone.setText(aluno.getTelefone());
            nota.setText(String.format(br, "Nota\n%.2f", aluno.getNota()));
        }
    }
}
