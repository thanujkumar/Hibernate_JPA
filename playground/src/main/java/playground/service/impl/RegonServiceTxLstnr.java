package playground.service.impl;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Component
public class RegonServiceTxLstnr {

    //@Async
    //@EventListener(String.class)
    @TransactionalEventListener(fallbackExecution = true)
    public void findAll(String message) {
        System.out.println(Thread.currentThread() + "++++++++++++++++++++++++++++++++DEFAULT with fallbackExecution true++++++++++++++++++++++++++++++++++++++++++" + message + " --isActualTransactionActive-- " + TransactionSynchronizationManager.isActualTransactionActive() + "--isSynchronizationActive--" + TransactionSynchronizationManager.isSynchronizationActive() + "--getCurrentTransactionName--" + TransactionSynchronizationManager.getCurrentTransactionName());
    }

    //BEFORE_COMMIT is used to fire the event right before transaction commit
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void beforeTx(String message) {
        System.out.println(Thread.currentThread() + "++++++++++++++++++++++++++++++++BEFORE OOMMIT++++++++++++++++++++++++++++++++++++++++++" + message + " --isActualTransactionActive-- " + TransactionSynchronizationManager.isActualTransactionActive() + "--isSynchronizationActive--" + TransactionSynchronizationManager.isSynchronizationActive() + "--getCurrentTransactionName--" + TransactionSynchronizationManager.getCurrentTransactionName());
//        List<TransactionSynchronization> txSync = TransactionSynchronizationManager.getSynchronizations();
//        for (TransactionSynchronization ts : txSync) {
//            System.out.println(ts);
//        }
    }


    //AFTER_ROLLBACK – if the transaction has rolled back
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void rollback(String message) {
        System.out.println(Thread.currentThread() + "++++++++++++++++++++++++++++++++AFTER ROLLBACK++++++++++++++++++++++++++++++++++++++++++" + message + " --isActualTransactionActive-- " + TransactionSynchronizationManager.isActualTransactionActive() + "--isSynchronizationActive--" + TransactionSynchronizationManager.isSynchronizationActive() + "--getCurrentTransactionName--" + TransactionSynchronizationManager.getCurrentTransactionName());
    }


    //AFTER_COMPLETION – if the transaction has completed (an alias for AFTER_COMMIT and AFTER_ROLLBACK)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void completion(String message) {
        System.out.println(Thread.currentThread() + "++++++++++++++++++++++++++++++++AFTER COMPLETION++++++++++++++++++++++++++++++++++++++++++" + message + " --isActualTransactionActive-- " + TransactionSynchronizationManager.isActualTransactionActive() + "--isSynchronizationActive--" + TransactionSynchronizationManager.isSynchronizationActive() + "--getCurrentTransactionName--" + TransactionSynchronizationManager.getCurrentTransactionName());

    }

    //AFTER_COMMIT (default) is used to fire the event if the transaction has completed successfully
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void commit(String message) {
        System.out.println(Thread.currentThread() + "++++++++++++++++++++++++++++++++AFTER COMMIT++++++++++++++++++++++++++++++++++++++++++" + message + " --isActualTransactionActive-- " + TransactionSynchronizationManager.isActualTransactionActive() + "--isSynchronizationActive--" + TransactionSynchronizationManager.isSynchronizationActive() + "--getCurrentTransactionName--" + TransactionSynchronizationManager.getCurrentTransactionName());

    }
}
