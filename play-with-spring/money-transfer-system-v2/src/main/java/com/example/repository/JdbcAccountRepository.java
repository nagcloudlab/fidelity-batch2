package com.example.repository;

import com.example.entity.Account;
import com.example.entity.AccountType;
import org.slf4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component("jdbcAccountRepository")
public class JdbcAccountRepository implements AccountRepository {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(JdbcAccountRepository_Without_Spring_Support.class);

    private JdbcTemplate jdbcTemplate;

    //    @Autowired
    public JdbcAccountRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        logger.info("JdbcAccountRepository initialized with DataSource: {}", dataSource);
    }

    @Override
    public Account findById(String accountNumber) {
        logger.info("JdbcAccountRepository findById called with accountNumber: {}", accountNumber);
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            String accountNum = rs.getString("account_number");
            String accountHolderName = rs.getString("account_holder_name");
            double balance = rs.getDouble("balance");
            String accountTypeStr = rs.getString("account_type");
            AccountType accountType = AccountType.valueOf(accountTypeStr);
            return new Account(accountNum, accountHolderName, balance, accountType);
        }, accountNumber);
    }

    @Override
    public void save(Account account) {
        logger.info("JdbcAccountRepository save called with account: {}", account);
        String sql = "UPDATE accounts SET account_holder_name = ?, balance = ?, account_type = ? WHERE account_number = ?";
        jdbcTemplate.update(sql, account.getAccountHolderName(), account.getBalance(), account.getAccountType().name(), account.getAccountNumber());
    }
}
