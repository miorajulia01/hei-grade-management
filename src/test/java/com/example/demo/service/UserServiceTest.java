package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.entity.JUser;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock private UserRepository userRepository;

  @Mock private PasswordEncoder passwordEncoder;

  @InjectMocks private UserService userService;

  private JUser userEntity;
  private User userModel;

  @BeforeEach
  void setUp() {
    userEntity = JUser.builder().id("user-1").email("test@example.com").build();

    userModel = User.builder().id("user-1").email("test@example.com").build();
  }

  @Test
  void shouldGetAllUsers() {
    when(userRepository.findAll()).thenReturn(List.of(userEntity));

    List<User> result = userService.getAllUsers();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("user-1", result.get(0).getId());
    assertEquals("test@example.com", result.get(0).getEmail());

    verify(userRepository).findAll();
  }

  @Test
  void shouldGetUserById() {
    when(userRepository.findById("user-1")).thenReturn(Optional.of(userEntity));

    User result = userService.getUserById("user-1");

    assertNotNull(result);
    assertEquals("user-1", result.getId());
    assertEquals("test@example.com", result.getEmail());

    verify(userRepository).findById("user-1");
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    when(userRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> userService.getUserById("unknown"));

    assertEquals("User not found with id: unknown", exception.getMessage());

    verify(userRepository).findById("unknown");
  }

  @Test
  void shouldSaveUser() {
    when(userRepository.save(any(JUser.class))).thenReturn(userEntity);

    User result = userService.saveUser(userModel);

    assertNotNull(result);
    assertEquals("user-1", result.getId());
    assertEquals("test@example.com", result.getEmail());

    ArgumentCaptor<JUser> captor = ArgumentCaptor.forClass(JUser.class);
    verify(userRepository).save(captor.capture());

    JUser savedEntity = captor.getValue();

    assertEquals("user-1", savedEntity.getId());
    assertEquals("test@example.com", savedEntity.getEmail());
  }

  @Test
  void shouldUpdateUser() {
    when(userRepository.findById("user-1")).thenReturn(Optional.of(userEntity));

    User model = User.builder().id("user-1").email("updated@example.com").build();

    JUser updatedEntity = JUser.builder().id("user-1").email("updated@example.com").build();

    when(userRepository.save(any(JUser.class))).thenReturn(updatedEntity);

    User result = userService.updateUser("user-1", model);

    assertNotNull(result);
    assertEquals("updated@example.com", result.getEmail());

    verify(userRepository).findById("user-1");
    verify(userRepository).save(any(JUser.class));
  }

  @Test
  void shouldUpdateUserPasswordWithEncoding() {
    when(userRepository.findById("user-1")).thenReturn(Optional.of(userEntity));

    when(passwordEncoder.encode("new-password")).thenReturn("encoded-password");

    User model = User.builder().id("user-1").password("new-password").build();

    JUser updatedEntity =
        JUser.builder().id("user-1").email("test@example.com").password("encoded-password").build();

    when(userRepository.save(any(JUser.class))).thenReturn(updatedEntity);

    userService.updateUser("user-1", model);

    ArgumentCaptor<JUser> captor = ArgumentCaptor.forClass(JUser.class);

    verify(userRepository).save(captor.capture());

    assertEquals("encoded-password", captor.getValue().getPassword());
    verify(passwordEncoder).encode("new-password");
  }

  @Test
  void shouldThrowExceptionWhenUpdatingUserNotFound() {
    when(userRepository.findById("unknown")).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> userService.updateUser("unknown", userModel));

    assertEquals("User not found with id: unknown", exception.getMessage());

    verify(userRepository).findById("unknown");
    verify(userRepository, never()).save(any());
  }

  @Test
  void shouldDeleteUser() {
    when(userRepository.existsById("user-1")).thenReturn(true);

    userService.deleteUser("user-1");

    verify(userRepository).deleteById("user-1");
  }

  @Test
  void shouldThrowExceptionWhenDeletingUserNotFound() {
    when(userRepository.existsById("unknown")).thenReturn(false);

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> userService.deleteUser("unknown"));

    assertEquals("User not found with id: unknown", exception.getMessage());

    verify(userRepository, never()).deleteById("unknown");
  }
}
