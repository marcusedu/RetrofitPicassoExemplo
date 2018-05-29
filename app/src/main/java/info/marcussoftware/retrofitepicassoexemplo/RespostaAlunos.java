package info.marcussoftware.retrofitepicassoexemplo;

import java.util.ArrayList;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 29/05/2018.
 */
public class RespostaAlunos {
    private ArrayList<Aluno> alunos;

    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    public RespostaAlunos setAlunos(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
        return this;
    }
}
