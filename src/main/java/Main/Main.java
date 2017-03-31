/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Main;

import Listener.CommandListener;
import Command.*;
import Config.*;
import Listener.*;
import Main.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.*;
import net.dv8tion.jda.core.entities.Game;

import java.util.HashMap;
import javax.security.auth.login.LoginException;


/**
 *
 * @author liaoyilin
 */
public class Main {

    public static JDA jda;
    public static final CommandParser parser = new CommandParser();
    public static final String BOT_TOKEN = "Mjk0MzI3Nzg1NTEyNzYzMzky.C7Thog.1_sklRqpnW8ECdjQbwDYzL9Um70";
    public static HashMap<String, Command> commands = new HashMap<String, Command>();
    public static long timeStart = 0;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .addListener(new CommandListener())
                    .setToken(BOT_TOKEN)
                    .buildBlocking();
            
            timeStart = System.currentTimeMillis();
            
            jda.getPresence().setGame(Game.of(Prefix.getDefaultPrefix() + "help | Developed by Ayy™"));
            
            jda.setAutoReconnect(true);
        } catch (LoginException | IllegalArgumentException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        }
        
        // Information Commands
        commands.put("help", new HelpCommand());
        commands.put("h", new HelpCommand());
        commands.put("invite", new InviteCommand());
        
        commands.put("botinfo", new InfoBotCommand());
        commands.put("bi", new InfoBotCommand());
        commands.put("serverinfo", new InfoServerCommand());
        commands.put("si", new InfoServerCommand());
        commands.put("channelinfo", new InfoChannelCommand());
        commands.put("ci", new InfoChannelCommand());
        commands.put("userinfo", new InfoUserCommand());
        commands.put("ui", new InfoUserCommand());
        
        commands.put("prefix", new PrefixCommand());
        commands.put("ping", new PingCommand());
        
        commands.put("about", new AboutCommand());
        commands.put("support", new SupportCommand());
        
        // Moderation Commands
        commands.put("prune", new PruneCommand());
        commands.put("p", new PruneCommand());
        
        commands.put("kick", new KickCommand());
        commands.put("k", new KickCommand());
        
        commands.put("ban", new BanCommand());
        commands.put("b", new BanCommand());
        commands.put("unban", new UnbanCommand());
        commands.put("ub", new UnbanCommand());
        
        //Utility Commands
        commands.put("number", new NumberCommand());
        commands.put("num", new NumberCommand());
        commands.put("n", new NumberCommand());
        
        commands.put("math", new MathCommand());
        commands.put("calc", new MathCommand());
        commands.put("m", new MathCommand());
        
        commands.put("weather", new WeatherCommand());
        commands.put("w", new WeatherCommand());
        
        commands.put("search", new SearchCommand("search"));
        commands.put("google", new SearchCommand("google"));
        commands.put("g", new SearchCommand("google"));
        commands.put("wiki", new SearchCommand("wiki"));
        commands.put("urban", new SearchCommand("ub"));
        commands.put("github", new SearchCommand("git"));
        commands.put("git", new SearchCommand("git"));
        
        commands.put("image", new ImageCommand("image"));
        commands.put("imgur", new ImageCommand("imgur"));
        commands.put("imgflip", new ImageCommand("imgflip"));
        commands.put("gif", new ImageCommand("gif"));
        commands.put("meme", new ImageCommand("meme"));
        
        commands.put("8ball", new EightBallCommand());
        commands.put("face", new FaceCommand());
        commands.put("f", new FaceCommand());
        
        // Music Commands
        commands.put("join", new JoinCommand());
        commands.put("j", new JoinCommand());
        commands.put("leave", new LeaveCommand());
        commands.put("l", new LeaveCommand());
        
        //Restricted Commands
        commands.put("shutdown", new ShutDownCommand());
        commands.put("source", new SourceCommand());
    }
    
    //HandleCommand 1 for Server Message Commands
    public static void handleCommand(CommandParser.CommandContainer cmd)
    {
        if(commands.containsKey(cmd.invoke)) {
            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
        
            if(safe) {
                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }
            else {
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }
        }
    }
}