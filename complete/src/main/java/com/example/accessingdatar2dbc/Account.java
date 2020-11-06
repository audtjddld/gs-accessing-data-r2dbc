package com.example.accessingdatar2dbc;

import org.springframework.data.annotation.Id;

public class Account {
  @Id
  private Long id;
  private String bankName;

  public Account(String bankName) {
    this.bankName = bankName;
  }

  public String getBankName() {
    return bankName;
  }

}
