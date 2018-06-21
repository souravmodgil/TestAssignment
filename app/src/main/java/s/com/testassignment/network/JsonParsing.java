package s.com.testassignment.network;

import android.provider.SyncStateContract;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import s.com.testassignment.model.UserModel;
import s.com.testassignment.utils.Constant;

public class JsonParsing<T> {
    private static NetworkCallBack callBack;
    private static int type;

    public static JsonResponse getJsonResponse(String response, int type) {
        try {
            Gson gsonObj = new Gson();
            JSONObject jsonobject = null;

            //  Object object = new JSONObject(response);
            Object object = new JSONTokener(response).nextValue();
            if (object instanceof JSONArray) {
                JSONArray jsonarray = new JSONArray(response);
                jsonobject = jsonarray.getJSONObject(0);
            } else {
                jsonobject = (JSONObject) object;
            }

            JsonResponse response1 = new JsonResponse();
            switch (type) {

                case Constant.USER_API:
                    UserModel userModel = gsonObj.fromJson(jsonobject.toString(), UserModel.class);
                    response1.setObject(userModel);
                    response1.setErrorString("");
                    return response1;




            }
        } catch (Exception ex) {

            JsonResponse response1 = new JsonResponse();
            response1.setObject(null);
            response1.setErrorString(Constant.INVALID_API_FORMAT);
            return response1;

        }
        return null;

    }


}
