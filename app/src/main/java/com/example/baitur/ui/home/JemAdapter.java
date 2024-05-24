package com.example.baitur.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.baitur.R;
import com.example.baitur.databinding.ItemProductBinding;
import com.example.baitur.models.ModelM;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class JemAdapter extends RecyclerView.Adapter<JemAdapter.ViewHolder> {

    ItemProductBinding binding;
    Context context;
    List<ModelM> list;
    ArrayList<ModelM> desc_list = new ArrayList<>();
    ArrayList<ModelM> selected_BasketList = new ArrayList<>();
    NavController navController;

    public JemAdapter() {
    }

    public JemAdapter(Context context, List<ModelM> list) {
        this.context = context;
        this.list = list;
    }

    public ArrayList<ModelM> getSelected_BasketList() {
        return selected_BasketList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<ModelM> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull JemAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding binding;

        public ViewHolder(@NonNull ItemProductBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void onBind(ModelM modelM) {
            binding.nameProductCard.setText(modelM.getModelTitle());
            binding.priceCard.setText(String.valueOf(modelM.getModelPrice()));
            binding.descriptionCard.setText(modelM.getModelDescription());
            Picasso.get().load(modelM.getModelImage()).into(binding.imageCard);
            binding.btnZoom.setOnClickListener(v -> {
                desc_list.add(modelM);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("see more", desc_list);
                navController = Navigation.findNavController((Activity) itemView.getContext(),
                        R.id.nav_host);
                //                navController.navigate(R.id.navigation_description, bundle);
                Log.e("TAG", "pass data to description ! ! ! ");
            });
            itemView.setOnClickListener(v1 -> {
                if (binding.tovarFavoriteCheck.getVisibility() == View.INVISIBLE) {
                    binding.tovarFavoriteCheck.setVisibility(View.VISIBLE);
                    selected_BasketList.add(modelM);
                } else {
                    binding.tovarFavoriteCheck.setVisibility(View.INVISIBLE);
                    selected_BasketList.remove(modelM);
                }
            });
        }
    }
}
