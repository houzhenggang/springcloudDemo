package com.sensor.sellCabinet.service.impl;
import com.pig4cloud.pig.common.core.util.R;
import com.sensor.sellCabinet.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Service;


@Slf4j
@ServerEndpoint("/webSocket/{username}")
@Service("WebSocketService")
public class WebSocketServiceImpl implements WebSocketService {

	      private static Map<String, WebSocketServiceImpl> clients = new ConcurrentHashMap<String, WebSocketServiceImpl>();
	      private Session session;
	      private String username;


	    //建立连接时触发
        @OnOpen
        public void onOpen(@PathParam("username") String username, Session session) throws IOException {

            this.username = username;
            this.session = session;
            if(clients.containsKey(username)){
            	clients.remove(username);
                log.debug("已删除旧链接");
            }

            clients.put(username, this);
            log.debug("已连接");
        }


	   //关闭连接时触发
        @OnClose
        public void onClose(@PathParam("username") String username, Session session) throws IOException {
            clients.remove(username);
            log.debug("已断开");
        }


	   //收到消息时触发
        @OnMessage
        public void onMessage(String param) throws IOException {
            log.debug("已收到信息"+param);
            JSONObject jsonTo = JSONObject.fromObject(param);
            String mes = (String) jsonTo.get("message");
            if (!jsonTo.get("To").equals("All")){
                sendMessageTo(mes, jsonTo.get("To").toString());
            }else{
                sendMessageAll("给所有人");
            }
        }

        //主动发送消息
        public R sendMessage(String param) throws IOException {
        	log.debug("已收到参数"+param);
        	R R = null;
            JSONObject jsonTo = JSONObject.fromObject(param);
            //拿到消息
            String mes = (String) jsonTo.get("message");
            //拿到发送对象
            String To = (String) jsonTo.get("To");
            //是否在本机发送的标志
            boolean flag = false ;
            //发送状态
            String status = "fail" ;
            //先判断发送对象是否在本机，在本机则直接发送
            for (WebSocketServiceImpl item : clients.values()) {
                if (item.username.equals(To) ){
                	item.session.getAsyncRemote().sendText(mes);
                    flag = true;
                    status ="success";
                    log.debug("已向"+To+"发送信息");
					JSONObject retJson = new JSONObject();
					retJson.put("status", status);
					retJson.put("message", "向"+To+"发送信息:"+mes+",发送"+status);
                    R = new R(retJson.toString());
                }
            }
             return R;
        }

        //显示在线连接
        public static JSONObject showOnline() throws IOException{
            JSONObject jsonTo = new JSONObject();
            for (WebSocketServiceImpl item : clients.values()) {
            	jsonTo.put( item.username,"在线");
            }
            jsonTo.put("onLineNumber", WebSocketServiceImpl.clients.size());
            return jsonTo;
        }


        @OnError
        public void onError(Session session, Throwable error) {
            error.printStackTrace();
        }



        public static void sendMessageTo(String message, String To) throws IOException {

            // session.getBasicRemote().sendText(message);

            //session.getAsyncRemote().sendText(message);

            for (WebSocketServiceImpl item : clients.values()) {

                if (item.username.equals(To) ){
                    item.session.getAsyncRemote().sendText(message);
                    log.debug("已向"+To+"发送信息");
                }
            }

        }


        public static void sendMessageAll(String message) throws IOException {
            for (WebSocketServiceImpl item : clients.values()) {
                item.session.getAsyncRemote().sendText(message);
            }
        }

        public static synchronized Map<String, WebSocketServiceImpl> getClients() {
            return clients;
        }

}
