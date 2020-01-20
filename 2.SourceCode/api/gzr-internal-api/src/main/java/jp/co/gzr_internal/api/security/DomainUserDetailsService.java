package jp.co.gzr_internal.api.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.gzr_internal.api.repository.AccountRepository;
import jp.co.gzr_internal.api.service.dto.AccountDto;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.errors.CustomeUsernameNotFoundException;

/**
 * Authenticate a user from the database.
 * 
 * @author GiangTT
 */
@Service("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    /** The account repository. */
    private final AccountRepository accountRepository;

    public DomainUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) {
        AccountDto data = accountRepository.getAccountWithAuthorities(login);
        if (data != null) {
            return createSpringSecurityUser(login, data);
        } else {
            throw new CustomeUsernameNotFoundException();
        }
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin,
        AccountDto account) {
        return new org.springframework.security.core.userdetails.User(account.getEmployeeCode()
            + Contants.CONST_STR_HASH_TAG + account.getRoleId() + Contants.CONST_STR_HASH_TAG + account.getEmail(),
            account.getPassword(), new ArrayList<>());
    }
}
