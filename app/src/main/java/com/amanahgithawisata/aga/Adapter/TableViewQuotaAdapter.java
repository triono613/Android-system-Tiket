package  com.amanahgithawisata.aga.Adapter;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amanahgithawisata.aga.Model.ModelQuotaLokWis;
import com.amanahgithawisata.aga.R;

import java.util.List;

public class TableViewAdapter extends RecyclerView.Adapter  {

    public List<ModelQuotaLokWis> modelQuotaLokWis;

    public TableViewAdapter(List<ModelQuotaLokWis> modelQuotaLokWis) {
        this.modelQuotaLokWis = modelQuotaLokWis;
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtId;
        protected TextView txtFromDate;
        protected TextView txtThruDate;
        protected TextView txtQuota;
        protected TextView txtQuotaIn;
        protected TextView txtQuotaOut;


        public RowViewHolder(View itemView) {
            super(itemView);

            txtId = itemView.findViewById(R.id.txtId);
            txtFromDate = itemView.findViewById(R.id.txtFromDate);
            txtThruDate = itemView.findViewById(R.id.txtThruDate);
            txtQuota = itemView.findViewById(R.id.txtQuota);
            txtQuotaIn = itemView.findViewById(R.id.txtQuotaIn);
            txtQuotaOut = itemView.findViewById(R.id.txtQuotaOut);


        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_view_list, parent, false);

        return new RowViewHolder(itemView);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RowViewHolder rowViewHolder = (RowViewHolder) holder;

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
        } else {
            ModelQuotaLokWis modal = modelQuotaLokWis.get(rowPos-1);

            // Content Cells. Content appear here
            rowViewHolder.txtId.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtFromDate.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtThruDate.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtQuota.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtQuotaIn.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtQuotaOut.setBackgroundResource(R.drawable.table_content_cell_bg);

            rowViewHolder.txtId.setText(modal.getId()+"");
            rowViewHolder.txtFromDate.setText(modal.getFrom_date());
            rowViewHolder.txtThruDate.setText(modal.getThru_date()+"");
            rowViewHolder.txtQuota.setText(modal.getQuota()+"");
            rowViewHolder.txtQuotaIn.setText(modal.getQuota_in()+"");
            rowViewHolder.txtQuotaOut.setText(modal.getQuota_out()+"");

        }
    }

    @Override
    public int getItemCount() {
        return modelQuotaLokWis.size()+1; // one more to add header row
    }


}
