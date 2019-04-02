package com.changsune.changsu.yeontalk.networking;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface YeonTalkApi {

    @FormUrlEncoded
    @POST("confirmuserbydeviceid.php")
    Call<ResponseBody> confirmuserbydeviceid(@Field("user_device_id") String deviceId);

    @FormUrlEncoded
    @POST("registration.php")
    Call<ResponseBody> insertRegistration(@Field("user_device_id") String device_id, @Field("user_nickname") String nickname, @Field("user_gender") String gender, @Field("user_birthyear") String birthYear, @Field("user_nation") String nation, @Field("user_region") String region);

    @FormUrlEncoded
    @POST("fetchuserslist.php")
    Call<MultiUsersListResponse> fetchUserList(@Field("user_device_id") String user_device_id, @Field("load_limit") String load_limit, @Field("user_login_time_loaded_last") String user_login_time_loaded_last, @Field("selection_gender") String selection_gender, @Field("selection_max_birthyear") String selection_max_birthyear, @Field("selection_min_birthyear") String selection_min_birthyear, @Field("selection_nation") String selection_nation, @Field("selection_region") String selection_region);

    @FormUrlEncoded
    @POST("fetchusersimagelist.php")
    Call<UserListResponseSchema> fetchUsersImageList(@Field("me_id") String me_id, @Field("load_limit") String load_limit, @Field("user_login_time_loaded_last") String user_login_time_loaded_last, @Field("selection_gender") String selection_gender, @Field("selection_max_birthyear") String selection_max_birthyear, @Field("selection_min_birthyear") String selection_min_birthyear, @Field("selection_nation") String selection_nation, @Field("selection_region") String selection_region);

    @FormUrlEncoded
    @POST("insertfavorites.php")
    Call<ResponseBody> insertFavorite(@Field("user_id_from") String user_id_from, @Field("user_id_to") String user_id_to);

    @FormUrlEncoded
    @POST("deletefavorites.php")
    Call<ResponseBody> deleteFavorite(@Field("user_id_from") String user_id_from, @Field("user_id_to") String user_id_to);

    @FormUrlEncoded
    @POST("updateuserdata.php")
    Call<ResponseBody> updateUserData(@Field("user_id") String user_id, @Field("data_key") String data_key, @Field("data_value") String data_value);

    @GET("deleteimages.php")
    Call<ResponseBody> deleteImage(@Query("user_id") String str, @Query("image_id") String str2);

    @FormUrlEncoded
    @POST("chat/fetchchatlist.php")
    Call<ChatListResponseSchema> fetchChatList(@Field("room_id") String room_id, @Field("me_id") String me_id, @Field("user_id") String user_id, @Field("chat_time_last_loaded") String chat_time_last_loaded, @Field("load_limit") String load_limit);

    @FormUrlEncoded
    @POST("chat/fetchchatroomlist.php")
    Call<ChatRoomListResponseSchema> fetchChatRoomListByUserId(@Field("user_id") String str);

    @FormUrlEncoded
    @POST("chat/insertchat.php")
    Call<ResponseBody> insertChat(@Field("chat_room_id") String str, @Field("from_user_id") String str2, @Field("to_user_id") String str3, @Field("chat_type") String str4, @Field("chat_message") String str5, @Field("chat_date") String str6, @Field("chat_status") String status);

    @FormUrlEncoded
    @POST("chat/deletechatunshown.php")
    Call<ResponseBody> deletechatunshown(@Field("room_id") String room_id, @Field("me_id") String user_id);

    @FormUrlEncoded
    @POST("chat/deleteuserschatroom.php")
    Call<ResponseBody> deleteuserschatroom(@Field("room_id") String room_id, @Field("me_id") String user_id);

    @FormUrlEncoded
    @POST("chat/create_chat_room.php")
    Call<ResponseBody> insertChatRoom(@Field("from_user_id") String str, @Field("to_user_id") String str2);


    @POST("updateuserprofileimage.php")
    @Multipart
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part part, @Part("file") RequestBody file, @Part("user_id") RequestBody userId);

    @POST("chat/insertimagechat.php")
    @Multipart
    Call<ResponseBody> uploadFileWithChat(@Part MultipartBody.Part part, @Part("file") RequestBody requestBody, @Part("chat_room_id") RequestBody requestBody2, @Part("from_user_id") RequestBody requestBody3, @Part("to_user_id") RequestBody requestBody4, @Part("chat_type") RequestBody requestBody5, @Part("chat_date") RequestBody requestBody6, @Part("chat_status") RequestBody status, @Part("chat_file_size") RequestBody chat_file_size);

    @POST("uploadimages.php")
    @Multipart
    Call<ImageListResponseSchema> uploadImages(@Part MultipartBody.Part file1, @Part MultipartBody.Part file2, @Part MultipartBody.Part file3, @Part MultipartBody.Part file4, @Part MultipartBody.Part file5, @Part("user_id") RequestBody requestBody2);

    @GET("fetchuserdetails.php")
    Call<UserDetailsResponseSchema> fetchUserDetails(@Query("user_id") String userId);

}
