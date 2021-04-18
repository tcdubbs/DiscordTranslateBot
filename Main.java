import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Main extends ListenerAdapter {

    boolean BotOn = true;

    public static void main(String[] args) throws LoginException {

        try
        {
            JDA jda = new JDABuilder("BotToken")
                    .addEventListeners(new Main())  // An instance of a class that will handle events.
                    .build();
            jda.awaitReady();
            System.out.println("Finished Building JDA!");
        }
        catch (LoginException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }




    }

    private static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbx-3ZfNO86r5WRa74NXOaZyl5ezINXTtzeLM4prdYLFL_1LJV8/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
// &#39;
    public String LoseQuote(String str){
        String astr = str;
        if(astr.length() <= 5){
            return str;
        }
        for(int i = 0;i < astr.length() - 6; i++){
            if(astr.substring(i,i+6).equals("&quot;")){
                astr = astr.substring(0,i) + "\"" + astr.substring(i+6);
            }
        }
        for(int i = 0;i < astr.length() - 5; i++){
            if(astr.substring(i,i+5).equals("&#39;")){
                astr = astr.substring(0,i) + "\'" + astr.substring(i+5);
            }
        }

        return astr;
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String msg = message.getContentDisplay();
        MessageChannel channel = event.getChannel();
        System.out.println("We received a message from " + event.getAuthor().getName());
        if(msg.equals("t!toggle") && (event.getAuthor().getName().equals("Toby Werner") || event.getAuthor().getName().equals("jas1284")))
        {
            if (BotOn) {
                BotOn = false;
            } else {
                BotOn = true;
            }
            channel.sendMessage("Toggle: " + BotOn).queue();
        } else if(msg.equals("t!toggle") && ! event.getAuthor().getName().equals("Toby Werner")){
            channel.sendMessage("Sorry, you are not authorized to do that. If you think this is a mistake, please contact the owner.").queue();
        }
        if ((event.getAuthor().getName().equals("Toby Werner"))){ //msg.substring(0,4).equals("t!say") &&
            channel.sendMessage("Received");
            event.getChannel().deleteMessageById(event.getMessageId());
            //channel.sendMessage(msg.substring(0,4));
        }
        if (event.getAuthor().isBot() || !BotOn) {
            return;
        }
        if (msg.equals("t!ping")) {
            channel.sendMessage("pong!").queue();
        }
        String translation = "";
        try {
            translation = translate("cn", "en", msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(LoseQuote(translation).toLowerCase().equals(msg.toLowerCase())){
            return;
        }
        channel.sendMessage(event.getAuthor().getName() + ": " + LoseQuote(translation)).queue();
    }
}

// sus, oof;
