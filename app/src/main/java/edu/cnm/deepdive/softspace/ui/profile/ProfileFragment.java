package edu.cnm.deepdive.softspace.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.softspace.R;
import edu.cnm.deepdive.softspace.databinding.FragmentProfileBinding;
import edu.cnm.deepdive.softspace.model.entity.UserProfile;
import edu.cnm.deepdive.softspace.viewmodel.AuthViewModel;
import edu.cnm.deepdive.softspace.viewmodel.UserProfileViewModel;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {

  private FragmentProfileBinding binding;
  private UserProfileViewModel profileViewModel;
  private AuthViewModel authViewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentProfileBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    profileViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
    authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

    authViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
      if (user == null) {
        Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_signInFragment);
      } else {
        binding.email.setText(user.getEmail());
        profileViewModel.load(user);
      }
    });
    profileViewModel.getUserProfile().observe(getViewLifecycleOwner(), this::showProfile);
    profileViewModel.getError().observe(getViewLifecycleOwner(), error -> {
      if (error != null) {
        Snackbar.make(view, error.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
      }
    });
    profileViewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
      if (message != null) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
      }
    });
    profileViewModel.getBusy().observe(getViewLifecycleOwner(), busy ->
        binding.saveButton.setEnabled(!Boolean.TRUE.equals(busy)));

    binding.saveButton.setOnClickListener(unused -> profileViewModel.save(
        text(binding.displayName), text(binding.avatarUrl), text(binding.bio)));
    binding.signOutButton.setOnClickListener(unused -> authViewModel.signOut());
  }

  private void showProfile(UserProfile profile) {
    if (profile != null) {
      binding.displayName.setText(profile.getDisplayName());
      binding.avatarUrl.setText(profile.getAvatarUrl());
      binding.bio.setText(profile.getBio());
    }
  }

  private String text(android.widget.EditText input) {
    return input.getText() != null ? input.getText().toString().trim() : "";
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  // Kotlin migration: use the Fragment KTX viewModels() delegate and binding.apply { } to remove
  // the explicit ViewModelProvider and repeated binding receiver.
}
