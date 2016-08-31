package com.stishki.iva.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stishki.iva.Entity.Poem;
import com.stishki.iva.R;

import java.util.List;

/**
 * Created by echessa on 7/24/15.
 */
public class DesignDemoRecyclerAdapter extends RecyclerView.Adapter<DesignDemoRecyclerAdapter.ViewHolder> {

    private List<Poem> mItems;

    public DesignDemoRecyclerAdapter(List<Poem> items) {
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String author = mItems.get(i).getAuthor();
        String text = mItems.get(i).getContent();
        viewHolder.mTextView.setText(author);
        viewHolder.mPoem.setText(text);


    }

    public void add(Poem poem) {
        mItems.add(poem);
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;
        private final TextView mPoem;


        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.list_item);
            mPoem = (TextView)v.findViewById(R.id.poem);
        }


    }

}
