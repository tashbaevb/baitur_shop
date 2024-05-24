package com.example.baitur.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.baitur.R;
import com.example.baitur.databinding.FragmentHomeBinding;
import com.example.baitur.models.ModelM;
import com.example.baitur.remote_data.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    JemAdapter adapter;
    NavController navController;
    SharedPreferences preferences;
    String emailuserIdentify;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        if (getArguments() != null) {
            emailuserIdentify = getArguments().getString("identify");
        }
        if (preferences.getBoolean("loggedin", true)) {
            binding.textViewIdentify.setVisibility(View.VISIBLE);
            binding.textViewIdentify.setText(emailuserIdentify);
        }

        Call<List<ModelM>> apiCall = RetrofitClient.getInstance().getApi().getStoreProducts();
        apiCall.enqueue(new Callback<List<ModelM>>() {
            @Override
            public void onResponse(Call<List<ModelM>> call, Response<List<ModelM>> response) {
                if (response.body() != null) {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    adapter = new JemAdapter();
                    binding.rvCatalogM.setAdapter(adapter);
                    adapter.setList(response.body());
                } else {
                    Toast.makeText(requireActivity(), "NO Ability DATA from Sanjar/BaiturSHOP ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ModelM>> call, Throwable throwable) {
                Log.e("TAG", "NO  DATA " + throwable.getLocalizedMessage());
                Toast.makeText(requireActivity(), "NO DATA", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.basketBtn.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(requireActivity(), binding.basketBtn);
            popup.getMenuInflater().inflate(R.menu.action_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getTitle().toString()) {
                        case "Добавить в корзину":
                            navController = Navigation.findNavController(requireActivity(),
                                    R.id.nav_host);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("keysss_basket", adapter.getSelected_BasketList());
                            navController.navigate(R.id.navigation_basket, bundle);
                            break;
                        case "Пометить":
                            Toast.makeText(requireActivity(), "Marked", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(requireActivity(), "default", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });
            popup.show();
        });
        binding.signInReg.setOnClickListener(v1 -> {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
            navController.navigate(R.id.action_navigation_home_to_navigation_registr);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}