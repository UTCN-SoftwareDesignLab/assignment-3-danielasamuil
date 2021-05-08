package com.example.backend.service;

import com.example.backend.mapper.UserMapper;
import com.example.backend.model.ERole;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.model.dtos.UserDto;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder encoder;

    private User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserDto> findAll() {

        return userRepository.findAll().stream().map(
                userMapper::toDto)
                .collect(toList());

    }

    public void delete(int id) {

        userRepository.deleteById(id);
    }

    public void deleteAll() {

        userRepository.deleteAll();
    }

    public UserDto create(UserDto userDto) {

        Set<Role> roles = new HashSet<>();

        Role defaultRole = roleRepository.findByName(ERole.SECRETARY)
                .orElseThrow(() -> new RuntimeException("Cannot find SECRETARY role"));

        roles.add(defaultRole);

        User user = userMapper.fromDto(userDto);

        user.setUsername(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRoles(roles);

        return userMapper.toDto(userRepository.save(user));
    }

    public UserDto update(Integer id, UserDto userDto) {

        User user = findById(id);

        user.setUsername(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));

        return userMapper.toDto(
                userRepository.save(user)
        );
    }

    public List<UserDto> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::toDto)
                .collect(toList());
    }
}
