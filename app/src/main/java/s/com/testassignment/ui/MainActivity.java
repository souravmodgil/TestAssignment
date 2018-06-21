package s.com.testassignment.ui;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import s.com.testassignment.R;
import s.com.testassignment.model.UserModel;
import s.com.testassignment.network.ApiFactory;
import s.com.testassignment.network.ApiInterface;
import s.com.testassignment.network.JsonResponse;
import s.com.testassignment.network.NetworkCallBack;
import s.com.testassignment.network.SendRequest;
import s.com.testassignment.ui.adapter.SeparatorDecoration;
import s.com.testassignment.ui.adapter.UserAdapter;
import s.com.testassignment.utils.Constant;

public class MainActivity extends AppCompatActivity implements NetworkCallBack {

    private RecyclerView listUser;
    private ProgressBar progressBar;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int offset = 0;
    private int limit = 10;
    private ApiInterface apiInterface;
    private int isFirstTime = 0;
    private UserAdapter userAdapter;
    private android.support.v7.widget.LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listUser = findViewById(R.id.list_user);
        progressBar = findViewById(R.id.progress_bar);
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
        mLayoutManager = new LinearLayoutManager(this);
        getUserList();

    }

    private void getUserList() {
        Map<String, Object> map = new HashMap<>();
        map.put("offset", offset);
        map.put("limit", limit);
        SendRequest.sendRequest(Constant.USER_API, apiInterface.getUser(map), this);
    }

    private void parseResposne(UserModel userModel) {

        if (isFirstTime == 0) {

            userAdapter = new UserAdapter(this, userModel.getData().getUsers());

            SeparatorDecoration separatorDecoration = new SeparatorDecoration(this, Color.parseColor("#e8e8e8"), 1.5F);
            listUser.setLayoutManager(mLayoutManager);
            listUser.setItemAnimator(new DefaultItemAnimator());
            listUser.addItemDecoration(separatorDecoration);
            listUser.setAdapter(userAdapter);
        } else
            userAdapter.updateList(userModel.getData().getUsers());
        listUser.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            if(offset==0)
                            offset = offset + 11;
                            else
                                offset = offset + 10;
                            limit = limit + 10;
                            getUserList();

                        }
                    }
                }
            }
        });


    }


    @Override
    public void getResponse(JsonResponse response, int type) {

        if (response.getObject() != null) {
            parseResposne((UserModel) response.getObject());
        } else {
            Toast.makeText(this, response.getErrorString(), Toast.LENGTH_LONG).show();
        }
        progressBar.setVisibility(View.GONE);
        loading = true;


    }
}
