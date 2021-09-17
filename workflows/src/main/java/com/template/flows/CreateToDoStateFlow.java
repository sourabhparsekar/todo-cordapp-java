package com.template.flows;

import co.paralleluniverse.fibers.Suspendable;
import com.template.contracts.DummyToDoCommand;
import com.template.states.ToDoState;
import net.corda.core.flows.*;
import net.corda.core.identity.Party;
import net.corda.core.transactions.SignedTransaction;
import net.corda.core.transactions.TransactionBuilder;

import java.util.Collections;

@StartableByRPC
public class CreateToDoStateFlow extends FlowLogic<String> {

    private final String taskDescription;

    public CreateToDoStateFlow(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Suspendable
    @Override
    public String call() throws FlowException {

        Party notary = getServiceHub().getNetworkMapCache().getNotaryIdentities().get(0);

        Party myParty = getOurIdentity();

        ToDoState toDoState = new ToDoState(myParty, myParty, taskDescription);

        TransactionBuilder transactionBuilder = new TransactionBuilder(notary)
                .addOutputState(toDoState)
                .addCommand(new DummyToDoCommand(), myParty.getOwningKey()); //this is mandatory

        SignedTransaction signedTransaction = getServiceHub().signInitialTransaction(transactionBuilder);

//        subFlow(new FinalityFlow(signedTransaction, Collections.emptyList()));

        System.out.println("Linear ID "+ toDoState.getLinearId());

        return toDoState.getLinearId().toString();
    }
}
