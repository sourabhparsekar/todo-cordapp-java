package com.template.states;

import com.template.contracts.DummyToDoCommand;
import com.template.contracts.TemplateContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.LinearState;
import net.corda.core.contracts.UniqueIdentifier;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@BelongsToContract(TemplateContract.class)
public class ToDoState implements LinearState {

    private final Party assignedBy;
    private final Party assignedTo;
    private final String taskDescription;
    private final UniqueIdentifier linearId = new UniqueIdentifier();

    public ToDoState(Party assignedBy, Party assignedTo, String taskDescription) {
        this.assignedBy = assignedBy;
        this.assignedTo = assignedTo;
        this.taskDescription = taskDescription;
    }

    public Party getAssignedBy() {
        return assignedBy;
    }

    public Party getAssignedTo() {
        return assignedTo;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(assignedBy, assignedTo);
    }

    @NotNull
    @Override
    public UniqueIdentifier getLinearId() {
        return linearId;
    }
}
