package com.pedoran.pwpbsesi12;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener,RVAdapter.OnUserClickListener {
    RecyclerView recyclerView;
    EditText edtName,edtAge;
    Button btnSubmit;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    List<Person> listPersonInfo;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        recyclerView = view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        edtName = view.findViewById(R.id.edtname);
        edtAge = view.findViewById(R.id.edtage);
        btnSubmit = view.findViewById(R.id.btnsubmit);

        btnSubmit.setOnClickListener(this);

        setupRV();
    }

    @Override
    public void onClick(View view) {
        Log.d("DEBUG","Button Clicked");
        if(view.getId()==R.id.btnsubmit){
            DatabaseHelper db = new DatabaseHelper(context);
            Person jalmi = new Person();
            String btnStatus = btnSubmit.getText().toString();

            if(btnStatus.equals("Submit")){
                jalmi.setName(edtName.getText().toString());
                jalmi.setAge(Integer.parseInt(edtAge.getText().toString()));
                db.insert(jalmi);
            }
            if(btnStatus.equals("Update")){
                jalmi.setName(edtName.getText().toString());
                jalmi.setAge(Integer.parseInt(edtAge.getText().toString()));
                db.update(jalmi);
            }
            setupRV();
            edtName.setText("");
            edtAge.setText("");
            edtName.setFocusable(true);
            btnSubmit.setText("Submit");
        }
    }

    private void setupRV(){
        DatabaseHelper db = new DatabaseHelper(context);
        listPersonInfo = db.selectUserData();

       RVAdapter adapter = new RVAdapter(context,listPersonInfo,this);
       recyclerView.setAdapter(adapter);
       adapter.notifyDataSetChanged();
    }

    @Override
    public void onUserClick(Person person, String action) {
        if(action.equals("Edit")){
            edtName.setText(person.getName());
            edtName.setFocusable(false);
            edtAge.setText(person.getAge()+"");
            btnSubmit.setText("Update");
        }
        if (action.equals("Delete")){
            DatabaseHelper db = new DatabaseHelper(context);
            db.delete(person.getName());
            setupRV();
        }
    }
}
