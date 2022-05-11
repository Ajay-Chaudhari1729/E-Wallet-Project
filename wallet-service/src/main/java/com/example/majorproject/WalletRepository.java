package com.example.majorproject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface WalletRepository extends JpaRepository<Wallet,Integer>{
    // if we use this then we don't need to query
    Wallet findByUserId(String userId); // by using this query is not needed
    @Modifying
    @Transactional
    @Query("update wallet w set w.balance = w.balance  + :amount where w.UserId = :w.UserId")
    void updateWallet(String UserId, int amount);
}
