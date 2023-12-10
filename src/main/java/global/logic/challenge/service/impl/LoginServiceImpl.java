package global.logic.challenge.service.impl;

import global.logic.challenge.dto.PhoneDTO;
import global.logic.challenge.dto.SignUpRequestDTO;
import global.logic.challenge.dto.UserLoginResponseDTO;
import global.logic.challenge.mapper.UserMapper;
import global.logic.challenge.model.Phone;
import global.logic.challenge.model.User;
import global.logic.challenge.repository.PhoneRepository;
import global.logic.challenge.repository.UserRepository;
import global.logic.challenge.service.LoginService;
import global.logic.challenge.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import global.logic.challenge.exception.UserException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService  {

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
        savePhone(signUpRequestDTO, user);
        return UserLoginResponseDTO.builder()
                .id(user.getId())
                .created(user.getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .lastLogin(user.getLastLogin().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .token(user.getToken())
                .isActive(user.getIsActive())
                .build();
    }

    private User saveUser(SignUpRequestDTO signUpRequestDTO) {
        User user = UserMapper.INSTANCE.dtoToUser(signUpRequestDTO);
        user.setCreated(new Date());
        user.setLastLogin(new Date());
        user.setIsActive(Boolean.TRUE);
        user.setToken(generateToken(signUpRequestDTO.getEmail()));
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

    private String generateToken(String username) {
        return JwtUtil.generateToken(username);
    }

    @Override
    public UserLoginResponseDTO login(String authorization) {
        return null;
    }


}
