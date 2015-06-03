import java.util.logging.Level;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
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
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public class testBot {

	public static void main(String[] args) {
		new testBot();
	}

	public testBot() {
		final TS3Config config = new TS3Config();
		config.setHost("127.0.0.1");
		config.setDebugLevel(Level.ALL);
		config.setLoginCredentials("serveradmin", "tester88");

		final TS3Query query = new TS3Query(config);
		query.connect();

		final TS3Api api = query.getApi();
		api.selectVirtualServerById(1);
		api.setNickname("PutPutBot");
		final int clientId = api.getClientByName("PutPutBot").get(0).getId();
		api.moveClient(8);	
		api.sendChannelMessage("PutPutBot is online!");

		api.registerAllEvents();
		api.addTS3Listeners(new TS3Listener() {

			public void onClientMoved(ClientMovedEvent e) {

			}

			public void onTextMessage(TextMessageEvent e) {
				if (e.getTargetMode() == TextMessageTargetMode.CHANNEL && e.getInvokerId() != clientId)
				{
					if (e.getMessage().equals("!ping"))
					{
						api.sendChannelMessage("pong");
					}
					if (e.getMessage().equals("!banfaggot") && e.getInvokerId() == api.getClientByName("Toxiktoe").get(0).getId())
					{
						api.sendChannelMessage("Banning faggot. Beep Boop.");
						api.banClient(api.getClientByName("Michael").get(0).getId(), 5, "Faggot.");
					}
					if (e.getMessage().toLowerCase().contains("hello"))
					{
						api.sendChannelMessage("Hello " + e.getInvokerName());
					}
				}
			}

			public void onServerEdit(ServerEditedEvent e) {

			}

			public void onClientLeave(ClientLeaveEvent e) {

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
	
