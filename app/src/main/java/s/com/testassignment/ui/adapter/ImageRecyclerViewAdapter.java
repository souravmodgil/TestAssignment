package s.com.testassignment.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import s.com.testassignment.R;
import s.com.testassignment.model.User;

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<ImageRecyclerViewAdapter.MyViewHolder> {
    private List<String> imageList;
    Context mcontext;

    /**
     * View holder class
     */


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageItem;



        public MyViewHolder(View view) {
            super(view);

            imageItem = view.findViewById(R.id.image_item);



        }
    }

    public ImageRecyclerViewAdapter(Context context, List<String> imageList) {

        this.mcontext = context;
        this.imageList = imageList;


    }


    @Override
    public void onBindViewHolder(final  ImageRecyclerViewAdapter.MyViewHolder holder , final int position) {
        if (imageList!= null && !imageList.get(position).isEmpty())
            Glide.with(mcontext).load(imageList.get(position)).
                    into(holder.imageItem);


    }

    @Override
    public int getItemCount() {

        return imageList.size();
    }

    @Override
    public ImageRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user_item, parent, false);
        return new ImageRecyclerViewAdapter.MyViewHolder(v);
    }
}



