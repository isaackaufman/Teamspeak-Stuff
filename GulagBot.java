/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.logging.Level;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerQueryInfo;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class GulagBot {

    public static void main(String[] args) {
        new GulagBot();
    }

    public GulagBot() {
        final TS3Config config = new TS3Config();
        config.setHost("127.0.0.1");
        config.setDebugLevel(Level.ALL);
        config.setLoginCredentials("serveradmin", "password");

        final TS3Query query = new TS3Query(config);
        query.connect();
        HashMap<ChannelProperty, String> channelProps = new HashMap<>();
        channelProps.put(ChannelProperty.CHANNEL_NEEDED_TALK_POWER, "100");
        final TS3Api api = query.getApi();
        api.selectVirtualServerById(1);
        api.setNickname("GulagBot");
        
        // GulagBot should not change channels, so this is okay for now
        final ServerQueryInfo sqi = api.whoAmI();
        final int clientId = sqi.getId();

        api.createChannel("Gulag", channelProps);

        api.registerAllEvents();
        api.addTS3Listeners(new TS3Listener() {

            public void onClientMoved(ClientMovedEvent e) {
                api.sendChannelMessage("Welcome to the Gulag, " + api.getClientInfo(e.getClientId()).getNickname() + "!");
            }

            public void onTextMessage(TextMessageEvent e) {
                if (e.getTargetMode() == TextMessageTargetMode.CHANNEL && e.getInvokerId() != clientId) {
                    api.sendChannelMessage("Quiet in the Gulag!");
                }
            }

            public void onServerEdit(ServerEditedEvent e) {

            }

            public void onClientLeave(ClientLeaveEvent e) {
                api.moveClient(e.getClientId(), sqi.getChannelId());
                api.sendChannelMessage("Nice try, " + api.getClientInfo(e.getClientId()).getNickname() + ", but there is no escaping the Gulag!");
            }

            public void onClientJoin(ClientJoinEvent e) {
            }

            public void onChannelEdit(ChannelEditedEvent e) {

            }

            public void onChannelDescriptionChanged(
                    ChannelDescriptionEditedEvent e) {
            }

            public void onChannelCreate(ChannelCreateEvent e) {

            }

            public void onChannelDeleted(ChannelDeletedEvent e) {

            }

            public void onChannelMoved(ChannelMovedEvent e) {

            }

            public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {

            }
        });
    }

}