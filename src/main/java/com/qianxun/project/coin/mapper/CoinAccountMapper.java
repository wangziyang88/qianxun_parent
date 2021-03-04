package com.qianxun.project.coin.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qianxun.project.coin.domain.CoinAccount;

public interface CoinAccountMapper {
    public CoinAccount selectCoinAccountById(Long id);

    public List<CoinAccount> selectCoinAccountList(CoinAccount coinAccount);

    public int insertCoinAccount(CoinAccount coinAccount);

    public int updateCoinAccount(CoinAccount coinAccount);

    public int deleteCoinAccountById(Long id);

    public int deleteCoinAccountByIds(Long[] ids);

    public CoinAccount getByUserIdAndCoinId(Long userId, Long coinId);

    public int balanceChange(@Param("id") Long id, @Param("available") BigDecimal available,
            @Param("frozen") BigDecimal frozen, @Param("lockup") BigDecimal lockup, @Param("version") Long version,
            @Param("date") Date date);

}
