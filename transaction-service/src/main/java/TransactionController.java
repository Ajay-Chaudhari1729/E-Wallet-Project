import com.example.majorproject.TransactionRequest;
import com.example.majorproject.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction")
    public String initiateTransaction(@RequestBody TransactionRequest transactionRequest) throws JsonProcessingException {
         return transactionService.initiateTransaction(transactionRequest);

    }
}
