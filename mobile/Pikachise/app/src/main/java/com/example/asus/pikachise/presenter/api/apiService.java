package com.example.asus.pikachise.presenter.api;

import com.example.asus.pikachise.model.BrochureListResponse;
import com.example.asus.pikachise.model.EventResponse;
import com.example.asus.pikachise.model.FranchiseeResponse;
import com.example.asus.pikachise.model.HotListFranchiseResponse;
import com.example.asus.pikachise.model.ListFranchiseResponse;
import com.example.asus.pikachise.model.MyFranchiseResponse;
import com.example.asus.pikachise.model.NotificationResponse;
import com.example.asus.pikachise.model.OutletResponse;
import com.example.asus.pikachise.model.ReviewRatingResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by WilliamSumitro on 22/11/2017.
 */

public interface apiService {

    @FormUrlEncoded
    @POST("franchise/my_franchise")
    Call<MyFranchiseResponse> myfranchiseRequest(@Field("token") String token);

    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);
    @FormUrlEncoded
    @POST("user")
    Call<ResponseBody> userRequest(@Field("token") String token);

    @FormUrlEncoded
    @POST("logout")
    Call<ResponseBody> logoutRequest(@Field("token") String token);

    @Multipart
    @POST("auth/register")
    Call<ResponseBody> registerRequest(
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("name") RequestBody name,
            @Part MultipartBody.Part image,
            @Part("address") RequestBody address,
            @Part("phone_number") RequestBody phonenumber
    );

    @Multipart
    @POST("auth/edit_profile")
    Call<ResponseBody> editProfile(
            @Part("token") RequestBody token,
            @Part("name") RequestBody name,
            @Part("address") RequestBody address,
            @Part("phone_number") RequestBody phonenumber,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("franchise/register")
    Call<ResponseBody> registerFranchiseRequest(
            @Part("name") RequestBody name,
            @Part MultipartBody.Part logo,
            @Part MultipartBody.Part banner,
            @Part("category") RequestBody category,
            @Part("type") RequestBody type,
            @Part("establishSince") RequestBody since,
            @Part("investment") RequestBody investment,
            @Part("franchiseFee") RequestBody franchisefee,
            @Part("website") RequestBody website,
            @Part("address") RequestBody address,
            @Part("location") RequestBody location,
            @Part("phoneNumber") RequestBody phoneumber,
            @Part("email") RequestBody email,
            @Part("averageRating") RequestBody averagerating,
            @Part("detail") RequestBody detail,
            @Part("token") RequestBody token
    );

    @Multipart
    @POST("franchise/upload_legal_doc")
    Call<ResponseBody> uploadTDP(
            @Part("franchise_id") RequestBody franchise_id,
            @Part MultipartBody.Part tdp,
            @Part("token") RequestBody token
    );

    @Multipart
    @POST("franchise/upload_legal_doc")
    Call<ResponseBody> uploadSIUP(
            @Part("franchise_id") RequestBody franchise_id,
            @Part MultipartBody.Part siup,
            @Part("token") RequestBody token
    );

    @Multipart
    @POST("franchise/upload_legal_doc")
    Call<ResponseBody> uploadSuratPerjanjian(
            @Part("franchise_id") RequestBody franchise_id,
            @Part MultipartBody.Part suratperjanjian,
            @Part("token") RequestBody token
    );
    @Multipart
    @POST("franchise/upload_legal_doc")
    Call<ResponseBody> uploadSTPW(
            @Part("franchise_id") RequestBody franchise_id,
            @Part MultipartBody.Part stpw,
            @Part("token") RequestBody token
    );
    @Multipart
    @POST("franchise/upload_legal_doc")
    Call<ResponseBody> uploadKTP(
            @Part("franchise_id") RequestBody franchise_id,
            @Part MultipartBody.Part ktpfranchisor,
            @Part("token") RequestBody token
    );
    @Multipart
    @POST("franchise/upload_legal_doc")
    Call<ResponseBody> uploadCompanyProfile(
            @Part("franchise_id") RequestBody franchise_id,
            @Part MultipartBody.Part companyprofile,
            @Part("token") RequestBody token
    );
    @Multipart
    @POST("franchise/upload_legal_doc")
    Call<ResponseBody> uploadLaporanKeuangan(
            @Part("franchise_id") RequestBody franchise_id,
            @Part MultipartBody.Part laporankeuangan2tahunterakhir,
            @Part("token") RequestBody token
    );
    @Multipart
    @POST("franchise/upload_legal_doc")
    Call<ResponseBody> uploadSuratIzinTeknik(
            @Part("franchise_id") RequestBody franchise_id,
            @Part MultipartBody.Part suratizinteknis,
            @Part("token") RequestBody token
    );
    @Multipart
    @POST("franchise/upload_legal_doc")
    Call<ResponseBody> uploadTDPF(
            @Part("franchise_id") RequestBody franchise_id,
            @Part MultipartBody.Part tandabuktipendaftaran,
            @Part("token") RequestBody token
    );
    @FormUrlEncoded
    @POST("franchise/document_status")
    Call<ResponseBody> documentstatusRequest(@Field("franchise_id") String franchise_id,
                                             @Field("token") String token);

    @FormUrlEncoded
    @POST("franchise/franchise_list")
    Call<ListFranchiseResponse> franchiselistRquest(@Field("token") String token);

    @FormUrlEncoded
    @POST("franchise/franchise_list_by_category")
    Call<ListFranchiseResponse> franchiselistCategoryRequest(@Field("token") String token,
                                                             @Field("category") String category);

    @FormUrlEncoded
    @POST("franchise/get_brochures")
    Call<BrochureListResponse> brochurelistRequest(@Field("token") String token,
                                                   @Field("franchise_id") String franchise_id);

    @Multipart
    @POST("franchise/add_brochure")
    Call<ResponseBody> addBrochureRequest(
            @Part("franchise_id") RequestBody franchise,
            @Part("token") RequestBody token,
            @Part MultipartBody.Part brochure
    );

    @Multipart
    @POST("franchise/add_franchisee")
    Call<ResponseBody> addFranchiseeRequest(
            @Part("token") RequestBody token,
            @Part("franchise_id") RequestBody franchiseid,
            @Part("franchisee_email") RequestBody franchiseeemail,
            @Part MultipartBody.Part agreement,
            @Part("address") RequestBody address,
            @Part("telp") RequestBody telp,
            @Part("date_join") RequestBody datejoin
    );

    @FormUrlEncoded
    @POST("franchise/get_outlets")
    Call<OutletResponse> outletlistRequest(@Field("token") String token,
                                           @Field("franchise_id") String franchise_id);

    @FormUrlEncoded
    @POST("franchise/new_franchise")
    Call<ListFranchiseResponse> newFranchiseRequest(@Field("token") String token,
                                                    @Field("count") String count);

    @FormUrlEncoded
    @POST("franchise/hot_franchise")
    Call<HotListFranchiseResponse> hotFranchiseRequest(@Field("token") String token,
                                                       @Field("count") String count);

    @FormUrlEncoded
    @POST("franchise/allow_review")
    Call<ResponseBody> allowRequest(@Field("token") String token,
                                    @Field("franchise_id") String franchiseid);

    @FormUrlEncoded
    @POST("franchise/add_review_rating")
    Call<ResponseBody> addreviewratingRequest(@Field("token") String token,
                                              @Field("franchise_id") String franchiseid,
                                              @Field("rating") String rating,
                                              @Field("review") String review);

    @FormUrlEncoded
    @POST("franchise/get_review_rating")
    Call<ReviewRatingResponse> getreviewratingRequest(@Field("token") String token,
                                                      @Field("franchise_id") String franchiseid);

    @FormUrlEncoded
    @POST("franchise/my_favorite")
    Call<MyFranchiseResponse> myFavoriteRequest(@Field("token") String token);

    @FormUrlEncoded
    @POST("franchise/unfavorite")
    Call<MyFranchiseResponse> UnFavourite(
            @Field("franchise_id") String franchise_id,
            @Field("token") String token);

    @FormUrlEncoded
    @POST("franchise/favorite")
    Call<MyFranchiseResponse> Favourite(
            @Field("franchise_id") String franchise_id,
            @Field("token") String token);

    @FormUrlEncoded
    @POST("franchise/favorite_status")
    Call<ResponseBody> Get_FavouritStatus(
            @Field("franchise_id") String franchise_id,
            @Field("token") String token);

    @FormUrlEncoded
    @POST("franchise/get_notifications_count")
    Call<ResponseBody> Get_Notification_Count(
            @Field("token") String token);

    @FormUrlEncoded
    @POST("franchise/get_notifications")
    Call<ResponseBody> Get_Notification(
            @Field("token") String token);

    @FormUrlEncoded
    @POST("franchise/read_notification")
    Call<NotificationResponse> Read_Notification(
            @Field("franchise_id") String franchise_id,
            @Field("token") String token);

    @FormUrlEncoded
    @POST("franchise/get_notifications")
    Call<NotificationResponse> myNotificationRequest(@Field("token") String token);


    @FormUrlEncoded
    @POST("auth/change_password")
    Call<ResponseBody> changePassword(@Field("token") String token,
                                      @Field("old_password") String oldpassword,
                                      @Field("new_password") String newpassword);
    @FormUrlEncoded
    @POST("franchise/get_franchisee")
    Call<FranchiseeResponse> getmyfranchisee(@Field("token") String token,
                                             @Field("franchise_id") String franchise_id);
    @FormUrlEncoded
    @POST("franchise/edit_outlet")
    Call<ResponseBody> editmyfranchisee(@Field("outlet_id") String outlet_id,
                                        @Field("address") String address,
                                        @Field("telp") String telp,
                                        @Field("date_join") String datejoin,
                                        @Field("token") String token);
    @FormUrlEncoded
    @POST("franchise/delete_brochure")
    Call<ResponseBody> deletebrochure(@Field("token") String token,
                                      @Field("brochure_id") String brochure_id);

    @Multipart
    @POST("franchise/add_event")
    Call<ResponseBody> addEvent(
            @Part("token") RequestBody token,
            @Part("franchise_id") RequestBody franchiseid,
            @Part("name") RequestBody name,
            @Part("date") RequestBody date,
            @Part("time") RequestBody time,
            @Part("venue") RequestBody venue,
            @Part("detail") RequestBody detail,
            @Part MultipartBody.Part image,
            @Part("price") RequestBody price
    );
    @FormUrlEncoded
    @POST("franchise/get_events")
    Call<EventResponse> getEvent(@Field("token") String token);

    @FormUrlEncoded
    @POST("franchise/allow_book_event")
    Call<EventResponse> AllowBookEvent(@Field("token") String token,
                                       @Field("event_id") String event_id);

    @FormUrlEncoded
    @POST("franchise/book_event")
    Call<EventResponse> BookEvent(@Field("token") String token,
                                  @Field("event_id") String event_id,
                                  @Field("qrcode") String qrcode,
                                  @Field("amount") String amount);
    @FormUrlEncoded
    @POST("franchise/my_booked_events")
    Call<EventResponse> MyBookEvent(@Field("token") String token);
}
