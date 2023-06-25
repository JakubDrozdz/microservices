package pl.jakubdrozdz.fraud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.jakubdrozdz.clients.fraud.FraudCheckResponse;

@RestController
@RequestMapping("api/v1/fraud-check")
@Slf4j
public record FraudCheckController(FraudCheckService fraudCheckService) {
    @GetMapping(path="{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId") Integer customerId){
        boolean isFraudulentCustomer = fraudCheckService.isFraudulentCustomer(customerId);
        log.info("Fraud check request for customer with id: {}", customerId);
        return new FraudCheckResponse(isFraudulentCustomer);
    }
}
