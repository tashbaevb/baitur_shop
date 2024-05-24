package com.example.baitur.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baitur.R;
import com.example.baitur.databinding.ItemOrderBinding;
import com.example.baitur.models.ModelM;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    ItemOrderBinding binding;
    Context context;
    ArrayList<ModelM> nn_list;

    public BasketAdapter(Context context, ArrayList<ModelM> nn_list) {
        this.context = context;
        this.nn_list = nn_list;
    }

    @NonNull
    @Override
    public BasketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemOrderBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.ViewHolder holder, int position) {
        holder.onBind(nn_list.get(position));
    }

    @Override
    public int getItemCount() {
        return nn_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderBinding binding;

        public ViewHolder(@NonNull ItemOrderBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void onBind(ModelM modelM) {
            binding.nameProductCard.setText(modelM.getModelTitle());
            binding.priceCard.setText(String.valueOf(modelM.getModelPrice()));
            Picasso.get().load(modelM.getModelImage())
                    .placeholder(R.drawable.place_holder_my).into(binding.imageCard);
        }
    }
}
