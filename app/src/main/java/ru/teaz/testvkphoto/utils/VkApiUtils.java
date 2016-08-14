package ru.teaz.testvkphoto.utils;

import com.vk.sdk.api.model.VKApiPhoto;

public class VkApiUtils {

    public static String getMinPhotoAbove604Url(VKApiPhoto item) {
        if (item == null) {
            return null;
        }

        if ((item.photo_604 != null) && (!item.photo_604.equals(""))) {
            return item.photo_604;
        }

        if ((item.photo_807 != null) && (!item.photo_807.equals(""))) {
            return item.photo_807;
        }

        if ((item.photo_1280 != null) && (!item.photo_1280.equals(""))) {
            return item.photo_1280;
        }

        return item.photo_2560;
    }

    public static String getMaxPhotoUrl(VKApiPhoto item) {
        if (item == null) {
            return null;
        }

        if ((item.photo_2560 != null) && (!item.photo_2560.equals(""))) {
            return item.photo_2560;
        }

        if ((item.photo_1280 != null) && (!item.photo_1280.equals(""))) {
            return item.photo_1280;
        }

        if ((item.photo_807 != null) && (!item.photo_807.equals(""))) {
            return item.photo_807;
        }

        if ((item.photo_604 != null) && (!item.photo_604.equals(""))) {
            return item.photo_604;
        }

        if ((item.photo_130 != null) && (!item.photo_130.equals(""))) {
            return item.photo_130;
        }

        return item.photo_75;
    }
}
