package com.ss.project.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.ss.project.constant.AppConstant;
import com.ss.project.dto.UserDTO;
import com.ss.project.model.User;
import com.ss.project.repository.IUserRepository;
import com.ss.project.response.Reponse;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Spy
	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private IUserRepository userRepository;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createUserSuccessTest() {
		UserDTO userDto = getUserDto(AppConstant.CREATE, false);
		Mockito.doReturn(userDto.convertToUser(userDto)).when(userRepository).save(Mockito.any(User.class));
		ResponseEntity<?> re = userService.createUser(userDto);
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		assertNotNull(((Reponse) re.getBody()).getUserId());
	}

	@Test
	public void createUserValidationFailTest() {
		UserDTO userDto = getUserDto(AppConstant.CREATE, true);
		ResponseEntity<?> re = userService.createUser(userDto);
		assertEquals(re.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
		assertNull(((Reponse) re.getBody()).getUserId());
		assertEquals(5, ((Reponse) re.getBody()).getValErrors().size());
	}

	@Test
	public void createUserNoSuccessUserAlreadyExistTest() {
		UserDTO userDto = getUserDto(AppConstant.CREATE, false);
		Mockito.doReturn(userDto.convertToUser(userDto)).when(userRepository).findByEMailAndIsActive(userDto.geteMail(),
				Boolean.TRUE);
		ResponseEntity<?> re = userService.createUser(userDto);
		assertEquals(re.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Test
	public void updateUserSuccessTest() {
		UserDTO userDto = getUserDto(AppConstant.UPDATE, false);
		Mockito.doReturn(userDto.convertToUser(userDto)).when(userRepository).findOne(userDto.getId());
		ResponseEntity<?> re = userService.updateUser(userDto);
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		assertNotNull(((Reponse) re.getBody()).getUserId());
		assertEquals(String.valueOf(userDto.getId()), ((Reponse) re.getBody()).getUserId());
	}

	@Test
	public void updateUserValidationFailTest() {
		UserDTO userDto = getUserDto(AppConstant.UPDATE, true);
		ResponseEntity<?> re = userService.updateUser(userDto);
		assertEquals(re.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
		assertNull(((Reponse) re.getBody()).getUserId());
		assertEquals(1, ((Reponse) re.getBody()).getValErrors().size());
	}

	@Test
	public void updateUserIdNotFoundTest() {
		UserDTO userDto = getUserDto(AppConstant.UPDATE, true);
		Mockito.doReturn(null).when(userRepository).findOne(userDto.getId());
		ResponseEntity<?> re = userService.updateUser(userDto);
		assertEquals(re.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
		assertNull(((Reponse) re.getBody()).getUserId());
		assertEquals(userDto.getId(), ((Reponse) re.getBody()).getUserId());
	}
	
	@Test
	public void deleteUserSuccessTest() {
		UserDTO userDto = getUserDto(AppConstant.DELETE, false);
		Mockito.doReturn(userDto.convertToUser(userDto)).when(userRepository).findOne(userDto.getId());
		ResponseEntity<?> re = userService.deleteUser(userDto);
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		assertNotNull(((Reponse) re.getBody()).getUserId());
		assertEquals(String.valueOf(userDto.getId()), ((Reponse) re.getBody()).getUserId());
	}

	@Test
	public void deleteUserValidationFailTest() {
		UserDTO userDto = getUserDto(AppConstant.DELETE, true);
		ResponseEntity<?> re = userService.deleteUser(userDto);
		assertEquals(re.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
		assertNull(((Reponse) re.getBody()).getUserId());
		assertEquals(1, ((Reponse) re.getBody()).getValErrors().size());
	}

	@Test
	public void deleteUserIdNotFoundTest() {
		UserDTO userDto = getUserDto(AppConstant.DELETE, true);
		Mockito.doReturn(null).when(userRepository).findOne(userDto.getId());
		ResponseEntity<?> re = userService.deleteUser(userDto);
		assertEquals(re.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
		assertNull(((Reponse) re.getBody()).getUserId());
		assertEquals(userDto.getId(), ((Reponse) re.getBody()).getUserId());
	}

	private UserDTO getUserDto(String type, boolean failFlag) {
		UserDTO userDto = new UserDTO();

		if (AppConstant.CREATE.equals(type)) {

			if (failFlag) {
				userDto.setfName(null);
				userDto.setlName(null);
				userDto.seteMail(null);
				userDto.setBirthDate(null);
				userDto.setPinCode(null);
			} else {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DATE, -5);
				userDto.setfName("F");
				userDto.setlName("L");
				userDto.seteMail("a@a.com");
				userDto.setBirthDate(cal.getTime());
				userDto.setPinCode(12345);
			}

		} else if (AppConstant.UPDATE.equals(type)) {

			if (failFlag) {
				userDto.setId(null);
				userDto.setBirthDate(null);
				userDto.setPinCode(null);
			} else {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.DATE, -5);
				userDto.setId(1l);
				userDto.setBirthDate(cal.getTime());
				userDto.setPinCode(12345);
			}

		} else if (AppConstant.DELETE.equals(type)) {
			if (failFlag) {
				userDto.setId(null);
			} else {
				userDto.setId(1l);
			}
		}

		return userDto;

	}

}
