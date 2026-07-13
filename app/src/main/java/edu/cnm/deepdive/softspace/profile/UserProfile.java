package edu.cnm.deepdive.softspace.profile;

public class UserProfile {

  private String id;
  public String displayName;
  public String email;
  public String avatarUrl;
  public String bio;

  public UserProfile() {
  }

  public UserProfile(String id, String displayName, String email, String avatarUrl) {
    this.id = id;
    this.displayName = displayName;
    this.email = email;
    this.avatarUrl = avatarUrl;
    this.bio = bio;

  }

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public String getDisplayName() {
    return displayName;
  }
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }
  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }

  public String getBio() {
    return bio;
  }
  public void setBio(String bio) {
    this.bio = bio;
  }


public void updateUserProfile(UserProfile userProfile) {
  this.displayName = userProfile.displayName;
  this.email = userProfile.email;
  this.avatarUrl = userProfile.avatarUrl;
  this.bio = userProfile.bio;
}

}
