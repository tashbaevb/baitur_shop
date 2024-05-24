package com.example.baitur.ui.payment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.baitur.R;
import com.example.baitur.databinding.FragmentPaymentBinding;
import com.example.baitur.models.ModelM;
import com.example.baitur.models.Order;
import com.example.baitur.remote_data.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentFragment extends Fragment {

    FragmentPaymentBinding binding;
    NavController navController;
    List<Order> payed_list;
    SharedPreferences preferences;

    public PaymentFragment() {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences = getActivity()
                .getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        if (getArguments() != null) {
            payed_list = getArguments().getParcelableArrayList("payed");
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnBack.setOnClickListener(v -> {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
            navController.navigate(R.id.action_navigation_payment_to_navigation_home);
        });
        binding.btnPayFinally.setOnClickListener(v1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            try {
                for (int i = 0; i < payed_list.size(); i++) {
                    Call<Order> apiCall = RetrofitClient.getInstance().getApi().createNewOrder(payed_list.get(i));
                    apiCall.enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    binding.progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(requireActivity(), "Order is SUCCESSFUL!", Toast.LENGTH_LONG).show();
                                    SharedPreferences.Editor prefLoginEdit = preferences.edit();
                                    prefLoginEdit.putBoolean("Order", true);
                                    prefLoginEdit.commit();
                                    binding.tvOtvet.setText("Ваш заказ выполняется, доставка  составляет от 5 до 12 дней.");
                                }
                            } else {
                                Log.e("fail", "fail");
                                Toast.makeText(requireActivity(), "Order is not available now", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable throwable) {
                            Toast.makeText(requireActivity(), "Order is not available now", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception ex) {
                Log.e("TAG", ex.toString());
                Toast.makeText(requireActivity(), "Товары не выбраны", Toast.LENGTH_SHORT).show();
            }

        });
    }
}