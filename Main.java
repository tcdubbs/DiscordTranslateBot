import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NjM3NzM0MjMxOTEyNzQyOTMx.XbSi3w.4v2MoPI_4APhttQ_O8s7ZEBAX3w";
        builder.setToken(token);
        builder.addEventListeners(new Main());
        builder.build();

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getAuthor().isBot()){
            return;
        }

        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay());
        if(event.getMessage().getContentRaw().equals("t!ping")){
            event.getChannel().sendMessage("Pong!").queue();
        }
    }
}
