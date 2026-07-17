package edu.cnm.deepdive.softspace.service;

import android.app.Application;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import edu.cnm.deepdive.softspace.model.AuthenticatedUser;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class AuthService {

  private final FirebaseAuth auth;
  private final MutableLiveData<AuthenticatedUser> user = new MutableLiveData<>();

  @Inject
  AuthService(Application application) {
    auth = FirebaseApp.getApps(application).isEmpty() ? null : FirebaseAuth.getInstance();
    if (auth != null) {
      user.setValue(map(auth.getCurrentUser()));
      auth.addAuthStateListener(instance -> user.postValue(map(instance.getCurrentUser())));
    }
  }

  public LiveData<AuthenticatedUser> getUser() {
    return user;
  }

  public boolean isConfigured() {
    return auth != null;
  }

  public Task<AuthenticatedUser> signIn(String email, String password) {
    return auth != null
        ? auth.signInWithEmailAndPassword(email, password)
            .continueWith(task -> map(task.getResult().getUser()))
        : Tasks.forException(configurationException());
  }

  public Task<AuthenticatedUser> register(String email, String password) {
    return auth != null
        ? auth.createUserWithEmailAndPassword(email, password)
            .continueWith(task -> map(task.getResult().getUser()))
        : Tasks.forException(configurationException());
  }

  public Task<Void> sendPasswordReset(String email) {
    return auth != null
        ? auth.sendPasswordResetEmail(email)
        : Tasks.forException(configurationException());
  }

  public void signOut() {
    if (auth != null) {
      auth.signOut();
    }
  }

  private IllegalStateException configurationException() {
    return new IllegalStateException("Add app/google-services.json to connect Firebase.");
  }

  @Nullable
  private AuthenticatedUser map(@Nullable FirebaseUser firebaseUser) {
    if (firebaseUser == null) {
      return null;
    }
    return new AuthenticatedUser(
        firebaseUser.getUid(),
        firebaseUser.getEmail(),
        firebaseUser.getDisplayName(),
        firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : null);
  }

  // Kotlin migration: expose authStateFlow/callbackFlow and make these operations suspend functions
  // with kotlinx-coroutines-play-services await(), eliminating UI-facing Task callbacks.
}
