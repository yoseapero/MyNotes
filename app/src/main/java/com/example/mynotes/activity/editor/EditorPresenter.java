package com.example.mynotes.activity.editor;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mynotes.api.ApiClient;
import com.example.mynotes.api.ApiInterface;
import com.example.mynotes.model.Note;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorPresenter {

    private EditorView view;

    public EditorPresenter(EditorView view) {
        this.view = view;
    }

    void saveNote(final String title, final String note, final int color){
        view.showProgress();

        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        Call<Note> call = apiInterface.saveNote(title,note,color);

        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(@NonNull Call<Note> call, @NonNull Response<Note> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null){
                    Boolean success = response.body().getSuccess();
                    if(success) {
                        view.onAddSuccess(response.body().getMessage());
                        /*
                        */
                    }
                    else{
                        view.onAddError(response.body().getMessage());
                        /**/
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Note> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onAddError(t.getLocalizedMessage());

            }
        });
    }

}
