package info.marcussoftware.retrofitepicassoexemplo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 29/05/2018.
 */
public interface AlunosService {
    @GET("alunos")
    public Call<RespostaAlunos> pegarListaDeAlunos();
}
