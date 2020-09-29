package com.amanahgithawisata.aga.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.amanahgithawisata.aga.SigninActivity;
import com.amanahgithawisata.aga.SuccessRegistrasiWisatawanActivity;

import java.util.HashMap;

@SuppressLint("Registered")
public class SessionManager extends PreferenceActivity {

    private static final String TAG = SessionManager.class.getSimpleName();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    public static final String pref_name = "pref";
    public static final String key_id = "key_id";
    public static final String is_login = "is_login";
    public static final String key_flag = "key_flag";
    public static final String key_email = "key_email";
    public static final String key_name = "key_name";
    public static final String key_ket = "key_ket";
    public static final String key_kode_lokasi = "key_kode_lokasi";
    public static final String key_nama_lokasi = "key_nama_lokasi";
    public static final String key_tgl_lhr = "key_tgl_lhr";
    public static final String key_jekel = "key_jekel";
    public static final String key_hp = "key_hp";
    public static final String key_user_level = "key_user_level";
    public static  final String key_kode_pintu = "key_kode_pintu";

    public static final String key_kode_ksda = "key_kode_ksda";
    public static final String key_kode_lokasi_wist_order = "key_kode_lokasi_wist_order";
    public static final String key_kode_karcis = "key_kode_karcis";
    public static final String key_nama_karcis = "key_nama_karcis";
    public static final String key_kode_libur = "key_kode_libur";
    public static final String key_harga_karcis_wisata_wisnu = "key_harga_karcis_wisata_wisnu";
    public static final String key_harga_karcis_wisata_wisman = "key_harga_karcis_wisata_wisman";
    public static final String key_harga_karcis_asuransi_wisnu = "key_harga_karcis_asuransi_wisnu";
    public static final String key_harga_karcis_asuransi_wisman = "key_harga_karcis_asuransi_wisman";
    public static final String key_id_wist_order = "key_id_wist_order";
    public static final String key_url_img_wist_utama = "key_url_img_wist_utama";

    public static final String key_kode_karcis_tmbhn = "key_kode_karcis_tmbhn";
    public static final String key_nama_karcis_tmbhn = "key_nama_karcis_tmbhn";
    public static final String key_harga_karcis_wisata_tmbhn = "key_harga_karcis_wisata_tmbhn";
    public static final String key_id_tmbhn = "key_id_tmbhn";
    public static final String key_url_img_tmbhn = "key_url_img_tmbhn";

    public static final String key_kd_lokwis = "key_kd_lokwis";
    public static final String key_nm_lokwis = "key_nm_lokwis";
    public static final String key_almt_lokwis = "key_almt_lokwis";
    public static final String key_kota_lokwis = "key_kota_lokwis";

    public static final String key_url_img_lokwis = "url_img_lokwis";

    public static final String key_kd_lokPintu = "key_kd_lokPintu";
    public static final String key_nm_lokPintu = "key_nm_lokPintu";
    public static final String key_url_img_lokPintu = "key_url_img_lokPintu";

    public static final String key_kd_ksda = "key_kd_ksda";
    public static final String key_nm_lokksda = "key_nm_lokksda";

    public static final String key_mode_pembayaran = "key_mode_pembayaran";
    public static final String key_nama_pembayaran = "key_nama_pembayaran";
    public static final String key_index = "key_index";


    public static final String key_kd_ksda_horz_1 = "key_kd_ksda_horz_1";
    public static final String key_nm_ksda_horz_1 = "key_nm_ksda_horz_1";

    public static final String key_mode_pembayaran_eksp = "key_mode_pembayaran_eksp";
    public static final String key_nama_pembayaran_eksp = "key_nama_pembayaran_eksp";

    public static final String key_jns_claim_position = "key_jns_claim_position";
    public static final String key_jns_claim = "key_jns_claim";
    public static final String key_nama_claim = "key_nama_claim";

    public static final String key_kd_lok_eksp = "key_kd_lok_eksp";
    public static final String key_nm_lok_eksp = "key_nm_lok_eksp";

