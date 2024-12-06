package ipp.estg.grupo9.services;

import io.camunda.zeebe.client.ZeebeClient;

public class MessageService {
    private final ZeebeClient zeebeClient;

    public MessageService(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    public void publishMessage(String messageName, String correlationKey, Object variables) {
        zeebeClient.newPublishMessageCommand()
                .messageName(messageName) // Match the name in your BPMN
                .correlationKey(correlationKey) // Process instance key or business key
                .variables(variables) // Optional variables to pass with the message
                .send()
                .join();

        System.out.println("Message '" + messageName + "' published with correlation key: " + correlationKey);
    }
}
