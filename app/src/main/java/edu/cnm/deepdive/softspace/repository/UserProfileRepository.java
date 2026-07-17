package edu.cnm.deepdive.softspace.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import edu.cnm.deepdive.softspace.model.entity.UserProfile;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class UserProfileRepository {

  private static final String USERS = "users";
  private final FirebaseFirestore firestore;
  private final MutableLiveData<UserProfile> profile = new MutableLiveData<>();
  private final MutableLiveData<Throwable> error = new MutableLiveData<>();

  @Inject
  UserProfileRepository(Application application) {
    firestore = FirebaseApp.getApps(application).isEmpty() ? null : FirebaseFirestore.getInstance();
  }

  public LiveData<UserProfile> getProfile() {
    return profile;
  }

  public LiveData<Throwable> getError() {
    return error;
  }

  public void loadOrCreate(FirebaseUser firebaseUser) {
    if (firestore == null) {
      error.setValue(configurationException());
      return;
    }
    document(firebaseUser.getUid()).get().addOnSuccessListener(snapshot -> {
      if (snapshot.exists()) {
        profile.setValue(snapshot.toObject(UserProfile.class));
      } else {
        createFor(firebaseUser);
      }
    }).addOnFailureListener(error::setValue);
  }

  public Task<Void> createFor(FirebaseUser firebaseUser) {
    UserProfile initial = new UserProfile(
        firebaseUser.getUid(),
        firebaseUser.getDisplayName(),
        firebaseUser.getEmail(),
        firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : null);
    return save(initial);
  }

  public Task<Void> save(UserProfile updated) {
    if (firestore == null) {
      return Tasks.forException(configurationException());
    }
    return document(updated.getId()).set(updated, SetOptions.merge())
        .addOnSuccessListener(unused -> profile.setValue(updated))
        .addOnFailureListener(error::setValue);
  }

  public void clear() {
    profile.setValue(null);
    error.setValue(null);
  }

  private DocumentReference document(String uid) {
    return firestore.collection(USERS).document(uid);
  }

  private IllegalStateException configurationException() {
    return new IllegalStateException("Add app/google-services.json to connect Firebase.");
  }

  // Kotlin migration: make UserProfile a data class and expose StateFlow<UserProfile?>. Firestore
  // Tasks can be awaited inside repository suspend functions rather than returned to the caller.
}
