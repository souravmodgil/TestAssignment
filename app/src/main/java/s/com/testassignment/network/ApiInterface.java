package s.com.testassignment.network;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import s.com.testassignment.model.UserModel;

public interface ApiInterface {
    @GET("users")
    public Call<String> getUser(@QueryMap Map<String ,Object> map);
}
