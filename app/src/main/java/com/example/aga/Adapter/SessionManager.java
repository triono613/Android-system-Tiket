package com.example.aga.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.aga.SigninActivity;
import com.example.aga.SuccessRegistrasiWisatawanActivity;

import java.util.HashMap;

public class SessionManager {

    private static final String TAG = SessionManager.class.getSimpleName() ;
    SharedPreferences pref;
    SharedPreferences.Editor editor ;
    Context context;
    int mode = 0;

    public  static final  String pref_name= "pref";
    public  static final  String key_id = "key_id";
    public static  final  String is_login = "is_login";
    public static  final  String key_flag = "key_flag";
    public static  final  String key_email = "key_email";
    public static  final  String key_name = "key_name";
    public static  final  String key_ket = "key_ket";
    public static  final  String key_kode_lokasi = "key_kode_lokasi";
    public static  final  String key_nama_lokasi = "key_nama_lokasi";
    public static  final  String key_tgl_lhr = "key_tgl_lhr";
    public static  final  String key_jekel = "key_jekel";
    public static  final  String key_hp = "key_hp";

    public static  final  String key_kode_ksda = "key_kode_ksda";
    public static  final  String key_kode_lokasi_wist_order = "key_kode_lokasi_wist_order";
    public static  final  String key_kode_karcis = "key_kode_karcis";
    public static  final  String key_nama_karcis = "key_nama_karcis";
    public static  final  String key_kode_libur = "key_kode_libur";
    public static  final  String key_harga_karcis_wisata_wisnu = "key_harga_karcis_wisata_wisnu";
    public static  final  String key_harga_karcis_wisata_wisman = "key_harga_karcis_wisata_wisman";
    public static  final  String key_harga_karcis_asuransi_wisnu = "key_harga_karcis_asuransi_wisnu";
    public static  final  String key_harga_karcis_asuransi_wisman = "key_harga_karcis_asuransi_wisman";
    public static  final  String key_id_wist_order = "key_id_wist_order";

    public static  final  String key_kode_ksda_tmbhn = "key_kode_ksda_tmbhn";
    public static  final  String key_kode_lokasi_wist_tmbhn_order = "key_kode_lokasi_wist_tmbhn_order";
    public static  final  String key_kode_karcis_tmbhn = "key_kode_karcis_tmbhn";
    public static  final  String key_nama_karcis_tmbhn = "key_nama_karcis_tmbhn";
    public static  final  String key_kode_libur_tmbhn = "key_kode_libur_tmbhn";
    public static  final  String key_harga_karcis_wisata_tmbhn = "key_harga_karcis_wisata_tmbhn";
    public static  final  String key_harga_karcis_asuransi_tmbhn = "key_harga_karcis_asuransi_tmbhn";
    public static  final  String key_id_tmbhn = "key_id_tmbhn";

    public  static  final String key_kd_lok = "key_kd_lok";
    public  static  final String key_nm_lok = "key_nm_lok";

    public  static  final String key_kd_ksda = "key_kd_ksda";
    public  static  final String key_mode_pembayaran = "key_mode_pembayaran";
    public  static  final String key_nama_pembayaran = "key_nama_pembayaran";
    public static  final  String key_index = "key_index";


    public SessionManager (Context context){
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
        editor.apply();
        editor.commit();
    }

    public String getFlag(){
        return pref.getString(key_flag,"");
    }
    public boolean isLoggedIn(){
        return pref.getBoolean(is_login, false);
    }


