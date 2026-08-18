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

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock private UserRepository userRepository;

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
}
