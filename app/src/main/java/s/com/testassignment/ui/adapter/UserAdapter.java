package s.com.testassignment.ui.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import s.com.testassignment.R;
import s.com.testassignment.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private List<User> userList;
    Context mcontext;

    /**
     * View holder class
     */


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtUserName;
        public final CircleImageView profileImage;
        public final RecyclerView rcRow;


        public MyViewHolder(View view) {
            super(view);

            txtUserName = view.findViewById(R.id.txt_user_name);
            profileImage = view.findViewById(R.id.profile_image);
            rcRow =view.findViewById(R.id.rc_row);


        }
    }

    public UserAdapter(Context context, List<User> userList) {

        this.mcontext = context;
        this.userList = userList;


    }
    public  void updateList(List<User> list)

    {
        int index = userList.size();
        userList.addAll(index+1 ,list);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(final UserAdapter.MyViewHolder holder, final int position) {
       final User user = userList.get(position);
        if (user.getImage() != null)
            Glide.with(mcontext).load(user.getImage()).
                    into(holder.profileImage);
        holder.txtUserName.setText(user.getName());
        List<String> userItems = user.getItems();


        if(userItems!=null && userItems.size()>0) {
            if(userItems.size()%2 !=0) {
                GridLayoutManager layoutManager = new GridLayoutManager(mcontext, 2);

                // Create a custom SpanSizeLookup where the first item spans both columns
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return position == 0 ? 2 : 1;
                    }
                });


                holder.rcRow.setLayoutManager(layoutManager);


            }
            else
            {

                RecyclerView.LayoutManager  recyclerViewLayoutManager = new GridLayoutManager(mcontext, 2);
                holder.rcRow.setLayoutManager(recyclerViewLayoutManager);

            }
            holder.rcRow.setNestedScrollingEnabled(false);
            holder.rcRow.addItemDecoration(new SpacesItemDecoration(20));
            holder.rcRow.setAdapter(new ImageRecyclerViewAdapter(mcontext,userItems));
        }


    }

    @Override
    public int getItemCount() {

        return userList.size();
    }

    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserAdapter.MyViewHolder(v);
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.top = space;

            // Add top margin only for the first item to avoid double space between items

        }
    }
}


