package de.davis.passwordmanager.database.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.davis.passwordmanager.gson.ElementDetailTypeAdapter;
import de.davis.passwordmanager.security.Cryptography;
import de.davis.passwordmanager.security.element.ElementDetail;

public class Converters {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(ElementDetail.class, new ElementDetailTypeAdapter()).create();

    @TypeConverter
    public static byte[] convertDetails(ElementDetail elementDetail){
        String json = gson.toJson(elementDetail, ElementDetail.class);
        return Cryptography.encryptAES(json.getBytes());
    }

    @TypeConverter
    public static ElementDetail convertByteArray(byte[] data){
        byte[] decrypted = Cryptography.decryptAES(data);
        return gson.fromJson(new String(decrypted), ElementDetail.class);
    }
}
