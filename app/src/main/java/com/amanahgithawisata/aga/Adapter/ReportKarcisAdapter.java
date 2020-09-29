package com.amanahgithawisata.aga.Adapter;

import android.app.DatePickerDialog;
import android.graphics.Color;
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

import java.util.List;

public class ReportKarcisAdapter extends RecyclerView.Adapter implements DatePickerDialog.OnDateSetListener {
    BottomSheetDialog bottomSheetDialog;
    public List<ModelReportKarcis> modelReportKarcisList;


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    public class RowViewHolder extends RecyclerView.ViewHolder  {

        public TextView nama_lokasi_wisata;
        public TextView nama_lokasi_pintu;
        public TextView status_karcis;
        public TextView nama_karcis;
        public TextView jumlah_transaksi;
        public TextView jumlah_wisnu;
        public TextView jumlah_tambahan;
        public TextView tagihan_wisata;
        public TextView tagihan_asuransi;


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

    public ReportKarcisAdapter(List<ModelReportKarcis> modelReportKarcisList){
        this.modelReportKarcisList = modelReportKarcisList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_view_list_report_karcis, parent, false);

        return new ReportKarcisAdapter.RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ReportKarcisAdapter.RowViewHolder rowViewHolder = (ReportKarcisAdapter.RowViewHolder) holder;

        TextView nama_lokasi_wisata = ((RowViewHolder) holder).nama_lokasi_wisata;
        TextView nama_lokasi_pintu = ((RowViewHolder) holder).nama_lokasi_pintu;
        TextView status_karcis = ((RowViewHolder) holder).status_karcis;
        TextView nama_karcis = ((RowViewHolder) holder).nama_karcis;
        TextView jumlah_transaksi = ((RowViewHolder) holder).jumlah_transaksi;
        TextView jumlah_wisnu = ((RowViewHolder) holder).jumlah_wisnu;
        TextView jumlah_tambahan = ((RowViewHolder) holder).jumlah_tambahan;
        TextView tagihan_wisata = ((RowViewHolder) holder).tagihan_wisata;
        TextView tagihan_asuransi = ((RowViewHolder) holder).tagihan_asuransi;


        String _txtId = null;
        String _txtFromDate = null;
        String _txtThruDate = null;
        String _txtJmlQuota = null;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
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


//            rowViewHolder.txtId.setText("Id");
//            rowViewHolder.txtFromDate.setText("From date");
//            rowViewHolder.txtThruDate.setText("Thru Date");
//            rowViewHolder.txtQuota.setText("Quota");


        } else {
            ModelReportKarcis modal = modelReportKarcisList.get(rowPos-1);

            // Content Cells. Content appear here



            //#5CC615
            rowViewHolder.nama_lokasi_wisata.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.nama_lokasi_pintu.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.status_karcis.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.nama_karcis.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.jumlah_transaksi.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.jumlah_wisnu.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.jumlah_tambahan.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.tagihan_wisata.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.tagihan_asuransi.setBackgroundResource(R.drawable.table_content_cell_bg);

//            _txtId = modal.getId();
//            _txtFromDate = modal.getFrom_date();
//            _txtThruDate = modal.getThru_date();
//            _txtJmlQuota = modal.getQuota();

        }




    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
