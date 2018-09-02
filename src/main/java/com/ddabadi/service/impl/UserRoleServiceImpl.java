package com.ddabadi.service.impl;

import com.ddabadi.model.Role;
import com.ddabadi.model.User;
import com.ddabadi.model.UserRole;
import com.ddabadi.model.compositekey.UserRoleId;
import com.ddabadi.model.dto.UserRoleDto;
import com.ddabadi.model.dto.UserRoleViewDto;
import com.ddabadi.repository.RoleRepository;
import com.ddabadi.repository.UserRoleRepository;
import com.ddabadi.service.CustomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserRoleServiceImpl implements CustomService<UserRole, Page<UserRole>> {

    @Autowired
    private UserRoleRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserServiceImpl userService;

    private static Logger log = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    public UserRole findOne(Long id) {
        return null;
    }

    public Page<UserRole> findAllByPage(Integer page, Integer total) {
        log.debug("Find Pageable page {} , total {}", page, total);
        Sort sort = new Sort(Sort.Direction.ASC,"name");
        PageRequest pageRequest = PageRequest.of (page -1, total, sort);

        return repository.findAll(pageRequest) ;
    }

    @Override
    public UserRole addnew(UserRole userRole) {
        return repository.save(userRole);
    }

    public void addnew(UserRoleDto userRoleDto) {

        log.debug("New Record ");
        User username = userService.getCurUserAsObj();
        userRoleDto.getRoleId().forEach(roleId -> {

            UserRole newRec = new UserRole();
            UserRoleId userRoleId = new UserRoleId(roleId, userRoleDto.getUserId());
            newRec.setUserRoleId(userRoleId);
            newRec.setUpdatedBy(username);
            newRec.setCreatedBy(username);
            newRec.setUpdatedAt(new Date());
            newRec.setCreatedAt(new Date());
            repository.save(newRec);
        });
    }

    @Override
    public UserRole update(UserRole userRole, Long idOld) {
        return null;
    }

    public List<Long> findByRoleId(Long roleId) {
        List<UserRole> roleUserList = repository.findByRoleId(roleId);

        List<Long> userList = new ArrayList<>();
        roleUserList.forEach(
                userRole -> userList.add(userRole.getUserRoleId().getUserId()) );
        return userList;
    }

    public List<Long> findByUserId(Long userId) {

        List<UserRole> roleUserList = repository.findByUserId(userId);

        List<Long> roleList = new ArrayList<>();
        roleUserList.forEach(userRole ->
            roleList.add(userRole.getUserRoleId().getRoleId())
        );
        return roleList;
    }

    @Transactional(readOnly = true)
    public List<UserRoleViewDto> findRoleViewByUserId(Long userId){
        final List<UserRoleViewDto> results = new ArrayList<>();
        List<Long> listRole = this.findByUserId(userId) ;
        listRole.forEach( roleId -> {
            Optional<Role> optionalRole = roleRepository.findById(roleId) ;
            if (optionalRole.isPresent()){

                Optional<UserRole> userRoleOpt = repository.findByRoleIdAndUserId(roleId, userId);
                if (userRoleOpt.isPresent()) {
                    UserRole userRole = userRoleOpt.get();
                    UserRoleViewDto dto = new UserRoleViewDto();

                    Role role = optionalRole.get();
                    dto.setRoleId(role.getId());
                    dto.setRoleDescription(role.getDescription());
                    dto.setRoleName(role.getName());
                    dto.setUserId(userId);
                    dto.setStatus(userRole.getStatus());
                    results.add(dto)  ;
                }
            }
        });

        results.sort((o1, o2) -> o1.getRoleName().compareTo(o2.getRoleName()));
        return results;
    }

    public UserRole findByRoleIdAndUserId(Long roleId, Long userId) {
        log.debug("Find one by role {} and user {}", roleId, userId );

        Optional<UserRole> roleUserOptional = repository.findByRoleIdAndUserId(roleId, userId);
        return roleUserOptional.orElseGet(UserRole::new);
    }

    public void delete(UserRole roleUserCheck) {
        repository.delete(roleUserCheck);
    }
}
