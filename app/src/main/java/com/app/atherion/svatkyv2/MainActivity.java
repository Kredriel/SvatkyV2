package com.app.atherion.svatkyv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.library.FocusResizeScrollListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int PoradiDne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        createCustomAdapter(recyclerView, linearLayoutManager);

        PoradiDne = GetCountOfDate();

        recyclerView.scrollToPosition(PoradiDne - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void createCustomAdapter(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        CustomAdapter customAdapter = new CustomAdapter(this, (int) getResources().getDimension(R.dimen.custom_item_height));
        customAdapter.addItems(addItems());
        if (recyclerView != null) {
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(false);
            recyclerView.setAdapter(customAdapter);
            recyclerView.addOnScrollListener(new FocusResizeScrollListener<>(customAdapter, linearLayoutManager));
        }
    }

    private List<CustomObject> addItems() {

        String[] PoleJmen = getResources().getStringArray(R.array.names);

        int ColorDrawableID = 0;

        String dt = "01-01";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<CustomObject> items = new ArrayList<>();
        for(int i = 0; i < 365; i++, ColorDrawableID++){
            items.add(new CustomObject(c.get(Calendar.DAY_OF_MONTH) + "." + String.format(Locale.getDefault(),"%tB",c), PoleJmen[i], getResources().getIdentifier("b" + ColorDrawableID, "drawable", getPackageName())));
            c.add(Calendar.DATE, 1);  // number of days to add
            dt = sdf.format(c.getTime());  // dt is now the new date

            if(ColorDrawableID == 1)
                ColorDrawableID = -1;


        }
        return items;
    }

    public int GetCountOfDate(){
        Calendar C = Calendar.getInstance();
        //int Month = C.get(Calendar.MONTH);
        int Day = C.get(Calendar.DAY_OF_YEAR) - 1;
        return Day;
    }
}