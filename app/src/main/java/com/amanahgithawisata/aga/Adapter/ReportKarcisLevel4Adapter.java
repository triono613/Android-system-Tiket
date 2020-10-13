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
        import com.amanahgithawisata.aga.Model.ModelReportKarcisLevel4;
        import com.amanahgithawisata.aga.R;
        import com.google.android.material.bottomsheet.BottomSheetDialog;

        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.List;
        import java.util.Locale;


public class ReportKarcisLevel4Adapter extends RecyclerView.Adapter implements DatePickerDialog.OnDateSetListener {
    BottomSheetDialog bottomSheetDialog;

    public List<ModelReportKarcisLevel4> modelReportKarcisLevel4s;

    public ReportKarcisLevel4Adapter(List<ModelReportKarcisLevel4> modelReportKarcisLevel4s) {
        this.modelReportKarcisLevel4s = modelReportKarcisLevel4s;
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

        protected TextView id;
        protected TextView nama_lokasi_regional;
        protected TextView nama_lokasi_wisata;
        protected TextView nama_lokasi_pintu;
        protected TextView status_karcis;
        protected TextView jumlah_transaksi;
        protected TextView jumlah_wisnu;
        protected TextView jumlah_tambahan;
        protected TextView tagihan_wisata;
        protected TextView tagihan_asuransi;



        public RowViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txt_id);
            nama_lokasi_regional = itemView.findViewById(R.id.txt_nama_lokasi_regional);
            nama_lokasi_wisata = itemView.findViewById(R.id.txt_nama_lokasi_wisata);
            nama_lokasi_pintu = itemView.findViewById(R.id.txt_nama_lokasi_pintu);
            status_karcis = itemView.findViewById(R.id.txt_status_karcis);
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
                inflate(R.layout.table_view_list_report_karcis_l4, parent, false);

        return new RowViewHolder(itemView);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        TextView id = ((RowViewHolder) holder).id;
        TextView nama_lokasi_regional = ((RowViewHolder) holder).nama_lokasi_regional;
        TextView nama_lokasi_wisata = ((RowViewHolder) holder).nama_lokasi_wisata;
        TextView nama_lokasi_pintu = ((RowViewHolder) holder).nama_lokasi_pintu;
        TextView status_karcis = ((RowViewHolder) holder).status_karcis;
        TextView jumlah_transaksi = ((RowViewHolder) holder).jumlah_transaksi;
        TextView jumlah_wisnu = ((RowViewHolder) holder).jumlah_wisnu;
        TextView jumlah_tambahan = ((RowViewHolder) holder).jumlah_tambahan;
        TextView tagihan_wisata = ((RowViewHolder) holder).tagihan_wisata;
        TextView tagihan_asuransi = ((RowViewHolder) holder).tagihan_asuransi;


        String _nama_lokasi_wisata = null;
        String _nama_lokasi_pintu = null;
        String _status_karcis = null;


        int rowPos = rowViewHolder.getAdapterPosition();

        Log.i("","rowPos= "+rowPos);

        if (rowPos == 0) {

            rowViewHolder.id.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.nama_lokasi_regional.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.nama_lokasi_wisata.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.nama_lokasi_pintu.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.status_karcis.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.jumlah_transaksi.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.jumlah_wisnu.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.jumlah_tambahan.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.tagihan_wisata.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.tagihan_asuransi.setBackgroundResource(R.drawable.table_header_cell_bg);


            //#5CC615
            rowViewHolder.id.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.nama_lokasi_regional.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.nama_lokasi_wisata.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.nama_lokasi_pintu.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.status_karcis.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.jumlah_transaksi.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.jumlah_wisnu.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.jumlah_tambahan.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.tagihan_wisata.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.tagihan_asuransi.setTextColor(Color.parseColor("#FFFFFF"));

            rowViewHolder.nama_lokasi_regional.setText("lokasi regional");
            rowViewHolder.nama_lokasi_wisata.setText("lokasi wisata");
            rowViewHolder.nama_lokasi_pintu.setText("Lokasi Pintu");
            rowViewHolder.status_karcis.setText("status karcis");

        } else {
            ModelReportKarcisLevel4 modal = modelReportKarcisLevel4s.get(rowPos-1);

            // Content Cells. Content appear here
            rowViewHolder.id.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.nama_lokasi_regional.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.nama_lokasi_wisata.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.nama_lokasi_pintu.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.status_karcis.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.jumlah_transaksi.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.jumlah_wisnu.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.jumlah_tambahan.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.tagihan_wisata.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.tagihan_asuransi.setBackgroundResource(R.drawable.table_content_cell_bg);



//            android:background="@drawable/bg_btn_blue"
            /*  this for binding data */

            rowViewHolder.nama_lokasi_regional.setText(modal.getNama_lokasi_regional());
            rowViewHolder.nama_lokasi_wisata.setText(modal.getNama_lokasi_wisata());
            rowViewHolder.nama_lokasi_pintu.setText(modal.getNama_lokasi_pintu());
            rowViewHolder.status_karcis.setText(modal.getStatus_karcis());
            rowViewHolder.jumlah_transaksi.setText(modal.getJumlah_transaksi());
            rowViewHolder.jumlah_wisnu.setText(modal.getJumlah_wisnu());
            rowViewHolder.jumlah_tambahan.setText(modal.getJumlah_tambahan());
            rowViewHolder.tagihan_wisata.setText(modal.getTagihan_wisata());
            rowViewHolder.tagihan_asuransi.setText(modal.getTagihan_asuransi());


            _nama_lokasi_wisata = modal.getNama_lokasi_wisata();
            _nama_lokasi_pintu = modal.getNama_lokasi_pintu();
            _status_karcis = modal.getStatus_karcis();

        }

    }


    @Override
    public int getItemCount() {
        return modelReportKarcisLevel4s.size()+1; // one more to add header row
    }


}
