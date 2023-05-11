package nnz.nanumservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.eello.nnz.common.kafka.KafkaMessage;
import io.github.eello.nnz.common.kafka.KafkaMessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nnz.nanumservice.dto.NanumTagDTO;
import nnz.nanumservice.dto.ShowDTO;
import nnz.nanumservice.dto.TagDTO;
import nnz.nanumservice.dto.UserDTO;
import nnz.nanumservice.entity.*;
import nnz.nanumservice.repository.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ShowRepository showRepository;
    private final TagRepository tagRepository;
    private final NanumTagRepository nanumTagRepository;
    private final UserRepository userRepository;
    private final NanumRepository nanumRepository;

    @Transactional
    @KafkaListener(topics = {"dev-show", "dev-show-admin"}, groupId = "nanum-service-1")
    public void getShowMessage(String message) throws JsonProcessingException {
        KafkaMessage<ShowDTO> kafkaMessage = KafkaMessageUtils.deserialize(message, ShowDTO.class);
        log.info("consume message: {}", message);
        log.info("kafkaMessage.getType() = {}", kafkaMessage.getType());
        log.info("kafkaMessage.getBody() = {}", kafkaMessage.getBody());

        if (kafkaMessage.getType() == KafkaMessage.KafkaMessageType.CREATE) {
            Show show = Show.of(kafkaMessage.getBody());
            showRepository.save(show);
        }
    }

//    @Transactional
//    @KafkaListener(topics = "dev-tag", groupId = "nanum-service-2")
//    public void getTagMessage(String message) throws JsonProcessingException {
//        KafkaMessage<TagDTO> kafkaMessage = KafkaMessageUtils.deserialize(message, TagDTO.class);
//        log.info("consume message: {}", message);
//        log.info("kafkaMessage.getType() = {}", kafkaMessage.getType());
//        log.info("kafkaMessage.getBody() = {}", kafkaMessage.getBody());
//
//        if (kafkaMessage.getType() == KafkaMessage.KafkaMessageType.CREATE) {
//            Tag tag = Tag.of(kafkaMessage.getBody());
//            tagRepository.save(tag);
//        }
//    }

//    @Transactional
//    @KafkaListener(topics = "dev-nanumtag", groupId = "nanum-service-3")
//    public void getNanumTagMessage(String message) throws JsonProcessingException {
//        KafkaMessage<NanumTagDTO> kafkaMessage = KafkaMessageUtils.deserialize(message, NanumTagDTO.class);
//        log.info("consume message: {}", message);r
//        log.info("kafkaMessage.getType() = {}", kafkaMessage.getType());
//        log.info("kafkaMessage.getBody() = {}", kafkaMessage.getBody());
//
//        if (kafkaMessage.getType() == KafkaMessage.KafkaMessageType.CREATE) {
//            // todo: error handling
//            Nanum nanum = nanumRepository.findById(kafkaMessage.getBody().getNanumId()).orElseThrow();
//            Tag tag = tagRepository.findById(kafkaMessage.getBody().getTag()).orElseThrow();
//            NanumTag nanumTag = NanumTag.of(kafkaMessage.getBody(), nanum, tag);
//            nanumTagRepository.save(nanumTag);
//        }
//    }

    @KafkaListener(topics = "dev-user", groupId = "nanum-service-4")
    public void getUserMessage(String message) throws JsonProcessingException {
        KafkaMessage<UserDTO> kafkaMessage = KafkaMessageUtils.deserialize(message, UserDTO.class);
        log.info("consume message: {}", message);
        log.info("kafkaMessage.getType() = {}", kafkaMessage.getType());
        log.info("kafkaMessage.getBody() = {}", kafkaMessage.getBody());

        if (kafkaMessage.getType() == KafkaMessage.KafkaMessageType.CREATE) {
            User user = User.of(kafkaMessage.getBody());
            userRepository.save(user);
        }
    }
}