package info.marcussoftware.retrofitepicassoexemplo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    //Definição dos campos
    private RecyclerView mRecyclerView;
    private FrameLayout carregando;
    private AlunosAdapter mAlunosAdapter = new AlunosAdapter();
    //Criando o retrofit que vai fazer a conexão com o WebService
    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl("https://demo5896037.mockable.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Busca referencias das view que estão com id definido no XML
        mRecyclerView = findViewById(R.id.meuRecyclerView);
        carregando = findViewById(R.id.carregando);

        //Inicializa o Recycler View definindo o LayoutManager
        //que é responsavel como a lista é exibida sem essa linha não aparece nada
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Define o adapter que é o responsavel por criar linkar as views que vão popular a lista
        mRecyclerView.setAdapter(mAlunosAdapter);

        carregarAlunos();
    }

    void carregarAlunos() {
        mostrarCarregando(true);
        //Cria o serviço de Alunos que definimos na intercae
        mRetrofit.create(AlunosService.class)
                //Esse metodo foi definido na interface
                .pegarListaDeAlunos()
                //Aqui ele faz uma chamada I/O Assincrona e chama esse CallBack quando estiver completa
                .enqueue(new Callback<RespostaAlunos>() {
                    //Se não houve nenhum erro durate requisição esse metodo é chamado
                    @Override
                    public void onResponse(Call<RespostaAlunos> call, Response<RespostaAlunos> response) {
                        //Checagem se a resposta do servidor foi processada com sucesso
                        if (response.isSuccessful() && response.body() != null) {
                            mostrarAlunos(response.body().getAlunos());
                            mostrarListaVazia(response.body().getAlunos().isEmpty());
                        } else {
                            mostrarError();
                        }
                    }

                    //Esse metodo só é chamado quando tem algum erro durante o processamento da requisição
                    // ou no parse da resposta de JSON ou XML para uma instancia de objeto
                    @Override
                    public void onFailure(Call<RespostaAlunos> call, Throwable t) {
                        mostrarError();
                    }
                });
    }

    /**
     * Exibe/oculta o carregando para informar ao usuario que alguma coisa está acontecendo
     *
     * @param mostrar
     */
    void mostrarCarregando(boolean mostrar) {
        carregando.setVisibility(mostrar ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(!mostrar ? View.VISIBLE : View.GONE);
    }

    /**
     * Exibe a lista dos alunos e oculta o carregando
     *
     * @param alunos
     */
    void mostrarAlunos(ArrayList<Aluno> alunos) {
        mAlunosAdapter.mostrarAlunos(alunos);
        mostrarCarregando(false);
    }

    /**
     * Mostrar um Taost que a lista recebida está vazia
     *
     * @param mostrar
     */
    void mostrarListaVazia(boolean mostrar) {
        if (mostrar)
            Toast.makeText(this, "Lista está vazia", Toast.LENGTH_LONG).show();
    }

    /**
     * Mostra uma mensagem que um erro ocorreu
     */
    void mostrarError() {
        mostrarCarregando(false);
        Snackbar.make(mRecyclerView, "Ops houve um erro ao conectar com o serviço, tente em alguns instantes", 5000).show();
    }
}
