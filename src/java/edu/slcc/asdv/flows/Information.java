package edu.slcc.asdv.flows;

import java.io.Serializable;
import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;

public class Information implements Serializable {
    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder) {
        String flowId = "information";
        flowBuilder.id("", flowId);
        flowBuilder.viewNode(flowId, "/" + flowId + "/" + flowId + ".xhtml").markAsStartNode();
        flowBuilder.viewNode("confirm-id", "/" + flowId + "/confirm.xhtml");
        flowBuilder.viewNode("thanks-id", "/" + flowId + "/thanks.xhtml");

        flowBuilder.returnNode("taskFlowReturnIndex").fromOutcome("/cart");
        flowBuilder.returnNode("taskFlowReturnDone").fromOutcome("#{informationBean.returnValue}");

        flowBuilder.flowCallNode("callCreditCard").flowReference("", "creditcard").
                outboundParameter("firstNameParam", "#{informationBean.firstName}").
                outboundParameter("lastNameParam", "#{informationBean.lastName}").
                outboundParameter("addressParam", "#{informationBean.address}").
                outboundParameter("cityParam", "#{informationBean.city}").
                outboundParameter("stateParam", "#{informationBean.state}").
                outboundParameter("zipParam", "#{informationBean.zip}").
                outboundParameter("phoneParam", "#{informationBean.phone}");

        return flowBuilder.getFlow();
    }
}
