package com.example.baitur.remote_data;

import com.example.baitur.models.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface API {

    @GET("product/all")
    Call<List<ModelM>> getStoreProducts();

    @POST("product/create/")
    Call<ModelM> createNewProduct();

    @GET("product/{id}/")
    Call<ModelM> getProductById();

    @PUT("product/{id}/")
    Call<ModelM> updateProductById();

    @DELETE("product/{id}/")
    Call<ModelM> deleteProductById();

    @PATCH("product/{id}/")
    Call<ModelM> changeProductById();

    @POST("account/register")
    Call<User> registrationNewUser(@Body User user);

    @POST("account/login")
    Call<LoginResponse> checkLoginUser(@Body CurrentUser currentUser);

    @POST("account/logout")
    Call<User> makeLogOutUser();

    @POST("order/create/")
    Call<Order> createNewOrder(@Body Order order);

    @GET("order/all")
    Call<List<Order>> getAllOrders();
}
