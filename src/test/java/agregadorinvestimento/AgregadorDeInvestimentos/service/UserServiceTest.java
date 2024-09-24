package agregadorinvestimento.AgregadorDeInvestimentos.service;

import agregadorinvestimento.AgregadorDeInvestimentos.controller.dto.CreateUserDto;
import agregadorinvestimento.AgregadorDeInvestimentos.controller.dto.UpdateUserDto;
import agregadorinvestimento.AgregadorDeInvestimentos.entity.User;
import agregadorinvestimento.AgregadorDeInvestimentos.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should created a user success")
        void shouldCreateAUserWithSuccess() {



            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "teste@teste.com",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto(
                    "teste",
                    "teste@teste.com",
                    "123456");

            var output = userService.createUser(input);

            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw Exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs(){

            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDto(
                    "teste",
                    "teste@teste.com",
                    "123456");

         assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("Should get user by id with success when optional is present")
        void shouldGetUserByIdWhenOptionalIsPresent() {


            //Arrange

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "teste@teste.com",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());
            //Act

            var output = userService.getUserById(user.getUserId().toString());
            //Assert

            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get user by id with success when optional is Empty")
        void shouldGetUserByIdWhenOptionalIsEmpty() {


            //Arrange

            var userId = UUID.randomUUID();

            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());
            //Act

            var output = userService.getUserById(userId.toString());
            //Assert

            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class listUsers {

        @Test
        @DisplayName("Should return all users with success")
        void shouldReturnAllUsersWithSuccess() {

            //Arrange

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "teste@teste.com",
                    "password",
                    Instant.now(),
                    null
            );

            var userList = List.of(user);
            doReturn(userList).when(userRepository).findAll();
            //Act

            var output = userService.listUsers();
            //Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }
    
    @Nested
    class DeleteById {
        @Test
        @DisplayName("Should delete user with success when user exists")
        void shouldDeleteUserWithSuccessWhenUserExists() {


            doReturn(true)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            doNothing()
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();
            //Act

            userService.deleteById(userId.toString());
            //Assert

            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));

        }

        @Test
        @DisplayName("Should delete user with success when do not user exists")
        void shouldDeleteUserWithSuccessWhenDoNotUserExists() {


            doReturn(true)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());


            var userId = UUID.randomUUID();
            //Act

            userService.deleteById(userId.toString());
            //Assert

            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1)).deleteById(any());

        }
    }

    @Nested
    class  updateUserById {

        @Test
        @DisplayName("Should update user by id when and user exists and username and password if filled")
        void shouldUpdateUserByIdWhenUserExistsAndUserNameAndPasswordIsFilled() {

            var updateUserDto = new UpdateUserDto(
                    "newUsername",
                    "newPassword"
            );
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "teste@teste.com",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            //Act

            userService.updateUserById(user.getUserId().toString(), updateUserDto);
            //Assert

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
            assertEquals(updateUserDto.username(), userCaptured.getUsername());
            assertEquals(updateUserDto.password(), userCaptured.getPassword());


            verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1)).save(user);
        }

    }
}
