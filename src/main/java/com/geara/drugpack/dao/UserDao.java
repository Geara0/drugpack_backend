package com.geara.drugpack.dao;

import com.geara.drugpack.entities.account.AccountController;
import com.geara.drugpack.entities.account.AccountRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao {

  private final AccountController accountController;
  private final AccountRepository accountRepository;

  public UserDetails findUserByEmail(String email) {
    final var account = accountRepository.findByEmail(email);

    if (account == null) return null;

    return new User(
        account.getEmail(),
        account.getShaPassword(),
        Collections.singleton(new SimpleGrantedAuthority("user")));
  }

  public UserDetails newUser(String email, String shaPassword) {
    final var account = accountController.newAccount(email, shaPassword);
    return new User(
        account.getEmail(),
        account.getShaPassword(),
        Collections.singleton(new SimpleGrantedAuthority("user")));
  }
}
