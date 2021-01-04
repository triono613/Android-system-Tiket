package com.amanahgithawisata.aga.Helper;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Help {

    private Object ConnectivityManager;

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public  static  double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
    }

    public static String getDateTime() {
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    @SuppressLint("SimpleDateFormat")
    public static String formatTgl(String dateString) {

            String[] parts = dateString.split("-");
            String part1 = parts[0]; // 004
            String part2 = parts[1]; // 034556
            String part3 = parts[2]; // 034556

            return part3+"-"+part2+"-"+part1;

        }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static  String getToday() {
        Date date = Calendar.getInstance().getTime();
        return new android.icu.text.SimpleDateFormat("EEEE, dd MMMM yyyy",new java.util.Locale("id")).format(date);
    }

    public static  String byteToHex(byte b) {
        // Returns hex String representation of byte b
        char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
        return new String(array);
    }

    public static  String charToHex(char c) {
        // Returns hex String representation of char c
        byte hi = (byte) (c >>> 8);
        byte lo = (byte) (c & 0xff);
        return byteToHex(hi) + byteToHex(lo);
    }

    public static String domain_api(){
      return "kaffah.amanahgitha.com";
    }


}
