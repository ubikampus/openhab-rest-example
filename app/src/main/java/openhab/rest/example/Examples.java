package openhab.rest.example;

import java.util.List;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.converter.jackson.JacksonConverterFactory;

public class Examples {

    // BINDINGS REST CALL
    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --  -- -- -- -- -- -- -- -- -- --

    public static class Binding
    {
        public String author;
        public String description;
        public String id;
        public String name;

        public void Binding(String author, String description, String id, String name)
        {
            this.author = author;
            this.description = description;
            this.id = id;
            this.name = name;
        }
    }

    public interface GetBindings
    {
        @GET("bindings")
        Call<List<Binding>> get();
    }

    // EVENT LISTENER FOR RESPONSES
    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --  -- -- -- -- -- -- -- -- -- --

    interface IEventListener {
        void response(List<Binding> bindings);
    }

    // ENQUEUE THE REST CALL
    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --  -- -- -- -- -- -- -- -- -- --

    public static void getBindings(IEventListener eventListener) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:8080/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetBindings getBindings = retrofit.create(GetBindings.class);

        Call<List<Binding>> call = getBindings.get();

        call.enqueue(new Callback<List<Binding>>() {
            @Override
            public void onResponse(Call<List<Binding>> call, Response<List<Binding>> response) {
                List<Binding> bindings = null;

                if (response.isSuccessful())
                    bindings = response.body();

                eventListener.response(bindings);
            }

            @Override
            public void onFailure(Call<List<Binding>> call, Throwable t) {
                eventListener.response(null);
            }
        });
    }

}


