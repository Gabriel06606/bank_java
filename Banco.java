import java.util.Random;

class Conta{

    private double saldo;

    public Conta(){

        this.saldo = 0.0d;
    }

    public void deposito(double valor){

        this.saldo = this.saldo + valor;

        try {
            Thread.sleep(100);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    public void saque(double valor){

        this.saldo = this.saldo - valor;

        try {
            Thread.sleep(100);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    public double getSaldo(){

        try {
            Thread.sleep(100);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        return this.saldo;
    }
}

class DepositaThread extends Thread{

    private Conta conta;
    private int id;

    public DepositaThread(Conta conta, int id){

        this.conta = conta;
        this.id = id;

    }

    public void run(){

        Random random = new Random();

        while(true){

            double valor = random.nextDouble();
            System.out.println("DEPOSITA - Thread " + this.id + " ira depositar valor: " + valor);
            synchronized (this.conta) {

                this.conta.deposito(valor);

            }

            System.out.println("DEPOSITA - Thread " + this.id + " depositou valor: " + valor + " saldo: " + this.conta.getSaldo());

            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }
        }
    }
}

class SaqueThread extends Thread{

    private Conta conta;
    private int id;

    public SaqueThread(Conta conta, int id){

        this.conta = conta;
        this.id = id;

    }

    public void run(){

        Random random = new Random();

        while(true){

            double valor = random.nextDouble();
            System.out.println("SAQUE - Thread " + this.id + " ira sacar valor: " + valor);
            synchronized (this.conta) {

                if (this.conta.getSaldo() >= valor) {

                    this.conta.saque(valor);
                    System.out.println("SAQUE - Thread " + this.id + " sacou valor: " + valor + " saldo: " + this.conta.getSaldo());

                } else {

                    System.out.println("SAQUE - Thread " + this.id + " não conseguiu sacar valor: " + valor);
                }
            }

            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}

public class Banco {
    public static void main(String[] args) throws InterruptedException {

        Conta conta = new Conta();
        SaqueThread saqueThread1 = new SaqueThread(conta, 1);
        SaqueThread saqueThread2 = new SaqueThread(conta, 2);
        SaqueThread saqueThread3 = new SaqueThread(conta, 3);
        DepositaThread depositaThread1 = new DepositaThread(conta, 1);
        DepositaThread depositaThread2 = new DepositaThread(conta, 2);

        saqueThread1.start();
        saqueThread2.start();
        saqueThread3.start();
        depositaThread1.start();
        depositaThread2.start();
    }
}