package global.logic.challenge.service.impl;

import global.logic.challenge.dto.PhoneDTO;
import global.logic.challenge.dto.SignUpRequestDTO;
import global.logic.challenge.dto.UserLoginResponseDTO;
import global.logic.challenge.model.Phone;
import global.logic.challenge.model.User;
import global.logic.challenge.repository.PhoneRepository;
import global.logic.challenge.repository.UserRepository;
import global.logic.challenge.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import global.logic.challenge.exception.UserException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Override
    public UserLoginResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) throws UserException {

        if (userRepository.findByEmail(signUpRequestDTO.getEmail()).isPresent()) {
            log.error("User already exists: {}", signUpRequestDTO.getEmail());
            throw new UserException("User already exists");
        }

        User user = saveUser(signUpRequestDTO);
        log.info("user saved: {}", user.toString());
        //List<User> userList= userRepository.findAll();
        //List<Phone> phones = phoneRepository.findByUser(user);
        savePhone(signUpRequestDTO, user);
        return null;
    }

    private User saveUser(SignUpRequestDTO signUpRequestDTO) {
        User user = User.builder().created(new Date())
                    .isActive(Boolean.TRUE)
                    .name(signUpRequestDTO.getName())
                    .email(signUpRequestDTO.getEmail())
                    .password(signUpRequestDTO.getPassword())
                    .build();
        return userRepository.save(user);
    }

    private void savePhone(SignUpRequestDTO signUpRequestDTO, User user) {
        signUpRequestDTO.getPhones().forEach(p -> {
            Phone phone = Phone.builder()
                    .number(p.getNumber())
                    .citycode(p.getCitycode())
                    .countrycode(p.getCountrycode())
                    .user(user)
                    .build();
            phoneRepository.save(phone);
            log.info("phone saved: {}", phone.toString());
        });
    }

    @Override
    public UserLoginResponseDTO login(String authorization) {
        return null;
    }


}
