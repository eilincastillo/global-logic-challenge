package global.logic.challenge.service.impl;

import global.logic.challenge.dto.PhoneDTO;
import global.logic.challenge.dto.SignUpRequestDTO;
import global.logic.challenge.dto.UserLoginResponseDTO;
import global.logic.challenge.mapper.PhoneMapper;
import global.logic.challenge.mapper.UserMapper;
import global.logic.challenge.model.Phone;
import global.logic.challenge.model.User;
import global.logic.challenge.repository.PhoneRepository;
import global.logic.challenge.repository.UserRepository;
import global.logic.challenge.service.LoginService;
import global.logic.challenge.util.DateUtil;
import global.logic.challenge.util.JwtUtil;
import global.logic.challenge.util.PasswordUtil;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import global.logic.challenge.exception.UserException;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
                .created(DateUtil.convertToDate(user.getCreated()))
                .lastLogin(DateUtil.convertToDate(user.getLastLogin()))
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
        user.setPassword(PasswordUtil.passwordEncoder(user.getPassword()));
        return userRepository.save(user);
    }

    private void savePhone(SignUpRequestDTO signUpRequestDTO, User user) {
        signUpRequestDTO.getPhones().stream()
                .map(p -> Phone.builder()
                        .number(p.getNumber())
                        .citycode(p.getCitycode())
                        .countrycode(p.getCountrycode())
                        .user(user)
                        .build())
                .forEach(phone -> {
                    phoneRepository.save(phone);
                    log.info("Phone saved: {}", phone.toString());
                });
    }

    private String generateToken(String username) {
        return JwtUtil.generateToken(username);
    }

    @Override
    public UserLoginResponseDTO login(String authorization) throws NotFoundException {
        String email = decodeToken(authorization);
        return findUser(email);
    }
    private String decodeToken(String token) {
        token = token.substring(7);
        return JwtUtil.extractUsername(token);
    }

    private UserLoginResponseDTO findUser(String email) throws NotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> {
            log.error("User not found: {}", email);
            return new NotFoundException("User not found");
        });
        user = refreshUserToken(user);
        return UserLoginResponseDTO.builder()
                .id(user.getId())
                .created(DateUtil.convertToDate(user.getCreated()))
                .lastLogin(DateUtil.convertToDate(user.getLastLogin()))
                .token(user.getToken())
                .isActive(user.getIsActive())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phones(parsePhone(user))
                .build();
    }

    private List<PhoneDTO> parsePhone (User user) {
        List <Phone> phones = phoneRepository.findByUser(user);
        return Optional.ofNullable(phones)
                .orElse(Collections.emptyList())
                .stream()
                .map(this::phoneToPhoneDTO)
                .collect(Collectors.toList());
    }

    private PhoneDTO phoneToPhoneDTO(Phone phone) {
        return PhoneMapper.INSTANCE.phoneToPhoneDTO(phone);
    }

    private User refreshUserToken(User user) {
        user.setToken(generateToken(user.getEmail()));
        return userRepository.save(user);
    }

}
