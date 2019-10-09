package com.trilogyed.levelupservice.dao;

import com.trilogyed.levelupservice.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LevelUpDaoJdbcTemplateImpl implements LevelUpDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public LevelUpDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){ this.jdbcTemplate=jdbcTemplate; }

    //prepared statements
    private final static String INSERT_ACCOUNT =
            "insert into level_up (customer_id, points, member_date) values (?,?,?)";
    private final static String SELECT_ACCOUNT =
            "select * from level_up where level_up_id = ?";
    private final static String SELECT_ACCOUNT_BY_CUSTOMER =
            "select * from level_up where customer_id = ?";
    private final static String SELECT_ALL_ACCOUNTS =
            "select * from level_up";
    private final static String UPDATE_ACCOUNT =
            "update level_up set customer_id=?, points=?, member_date=? where level_up_id = ?";
    private final static String DELETE_ACCOUNT =
            "delete from level_up where level_up_id=?";

    //db row to object mapper
    private LevelUp mapRowToLevelUp (ResultSet rs, int rowNum) throws SQLException {
        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(rs.getInt("level_up_id"));
        levelUp.setCustomerId(rs.getInt("customer_id"));
        levelUp.setPoints(rs.getInt("points"));
        levelUp.setMemberDate(rs.getDate("member_date").toLocalDate());

        return levelUp;
    }

    @Override
    public LevelUp createNewAccount(LevelUp newAccount) {
        jdbcTemplate.update(
                INSERT_ACCOUNT,
                newAccount.getCustomerId(),
                newAccount.getPoints(),
                newAccount.getMemberDate()
        );

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        newAccount.setLevelUpId(id);

        return newAccount;
    }

    @Override
    public LevelUp getAccount(int levelUpId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_ACCOUNT, this::mapRowToLevelUp, levelUpId);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public LevelUp getAccountByCustomer(int customerId) {
        try{
            return jdbcTemplate.queryForObject(SELECT_ACCOUNT_BY_CUSTOMER, this::mapRowToLevelUp, customerId);
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<LevelUp> getAllAccounts() {

        return jdbcTemplate.query(SELECT_ALL_ACCOUNTS, this::mapRowToLevelUp);

    }

    @Override
    public void updateAccount(LevelUp updatedAccount) {
        jdbcTemplate.update(
                UPDATE_ACCOUNT,
                updatedAccount.getCustomerId(),
                updatedAccount.getPoints(),
                updatedAccount.getMemberDate(),
                updatedAccount.getLevelUpId()
        );
    }

    @Override
    public void deleteAccount(int levelUpId) {

        jdbcTemplate.update(DELETE_ACCOUNT, levelUpId);

    }
}
