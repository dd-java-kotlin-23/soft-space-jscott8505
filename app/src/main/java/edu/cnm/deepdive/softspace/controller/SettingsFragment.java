package edu.cnm.deepdive.softspace.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.softspace.databinding.FragmentSettingsBinding;

@AndroidEntryPoint
public class SettingsFragment extends Fragment {

  private static final String KEY_DARK_MODE = "KEY_DARK_MODE";
  private static final String KEY_EVENT_REMINDERS = "key_event_reminders";
  private static final String KEY_MESSAGE_ALERTS = "key_message_alerts";

  private FragmentSettingsBinding binding;
  private SharedPreferences preferences;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentSettingsBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
    // --- 1. DARK MODE SWITCH ---
    boolean isDarkMode = preferences.getBoolean(KEY_DARK_MODE, false);
    binding.switchDarkMode.setChecked(isDarkMode);

    binding.switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
      preferences.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
      if (isChecked) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
      } else {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
      }
    });
    // --- 2. SENSORY EVENT REMINDERS SWITCH ---
    boolean eventReminders = preferences.getBoolean(KEY_EVENT_REMINDERS, true);
    binding.switchEventReminders.setChecked(eventReminders);

    binding.switchEventReminders.setOnCheckedChangeListener((buttonView, isChecked) -> {
      preferences.edit().putBoolean(KEY_EVENT_REMINDERS, isChecked).apply();
      // Optional: Trigger or cancel notification channel/alarm manager here
    });
    // --- 3. DIRECT MESSAGE ALERTS SWITCH ---
    boolean messageAlerts = preferences.getBoolean(KEY_MESSAGE_ALERTS, true);
    binding.switchMessageNotifications.setChecked(messageAlerts);
    binding.switchMessageNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
      preferences.edit().putBoolean(KEY_MESSAGE_ALERTS, isChecked).apply();
      // Optional: Enable or disable push notification handling here
    });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

}