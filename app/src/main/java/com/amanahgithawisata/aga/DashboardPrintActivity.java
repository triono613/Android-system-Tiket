package com.amanahgithawisata.aga;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.printable.Printable;
import com.mazenrashed.printooth.data.printable.RawPrintable;
import com.mazenrashed.printooth.data.printable.TextPrintable;
import com.mazenrashed.printooth.data.printer.DefaultPrinter;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;
import com.mazenrashed.printooth.utilities.PrintingCallback;

import java.util.ArrayList;

public class DashboardPrintActivity extends AppCompatActivity {
    private Printing printing = null;
    PrintingCallback printingCallback=null;
    Button pairUnpair, printStruk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_print);
        Printooth.INSTANCE.init(this);

        pairUnpair = findViewById(R.id.btnPairUnpair);
        printStruk = findViewById(R.id.btnPrint);

        if (Printooth.INSTANCE.hasPairedPrinter()){
            printing=Printooth.INSTANCE.printer();
        }

        initViews();
        initListeners();


        pairUnpair.setOnClickListener(v -> {
            if (Printooth.INSTANCE.hasPairedPrinter()) {Printooth.INSTANCE.removeCurrentPrinter();
            } else {
                startActivityForResult(new Intent(this, ScanningActivity.class ),ScanningActivity.SCANNING_FOR_PRINTER);
                initViews();
            }
        });

        printStruk.setOnClickListener(v -> {
                if(!Printooth.INSTANCE.hasPairedPrinter()){
                    startActivityForResult(new Intent(this,ScanningActivity.class),ScanningActivity.SCANNING_FOR_PRINTER);
                } else {
                this.printFn();
                }
        });

    }

    private void printFn() {
        if (printing!=null)
            printing.print(getSomePrintables());

    }

    private ArrayList<Printable> getSomePrintables() {
        ArrayList<Printable> p =new ArrayList<>();
        p.add(new RawPrintable.Builder(new byte[]{27,100,4}).build());
        p.add( (new TextPrintable.Builder())
                .setText("Assalamualaikum warahmatullahi wabarakatuh")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());

        p.add( (new TextPrintable.Builder())
                .setText("Terima kasih atas Booking Online KPH Kedu Utara dari Petugas yang telah dilakukan.")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        p.add( (new TextPrintable.Builder())
                .setText("Bersama ini konfirmasi data Booking Online sebagai berikut :")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());

        p.add( (new TextPrintable.Builder())
                .setText("Nomor Karcis Virtual\t:\t0000220000000165")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        p.add( (new TextPrintable.Builder())
                .setText("Nama Ketua Kelompok\t:\ttriono")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        p.add( (new TextPrintable.Builder())
                .setText("Email Ketua Kelompok\t:\ttriono.triono1@gmail.com")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        p.add( (new TextPrintable.Builder())
                .setText("No HP : 089508611088")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        p.add( (new TextPrintable.Builder())
                .setText("Jumlah peserta Wisnu : 1")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        p.add( (new TextPrintable.Builder())
                .setText("Jumlah peserta Wisman : 0")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        p.add( (new TextPrintable.Builder())
                .setText("Jumlah peserta : 1")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        p.add( (new TextPrintable.Builder())
                .setText("Jumlah Karcis Tambahan : 1")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        p.add( (new TextPrintable.Builder())
                .setText("Jumlah Hari : 2 ")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());

        p.add( (new TextPrintable.Builder())
                .setText("Tgl. Pembelian : 02-12-2020")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        p.add( (new TextPrintable.Builder())
                .setText("Tgl. Kunjungan : 02-12-2020")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        p.add( (new TextPrintable.Builder())
                .setText("Tgl. Pulang : 05-12-2020")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());
        p.add( (new TextPrintable.Builder())
                .setText("Terimakasih telah melakukan pembayaran secara CASH :")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());


        p.add( (new TextPrintable.Builder())
                .setText(" Tanggal : 02-12-2020 WIB")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_60())
                .build());
        p.add( (new TextPrintable.Builder())
                .setText(" Nomor Karcis Virtual : 0000220000000165")
                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setNewLinesAfter(1)
                .build());

//        p.add( (new TextPrintable.Builder())
//                .setText("Hello World")
//                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_60())
//                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
//                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
//                .setUnderlined(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
//                .setNewLinesAfter(1)
//                .build());

        return  p;
    }
/*
    Assalamualaikum warahmatullahi wabarakatuh
    Terima kasih atas Booking Online KPH Kedu Utara dari Petugas yang telah dilakukan. Bersama ini konfirmasi data Booking Online sebagai berikut :

    Nomor Karcis Virtual	:	0000220000000165
    Nama Ketua Kelompok	:	gg
    Email Ketua Kelompok	:	triono.triono1@gmail.com
    No HP	:	88888
    Jumlah peserta Wisnu	:	1
    Jumlah peserta Wisman	:	0
    Jumlah Peserta	:	1
    Jumlah Karcis Tambahan	:	1
    Jumlah Hari	:	2
    Tgl. Pembelian	:	02-12-2020
    Tgl. Kunjungan	:	02-12-2020
    Tgl. Pulang	:	03-12-2020
    Terimakasih telah melakukan pembayaran secara CASH :

    Tanggal : 02-12-2020 WIB
    Nomor Karcis Virtual : 0000220000000165
    Atas Nama gg
    Alamat e-Mail triono.triono1@gmail.com
    No. Sellular 88888
    BUKTI PEMBAYARAN
    Jumlah Yang telah Dibayar Rp. 12.000,00

    Mohon tidak meng-hapus notifikasi e-mail ini, notifikasi email ini sebagai bukti bahwa sudah membayar dipintu masuk
    PEMBAYARAN KARCIS VIRTUAL INI DILAKUKAN SECARA TUNAI/CASH.
    Terima kasih telah melakukan pembayaran secara TUNAI, lain waktu boleh dicoba dengan VIRTUAL ACCOUNT.

    Wassalamualaikum warahmatullahi wabarakatuh


    KPH Kedu Utara
    KPH Kedu Utara
    Jawa Tengah
*/

    private void initListeners() {
        if (printing!=null && printingCallback==null) {
            Log.d("xxx", "initListeners ");
            printingCallback = new PrintingCallback() {

                public void connectingWithPrinter() {
                    Toast.makeText(getApplicationContext(), "Connecting with printer", Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "Connecting");
                }
                public void printingOrderSentSuccessfully() {
                    Toast.makeText(getApplicationContext(), "printingOrderSentSuccessfully", Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "printingOrderSentSuccessfully");
                }
                public void connectionFailed(@NonNull String error) {
                    Toast.makeText(getApplicationContext(), "connectionFailed :"+error, Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "connectionFailed : "+error);
                }
                public void onError(@NonNull String error) {
                    Toast.makeText(getApplicationContext(), "onError :"+error, Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "onError : "+error);
                }
                public void onMessage(@NonNull String message) {
                    Toast.makeText(getApplicationContext(), "onMessage :" +message, Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "onMessage : "+message);
                }
            };

            Printooth.INSTANCE.printer().setPrintingCallback(printingCallback);
        }
    }

    private void initViews() {

       if (Printooth.INSTANCE.getPairedPrinter()!=null)
                ((Button) findViewById(R.id.btnPairUnpair)).setText(
                        (Printooth.INSTANCE.hasPairedPrinter())?("Un-pair "+ Printooth.INSTANCE.getPairedPrinter().getName()):"Pair with printer");
        }

}