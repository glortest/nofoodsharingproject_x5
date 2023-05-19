package com.example.nofoodsharingproject.data.api.notifications;

import com.example.nofoodsharingproject.data.RetrofitNotificationService;

public class NotificationApiService {
    private static NotificationAPI notificationAPI;

    public static NotificationAPI create() {
        return RetrofitNotificationService.getInstance().create(NotificationAPI.class);
    }

    public static NotificationAPI getInstance() {
        if (notificationAPI == null) notificationAPI = create();
        return notificationAPI;
    }
}
