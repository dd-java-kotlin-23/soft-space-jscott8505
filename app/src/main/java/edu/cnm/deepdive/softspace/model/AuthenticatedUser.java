package edu.cnm.deepdive.softspace.model;

import androidx.annotation.Nullable;

/**
 * Immutable application representation of the identity supplied by an authentication provider.
 * This keeps Firebase SDK types out of view models, repositories, and UI code.
 */
public final class AuthenticatedUser {

  private final String id;
  private final String email;
  private final String displayName;
  private final String avatarUrl;

  public AuthenticatedUser(String id, @Nullable String email, @Nullable String displayName,
      @Nullable String avatarUrl) {
    this.id = id;
    this.email = email;
    this.displayName = displayName;
    this.avatarUrl = avatarUrl;
  }

  public String getId() {
    return id;
  }

  @Nullable
  public String getEmail() {
    return email;
  }

  @Nullable
  public String getDisplayName() {
    return displayName;
  }

  @Nullable
  public String getAvatarUrl() {
    return avatarUrl;
  }

  // Kotlin migration: this class becomes a data class whose nullable String properties have the
  // same types. No Firebase import should be added to that Kotlin model.
}
