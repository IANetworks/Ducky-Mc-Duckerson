package bot;
/*
 *     Copyright 2015-2017 Austin Keener & Michael Ritter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Random;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class McDucky extends ListenerAdapter
{
	private File configFile = new File("config.properties");
	private Properties configProps;
	
	private void loadProperties() throws IOException {
		configProps = new Properties();
		
		// loads properties from file
		InputStream inputStream = new FileInputStream(configFile);
		configProps.load(inputStream);
		inputStream.close();
	}
	
	//Our setup. My god, Abby is terrible at commenting
	public McDucky() {

    	//Load properties from file
    	try
    	{
    		loadProperties();
    	} catch (IOException ex) {
    		System.out.printf("there's no config file, creating file.");
    		OutputStream outputStream;
			try {
				configProps.setProperty("bot_token", "");
				outputStream = new FileOutputStream(configFile);
				configProps.store(outputStream, "Info here");
	    		outputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.printf("Set values in config.propteries before running bot.");
			return;
    		
    	}
    	
    		//Load token
    		String botToken = configProps.getProperty("bot_token");
    		if (botToken.isEmpty())
    		{
    			//We GOT to have this value
    			System.out.printf("Bot Token Cannot be empty. Set bot token in the config.properties");
    			return;
    		}
    		
        //We construct a builder for a BOT account. If we wanted to use a CLIENT account
        // we would use AccountType.CLIENT
        try
        {
            JDA jda = new JDABuilder(AccountType.BOT)
                    .setToken(botToken)           //The token of the account that is logging in.
                    .addEventListener(new MessageListener())  //An instance of a class that will handle events.
                    .buildBlocking();  //There are 2 ways to login, blocking vs async. Blocking guarantees that JDA will be completely loaded.
        }
        catch (LoginException e)
        {
            //If anything goes wrong in terms of authentication, this is the exception that will represent it
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            //Due to the fact that buildBlocking is a blocking method, one which waits until JDA is fully loaded,
            // the waiting can be interrupted. This is the exception that would fire in that situation.
            //As a note: in this extremely simplified example this will never occur. In fact, this will never occur unless
            // you use buildBlocking in a thread that has the possibility of being interrupted (async thread usage and interrupts)
            e.printStackTrace();
        }
        catch (RateLimitedException e)
        {
            //The login process is one which can be ratelimited. If you attempt to login in multiple times, in rapid succession
            // (multiple times a second), you would hit the ratelimit, and would see this exception.
            //As a note: It is highly unlikely that you will ever see the exception here due to how infrequent login is.
            e.printStackTrace();
        }
	}
	
    /**
     * This is the method where the program starts.
     */
    public static void main(String[] args)
    {
    	new McDucky();
    }

    
}