package com.example.baitur.auth;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.baitur.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.baitur.databinding.FragmentLoginBinding;
import com.example.baitur.models.CurrentUser;
import com.example.baitur.remote_data.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.baitur.models.LoginResponse;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    NavController navController;
    String emailUserIdentify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding
                .inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnLogin.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            if (!isEmptyEditTextLogin()) {
                loginUser(new CurrentUser(binding.email.getText().toString(),
                        binding.password.getText().toString()));
            }
        });
        binding.btnToRegistration.setOnClickListener(v1 -> {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
            navController.navigate(R.id.action_navigation_login_to_navigation_registr);
        });
    }

    private void loginUser(CurrentUser currentUser) {
        try {
            Call<LoginResponse> response = RetrofitClient.getInstance().getApi()
                    .checkLoginUser(currentUser);

            response.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {

                        String token_n = response.body().getAccess_token();

                        emailUserIdentify = binding.email.getText().toString();
                        Bundle bundle = new Bundle();
                        bundle.putString("identify", emailUserIdentify);

//                        Bundle bundle_token = new Bundle();
//                        bundle_token.putString("key_token", token_n);

                        try {
                            SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor prefLoginEdit = preferences.edit();
                            prefLoginEdit.putBoolean("loggedin", true);
//                            prefLoginEdit.putString("token", token_n);
//                            prefLoginEdit.commit();


                            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                            navController.navigate(R.id.action_navigation_login_to_navigation_home, bundle);
                        } catch (Exception e) {
                            Log.d("API", "Token error: " + e.toString());
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("API", t.toString());
                    binding.textError.setText("FAILER LOGIN!" + toString());
                }
            });
        } catch (Exception e) {
            Log.d("API", e.toString());
        }
    }

    private Boolean isEmptyEditTextLogin() {

        if (binding.email.getText().toString().isEmpty()
                || binding.password.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Empty EditText", Toast.LENGTH_LONG);
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