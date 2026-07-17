package edu.cnm.deepdive.softspace.ui.auth;

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
import edu.cnm.deepdive.softspace.databinding.FragmentSignInBinding;
import edu.cnm.deepdive.softspace.viewmodel.AuthViewModel;

@AndroidEntryPoint
public class SignInFragment extends Fragment {

  private FragmentSignInBinding binding;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentSignInBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    AuthViewModel viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
      if (user != null) {
        Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_profileFragment);
      }
    });
    viewModel.getBusy().observe(getViewLifecycleOwner(), busy -> setEnabled(!Boolean.TRUE.equals(busy)));
    viewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
      if (message != null) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
      }
    });

    binding.signInButton.setOnClickListener(unused -> {
      Credentials credentials = credentials();
      if (credentials != null) {
        viewModel.signIn(credentials.email, credentials.password);
      }
    });
    binding.registerButton.setOnClickListener(unused -> {
      Credentials credentials = credentials();
      if (credentials != null) {
        viewModel.register(credentials.email, credentials.password);
      }
    });
    binding.resetButton.setOnClickListener(unused -> viewModel.resetPassword(text(binding.email)));
  }

  private Credentials credentials() {
    String email = text(binding.email);
    String password = text(binding.password);
    if (email.isBlank() || password.isBlank()) {
      Snackbar.make(requireView(), R.string.required_fields, Snackbar.LENGTH_SHORT).show();
      return null;
    }
    return new Credentials(email, password);
  }

  private String text(android.widget.EditText input) {
    return input.getText() != null ? input.getText().toString().trim() : "";
  }

  private void setEnabled(boolean enabled) {
    binding.signInButton.setEnabled(enabled);
    binding.registerButton.setEnabled(enabled);
    binding.resetButton.setEnabled(enabled);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  private static class Credentials {
    final String email;
    final String password;

    Credentials(String email, String password) {
      this.email = email;
      this.password = password;
    }
  }

  // Kotlin migration: replace Credentials with a data class or a nullable Pair, and collect a
  // lifecycle-aware StateFlow using repeatOnLifecycle rather than observing separate LiveData values.
}
