package info.androidhive.retrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.model.Fortune;

public class FortunesAdapter extends RecyclerView.Adapter<FortunesAdapter.FortuneViewHolder> {

    private List<Fortune> mFortunes;
    private int rowLayout;
    private Context mContext;


    public static class FortuneViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLayout;
        TextView title;

        TextView description;



        public FortuneViewHolder(View v) {
            super(v);
            mLayout = (LinearLayout) v.findViewById(R.id.fortunes_layout);
            title = (TextView) v.findViewById(R.id.title);
            description = (TextView) v.findViewById(R.id.description);

        }
    }

    public FortunesAdapter(List<Fortune> fortunes, int rowLayout, Context context) {
        this.mFortunes = fortunes;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public FortuneViewHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new FortuneViewHolder(view);
    }


    @Override
    public void onBindViewHolder(FortuneViewHolder holder, final int position) {
        holder.title.setText(String.valueOf(mFortunes.get(position).getId()));
        holder.description.setText(mFortunes.get(position).getQuote());
    }

    @Override
    public int getItemCount() {
        return mFortunes.size();
    }
}