package com.example.baitur.auth;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.baitur.R;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.baitur.databinding.FragmentRegistrationBinding;
import com.example.baitur.models.User;
import com.example.baitur.remote_data.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationFragment extends Fragment {

    FragmentRegistrationBinding binding;
    User newUser;

    NavController navController;
    private boolean emptyEditTextRegistration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding
                .inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host);

        binding.btnRegistration.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            if (!IsEmptyRditTextRegistration()) {
                registerToTable();
            }
        });

        binding.tvLoginNow.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_registr_to_navigation_login);
        });
    }


    private void registerToTable() {
        newUser = new User(binding.nameUser.getText().toString(),
                binding.email.getText().toString(),
                binding.password.getText().toString());

        Call<User> callCreateUser = RetrofitClient.getInstance().getApi().registrationNewUser(newUser);
        callCreateUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(requireActivity(), "Registration is Success!", Toast.LENGTH_SHORT).show();

                        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefLoginEdit = preferences.edit();
                        prefLoginEdit.commit();
                    }
                } else {
                    Log.e("fail", "fail");
                    Toast.makeText(requireActivity(), "Registration is not available now", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Log.e("FAILURE", "Registration is FAILURE");
                Toast.makeText(requireActivity(), "Registration is FAILURE", Toast.LENGTH_LONG).show();

            }
        });
    }

    private boolean IsEmptyRditTextRegistration() {
        if (binding.nameUser.getText().toString().isEmpty() ||
                binding.email.getText().toString().isEmpty() ||
                binding.password.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Заполните поля для регистрации!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}