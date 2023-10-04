package com.random.security.account;

import com.random.security.account.request.CreateAccountRequest;
import com.random.security.account.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accountant")
@RequiredArgsConstructor
public class AccountantController {

    private final AccountService accountService;

    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('ACCOUNTANT')" + " || hasRole('USER')")
    @GetMapping("/get-all-account-by-userid/{userId}")
    public ResponseEntity<List<AccountResponse>> getAllAccountByUserId(@PathVariable(value="userId") int userId) {
        return new ResponseEntity<>(accountService.getAllAccount(userId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')" + " || hasRole('ACCOUNTANT')" + " || hasRole('USER')")
    @GetMapping("/get-account-by-id/{accountId}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable(value="accountId") int accountId) {
        return new ResponseEntity<AccountResponse>(accountService.getAccountById(accountId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ACCOUNTANT')" + " || hasRole('ADMIN')")
    @PutMapping("/create-account")
    public ResponseEntity createAccount(@RequestBody CreateAccountRequest request) {
        accountService.createAccount(request);
        return ResponseEntity.ok().build();
    }
}