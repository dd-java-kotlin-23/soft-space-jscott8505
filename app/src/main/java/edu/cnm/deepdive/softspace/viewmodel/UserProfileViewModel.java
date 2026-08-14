package edu.cnm.deepdive.softspace.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.softspace.model.AuthenticatedUser;
import edu.cnm.deepdive.softspace.model.entity.Post;
import edu.cnm.deepdive.softspace.model.entity.UserProfile;
import edu.cnm.deepdive.softspace.repository.UserProfileRepository;
import edu.cnm.deepdive.softspace.repository.PostRepository;
import edu.cnm.deepdive.softspace.service.AuthService;
import jakarta.inject.Inject;
import java.util.List;

@HiltViewModel
public class UserProfileViewModel extends ViewModel {

  private final UserProfileRepository repository;
  private final PostRepository postRepository;
  private final AuthService authService;
  private final MutableLiveData<Boolean> busy = new MutableLiveData<>(false);
  private final MutableLiveData<String> message = new MutableLiveData<>();
  private final MediatorLiveData<List<Post>> posts = new MediatorLiveData<>();
  private LiveData<List<Post>> postSource;

  @Inject
  public UserProfileViewModel(UserProfileRepository repository, PostRepository postRepository,
      AuthService authService) {
    this.repository = repository;
    this.postRepository = postRepository;
    this.authService = authService;
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

  public LiveData<List<Post>> getPosts() {
    return posts;
  }

  public void load(AuthenticatedUser user) {
    repository.loadOrCreate(user);
    loadPosts(user.getId());
  }

  public void load(String userId) {
    if (userId == null || userId.isBlank()) {
      message.setValue("A user profile is required.");
      posts.setValue(List.of());
      return;
    }
    repository.load(userId);
    loadPosts(userId);
  }

  /** Loads the signed-in user's profile when navigation did not supply a profile ID. */
  public void loadCurrentUser() {
    AuthenticatedUser user = authService.getUser().getValue();
    if (user == null) {
      message.setValue("Sign in to view your profile.");
      posts.setValue(List.of());
      return;
    }
    load(user);
  }

  private void loadPosts(String userId) {
    if (postSource != null) {
      posts.removeSource(postSource);
    }
    postSource = postRepository.postsByAuthor(userId);
    posts.addSource(postSource, posts::setValue);
  }


  public void save(String displayName, String profilePicture, String bio) {
    UserProfile current = repository.getProfile().getValue();
    if (current == null) {
      message.setValue("Profile is not loaded yet.");
      return;
    }
    current.setDisplayName(displayName);
    current.setProfilePicture(profilePicture);
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
