package com.trilogyed.levelupqueueconsumer;

import com.trilogyed.levelupqueueconsumer.util.feign.LevelUpServiceClient;
import com.trilogyed.levelupqueueconsumer.util.message.LevelUpEntry;
import com.trilogyed.levelupqueueconsumer.viewmodels.LevelUpViewModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MessageListener {

    @Autowired
    private LevelUpServiceClient client;

    MessageListener(LevelUpServiceClient client){
        this.client = client;
    }

    @RabbitListener(queues = LevelUpQueueConsumerApplication.QUEUE_NAME)
    public void receiveMessage(LevelUpEntry msg) {
        System.out.println(msg.toString());
        LevelUpViewModel account = new LevelUpViewModel();

            account.setLevelUpId(msg.getLevelUpId());
            account.setCustomerId(msg.getCustomerId());
            account.setPoints(msg.getPoints());
            account.setMemberDate(LocalDate.parse(msg.getMemberDate()));

            client.updateAccount(account.getLevelUpId(), account);
    }

}