    public static final String key_kode_pintu_eksp = "key_kode_pintu_eksp";
    public static final String key_jns_byr_eksp = "key_jns_byr_eksp";

    public static final String key_registration_by = "key_registration_by";
    public static final String key_flag_pemesan = "key_flag_pemesan";
    public static final String key_nama = "key_nama";
    public static final String key_sellular_no = "key_sellular_no";
    public static final String key_alamat_email = "key_alamat_email";
    public static final String key_tgl_penjualan = "key_tgl_penjualan";
    public static final String key_tgl_kunjungan = "key_tgl_kunjungan";
    public static final String key_kode_lokasi_pesan = "key_kode_lokasi";
    public static final String key_id_karcis_utama = "key_id_karcis_utama";
    public static final String key_id_karcis_tambahan = "key_id_karcis_tambahan";
    public static final String key_jumlah_wisnu = "key_jumlah_wisnu";
    public static final String key_jumlah_wisman = "key_jumlah_wisman";
    public static final String key_jumlah_tambahan = "key_jumlah_tambahan";


    public static final String key_kd_lok_wist_state = "key_kd_lok_wist_state";
    public static final String key_nm_lok_wist_state = "key_nm_lok_wist_state";
    public static final String key_url_lok_wist_state = "key_url_lok_wist_state";
    public static final String key_kd_lok_pintu_state = "key_kd_lok_pintu_state";
    public static final String key_nm_lok_pintu_state = "key_nm_lok_pintu_state";
    public static final String key_url_lok_pintu_state = "key_url_lok_pintu_state";
    public static final String key_tgl_kunj_state = "key_tgl_kunj_state";
    public static final String key_id_karcis_utama_state = "key_id_karcis_utama_state";
    public static final String key_nm_karcis_utama_state = "key_nm_karcis_utama_state";
    public static final String key_url_karcis_utama_state = "key_url_karcis_utama_state";
    public static final String key_id_karcis_tmbhn_state = "key_id_karcis_tmbhn_state";
    public static final String key_nm_karcis_tmbhn_state = "key_nm_karcis_tmbhn_state";
    public static final String key_url_karcis_tmbhn_state = "key_url_karcis_tmbhn_state";
    public static final String key_jml_karcis_wisnu_state = "key_jml_karcis_wisnu_state";
    public static final String key_jml_karcis_wisman_state = "key_jml_karcis_wisman_state";
    public static final String key_ttl_wisnu_wisman_state = "key_ttl_wisnu_wisman_state";
    public static final String key_jml_karcis_tmbhn_state = "key_jml_karcis_tmbhn_state";
    public static final String key_ttl_karcis_tmbhn_state = "key_ttl_karcis_tmbhn_state";
    public static final String key_grand_ttl_state = "key_grand_ttl_state";

    public static  final String key_JmlKarcisWisnu = "key_JmlKarcisWisnu";
    public static  final String key_JmlKarcisWisman = "key_JmlKarcisWisman";
    public static  final String key_TtlKarcisWisnuWisman = "key_TtlKarcisWisnuWisman";
    public static  final String key_JmlKarcisTmbhn = "key_JmlKarcisTmbhn";
    public static  final String key_TtlKarcisTmbhn = "key_TtlKarcisTmbhn";
    public static  final String key_GrandTtlKarcis = "key_GrandTtlKarcis";

