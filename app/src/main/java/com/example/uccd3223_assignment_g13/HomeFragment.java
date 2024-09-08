package com.example.uccd3223_assignment_g13;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.Arrays;
import java.util.Objects;


public class HomeFragment extends Fragment {

    public static String[] appendArray(String arr[], String x){
        int n = arr.length;
        int i;
        String newArr[] = new String[n + 1];
        for (i = 0; i < n; i++){
            newArr[i] = arr[i];
        }
        newArr[i] = x;
        return newArr;
    }

    public static String[] arrSort(String arr[]){
        String[] tempArr ={};
        String[] newArr ={};

        for(String arrItem : arr) {
            String arrItem_1 = arrItem.split("/")[0];
            String arrItem_2 = arrItem.split("/")[1];
            tempArr = appendArray(tempArr, arrItem_2 + "/" + arrItem_1);
        }
        Arrays.sort(tempArr);
        for(String arrItem : tempArr) {
            String arrItem_1 = arrItem.split("/")[0];
            String arrItem_2 = arrItem.split("/")[1];
            newArr = appendArray(newArr, arrItem_2 + "/" + arrItem_1);
        }
        return newArr;
    }

    public HomeFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Spinner date_sel = (Spinner) getView().findViewById(R.id.date_selected);
        String[] date_arr = {};

        ExpenseDatabase ExpenseDB = new ExpenseDatabase(getActivity());
        Cursor getMonth_Year = ExpenseDB.getMonth_Year();

        if (getMonth_Year != null){
            while (getMonth_Year.moveToNext()){
                String newDate = getMonth_Year.getString(getMonth_Year.getColumnIndex(ExpenseDatabase.DATE_COL));
                newDate = newDate.split("/", 2)[1];
                boolean found_Date = false;
                for (String s : date_arr){
                    if (Objects.equals(s, newDate)){
                        found_Date = true;
                    }
                }
                if (!found_Date){
                    date_arr = appendArray(date_arr, newDate);
                }

            }
        }
        date_arr = arrSort(date_arr);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, date_arr);
        date_sel.setAdapter(adapter);

        PieChart pieChart = (PieChart) getView().findViewById(R.id.piechart);

        ValueLineChart mCubicValueLineChart = (ValueLineChart) getView().findViewById(R.id.cubiclinechart);

        date_sel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String date_val = date_sel.getItemAtPosition(i).toString();
                //Toast.makeText(getActivity(), ""+value, Toast.LENGTH_SHORT).show();
                pieChart.clearChart();
                mCubicValueLineChart.clearChart();

                Cursor getMonthyCost = ExpenseDB.getMonthlyCost(date_val);
                LinearLayout piechart_bar = (LinearLayout) getView().findViewById(R.id.piechart_bar);

                int count = 1;
                String color_val;
                if (getMonthyCost != null) {
                    while (getMonthyCost.moveToNext()) {
                        String newPieLabel = getMonthyCost.getString(getMonthyCost.getColumnIndex(ExpenseDatabase.CATEGORY_COL));
                        Float newPieAmount = getMonthyCost.getFloat(getMonthyCost.getColumnIndex(ExpenseDatabase.AMOUNT_COL));
                        if (count == 1) {
                            color_val = "#66C2A5";
                            count++;
                        } else if (count == 2) {
                            color_val = "#FFD92F";
                            count++;
                        } else if (count == 3) {
                            color_val = "#A6D854";
                            count++;
                        } else if (count == 4) {
                            color_val = "#E78AC3";
                            count++;
                        } else if (count == 5) {
                            color_val = "#8DA0CB";
                            count++;
                        } else {
                            color_val = "#FC8D62";
                            count = 1;
                        }
                        pieChart.addPieSlice(new PieModel("" + newPieLabel, newPieAmount, Color.parseColor(color_val)));
                        LinearLayout newPieChartBar = new LinearLayout(getActivity());
                        View newPieChartBarColor = new View(getActivity());
                        TextView newPieChartBarLabel = new TextView(getActivity());

                        newPieChartBar.setOrientation(LinearLayout.HORIZONTAL);
                        newPieChartBar.setLayoutParams(new LinearLayout.LayoutParams(-1, 50));

                        newPieChartBarColor.setBackgroundColor(Color.parseColor(color_val));
                        newPieChartBarColor.setLayoutParams(new ViewGroup.LayoutParams(50, -1));

                        newPieChartBarLabel.setText(newPieLabel);
                        newPieChartBarLabel.setPadding(10, 0, 0, 0);

                        newPieChartBar.addView(newPieChartBarColor);
                        newPieChartBar.addView(newPieChartBarLabel);

                        piechart_bar.addView(newPieChartBar);
                    }
                    pieChart.startAnimation();
                }

                ValueLineSeries series = new ValueLineSeries();
                series.setColor(0xFF56B7F1);
                boolean has_31_day = false;
                boolean has_30_day = false;
                boolean has_29_day = false;
                boolean has_28_day = false;
                int[] month_with_31 = {1, 3, 5, 7, 8, 10, 12};
                int[] month_with_30 = {4, 6, 9, 11};
                if (Integer.parseInt(date_val.split("/")[0]) == 2) {
                    if (Integer.parseInt(date_val.split("/")[1]) % 4 == 0) {
                        has_29_day = true;
                    } else {
                        has_28_day = true;
                    }
                } else {
                    for (int month : month_with_31) {
                        if (Integer.parseInt(date_val.split("/")[0]) == month) {
                            has_31_day = true;
                        }
                    }
                    if (has_31_day == false) {
                        for (int month : month_with_30) {
                            if (Integer.parseInt(date_val.split("/")[0]) == month) {
                                has_30_day = true;
                            }
                        }
                    }
                }
                int day = 1;
                while (true) {
                    if(has_28_day || day > 28){
                        break;
                    } else if (has_29_day && day > 29) {
                        break;
                    } else if (has_30_day && day > 30) {
                        break;
                    } else if (has_31_day && day > 31) {
                        break;
                    }else{
                        Cursor getDailyCost = ExpenseDB.getDairyCost(day+"/"+date_val);
                        if (getDailyCost != null && getDailyCost.moveToFirst()) {
                            Float newDailyCost = getDailyCost.getFloat(getDailyCost.getColumnIndex(ExpenseDatabase.AMOUNT_COL));
                            series.addPoint(new ValueLinePoint(""+day, newDailyCost));
                        }else{
                            series.addPoint(new ValueLinePoint(""+day, 0));
                        }
                        day++;
                    }
                }

                series.addPoint(new ValueLinePoint("0", 0));
                mCubicValueLineChart.addSeries(series);
                mCubicValueLineChart.startAnimation();

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

}