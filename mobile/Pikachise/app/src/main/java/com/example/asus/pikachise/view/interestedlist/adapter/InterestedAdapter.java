//package com.example.asus.pikachise.view.interestedlist.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.asus.pikachise.R;
//import com.example.asus.pikachise.view.franchisedetail.activity.FranchiseDetail;
//import com.example.asus.pikachise.presenter.helper.DatabaseHelper;
//import com.example.asus.pikachise.model.Franchise;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
///**
// * Created by WilliamSumitro on 01/10/2017.
// */
//
//public class InterestedAdapter extends RecyclerView.Adapter<InterestedAdapter.ViewHolder> {
//    private static final String EXTRA_ID = "EXTRA_ID";
//    private static final String EXTRA_NAMA = "EXTRA_NAMA";
//    private static final String EXTRA_KETERANGAN = "EXTRA_KETERANGAN";
//    private static final String EXTRA_JENIS = "EXTRA_JENIS";
//    private static final String EXTRA_KATEGORI = "EXTRA_KATEGORI";
//    private static final String EXTRA_BERDIRI = "EXTRA_BERDIRI";
//    private static final String EXTRA_INVESTASI = "EXTRA_INVESTASI";
//    private static final String EXTRA_WEBSITE= "EXTRA_WEBSITE";
//    private static final String EXTRA_ALAMAT = "EXTRA_ALAMAT";
//    private static final String EXTRA_LOKASI = "EXTRA_LOKASI";
//    private static final String EXTRA_TELEPON = "EXTRA_TELEPON";
//    private static final String EXTRA_EMAIL = "EXTRA_EMAIL";
//    private static final String EXTRA_GAMBAR = "EXTRA_GAMBAR";
//    private List<Franchise> franchiseList;
//    private LayoutInflater inflater;
//    private Context context;
//    private DatabaseHelper db;
//    public InterestedAdapter(List<Franchise> franchises, Context c){
//        this.franchiseList = franchises;
//        this.inflater = LayoutInflater.from(c);
//    }
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout.item_interestedlist, parent, false);
//        context = parent.getContext();
//        db = new DatabaseHelper(context);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        final Franchise franchisor = franchiseList.get(position);
//        final long hit = db.getSameDFinOutlet(String.valueOf(franchisor.getId()));
//        holder.namafranchise.setText(franchisor.getNama_franchise());
//        holder.namaptfranchise.setText(franchisor.getNama_pt_franchisor());
//        holder.nfranchisee.setText(String.valueOf(hit));
//        holder.container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, FranchiseDetail.class);
//                intent.putExtra(EXTRA_ID,franchisor.getId());
//                intent.putExtra(EXTRA_NAMA,franchisor.getNama_franchise());
//                intent.putExtra(EXTRA_KETERANGAN,franchisor.getKeterangan());
//                intent.putExtra(EXTRA_JENIS,franchisor.getJenis());
//                intent.putExtra(EXTRA_KATEGORI,franchisor.getKategori());
//                intent.putExtra(EXTRA_BERDIRI,franchisor.getBerdiri_sejak());
//                intent.putExtra(EXTRA_INVESTASI,franchisor.getInvestasi());
//                intent.putExtra(EXTRA_WEBSITE,franchisor.getWebsite());
//                intent.putExtra(EXTRA_ALAMAT,franchisor.getAlamat());
//                intent.putExtra(EXTRA_LOKASI,franchisor.getLokasi());
//                intent.putExtra(EXTRA_TELEPON,franchisor.getTelepon());
//                intent.putExtra(EXTRA_EMAIL,franchisor.getEmail());
//                intent.putExtra(EXTRA_GAMBAR,franchisor.getGambar_franchise());
//                context.startActivity(intent);
//            }
//        });
//        Picasso.with(context).load("file:///android_asset/"+franchiseList.get(position).getGambar_franchise()).into(holder.gambarbanner);
//    }
//
//    @Override
//    public int getItemCount() {
//        return franchiseList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView namafranchise, namaptfranchise, nfranchisee;
//        private ImageView gambarbanner;
//        private View container;
//        public ViewHolder(View itemView) {
//            super(itemView);
//            namafranchise = (TextView) itemView.findViewById(R.id.topitem_namefranchise);
//            namaptfranchise = (TextView) itemView.findViewById(R.id.topitem_namecompany);
//            nfranchisee = (TextView) itemView.findViewById(R.id.topitem_totalfranchisee);
//            gambarbanner = (ImageView) itemView.findViewById(R.id.topitem_image);
//            container = itemView.findViewById(R.id.topitem_conitemroot);
//        }
//    }
//}
