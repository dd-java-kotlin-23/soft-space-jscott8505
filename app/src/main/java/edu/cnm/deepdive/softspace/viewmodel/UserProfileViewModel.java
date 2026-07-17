package edu.cnm.deepdive.softspace.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseUser;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.softspace.model.entity.UserProfile;
import edu.cnm.deepdive.softspace.repository.UserProfileRepository;
import jakarta.inject.Inject;

@HiltViewModel
public class UserProfileViewModel extends ViewModel {

  private final UserProfileRepository repository;
  private final MutableLiveData<Boolean> busy = new MutableLiveData<>(false);
  private final MutableLiveData<String> message = new MutableLiveData<>();

  @Inject
  public UserProfileViewModel(UserProfileRepository repository) {
    this.repository = repository;
  }

  public LiveData<UserProfile> getUserProfile() {
    return repository.getProfile();
  }

  public LiveData<Throwable> getError() {
    return repository.getError();
  }

  public LiveData<Boolean> getBusy() {
    return busy;
  }

  public LiveData<String> getMessage() {
    return message;
  }

  public void load(FirebaseUser user) {
    repository.loadOrCreate(user);
  }

  public void save(String displayName, String avatarUrl, String bio) {
    UserProfile current = repository.getProfile().getValue();
    if (current == null) {
      message.setValue("Profile is not loaded yet.");
      return;
    }
    current.setDisplayName(displayName);
    current.setAvatarUrl(avatarUrl);
    current.setBio(bio);
    busy.setValue(true);
    repository.save(current).addOnCompleteListener(task -> {
      busy.setValue(false);
      message.setValue(task.isSuccessful() ? "Profile saved."
          : task.getException() != null ? task.getException().getLocalizedMessage()
              : "Unable to save profile.");
    });
  }

  // Kotlin migration: use viewModelScope and a repository StateFlow. A data-class copy call such as
  // current.copy(displayName = displayName, bio = bio) avoids mutating the observed object in place.
}
