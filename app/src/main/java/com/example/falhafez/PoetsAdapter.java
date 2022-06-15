package com.example.falhafez;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
public class PoetsAdapter extends RecyclerView.Adapter<PoetsAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<poet> poetList;
    private List<poet> poetListFiltered;
    private PoetAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView birthdate;
        public TextView deathdate;
        public TextView   birthplace;
        public ImageView thumbnail;



        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.txtname);
            birthdate = view.findViewById(R.id.txtbirth);
            deathdate = view.findViewById(R.id.txtdeath);
            birthplace = view.findViewById(R.id.txtbirthplace);
            thumbnail = view.findViewById(R.id.imgpoet);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onPoetSelected(poetListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public PoetsAdapter(Context context, List<poet> poettList, PoetAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.poetList = poetList;
        this.poetListFiltered = poetList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poets_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final poet poets = poetListFiltered.get(position);
        holder.name.setText(poets.getName());
        holder.birthdate.setText(poets.getBirthdate());
        holder.deathdate.setText(poets.getDeathdate());
        holder.birthplace.setText(poets.getBirthplace());

        Glide.with(context)
                .load(poets.getImgpoet())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return poetListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    poetListFiltered = poetList;
                } else {
                    List<poet> filteredList = new ArrayList<>();
                    for (poet row : poetList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getBirthplace().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    poetListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = poetListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                poetListFiltered = (ArrayList<poet>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface PoetAdapterListener {
        void onPoetSelected(poet poet2);
    }
}