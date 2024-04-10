package com.geara.drugpack.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountController {

  private final AccountRepository repository;

  public Account newAccount(String email, String password) {
    final var account = new Account(email, password);
    repository.save(account);
    return account;
  }
}
