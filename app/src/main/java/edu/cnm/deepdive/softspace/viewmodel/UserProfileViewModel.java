package edu.cnm.deepdive.softspace.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.softspace.model.entity.UserProfile;
import jakarta.inject.Inject;

@HiltViewModel
public class UserProfileViewModel extends ViewModel {

  private final MutableLiveData<UserProfile> userProfile = new MutableLiveData<>();

  @Inject
  public UserProfileViewModel() {
  }

  public MutableLiveData<UserProfile> getUserProfile() {
    return userProfile;
  }

  public void saveBio(String newBio) {
    UserProfile userProfile = getUserProfile().getValue();
    if (userProfile != null) {
      userProfile.setBio(newBio);
      getUserProfile().postValue(userProfile);
    }
  }

  public void saveAvatar(String newAvatar) {
    UserProfile userProfile = getUserProfile().getValue();
    if (userProfile != null) {
      userProfile.setAvatarUrl(newAvatar);
      getUserProfile().postValue(userProfile);
    }
  }

  public void saveDisplayName(String newDisplayName) {
    UserProfile userProfile = getUserProfile().getValue();
    if (userProfile != null) {
      userProfile.setDisplayName(newDisplayName);
      getUserProfile().postValue(userProfile);
    }
  }

  public void saveEmail(String newEmail) {
    UserProfile userProfile = getUserProfile().getValue();
    if (userProfile != null) {
      userProfile.setEmail(newEmail);
      getUserProfile().postValue(userProfile);
    }
  }

  public void saveId(String newId) {
    UserProfile userProfile = getUserProfile().getValue();
    if (userProfile != null) {
      userProfile.setId(newId);
      getUserProfile().postValue(userProfile);
    }
  }

}
