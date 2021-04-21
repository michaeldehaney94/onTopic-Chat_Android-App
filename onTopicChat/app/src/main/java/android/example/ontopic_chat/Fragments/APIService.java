package android.example.ontopic_chat.Fragments;

import android.example.ontopic_chat.Norifications.MyResponse;
import android.example.ontopic_chat.Norifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type: application/json",
                    "Authorization: key=AAAAC5NbQpU:APA91bHBHaVdpDrGhjdlQxN5lOjg_7pU-zt6OYk9C_cAQWKXO-DZqaIwsYbgRIxjNItM5ig9PL307fE8NtKwU03vcHSRNaLbx2_i4qtlAia7zhhVukX7AoNstoVgPpXEz8ye4dMDBQ7p"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
