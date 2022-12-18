package com.buying.back.application.account.service;

import com.buying.back.application.account.code.exception.AccountException;
import com.buying.back.application.account.code.exception.AccountException.AccountExceptionCode;
import com.buying.back.application.account.controller.dto.CreateAccountDTO;
import com.buying.back.application.account.controller.dto.SearchAccountManagementDTO;
import com.buying.back.application.account.controller.dto.UpdateActivateAccountDTO;
import com.buying.back.application.account.domain.Account;
import com.buying.back.application.account.repository.AccountRepository;
import com.buying.back.application.account.service.vo.AccountDefaultVO;
import com.buying.back.application.account.service.vo.AccountManagementVO;
import com.buying.back.util.encryption.PasswordProvider;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

  private final AccountRepository accountRepository;
  private final PasswordProvider passwordProvider;

  // auth
  @Transactional
  public AccountDefaultVO createAccount(CreateAccountDTO dto) {
    Account account = accountRepository.findByEmail(dto.getEmail())
      .orElse(null);

    if (Objects.nonNull(account)) {
      throw new AccountException(AccountExceptionCode.ALREADY_EXIST_ACCOUNT);
    }

    dto.setPassword(passwordProvider.encode(dto.getPassword()));
    account = Account.initAccount()
      .dto(dto)
      .build();
    accountRepository.save(account);

    return new AccountDefaultVO(account);
  }

  // login user
  public AccountDefaultVO getMyInformation(Long loginUserId) {
    Account account = accountRepository.findById(loginUserId)
      .orElseThrow(() -> new AccountException(AccountExceptionCode.NOT_FOUND_ACCOUNT));
    return new AccountDefaultVO(account);
  }

  // management
  public Page<AccountManagementVO> getAccountList(SearchAccountManagementDTO dto) {
    return accountRepository.findAll(dto.getPageRequest(), dto);
  }

  public AccountManagementVO activateAccount(Long accountId, UpdateActivateAccountDTO dto) {
    Account account = accountRepository.findById(accountId)
      .orElseThrow(() -> new AccountException(AccountExceptionCode.NOT_FOUND_ACCOUNT));

    account.setActivated(dto.getActivated());
    accountRepository.save(account);
    return new AccountManagementVO(account);
  }

}