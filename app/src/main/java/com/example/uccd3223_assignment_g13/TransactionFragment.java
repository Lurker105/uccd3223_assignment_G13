//package com.example.uccd3223_assignment_g13;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.LinearLayout;
//
//public class TransactionFragment extends Fragment {
//
//
//    public TransactionFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_transaction, container, false);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // set size
//        SharedPreferences pref = getActivity().getSharedPreferences("appearance",0);
//        SharedPreferences.Editor prefEd = pref.edit();
//
//        LinearLayout ll = (LinearLayout) getView().findViewById(R.id.bgTran);
//        Button bt_addexpense = (Button) getView().findViewById(R.id.addexpense);
//
//        switch (pref.getString("color","white")){
//            case "white":
//                ll.setBackgroundColor(getResources().getColor(R.color.white));
//                break;
//            case "blue":
//                ll.setBackgroundColor(getResources().getColor(R.color.blue));
//                break;
//            case "green":
//                ll.setBackgroundColor(getResources().getColor(R.color.green));
//                break;
//        }
//        bt_addexpense.setTextSize(TypedValue.COMPLEX_UNIT_SP, pref.getInt("size", 12));
//
//        bt_addexpense.setOnClickListener(new View.OnClickListener(){
//
//            public void onClick(View v){
//                Intent in_addexpense = new Intent(getActivity().getApplication(), AddExpense.class);
//                startActivity(in_addexpense);
//            }
//        });
//
//    }
//}