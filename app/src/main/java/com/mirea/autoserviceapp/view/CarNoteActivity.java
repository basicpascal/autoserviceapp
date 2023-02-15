package com.mirea.autoserviceapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mirea.autoserviceapp.adapter.CarNoteAdapter;
import com.mirea.autoserviceapp.data.CarNote;
import com.mirea.autoserviceapp.databinding.ActivityTaxiNoteBinding;
import com.mirea.autoserviceapp.viewmodel.TaxiNoteViewModel;

import java.util.List;

public class CarNoteActivity extends AppCompatActivity {

    private ActivityTaxiNoteBinding binding;

    private CarNoteAdapter carNoteAdapter = new CarNoteAdapter();

    private TaxiNoteViewModel taxiNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaxiNoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        taxiNoteViewModel = new ViewModelProvider(this).get(TaxiNoteViewModel.class);

        binding.recyclerViewCar.setAdapter(carNoteAdapter);

        taxiNoteViewModel.getCarNotes().observe(this, new Observer<List<CarNote>>() {
            @Override
            public void onChanged(List<CarNote> carNotes) {
                carNoteAdapter.setCarNotes(carNotes);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                    0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
                ) {
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target
                    ) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        CarNote carNote = carNoteAdapter.getCarNotes().get(position);
                        taxiNoteViewModel.remove(carNote);
                    }
                });
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewCar);

        binding.buttonAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddCarNoteActivity.newIntent(CarNoteActivity.this);
                startActivity(intent);
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, CarNoteActivity.class);
    }
}