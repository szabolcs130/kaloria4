package com.example.kaloria4.ui.diagram;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kaloria4.R;
import com.example.kaloria4.databinding.FragmentDiagramBinding;
import com.example.kaloria4.model.EtkezesOsszevont;
import com.example.kaloria4.viewmodel.EtkezesViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DiagramFragment extends Fragment {
    private EtkezesViewModel etkezesViewModel;

    private FragmentDiagramBinding binding;
    BarChart barChart;
    private BarData barData;
    ArrayList<BarEntry> entries;
    double maxKaloria;
    private MenuItem item;
    private MenuItem item2;
    double maxKaloriaHeti;
    String xNev;
    String formattedDate;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDiagramBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setHasOptionsMenu(true);

        etkezesViewModel = new ViewModelProvider(this).get(EtkezesViewModel.class);
        barChart = root.findViewById(R.id.chart);
        barChart.getAxisRight().setDrawLabels(false);

        entries = new ArrayList<>();
        NapiKaloriaDiagram();




        return root;
    }
    public void NapiKaloriaDiagram(){
        if (barChart!=null) {
            barChart.clear();
        }
        if (entries!=null) {
            entries.clear();
        }
        etkezesViewModel.getAllEtkezesMaxKaloriaMax().observe(getViewLifecycleOwner(), etkezesek -> {
            maxKaloria=etkezesek.get(0).getKaloria();
            // System.out.println("Max kaloria "+maxKaloria);
        });
        etkezesViewModel.getAllEtkezesMaxKaloria().observe(getViewLifecycleOwner(), etkezesek -> {

            for (int i = 0; i < etkezesek.size(); i++) {
                EtkezesOsszevont fogas = etkezesek.get(i);
           /* if (maxKaloria<fogas.getKaloria()){
                maxKaloria=fogas.getKaloria();
            }*/
                entries.add(new BarEntry(i, (float) fogas.getKaloria()));
                // System.out.println("Kaloriak: "+fogas.getKaloria());
            }
            YAxis yAxis = barChart.getAxisLeft();
            yAxis.setAxisMaximum((float)maxKaloria);
            yAxis.setAxisMinimum(0f);
            yAxis.setAxisLineWidth(2f);
            yAxis.setAxisLineColor(Color.BLACK);
            yAxis.setLabelCount(10);
            BarDataSet dataSet = new BarDataSet(entries, "Nap Kaloria Adatok");
            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            barData = new BarData(dataSet);
            barChart.setData(barData);
            List<String> xValues = new ArrayList<>();
            for (EtkezesOsszevont etkezes : etkezesek) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(etkezes.getEtkezesIdopontIdo());
                xValues.add(String.valueOf(formattedDate));
            }
            barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues));
            barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            barChart.getXAxis().setGranularity(1f);
            barChart.getXAxis().setGranularityEnabled(true);

            barChart.getDescription().setEnabled(false);
            barChart.invalidate();
        });
    }
    public void HetiKaloriaDiagram(){
        if (barChart!=null) {
            barChart.clear();
        }
        if (entries!=null) {
            entries.clear();
        }
       /* etkezesViewModel.getAllEtkezesGroupByHet().observe(getViewLifecycleOwner(), etkezesek -> {
            for (int i=0;i<etkezesek.size();i++){
                System.out.println("Eprecske");
                System.out.println(etkezesek.get(i));
            }
        });
        etkezesViewModel.getAllEtkezesMaxKaloriaMax().observe(getViewLifecycleOwner(), etkezesek -> {
            maxKaloria=etkezesek.get(0).getKaloria();
            // System.out.println("Max kaloria "+maxKaloria);
        });*/
        etkezesViewModel.getAllEtkezesGroupByHet().observe(getViewLifecycleOwner(), etkezesek -> {

            for (int i = 0; i < etkezesek.size(); i++) {
                EtkezesOsszevont fogas = etkezesek.get(i);
                if (maxKaloriaHeti<fogas.getKaloria()){
                    maxKaloriaHeti=fogas.getKaloria();
                }
                entries.add(new BarEntry(i, (float) fogas.getKaloria()));
                // System.out.println("Kaloriak: "+fogas.getKaloria());
            }
            YAxis yAxis = barChart.getAxisLeft();
            yAxis.setAxisMaximum((float)maxKaloriaHeti);
            yAxis.setAxisMinimum(0f);
            yAxis.setAxisLineWidth(2f);
            yAxis.setAxisLineColor(Color.BLACK);
            yAxis.setLabelCount(10);
            BarDataSet dataSet = new BarDataSet(entries, "Nap Kaloria Adatok");
            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            barData = new BarData(dataSet);
            barChart.setData(barData);
            List<String> xValues = new ArrayList<>();
            for (EtkezesOsszevont etkezes : etkezesek) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Calendar calendar = Calendar.getInstance();
                Date date = new Date(etkezes.getEtkezesIdopontIdo());
                calendar.setTime(date);
                formattedDate = sdf.format(date);
                int honaphete = calendar.get(Calendar.WEEK_OF_MONTH);
                 xNev = "("+formattedDate+")"+ " " + honaphete;
                xValues.add(xNev);
            }
            barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues));
            barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            barChart.getXAxis().setGranularity(1f);
            barChart.getXAxis().setGranularityEnabled(true);

            barChart.getDescription().setEnabled(false);
            barChart.invalidate();
        });
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        item = menu.findItem(R.id.action_NapiKaloria);
        item.setVisible(true);
        item.setOnMenuItemClickListener(v->{
            NapiKaloriaDiagram();
            return true;
        });
        item2 = menu.findItem(R.id.action_HetiKaloria);
        item2.setVisible(true);
        item2.setOnMenuItemClickListener(v->{
            HetiKaloriaDiagram();
            return true;
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
