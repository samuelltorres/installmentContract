package services;

import entities.Contract;
import entities.Installment;

import java.time.LocalDate;

public class ContractService {

    private OnlinePaymentService onlinePaymentService;

    public ContractService(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }

    public void processContract(Contract contract, Integer months) {

        double valuePerMonth = contract.getTotalValue() / months;

        for (int i = 1; i <= months; i++) {
            LocalDate dueDate = contract.getDate().plusMonths(i);
            double interest = onlinePaymentService.interest(valuePerMonth, i);
            double fee = onlinePaymentService.paymentFee(valuePerMonth + interest);
            double amount = valuePerMonth + fee + interest;

            contract.getInstallments().add(new Installment(dueDate, amount));
        }
    }
}
