package com.mirea.autoserviceapp.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mirea.autoserviceapp.data.CarNote;
import com.mirea.autoserviceapp.data.CarNoteDatabase;

public class AddTaxiNoteViewModel extends AndroidViewModel {

    private CarNoteDatabase carNoteDatabase;

    private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();

    private Handler handler = new Handler(Looper.getMainLooper());

    public AddTaxiNoteViewModel(@NonNull Application application) {
        super(application);
        carNoteDatabase = CarNoteDatabase.getInstance(application);
    }

    public MutableLiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }

    public void add(CarNote carNote){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                carNoteDatabase.carNoteDao().add(carNote);
                shouldCloseScreen.postValue(true);
            }
        });
        thread.start();
    }
}
