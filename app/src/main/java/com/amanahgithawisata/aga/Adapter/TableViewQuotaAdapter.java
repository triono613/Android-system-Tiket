package  com.amanahgithawisata.aga.Adapter;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Model.ModelQuotaLokWis;
import com.amanahgithawisata.aga.R;
import com.amanahgithawisata.aga.TableViewQuotaActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

// implements DatePickerDialog.OnDateSetListener
public class TableViewQuotaAdapter extends RecyclerView.Adapter implements DatePickerDialog.OnDateSetListener {
    BottomSheetDialog bottomSheetDialog;

    public List<ModelQuotaLokWis> modelQuotaLokWis;

    public TableViewQuotaAdapter(List<ModelQuotaLokWis> modelQuotaLokWis) {
        this.modelQuotaLokWis = modelQuotaLokWis;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//        _tgl_kunjungan_ptgs.setText(sdf.format(c.getTime()));
    }

    public class RowViewHolder extends RecyclerView.ViewHolder  {
        protected TextView txtId;
        protected TextView txtFromDate;
        protected TextView txtThruDate;
        protected TextView txtQuota;
        protected TextView txtQuotaIn;
        protected TextView txtQuotaOut;
        protected TextView txtview_update;
        protected TextView txtview_quota;


        public RowViewHolder(View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtFromDate = itemView.findViewById(R.id.txtFromDate);
            txtThruDate = itemView.findViewById(R.id.txtThruDate);
            txtQuota = itemView.findViewById(R.id.txtQuota);
            txtQuotaIn = itemView.findViewById(R.id.txtQuotaIn);
            txtQuotaOut = itemView.findViewById(R.id.txtQuotaOut);
            txtview_update = itemView.findViewById(R.id.txtview_update);
            txtview_quota = itemView.findViewById(R.id.txtview_quota);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_view_list_quota, parent, false);

        return new RowViewHolder(itemView);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

        TextView txtId = ((RowViewHolder) holder).txtId;
        TextView txtFromDate = ((RowViewHolder) holder).txtFromDate;
        TextView txtview_update = ((RowViewHolder) holder).txtview_update;
        TextView txtview_quota = ((RowViewHolder) holder).txtview_quota;


//
//        final String _txtId = modelQuotaLokWis.get(position).getId();
//        final String _txtFromDate = modelQuotaLokWis.get(position).getFrom_date();
//        final String _txtThruDate = modelQuotaLokWis.get(position).getThru_date();
//        final String _txtJmlQuota = modelQuotaLokWis.get(position).getQuota();

         String _txtId = null;
         String _txtFromDate = null;
         String _txtThruDate = null;
         String _txtJmlQuota = null;

        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            rowViewHolder.txtId.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtFromDate.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtThruDate.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtQuota.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtQuotaIn.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtQuotaOut.setBackgroundResource(R.drawable.table_header_cell_bg);
            //#5CC615
            rowViewHolder.txtFromDate.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.txtThruDate.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.txtQuota.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.txtQuotaIn.setTextColor(Color.parseColor("#FFFFFF"));
            rowViewHolder.txtQuotaOut.setTextColor(Color.parseColor("#FFFFFF"));



            rowViewHolder.txtId.setText("Id");
            rowViewHolder.txtFromDate.setText("From date");
            rowViewHolder.txtThruDate.setText("Thru Date");
            rowViewHolder.txtQuota.setText("Quota");
//
//            final String _txtId = modal.getId();
//            final String _txtFromDate = modal.getFrom_date();
//            final String _txtThruDate = modal.getThru_date();
//            final String _txtJmlQuota = modal.getQuota();

        } else {
            ModelQuotaLokWis modal = modelQuotaLokWis.get(rowPos-1);

            // Content Cells. Content appear here
            rowViewHolder.txtId.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtFromDate.setBackgroundResource(R.drawable.bg_btn_blue);
            rowViewHolder.txtThruDate.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtQuota.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtQuotaIn.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtQuotaOut.setBackgroundResource(R.drawable.table_content_cell_bg);

//            android:background="@drawable/bg_btn_blue"

            rowViewHolder.txtId.setText(modal.getId()+"");
            rowViewHolder.txtFromDate.setText(modal.getFrom_date());
            rowViewHolder.txtThruDate.setText(modal.getThru_date()+"");
            rowViewHolder.txtQuota.setText(modal.getQuota()+"");
            rowViewHolder.txtQuotaIn.setText(modal.getQuota_in()+"");
            rowViewHolder.txtQuotaOut.setText(modal.getQuota_out()+"");



           _txtId = modal.getId();
            _txtFromDate = modal.getFrom_date();
            _txtThruDate = modal.getThru_date();
            _txtJmlQuota = modal.getQuota();

        }

        String final_txtId = _txtId;
        String final_txtFromDate = _txtFromDate;
        String final_txtThruDate = _txtThruDate;
        String final_txtJmlQuota = _txtJmlQuota;

        txtFromDate.setOnClickListener(v -> {

//            txtview_update.setText("Update ");

            View modelBottomSheet = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_bottom_sheet_edit_quota, null);
            BottomSheetDialog dialog = new BottomSheetDialog(v.getContext());

            TextView id = modelBottomSheet.findViewById(R.id.txt_id);
            TextView from_date= modelBottomSheet.findViewById(R.id.txt_from_date);
            TextView thru_date= modelBottomSheet.findViewById(R.id.txt_thru_date);
            TextView quota= modelBottomSheet.findViewById(R.id.txt_quota);


            id.setText(final_txtId);
            from_date.setText(final_txtFromDate);
            thru_date.setText(final_txtThruDate);
            quota.setText(final_txtJmlQuota);

            final String xid = id.getText().toString();

            modelBottomSheet.findViewById(R.id.btn_update).setOnClickListener(v1 -> {


                final String fromdate = from_date.getText().toString();
                            Log.i("","fromdate "+fromdate);
//                            Toast.makeText(TableViewQuotaAdapter.class,"quota > 0.", Toast.LENGTH_LONG).show();

                Intent i = new Intent(v1.getContext(), TableViewQuotaActivity.class);
                i.putExtra("_id", id.getText().toString());
                i.putExtra("_from_date", from_date.getText().toString());
                i.putExtra("_thru_date", thru_date.getText().toString());
                i.putExtra("_quota", quota.getText().toString());
                v1.getContext().startActivity(i);
                dialog.dismiss();

            });


            dialog.setContentView(modelBottomSheet);
            dialog.show();


            from_date.setOnFocusChangeListener((v13, hasFocus) -> {
                if (hasFocus){
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(v13.getContext(),
                            (view, year, monthOfYear, dayOfMonth) -> {
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat fr = new SimpleDateFormat("dd-MM-yyyy");
                                from_date.setText( year + "-" + (monthOfYear + 1)  + "-" +dayOfMonth );

                            },mYear , mMonth,mDay );
                    datePickerDialog.show();

                }
            });

            thru_date.setOnFocusChangeListener((v13, hasFocus) -> {
                if (hasFocus){
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(v13.getContext(),
                            (view, year, monthOfYear, dayOfMonth) -> {
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat fr = new SimpleDateFormat("dd-MM-yyyy");
                                thru_date.setText( year + "-" + (monthOfYear + 1)  + "-" +dayOfMonth );

                            },mYear , mMonth,mDay );
                    datePickerDialog.show();

                }
            });


        });





    }


    @Override
    public int getItemCount() {
        return modelQuotaLokWis.size()+1; // one more to add header row
    }


}
