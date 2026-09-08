package com.phishing.Services;

import com.phishing.DTO.AdminUserResponse;
import com.phishing.DTO.UpdateProfileRequest;
import com.phishing.Entities.Role;
import com.phishing.Entities.User;
import com.phishing.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User updateProfile(User user, UpdateProfileRequest request) {

		if (!user.getEmail().equalsIgnoreCase(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {

			throw new IllegalArgumentException("Email is already registered");
		}

		user.setName(request.getName());
		user.setEmail(request.getEmail());

		return userRepository.save(user);
	}

	public List<AdminUserResponse> getAllUsers() {

		List<User> users = userRepository.findAll();

		List<AdminUserResponse> responseList = new ArrayList<>();

		for (User user : users) {

			AdminUserResponse response = new AdminUserResponse(user.getId(), user.getName(), user.getEmail(),
					user.getRole().name(), user.isActive());

			responseList.add(response);
		}

		return responseList;
	}

	  public User updateUserRole(Long id, String role, User currentUser) {
		  
		  if (currentUser.getId().equals(id)) {

			    throw new IllegalArgumentException(
			            "Admin cannot change their own role"
			    );
			}
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + id));

		Role newRole;

		try {
			newRole = Role.valueOf(role.toUpperCase());
		} catch (IllegalArgumentException exception) {
			throw new IllegalArgumentException("Invalid role. Allowed roles are USER and ADMIN");
		}

		user.setRole(newRole);

		return userRepository.save(user);
	}
	  public User updateUserStatus(Long id, boolean active, User currentUser) {

		    // Admin cannot change their own status
		    if (currentUser.getId().equals(id)) {
		        throw new IllegalArgumentException(
		                "Admin cannot change their own account status"
		        );
		    }

		    User user = userRepository.findById(id)
		            .orElseThrow(() ->
		                    new RuntimeException("User not found with id: " + id)
		            );

		    // Prevent deactivating the last active admin
		    if (user.getRole() == Role.ADMIN && !active) {

		        long activeAdminCount =
		                userRepository.countByRoleAndActiveTrue(Role.ADMIN);

		        if (activeAdminCount <= 1) {
		            throw new IllegalArgumentException(
		                    "Cannot deactivate the last active admin"
		            );
		        }
		    }

		    user.setActive(active);

		    return userRepository.save(user);
		}
	  
}