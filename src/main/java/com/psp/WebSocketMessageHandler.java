package com.psp;

import com.psp.protobuf.DataModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WebSocketMessageHandler  implements WebSocketHandler, SubProtocolCapable {

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

        log.info("Session Established Id[" + webSocketSession.getId() + "], Protocol[" + webSocketSession.getAcceptedProtocol() + "]");
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        log.info("getId:" + webSocketSession.getId());
        log.info("getAcceptedProtocol:" + webSocketSession.getAcceptedProtocol());
        log.info("isOpen:" + webSocketSession.isOpen());
        log.info("getRemoteAddress:" + webSocketSession.getRemoteAddress());
        log.info("getBinaryMessageSizeLimit:" + webSocketSession.getBinaryMessageSizeLimit());
        log.info("payload:" + webSocketMessage.getPayload());
        log.info("payloadLength:" + webSocketMessage.getPayloadLength());
        log.info("isLast:" + webSocketMessage.isLast());
        DataModel.DataMessageList.Builder builder = DataModel.DataMessageList.newBuilder();



        ByteBuffer byteBuffer;
        WebSocketMessage msg;
        if (webSocketMessage instanceof TextMessage) {
            byteBuffer = ByteBuffer.wrap(((TextMessage) webSocketMessage).asBytes());
            msg = new BinaryMessage(byteBuffer.array());
        } else {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                for (int i = 1; i < 100; i++) {
                    DataModel.DataMessage dataMsg = DataModel.DataMessage.newBuilder().setId(i).setAddress("Đông Anh - Hà Nội - " + i).setName("Quang Hòa - " + i).build();
                    builder.addPacket(dataMsg);
                }
                ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
                DataModel.DataMessageList dataMessageList1 = builder.build();

                oos.writeObject(dataMessageList1);
                msg = new BinaryMessage(dataMessageList1.toByteArray());
            } catch (Exception e) {
                throw e;
            }
        }

        webSocketSession.sendMessage(msg);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

        log.info("Session closed Id[" + webSocketSession.getId() + "] CloseStatus:" + closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {

        return false;
    }

    /**
     * Sub Protocol list
     *
     * @return
     */
    @Override
    public List<String> getSubProtocols() {

        return WSConstants.SUB_PROTOCOL_LIST;
    }
}
