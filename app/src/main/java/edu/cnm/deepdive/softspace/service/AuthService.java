package edu.cnm.deepdive.softspace.service;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class AuthService {

  private final FirebaseAuth auth;
  private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

  @Inject
  AuthService(Application application) {
    auth = FirebaseApp.getApps(application).isEmpty() ? null : FirebaseAuth.getInstance();
    if (auth != null) {
      user.setValue(auth.getCurrentUser());
      auth.addAuthStateListener(instance -> user.postValue(instance.getCurrentUser()));
    }
  }

  public LiveData<FirebaseUser> getUser() {
    return user;
  }

  public boolean isConfigured() {
    return auth != null;
  }

  public Task<AuthResult> signIn(String email, String password) {
    return auth != null
        ? auth.signInWithEmailAndPassword(email, password)
        : Tasks.forException(configurationException());
  }

  public Task<AuthResult> register(String email, String password) {
    return auth != null
        ? auth.createUserWithEmailAndPassword(email, password)
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

  // Kotlin migration: expose authStateFlow/callbackFlow and make these operations suspend functions
  // with kotlinx-coroutines-play-services await(), eliminating UI-facing Task callbacks.
}
