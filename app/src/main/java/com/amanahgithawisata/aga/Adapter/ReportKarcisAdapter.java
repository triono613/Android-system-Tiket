package  com.amanahgithawisata.aga.Adapter;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Model.ModelReportKarcis;
import com.amanahgithawisata.aga.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ReportKarcisAdapter extends RecyclerView.Adapter implements DatePickerDialog.OnDateSetListener {
    BottomSheetDialog bottomSheetDialog;

    public List<ModelReportKarcis> modelReportKarcis;

    public ReportKarcisAdapter(List<ModelReportKarcis> modelReportKarcis) {
        this.modelReportKarcis = modelReportKarcis;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    }

    public class RowViewHolder extends RecyclerView.ViewHolder  {

        protected TextView nama_lokasi_wisata;
        protected TextView nama_lokasi_pintu;
        protected TextView status_karcis;
        protected TextView nama_karcis;
        protected TextView jumlah_transaksi;
        protected TextView jumlah_wisnu;
        protected TextView jumlah_tambahan;
        protected TextView tagihan_wisata;
        protected TextView tagihan_asuransi;


        public RowViewHolder(View itemView) {
            super(itemView);

            nama_lokasi_wisata = itemView.findViewById(R.id.txt_nama_lokasi_wisata);
            nama_lokasi_pintu = itemView.findViewById(R.id.txt_nama_lokasi_pintu);
            status_karcis = itemView.findViewById(R.id.txt_status_karcis);
            nama_karcis = itemView.findViewById(R.id.txt_nama_karcis);
            jumlah_transaksi = itemView.findViewById(R.id.txt_jumlah_transaksi);
            jumlah_wisnu = itemView.findViewById(R.id.txt_jumlah_wisnu);
            jumlah_tambahan = itemView.findViewById(R.id.txt_jumlah_tambahan);
            tagihan_wisata = itemView.findViewById(R.id.txt_tagihan_wisata);
            tagihan_asuransi = itemView.findViewById(R.id.txt_tagihan_asuransi);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_view_list_report_karcis_l2, parent, false);

        return new RowViewHolder(itemView);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        TextView nama_lokasi_wisata = ((RowViewHolder) holder).nama_lokasi_wisata;
        TextView nama_lokasi_pintu = ((RowViewHolder) holder).nama_lokasi_pintu;
        TextView status_karcis = ((RowViewHolder) holder).status_karcis;
        TextView nama_karcis = ((RowViewHolder) holder).nama_karcis;
        TextView jumlah_transaksi = ((RowViewHolder) holder).jumlah_transaksi;
        TextView jumlah_wisnu = ((RowViewHolder) holder).jumlah_wisnu;
        TextView jumlah_tambahan = ((RowViewHolder) holder).jumlah_tambahan;
        TextView tagihan_wisata = ((RowViewHolder) holder).tagihan_wisata;
        TextView tagihan_asuransi = ((RowViewHolder) holder).tagihan_asuransi;


        String _nama_lokasi_wisata = null;
        String _nama_lokasi_pintu = null;
        String _status_karcis = null;
        String _nama_karcis = null;

        int rowPos = rowViewHolder.getAdapterPosition();

        Log.i("","rowPos= "+rowPos);

        if (rowPos == 0) {

            rowViewHolder.nama_lokasi_wisata.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.nama_lokasi_pintu.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.status_karcis.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.nama_karcis.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.jumlah_transaksi.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.jumlah_wisnu.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.jumlah_tambahan.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.tagihan_wisata.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.tagihan_asuransi.setBackgroundResource(R.drawable.table_header_cell_bg);


            //#5CC615
            rowViewHolder.nama_lokasi_wisata.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.nama_lokasi_pintu.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.status_karcis.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.nama_karcis.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.jumlah_transaksi.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.jumlah_wisnu.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.jumlah_tambahan.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.tagihan_wisata.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.tagihan_asuransi.setTextColor(Color.parseColor("#FFFFFF"));


            rowViewHolder.nama_lokasi_wisata.setText("lokasi wisata");
            rowViewHolder.nama_lokasi_pintu.setText("Lokasi Pintu");
            rowViewHolder.status_karcis.setText("status karcis");
            rowViewHolder.nama_karcis.setText("nama karcis");
            rowViewHolder.nama_karcis.setText("nama karcis");


        } else {
            ModelReportKarcis modal = modelReportKarcis.get(rowPos-1);

            // Content Cells. Content appear here

            rowViewHolder.nama_lokasi_wisata.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.nama_lokasi_pintu.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.status_karcis.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.nama_karcis.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.jumlah_transaksi.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.jumlah_wisnu.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.jumlah_tambahan.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.tagihan_wisata.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.tagihan_asuransi.setBackgroundResource(R.drawable.table_content_cell_bg);



//            android:background="@drawable/bg_btn_blue"
            /*  this for binding data */
            rowViewHolder.nama_lokasi_wisata.setText(modal.getNama_lokasi_wisata());
            rowViewHolder.nama_lokasi_pintu.setText(modal.getNama_lokasi_pintu());
            rowViewHolder.status_karcis.setText(modal.getStatus_karcis());
            rowViewHolder.nama_karcis.setText(modal.getNama_karcis());
            rowViewHolder.jumlah_transaksi.setText(modal.getJumlah_transaksi());
            rowViewHolder.jumlah_wisnu.setText(modal.getJumlah_wisnu());
            rowViewHolder.jumlah_tambahan.setText(modal.getJumlah_tambahan());
            rowViewHolder.tagihan_wisata.setText(modal.getTagihan_wisata());
            rowViewHolder.tagihan_asuransi.setText(modal.getTagihan_asuransi());


            _nama_lokasi_wisata = modal.getNama_lokasi_wisata();
            _nama_lokasi_pintu = modal.getNama_lokasi_pintu();
            _status_karcis = modal.getStatus_karcis();
            _nama_karcis = modal.getNama_karcis();

        }

    }


    @Override
    public int getItemCount() {
        return modelReportKarcis.size()+1; // one more to add header row
    }


}