    SharedPreferences.OnSharedPreferenceChangeListener spChanged = (sharedPreferences, key) -> {
        // your stuff here
    };

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
        editor.apply();
        editor.commit();
    }

    public String getFlag() {
        return pref.getString(key_flag, "");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(is_login, false);
    }


    public void setFlag(String key_flag) {
        editor.putString(key_flag, key_flag);
        editor.commit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(is_login, isLoggedIn);
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }


    public void createSessionUserLogin(String email, String flag, String name, String id,
                                       String ket, String kd_lokasi, String nm_lokasi,
                                       String tgllhr, String jekel, String hp,
                                       String user_level ,
                                       String kode_pintu ) {
        editor.putBoolean(is_login, true);
        editor.putString(key_email, email);
        editor.putString(key_flag, flag);
        editor.putString(key_name, name);
        editor.putString(key_id, id);
        editor.putString(key_ket, ket);
        editor.putString(key_kode_lokasi, kd_lokasi);
        editor.putString(key_nama_lokasi, nm_lokasi);
        editor.putString(key_tgl_lhr, tgllhr);
        editor.putString(key_jekel, jekel);
        editor.putString(key_hp, hp);
        editor.putString(key_user_level, user_level);
        editor.putString(key_kode_pintu, kode_pintu);
        editor.commit();
        editor.apply();

    }

    public void createSessionPesankarcisWist(
            String registration_by,
            String flag_pemesan,
            String nama,
            String sellular_no,
            String alamat_email,
            String tgl_penjualan,
            String tgl_kunjungan,
            String kode_lokasi,
            String id_karcis_utama,
            String id_karcis_tambahan,
            String jumlah_wisnu,
            String jumlah_wisman,
            String jumlah_tambahan
    ) {
        editor.putString(key_registration_by, registration_by);
        editor.putString(key_flag_pemesan, flag_pemesan);
        editor.putString(key_nama, nama);
        editor.putString(key_sellular_no, sellular_no);
        editor.putString(key_alamat_email, alamat_email);
        editor.putString(key_tgl_penjualan, tgl_penjualan);
        editor.putString(key_tgl_kunjungan, tgl_kunjungan);
        editor.putString(key_kode_lokasi_pesan, kode_lokasi);
        editor.putString(key_id_karcis_utama, id_karcis_utama);
        editor.putString(key_id_karcis_tambahan, id_karcis_tambahan);
        editor.putString(key_jumlah_wisnu, jumlah_wisnu);
        editor.putString(key_jumlah_wisman, jumlah_wisman);
        editor.putString(key_jumlah_tambahan, jumlah_tambahan);
        editor.commit();
        editor.apply();

    }
    public void createSessionTglKunjungan(String tgl_kunjungan){
        editor.putString(key_tgl_kunjungan, tgl_kunjungan );
        editor.commit();
        editor.apply();
    }

    public void createSessionStateJmlKarcisWisnu(String JmlKarcisWisnu){
        editor.putString(key_JmlKarcisWisnu,         JmlKarcisWisnu);
        editor.commit();
        editor.apply();
    }
    public void createSessionStateJmlKarcisWisman(String JmlKarcisWisman){
        editor.putString(key_JmlKarcisWisman,         JmlKarcisWisman);
        editor.commit();
        editor.apply();
    }
    public void createSessionStateTtlKarcisWisnuWisman(String TtlKarcisWisnuWisman){
        editor.putString(key_TtlKarcisWisnuWisman,         TtlKarcisWisnuWisman);
        editor.commit();
        editor.apply();
    }

    public void createSessionStateJmlKarcisTmbhn(String JmlKarcisTmbhn){
        editor.putString(key_JmlKarcisTmbhn,         JmlKarcisTmbhn);
        editor.commit();
        editor.apply();
    }
    public void createSessionStateTtlKarcisTmbhn(String TtlKarcisTmbhn){
        editor.putString(key_TtlKarcisTmbhn,         TtlKarcisTmbhn);
        editor.commit();
        editor.apply();
    }
    public void createSessionStateGrandTtlKarcis(String GrandTtlKarcis){
        editor.putString(key_GrandTtlKarcis,         GrandTtlKarcis);
        editor.commit();
        editor.apply();
    }

    public void createSessionStatePesankarcisWist(
            String kd_lok_wist_state,
            String nm_lok_wist_state,
            String url_lok_wist_state,

            String kd_lok_pintu_state,
            String nm_lok_pintu_state,
            String url_lok_pintu_state,

            String tgl_kunj_state,
            String id_karcis_utama_state,
            String nm_karcis_utama_state,
            String url_karcis_utama_state,

            String id_karcis_tmbhn_state,
            String nm_karcis_tmbhn_state,
            String url_karcis_tmbhn_state,

            String jml_karcis_wisnu_state,
            String jml_karcis_wisman_state,
            String ttl_wisnu_wisman_state,
            String jml_karcis_tmbhn_state,
            String ttl_karcis_tmbhn_state,
            String grand_ttl_state

    ) {
        editor.putString(key_kd_lok_wist_state,      kd_lok_wist_state);
        editor.putString(key_nm_lok_wist_state,		 nm_lok_wist_state);
        editor.putString(key_url_lok_wist_state,     url_lok_wist_state);

        editor.putString(key_kd_lok_pintu_state,     kd_lok_pintu_state);
        editor.putString(key_nm_lok_pintu_state,     nm_lok_pintu_state);
        editor.putString(key_url_lok_pintu_state,    url_lok_pintu_state);

        editor.putString(key_tgl_kunj_state,         tgl_kunj_state);
        editor.putString(key_id_karcis_utama_state,  id_karcis_utama_state);
        editor.putString(key_nm_karcis_utama_state,  nm_karcis_utama_state);
        editor.putString(key_url_karcis_utama_state, url_karcis_utama_state);

        editor.putString(key_id_karcis_tmbhn_state,  id_karcis_tmbhn_state);
        editor.putString(key_nm_karcis_tmbhn_state,  nm_karcis_tmbhn_state);
        editor.putString(key_url_karcis_tmbhn_state, url_karcis_tmbhn_state);

        editor.putString(key_jml_karcis_wisnu_state, jml_karcis_wisnu_state);
        editor.putString(key_jml_karcis_wisman_state,jml_karcis_wisman_state);
        editor.putString(key_ttl_wisnu_wisman_state, ttl_wisnu_wisman_state);
        editor.putString(key_jml_karcis_tmbhn_state, jml_karcis_tmbhn_state);
        editor.putString(key_ttl_karcis_tmbhn_state, ttl_karcis_tmbhn_state);
        editor.putString(key_grand_ttl_state,         grand_ttl_state);
        editor.commit();
        editor.apply();

    }

    public void createSessionWisUtm(String kode_ksda, String kode_lokasi_wist_order, String kode_karcis, String nama_karcis,
                                    String kode_libur, String harga_karcis_wisata_wisnu, String harga_karcis_wisata_wisman,
                                    String harga_karcis_asuransi_wisnu, String harga_karcis_asuransi_wisman, String id_wist_order, String url_img_wist_utama) {
        editor.putString(key_kode_ksda, kode_ksda);
        editor.putString(key_kode_lokasi_wist_order, kode_lokasi_wist_order);
        editor.putString(key_kode_karcis, kode_karcis);
        editor.putString(key_nama_karcis, nama_karcis);
        editor.putString(key_kode_libur, kode_libur);
        editor.putString(key_harga_karcis_wisata_wisnu, harga_karcis_wisata_wisnu);
        editor.putString(key_harga_karcis_wisata_wisman, harga_karcis_wisata_wisman);
        editor.putString(key_harga_karcis_asuransi_wisnu, harga_karcis_asuransi_wisnu);
        editor.putString(key_harga_karcis_asuransi_wisman, harga_karcis_asuransi_wisman);
        editor.putString(key_id_wist_order, id_wist_order);
        editor.putString(key_url_img_wist_utama, url_img_wist_utama);
        editor.commit();
        editor.apply();
    }

    public void createSessionWisTmbhn(String kode_karcis_tmbhn,
                                      String nama_karcis_tmbhn,
                                      String harga_karcis_wisata_tmbhn,
                                      String id_tmbhn,
                                      String url_img_tmbhn) {
        editor.putString(key_kode_karcis_tmbhn, kode_karcis_tmbhn);
        editor.putString(key_nama_karcis_tmbhn, nama_karcis_tmbhn);
        editor.putString(key_harga_karcis_wisata_tmbhn, harga_karcis_wisata_tmbhn);
        editor.putString(key_id_tmbhn, id_tmbhn);
        editor.putString(key_url_img_tmbhn, url_img_tmbhn);

        editor.commit();
        editor.apply();
    }

    public void createSessionLokWisPesankarcisWisatawan(String kd_lok, String nm_lok,String almt_lok,String kota_lok, String url_img_lok) {
        editor.putString(key_kd_lokwis, kd_lok);
        editor.putString(key_nm_lokwis, nm_lok);
        editor.putString(key_almt_lokwis, almt_lok);
        editor.putString(key_kota_lokwis, kota_lok);
        editor.putString(key_url_img_lokwis, url_img_lok);
        editor.commit();
        editor.apply();

    }

    public void createSessionLokPintuPesankarcisWisatawan(String kd_lok, String nm_lok, String url_img_lok) {
        editor.putString(key_kd_lokPintu, kd_lok);
        editor.putString(key_nm_lokPintu, nm_lok);
        editor.putString(key_url_img_lokPintu, url_img_lok);
        editor.commit();
        editor.apply();

    }

    public void createSessionSpinnerEksp(String kode_pintu_eksp, String jns_byr_eksp) {
        editor.putString(key_kode_pintu_eksp, kode_pintu_eksp);
        editor.putString(key_jns_byr_eksp, jns_byr_eksp);
        editor.commit();
        editor.apply();
    }

    public void createSessionJnsByr(String mode_pembayaran, String nama_pembayaran) {
        editor.putString(key_mode_pembayaran, mode_pembayaran);
        editor.putString(key_nama_pembayaran, nama_pembayaran);
        editor.commit();
        editor.apply();
    }

    public void createSessionJnsByrEksp(String mode_pembayaran_eksp, String nama_pembayaran_eksp) {
        editor.putString(key_mode_pembayaran_eksp, mode_pembayaran_eksp);
        editor.putString(key_nama_pembayaran_eksp, nama_pembayaran_eksp);
        editor.commit();
        editor.apply();
    }

    public void createSessionJnsClaim(String position, String jns_claim, String nama_claim) {
        editor.putString(key_jns_claim_position, position);
        editor.putString(key_jns_claim, jns_claim);
        editor.putString(key_nama_claim, nama_claim);
        editor.commit();
        editor.apply();

    }

    public void createSessionLokWisPesankarcisWisatawanKsda(String kd_ksda, String nm_lok) {
        editor.putString(key_kd_ksda, kd_ksda);
        editor.putString(key_nm_lokksda, nm_lok);
        editor.commit();
        editor.apply();

    }

    public void createSessionForHorizontalPintu(String kd_ksda_horz_1, String nm_ksda_horz_1) {
        Log.i("", "ke kd_ksda_horz_1 " + kd_ksda_horz_1);
        editor.putString(key_kd_ksda_horz_1, kd_ksda_horz_1);
        editor.putString(key_nm_ksda_horz_1, nm_ksda_horz_1);
        editor.commit();
        editor.apply();

    }

    public void createSessionQuota(String kd_lok_eksp, String nm_lok_eksp) {
        editor.putString(key_kd_lok_eksp, kd_lok_eksp);
        editor.putString(key_nm_lok_eksp, nm_lok_eksp);
        editor.commit();
        editor.apply();

    }

    public void createSessionEksp(String kd_lok_eksp, String nm_lok_eksp) {
        editor.putString(key_kd_lok_eksp, kd_lok_eksp);
        editor.putString(key_nm_lok_eksp, nm_lok_eksp);
        editor.commit();
        editor.apply();

    }

    public void createSessionSetupPintu(int index, String kd_ksda, String nm_lok) {
        editor.putString(key_index, String.valueOf(index));
        editor.putString(key_kd_ksda, kd_ksda);
        editor.putString(key_nm_lokksda, nm_lok);
        editor.commit();
        editor.apply();

    }

    public void checkLogin() {
        if (!this.is_login()) {
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
        return pref.getBoolean(is_login, false);
    }

    public void logout() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, SigninActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }

    public void clear_key_TtlKarcisWisnuWisman() {

//        SharedPreferences settings = context.getSharedPreferences("key_TtlKarcisWisnuWisman", Context.MODE_PRIVATE);
//        settings.edit().remove("key_TtlKarcisWisnuWisman").apply();

        context.getSharedPreferences("key_TtlKarcisWisnuWisman", 0).edit().clear().apply();

    }

    public void clear_key_JmlKarcisTmbhn() {

        SharedPreferences settings = context.getSharedPreferences("key_JmlKarcisTmbhn", Context.MODE_PRIVATE);
        settings.edit().remove("key_JmlKarcisTmbhn").commit();

//        context.getSharedPreferences("key_JmlKarcisTmbhn", 0).edit().clear().apply();
//        context.getSharedPreferences("key_TtlKarcisTmbhn", 0).edit().clear().apply();
//        context.getSharedPreferences("key_GrandTtlKarcis", 0).edit().clear().apply();
    }


    public HashMap<String, String> getTglKunjungan(){
        HashMap<String ,String > data = new HashMap<String,String>();
        data.put(key_tgl_kunjungan,pref.getString(key_tgl_kunjungan,null));
        return data;
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(key_flag, pref.getString(key_flag, null));
        user.put(key_email, pref.getString(key_email, null));
        user.put(key_name, pref.getString(key_name, null));
        user.put(key_id, pref.getString(key_id, null));
        user.put(key_ket, pref.getString(key_ket, null));
        user.put(key_kode_lokasi, pref.getString(key_kode_lokasi, null));
        user.put(key_nama_lokasi, pref.getString(key_nama_lokasi, null));
        user.put(key_tgl_lhr, pref.getString(key_tgl_lhr, null));
        user.put(key_jekel, pref.getString(key_jekel, null));
        user.put(key_hp, pref.getString(key_hp, null));
        user.put(key_user_level, pref.getString(key_user_level, null));
        user.put(key_kode_pintu, pref.getString(key_kode_pintu, null));
        return user;
    }

    public HashMap<String, String> getStatePesankarcisWist() {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(key_kd_lok_wist_state, pref.getString(key_kd_lok_wist_state,null));
        user.put(key_nm_lok_wist_state, pref.getString(key_nm_lok_wist_state,null));
        user.put(key_url_lok_wist_state, pref.getString(key_url_lok_wist_state,null));

        user.put(key_kd_lok_pintu_state, pref.getString(key_kd_lok_pintu_state,null));
        user.put(key_nm_lok_pintu_state, pref.getString(key_nm_lok_pintu_state,null));
        user.put(key_url_lok_pintu_state, pref.getString(key_url_lok_pintu_state,null));

        user.put(key_tgl_kunj_state, pref.getString(key_tgl_kunj_state,null));
        user.put(key_id_karcis_utama_state, pref.getString(key_id_karcis_utama_state,null));
        user.put(key_nm_karcis_utama_state, pref.getString(key_nm_karcis_utama_state,null));
        user.put(key_url_karcis_utama_state, pref.getString(key_url_karcis_utama_state,null));

        user.put(key_id_karcis_tmbhn_state, pref.getString(key_id_karcis_tmbhn_state,null));
        user.put(key_nm_karcis_tmbhn_state, pref.getString(key_nm_karcis_tmbhn_state,null));
        user.put(key_url_karcis_tmbhn_state, pref.getString(key_url_karcis_tmbhn_state,null));

        user.put(key_jml_karcis_wisnu_state, pref.getString(key_jml_karcis_wisnu_state,null));
        user.put(key_jml_karcis_wisman_state, pref.getString(key_jml_karcis_wisman_state,null));
        user.put(key_ttl_wisnu_wisman_state, pref.getString(key_ttl_wisnu_wisman_state,null));
        user.put(key_jml_karcis_tmbhn_state, pref.getString(key_jml_karcis_tmbhn_state,null));
        user.put(key_ttl_karcis_tmbhn_state, pref.getString(key_ttl_karcis_tmbhn_state,null));
        user.put(key_grand_ttl_state, pref.getString(key_grand_ttl_state,null));

        return user;
    }

    public HashMap<String, String> getDaftarKarcisWisatawanUtama() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_kode_ksda, pref.getString(key_kode_ksda, null));
        data.put(key_kode_lokasi_wist_order, pref.getString(key_kode_lokasi_wist_order, null));
        data.put(key_kode_karcis, pref.getString(key_kode_karcis, null));
        data.put(key_nama_karcis, pref.getString(key_nama_karcis, null));
        data.put(key_kode_libur, pref.getString(key_kode_libur, null));
        data.put(key_harga_karcis_wisata_wisnu, pref.getString(key_harga_karcis_wisata_wisnu, null));
        data.put(key_harga_karcis_wisata_wisman, pref.getString(key_harga_karcis_wisata_wisman, null));
        data.put(key_harga_karcis_asuransi_wisnu, pref.getString(key_harga_karcis_asuransi_wisnu, null));
        data.put(key_harga_karcis_asuransi_wisman, pref.getString(key_harga_karcis_asuransi_wisman, null));
        data.put(key_id_wist_order, pref.getString(key_id_wist_order, null));
        data.put(key_url_img_wist_utama,pref.getString(key_url_img_wist_utama, null));
        return data;
    }

    public HashMap<String, String> getDaftarKarcisWisatawanTmbhn() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_kode_karcis_tmbhn, pref.getString(key_kode_karcis_tmbhn, null));
        data.put(key_nama_karcis_tmbhn, pref.getString(key_nama_karcis_tmbhn, null));
        data.put(key_harga_karcis_wisata_tmbhn, pref.getString(key_harga_karcis_wisata_tmbhn, null));
        data.put(key_id_tmbhn, pref.getString(key_id_tmbhn, null));
        data.put(key_url_img_tmbhn, pref.getString(key_url_img_tmbhn, null));

        return data;
    }


    public HashMap<String, String> getDataLokWisPesankarcisWisatawan() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_kd_lokwis, pref.getString(key_kd_lokwis, null));
        data.put(key_nm_lokwis, pref.getString(key_nm_lokwis, null));
        data.put(key_almt_lokwis, pref.getString(key_almt_lokwis, null));
        data.put(key_kota_lokwis, pref.getString(key_kota_lokwis, null));
        data.put(key_url_img_lokwis, pref.getString(key_url_img_lokwis, null));
        return data;
    }

    public HashMap<String, String> getDataLokPintuPesankarcisWisatawan() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_kd_lokPintu, pref.getString(key_kd_lokPintu, null));
        data.put(key_nm_lokPintu, pref.getString(key_nm_lokPintu, null));
        data.put(key_url_img_lokPintu, pref.getString(key_url_img_lokPintu, null));
        return data;
    }

    public HashMap<String, String> getDataLokWisPesankarcisWisatawanKsda() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_kd_ksda, pref.getString(key_kd_ksda, null));
        data.put(key_nm_lokksda, pref.getString(key_nm_lokksda, null));
        return data;
    }

    public HashMap<String, String> getDataSetupPintu() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_index, pref.getString(key_index, null));
        data.put(key_kd_ksda, pref.getString(key_kd_ksda, null));
        data.put(key_nm_lokksda, pref.getString(key_nm_lokksda, null));
        return data;
    }

    public HashMap<String, String> getDataJnsByr() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_mode_pembayaran, pref.getString(key_mode_pembayaran, null));
        data.put(key_nama_pembayaran, pref.getString(key_nama_pembayaran, null));
        return data;
    }

    public HashMap<String, String> getDataJnsByrEksp() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_mode_pembayaran_eksp, pref.getString(key_mode_pembayaran_eksp, null));
        data.put(key_nama_pembayaran_eksp, pref.getString(key_nama_pembayaran_eksp, null));
        return data;
    }


    public HashMap<String, String> getDataJnsClaim() {
        HashMap<String, String> data = new HashMap<String, String>();

        data.put(key_jns_claim_position, pref.getString(key_jns_claim_position, null));
        data.put(key_jns_claim, pref.getString(key_jns_claim, null));
        data.put(key_nama_claim, pref.getString(key_nama_claim, null));
        return data;
    }


    public HashMap<String, String> getDataLokEksp() {
        HashMap<String, String> data = new HashMap<String, String>();

        data.put(key_kd_lok_eksp, pref.getString(key_kd_lok_eksp, null));
        data.put(key_nm_lok_eksp, pref.getString(key_nm_lok_eksp, null));
        return data;
    }


    public HashMap<String, String> getDataHorizontalPintu() {
        HashMap<String, String> data = new HashMap<String, String>();

        data.put(key_kd_ksda_horz_1, pref.getString(key_kd_ksda_horz_1, null));
        data.put(key_nm_ksda_horz_1, pref.getString(key_nm_ksda_horz_1, null));
        return data;
    }


    public HashMap<String, String> getDataSpinnerEksp() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_kode_pintu_eksp, pref.getString(key_kode_pintu_eksp, null));
        data.put(key_jns_byr_eksp, pref.getString(key_jns_byr_eksp, null));
        return data;
    }


    public HashMap<String, String> getPesankarcisWist() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(key_registration_by, pref.getString(key_registration_by, null));
        user.put(key_flag_pemesan, pref.getString(key_flag_pemesan, null));
        user.put(key_nama, pref.getString(key_nama, null));
        user.put(key_sellular_no, pref.getString(key_sellular_no, null));
        user.put(key_alamat_email, pref.getString(key_alamat_email, null));
        user.put(key_tgl_penjualan, pref.getString(key_tgl_penjualan, null));
        user.put(key_tgl_kunjungan, pref.getString(key_tgl_kunjungan, null));
        user.put(key_kode_lokasi_pesan, pref.getString(key_kode_lokasi_pesan, null));
        user.put(key_id_karcis_utama, pref.getString(key_id_karcis_utama, null));
        user.put(key_id_karcis_tambahan, pref.getString(key_id_karcis_tambahan, null));
        user.put(key_jumlah_wisnu, pref.getString(key_jumlah_wisnu, null));
        user.put(key_jumlah_wisman, pref.getString(key_jumlah_wisman, null));
        user.put(key_jumlah_tambahan, pref.getString(key_jumlah_tambahan, null));
        return user;
    }


    public HashMap<String, String> getDataJmlKarcisWisnu() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_JmlKarcisWisnu, pref.getString(key_JmlKarcisWisnu, null));
        return data;
    }
    public HashMap<String, String> getDataJmlKarcisWisman() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_JmlKarcisWisman, pref.getString(key_JmlKarcisWisman, null));
        return data;
    }
    public HashMap<String, String> getDataTtlKarcisWisnuWisman() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_TtlKarcisWisnuWisman, pref.getString(key_TtlKarcisWisnuWisman, null));
        return data;
    }
    public HashMap<String, String> getDataJmlKarcisTmbhn() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_JmlKarcisTmbhn, pref.getString(key_JmlKarcisTmbhn, null));
        return data;
    }
    public HashMap<String, String> getDataTtlKarcisTmbhn() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_TtlKarcisTmbhn, pref.getString(key_TtlKarcisTmbhn, null));
        return data;
    }

    public HashMap<String, String> getDataGrandTtlKarcis() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(key_GrandTtlKarcis, pref.getString(key_GrandTtlKarcis, null));
        return data;
    }
}