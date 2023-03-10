package de.davis.passwordmanager.ui.viewmodels;

import static androidx.lifecycle.SavedStateHandleSupport.createSavedStateHandle;
import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import java.util.List;

import de.davis.passwordmanager.PasswordManagerApplication;
import de.davis.passwordmanager.security.element.SecureElement;
import de.davis.passwordmanager.ui.viewmodels.repositories.DashboardRepo;

public class DashboardViewModel extends ViewModel {

    private static final String QUERY = "query";

    private final LiveData<List<SecureElement>> filteredList;
    private final SavedStateHandle savedStateHandle;

    private final DashboardRepo dashboardRepo;

    public DashboardViewModel(DashboardRepo dashboardRepo, SavedStateHandle savedStateHandle) {
        this.savedStateHandle = savedStateHandle;
        this.dashboardRepo = dashboardRepo;

        this.filteredList = Transformations.switchMap(savedStateHandle.getLiveData(QUERY, ""), input -> {
            if(TextUtils.isEmpty(input))
                return dashboardRepo.getElements();

            return dashboardRepo.filter("%"+ input +"%");
        });
    }

    public LiveData<List<SecureElement>> getElements() {
        return dashboardRepo.getElements();
    }

    public void filter(String query){
        savedStateHandle.set(QUERY, query);
    }

    public LiveData<List<SecureElement>> getFiltered(){
        return filteredList;
    }

    public String getQuery(){
        return savedStateHandle.get(QUERY);
    }

    public static final ViewModelInitializer<DashboardViewModel> initializer = new ViewModelInitializer<>(DashboardViewModel.class, creationExtras ->
    {
        PasswordManagerApplication app = (PasswordManagerApplication) creationExtras.get(APPLICATION_KEY);
        if(app == null)
            throw new RuntimeException("app is null");

        return new DashboardViewModel(DashboardRepo.getInstance(app.getDatabase()), createSavedStateHandle(creationExtras));
    });
}
