

package com.levelup.levelup_auth.presentation.rest.controller;

import com.levelup.levelup_auth.application.port.in.profile.DeleteProfileUseCase;
import com.levelup.levelup_auth.application.port.in.profile.GetProfileUseCase;
import com.levelup.levelup_auth.application.port.in.profile.UpdateProfileCommand;
import com.levelup.levelup_auth.application.port.in.profile.UpdateProfileUseCase;
import com.levelup.levelup_auth.application.port.in.profile.UserProfileResult;
import com.levelup.levelup_auth.presentation.rest.dto.request.UpdateProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final GetProfileUseCase getProfileUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final DeleteProfileUseCase deleteProfileUseCase;

    public ProfileController(
            GetProfileUseCase getProfileUseCase,
            UpdateProfileUseCase updateProfileUseCase,
            DeleteProfileUseCase deleteProfileUseCase) {
        this.getProfileUseCase = getProfileUseCase;
        this.updateProfileUseCase = updateProfileUseCase;
        this.deleteProfileUseCase = deleteProfileUseCase;
    }

    @GetMapping
    public ResponseEntity<UserProfileResult> getProfile(Principal principal) {
        UserProfileResult result = getProfileUseCase.getByLogin(principal.getName());
        return ResponseEntity.ok(result);
    }

    @PatchMapping
    public ResponseEntity<UserProfileResult> updateProfile(
            Principal principal,
            @RequestBody UpdateProfileRequest request) {

        UpdateProfileCommand command = new UpdateProfileCommand(
                principal.getName(),
                request.email(),
                request.name()
        );

        UserProfileResult result = updateProfileUseCase.update(command);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProfile(Principal principal) {
        deleteProfileUseCase.deleteByLogin(principal.getName());
        return ResponseEntity.noContent().build();
    }
}