package org.example.microsvc.account.model.data;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@Data
public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name="account_generator", sequenceName = "account_seq")
    @Column(name="id")
    private long id;
    @Column(name="userid",nullable = false)
    private String userId;
    @Column(name="accountid",nullable = false,unique = true)
    private String accountId;
    @Column(name="balance",nullable = false)
    private Double balance;
}
