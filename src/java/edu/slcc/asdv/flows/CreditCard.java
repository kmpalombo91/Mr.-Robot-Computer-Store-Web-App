package edu.slcc.asdv.flows;

import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

public class CreditCard implements Serializable {
    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {

        String flowId = "creditcard";
        flowBuilder.id("", flowId);
        flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();
        flowBuilder.viewNode("confirm-id", "/" + flowId + "/confirm.xhtml");

        flowBuilder.returnNode("taskFlowReturnInfo").fromOutcome("/information/information.xhtml");
        flowBuilder.returnNode("taskFlowReturnDone").fromOutcome("/information/thanks.xhtml");

        flowBuilder.inboundParameter("firstNameParam", "#{flowScope.firstName}")
                .inboundParameter("lastNameParam", "#{flowScope.lastName}")
                .inboundParameter("addressParam", "#{flowScope.address}")
                .inboundParameter("cityParam", "#{flowScope.city}")
                .inboundParameter("zipParam", "#{flowScope.zip}")
                .inboundParameter("stateParam", "#{flowScope.state}");

        return flowBuilder.getFlow();
    }

}