    public void setFlag(String key_flag){
        editor.putString(key_flag, key_flag) ;
        editor.commit();
    }
    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(is_login, isLoggedIn);
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }


    public void createSession( String email, String flag, String name, String id,
                               String ket,String kd_lokasi,String nm_lokasi,
                               String tgllhr,String jekel, String hp)
    {
        editor.putBoolean(is_login,true);
        editor.putString(key_email,email);
        editor.putString(key_flag,flag);
        editor.putString(key_name,name);
        editor.putString(key_id,id);
        editor.putString(key_ket,ket);
        editor.putString(key_kode_lokasi,kd_lokasi);
        editor.putString(key_nama_lokasi,nm_lokasi);
        editor.putString(key_tgl_lhr,tgllhr);
        editor.putString(key_jekel,jekel);
        editor.putString(key_hp,hp);
        editor.commit();
        editor.apply();

    }

    public void createSessionWisUtm( String kode_ksda, String kode_lokasi_wist_order, String kode_karcis,String nama_karcis,
                                     String kode_libur, String harga_karcis_wisata_wisnu,String harga_karcis_wisata_wisman,
                                     String harga_karcis_asuransi_wisnu, String harga_karcis_asuransi_wisman,String id_wist_order)
    {
        editor.putString(key_kode_ksda,kode_ksda);
        editor.putString(key_kode_lokasi_wist_order,kode_lokasi_wist_order);
        editor.putString(key_kode_karcis,kode_karcis);
        editor.putString(key_nama_karcis,nama_karcis);
        editor.putString(key_kode_libur,kode_libur);
        editor.putString(key_harga_karcis_wisata_wisnu,harga_karcis_wisata_wisnu);
        editor.putString(key_harga_karcis_wisata_wisman,harga_karcis_wisata_wisman);
        editor.putString(key_harga_karcis_asuransi_wisnu,harga_karcis_asuransi_wisnu);
        editor.putString(key_harga_karcis_asuransi_wisman,harga_karcis_asuransi_wisman);
        editor.putString(key_id_wist_order,id_wist_order);
        editor.commit();
        editor.apply();
    }

    public void createSessionWisTmbhn( String kode_ksda_tmbhn, String kode_lokasi_wist_tmbhn_order, String kode_karcis_tmbhn,
                                       String nama_karcis_tmbhn, String kode_libur_tmbhn, String harga_karcis_wisata_tmbhn,
                                       String harga_karcis_asuransi_tmbhn, String id_tmbhn){


        editor.putString(key_kode_ksda_tmbhn,kode_ksda_tmbhn);
        editor.putString(key_kode_lokasi_wist_tmbhn_order,kode_lokasi_wist_tmbhn_order);
        editor.putString(key_kode_karcis_tmbhn,kode_karcis_tmbhn);
        editor.putString(key_nama_karcis_tmbhn,nama_karcis_tmbhn);
        editor.putString(key_kode_libur_tmbhn,kode_libur_tmbhn);
        editor.putString(key_harga_karcis_wisata_tmbhn,harga_karcis_wisata_tmbhn);
        editor.putString(key_harga_karcis_asuransi_tmbhn,harga_karcis_asuransi_tmbhn);
        editor.putString(key_id_tmbhn,id_tmbhn);
        editor.commit();
        editor.apply();
    }

    public void createSessionLokWisPesankarcisWisatawan( String kd_lok, String  nm_lok)
    {
        editor.putString(key_kd_lok,kd_lok);
        editor.putString(key_nm_lok,nm_lok);
        editor.commit();
        editor.apply();

    }

    public void createSessionJnsByr(String mode_pembayaran, String  nama_pembayaran)
    {
        editor.putString(key_mode_pembayaran,mode_pembayaran);
        editor.putString(key_nama_pembayaran,nama_pembayaran);
        editor.commit();
        editor.apply();

    }

    public void createSessionLokWisPesankarcisWisatawanKsda( String kd_ksda, String  nm_lok)
    {
        editor.putString(key_kd_ksda,kd_ksda);
        editor.putString(key_nm_lok,nm_lok);
        editor.commit();
        editor.apply();

    }

    public void createSessionSetupPintu(int index, String kd_ksda, String  nm_lok)
    {
        editor.putString(key_index, String.valueOf(index));
        editor.putString(key_kd_ksda,kd_ksda);
        editor.putString(key_nm_lok,nm_lok);
        editor.commit();
        editor.apply();

    }

    public  void checkLogin() {
        if( !this.is_login() ){
            Intent i = new Intent(context, SigninActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Intent i = new Intent(context, SuccessRegistrasiWisatawanActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    private boolean is_login() {
        return pref.getBoolean(is_login,false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, SigninActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }


    public HashMap<String,String > getUserDetail() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name,pref.getString(pref_name,null));
        user.put(key_flag,pref.getString(key_flag,null));
        user.put(key_email,pref.getString(key_email,null));
        user.put(key_name,pref.getString(key_name,null));
        user.put(key_id,pref.getString(key_id,null));
        user.put(key_ket,pref.getString(key_ket,null));
        user.put(key_kode_lokasi,pref.getString(key_kode_lokasi,null));
        user.put(key_nama_lokasi,pref.getString(key_nama_lokasi,null));
        user.put(key_tgl_lhr,pref.getString(key_tgl_lhr,null));
        user.put(key_jekel,pref.getString(key_jekel,null));
        user.put(key_hp,pref.getString(key_hp,null));
        return user;
    }


    public HashMap<String,String > getDaftarKarcisWisatawanUtama() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_kode_ksda,pref.getString(key_kode_ksda,null));
        data.put(key_kode_lokasi_wist_order,pref.getString(key_kode_lokasi_wist_order,null));
        data.put(key_kode_karcis,pref.getString(key_kode_karcis,null));
        data.put(key_nama_karcis,pref.getString(key_nama_karcis,null));
        data.put(key_kode_libur,pref.getString(key_kode_libur,null));
        data.put(key_harga_karcis_wisata_wisnu,pref.getString(key_harga_karcis_wisata_wisnu,null));
        data.put(key_harga_karcis_wisata_wisman,pref.getString(key_harga_karcis_wisata_wisman,null));
        data.put(key_harga_karcis_asuransi_wisnu,pref.getString(key_harga_karcis_asuransi_wisnu,null));
        data.put(key_harga_karcis_asuransi_wisman,pref.getString(key_harga_karcis_asuransi_wisman,null));
        data.put(key_id_wist_order,pref.getString(key_id_wist_order,null));
        return data;
    }

    public HashMap<String,String > getDaftarKarcisWisatawanTmbhn() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_kode_ksda_tmbhn,pref.getString(key_kode_ksda_tmbhn,null));
        data.put(key_kode_lokasi_wist_tmbhn_order,pref.getString(key_kode_lokasi_wist_tmbhn_order,null));
        data.put(key_kode_karcis_tmbhn,pref.getString(key_kode_karcis_tmbhn,null));
        data.put(key_nama_karcis_tmbhn,pref.getString(key_nama_karcis_tmbhn,null));
        data.put(key_kode_libur_tmbhn,pref.getString(key_kode_libur_tmbhn,null));
        data.put(key_harga_karcis_wisata_tmbhn,pref.getString(key_harga_karcis_wisata_tmbhn,null));
        data.put(key_harga_karcis_asuransi_tmbhn,pref.getString(key_harga_karcis_asuransi_tmbhn,null));
        data.put(key_id_tmbhn,pref.getString(key_id_tmbhn,null));
        return data;
    }


    public HashMap<String,String > getDataLokWisPesankarcisWisatawan() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_kd_lok,pref.getString(key_kd_lok,null));
        data.put(key_nm_lok,pref.getString(key_nm_lok,null));
        return data;
    }

    public HashMap<String,String > getDataLokWisPesankarcisWisatawanKsda() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_kd_ksda,pref.getString(key_kd_ksda,null));
        data.put(key_nm_lok,pref.getString(key_nm_lok,null));
        return data;
    }

    public HashMap<String,String > getDataSetupPintu() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_index,pref.getString(key_index,null));
        data.put(key_kd_ksda,pref.getString(key_kd_ksda,null));
        data.put(key_nm_lok,pref.getString(key_nm_lok,null));
        return data;
    }

    public HashMap<String,String > getDataJnsByr() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_mode_pembayaran,pref.getString(key_mode_pembayaran,null));
        data.put(key_nama_pembayaran,pref.getString(key_nama_pembayaran,null));
        return data;
    }



}
