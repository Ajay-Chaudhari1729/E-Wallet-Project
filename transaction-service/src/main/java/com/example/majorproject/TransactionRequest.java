package com.example.majorproject;


import lombok.*;

import javax.persistence.Entity;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class TransactionRequest {


    private int amount;
    private String fromUser;

    private String toUser;

    private String Purpose;

}
