package com.example.majorproject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    KafkaTemplate<String, String>kafkaTemplate;
    @Autowired
    ObjectMapper objectMapper;
    private static final String TOPIC_TRANSACTION_INITIATED = "transactionInitiated";
    private static final String TOPIC_WALLET_UPDATE = "wallet_update";
    private static final String TOPIC_TRANSACTION_COMPLETE = "transaction_complete";

    @Autowired
    TransactionRepository transactionRepository ;
    public String initiateTransaction(TransactionRequest transactionRequest) throws JsonProcessingException {
        // using this transactionRequest create a  object of transaction and save in d.b so
        // we need a transactionRepository


        Transaction transaction = Transaction.builder()
                .amount(transactionRequest.getAmount())
                .fromUser(transactionRequest.getFromUser())
                .toUser(transactionRequest.getToUser())
                .Purpose(transactionRequest.getPurpose())
                .transactionId(UUID.randomUUID().toString())
                .transactionStatus(TransactionStatus.PENDING)
                .build();
        transactionRepository.save(transaction);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromUser",transaction.getFromUser());
        jsonObject.put("toUser", transaction.getToUser());
        jsonObject.put("amount", transaction.getAmount());
        jsonObject.put("transactionId", transaction.getTransactionId());
        kafkaTemplate.send(TOPIC_TRANSACTION_INITIATED,objectMapper.writeValueAsString(jsonObject));
        return transaction.getTransactionId();
    }
    @KafkaListener(topics = {TOPIC_WALLET_UPDATE}, groupId = "jbdl-15")
//    @KafkaListener(topics = {TOPIC_WALLET_UPDATE}, groupId = "jbdl-15")
    public void updateTransaction(String msg) throws JsonProcessingException {
        JSONObject jsonObject = objectMapper.readValue(msg, JSONObject.class);
        String transactionId = (String)jsonObject.get("transactionId");
        String status = (String)jsonObject.get("status");
        String fromUser = (String) jsonObject.get("fromUser");
        String toUser = (String) jsonObject.get("toUser");
        int amount = (int) jsonObject.get("amount");

        TransactionStatus transactionStatus = TransactionStatus.valueOf(status);
        transactionRepository.updateTransactionByStatus(transactionStatus, transactionId);


    }

}
