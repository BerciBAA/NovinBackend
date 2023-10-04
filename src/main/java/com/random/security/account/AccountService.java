package com.random.security.account;

import com.random.security.account.request.CreateAccountRequest;
import com.random.security.account.response.AccountResponse;
import com.random.security.user.User;
import com.random.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final UserService userService;


    public List<AccountResponse> getAllAccount(int userId) {
        List<Account> accounts = accountRepository.findAllByUserId(userId);
        return accounts.stream()
                .map(account ->
                    AccountResponse.builder()
                            .id(account.getId())
                            .price(account.getPrice())
                            .comment(account.getComment())
                            .dueDate(account.getDueDate())
                            .customerName(account.getCustomerName())
                            .exhibitionDate(account.getExhibitionDate())
                            .itemName(account.getItemName())
                            .build()
                ).collect(Collectors.toList());
    }

    public AccountResponse getAccountById(int accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        return  AccountResponse.builder()
                .id(account.getId())
                .price(account.getPrice())
                .comment(account.getComment())
                .dueDate(account.getDueDate())
                .customerName(account.getCustomerName())
                .exhibitionDate(account.getExhibitionDate())
                .itemName(account.getItemName())
                .build();

    }

    public void createAccount(CreateAccountRequest request) {
        User user = userService.findByUsernameAccount(request.getCustomerName());
        Account newAccount = Account.builder()
                .comment(request.getComment())
                .customerName(request.getCustomerName())
                .dueDate(request.getDueDate())
                .exhibitionDate(request.getExhibitionDate())
                .price(request.getPrice())
                .itemName(request.getItemName())
                .user(user)
                .build();
        accountRepository.save(newAccount);
    }
}
