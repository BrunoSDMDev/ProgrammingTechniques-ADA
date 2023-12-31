package aula8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Atomic {

    public static void main(String[] args) {
        final int THREADS = 3;
        var wallet = new Wallet();
        ExecutorService service = Executors.newFixedThreadPool(THREADS);  // Não tem consistência no valor final

        for (int i = 0; i < THREADS; i++) {
            service.execute(() -> {
                wallet.credit(10L);
                wallet.debit(1L);
                System.out.println(wallet.getBalance());
            });
        }
        service.shutdown();
    }
}

class Wallet {
    private String currency;
    private Long balance;

    public Wallet() {
        this.currency = "BRL";
        this.balance = 0L;
    }

    public String getCurrency() {
        return currency;
    }

    public Long getBalance() {
        return balance;
    }

    public void credit(Long value) { this.balance += value; }
    public void debit(Long value) { this.balance -= value; }
}
