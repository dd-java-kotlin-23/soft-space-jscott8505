package edu.cnm.deepdive.softspace.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.softspace.repository.UserProfileRepository;
import edu.cnm.deepdive.softspace.service.AuthService;
import jakarta.inject.Inject;

@HiltViewModel
public class AuthViewModel extends ViewModel {

  private final AuthService authService;
  private final UserProfileRepository profileRepository;
  private final MutableLiveData<Boolean> busy = new MutableLiveData<>(false);
  private final MutableLiveData<String> message = new MutableLiveData<>();

  @Inject
  AuthViewModel(AuthService authService, UserProfileRepository profileRepository) {
    this.authService = authService;
    this.profileRepository = profileRepository;
    if (!authService.isConfigured()) {
      message.setValue("Add app/google-services.json to connect Firebase.");
    }
  }

  public LiveData<FirebaseUser> getUser() {
    return authService.getUser();
  }

  public LiveData<Boolean> getBusy() {
    return busy;
  }

  public LiveData<String> getMessage() {
    return message;
  }

  public void signIn(String email, String password) {
    run(authService.signIn(email, password), null);
  }

  public void register(String email, String password) {
    busy.setValue(true);
    message.setValue(null);
    authService.register(email, password).addOnSuccessListener(result ->
        profileRepository.createFor(result.getUser()).addOnCompleteListener(profileTask -> {
          busy.setValue(false);
          if (!profileTask.isSuccessful()) {
            postError(profileTask.getException());
          }
        })).addOnFailureListener(exception -> {
          busy.setValue(false);
          postError(exception);
        });
  }

  public void resetPassword(String email) {
    if (email.isBlank()) {
      message.setValue("Enter your email address first.");
      return;
    }
    busy.setValue(true);
    authService.sendPasswordReset(email).addOnCompleteListener(task -> {
      busy.setValue(false);
      if (task.isSuccessful()) {
        message.setValue("Password reset email sent.");
      } else {
        postError(task.getException());
      }
    });
  }

  public void signOut() {
    profileRepository.clear();
    authService.signOut();
  }

  private void run(Task<AuthResult> task, String successMessage) {
    busy.setValue(true);
    message.setValue(null);
    task.addOnCompleteListener(result -> {
      busy.setValue(false);
      if (result.isSuccessful()) {
        message.setValue(successMessage);
      } else {
        postError(result.getException());
      }
    });
  }

  private void postError(Exception exception) {
    message.setValue(exception != null && exception.getLocalizedMessage() != null
        ? exception.getLocalizedMessage() : "Firebase operation failed.");
  }

  // Kotlin migration: viewModelScope.launch plus immutable StateFlow gives a single AuthUiState
  // containing user, busy, and message instead of coordinating three LiveData properties.
}
