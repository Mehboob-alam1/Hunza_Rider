package com.mehboob.hunzarider.api;





import static com.mehboob.hunzarider.constants.Constants.CONTENT_TYPE;
import static com.mehboob.hunzarider.constants.Constants.SERVER_KEY;

import com.mehboob.hunzarider.models.PushNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers({"Authorization: key="+SERVER_KEY,"Content-Type:"+CONTENT_TYPE})
@POST("fcm/send")
Call<PushNotification> sendNotification(@Body PushNotification notification);
}
