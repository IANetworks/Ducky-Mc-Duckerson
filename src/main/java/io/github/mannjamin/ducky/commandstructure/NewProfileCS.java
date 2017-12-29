package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.SharedContainer;
import io.github.mannjamin.ducky.database.manager.data.UserProfile;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The type Profile cs.
 */
public class NewProfileCS extends CommandStructure {

    /**
     * Instantiates a new Profile cs.
     *
     * @param container           the container
     * @param commandName         the command name
     * @param commandID           the command id
     * @param commandDefaultLevel the command default level
     */
    public NewProfileCS(SharedContainer container, String commandName, int commandID,
                        int commandDefaultLevel) {
        super(container, commandName, commandID, commandDefaultLevel);
    }

    @Override
    public void execute(Member author, User authorUser, MessageChannel channel, Message message, String parameters,
                        Map<String, CommandStructure> commandList) {
        Long guildID = author.getGuild().getIdLong();
        String prefix = dbMan.getPrefix(guildID);


        List<User> mentionedUsers = message.getMentionedUsers();
        if (hasPermission(author)) {
            message.addReaction("⏳").queue();
            if (mentionedUsers.isEmpty()) {
                Integer userLevel = getPermissionLevel(author);
                String userLevelName = dbMan.getLevelName(guildID, userLevel);
                if (sendProfile(author, channel, prefix, userLevelName)) {
                    message.addReaction("✅").queue();
                } else {
                    message.addReaction("❌").queue();
                }
            } else {
                //We can't loop through the whole list of mentioned users, so we only going to grab the first mentioned user and ignored the rest
                User user = mentionedUsers.get(0);
                Member userMember = author.getGuild().getMemberById(user.getIdLong());

                if (userMember == null) {
                    channel.sendMessage(localize(channel, "command.profile.user_not_found", user.getAsMention())).queue();
                } else {
                    Integer userLevel = getPermissionLevel(userMember);
                    String userLevelName = dbMan.getLevelName(guildID, userLevel);
                    if (sendProfile(userMember, channel, prefix, userLevelName, author)) {
                        message.addReaction("✅").queue();
                    } else {
                        message.addReaction("❌").queue();
                    }
                }
            }
        }
    }

    private boolean sendProfile(Member member, MessageChannel channel, String prefix, String userLevelName) {
        return sendProfile(member, channel, prefix, userLevelName, null);
    }

    private boolean sendProfile(Member member, MessageChannel channel, String prefix, String userLevelName, Member requestedBy) {
        try {
            URI profileBackgroundUri = new URI("profile/profile_background000.png");
            URL avatarURL = new URL(member.getUser().getAvatarUrl());
            BufferedImage avatar = getAvatar(avatarURL);
            String nickname = member.getEffectiveName();
            UserProfile up;
            String title = null;
            String rank = null;
            String guildName = member.getGuild().getName();
            boolean hasTitle = false;

            up = dbMan.getUserProfile(member.getGuild().getIdLong(), member.getUser().getIdLong());
            title = up.getTitle();
            rank = up.getRankName();
            if (title == null) title = "";
            if (!Objects.equals(title, "")) {
                hasTitle = true;
            } else {
                title = "1";
            }

            if (avatar == null) {
                return false;
            }
            BufferedImage profileBackgroundImg = ImageIO.read(new File(profileBackgroundUri));
            if (profileBackgroundImg == null)
                return false;
            BufferedImage profileImg = new BufferedImage(profileBackgroundImg.getWidth(), profileBackgroundImg.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D canvas = (Graphics2D) profileImg.getGraphics();
            canvas.drawImage(profileBackgroundImg, 0, 0, null);
            canvas.setColor(Color.BLACK);
            canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));

            FontMetrics fontMetrics = canvas.getFontMetrics();
            Rectangle2D nameTag = fontMetrics.getStringBounds(nickname, canvas);

            int centerXBackground = Math.round((profileBackgroundImg.getWidth() - Math.round(nameTag.getWidth())) / 2);
            int dropYNameTagBackground = (int) Math.floor(nameTag.getHeight()) + 10;

            int heightDropAvatar = dropYNameTagBackground + 20;
            int rightJustifiedAvatar = profileBackgroundImg.getWidth() - (int) Math.floor(avatar.getWidth()) - 20;


            int nameTagBackgroundWidth = profileImg.getWidth() - 20;
            int nameTagBackgroundHeight = (int) Math.floor(nameTag.getHeight()) + 10;
            int centerXNameTagBG = Math.round((profileImg.getWidth() - nameTagBackgroundWidth) / 2);
            Color nameTagColour = new Color(0, 0, 0, 64);

            canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
            fontMetrics = canvas.getFontMetrics();
            Rectangle2D titleTag = fontMetrics.getStringBounds(title, canvas);
            nameTagBackgroundHeight = (int) Math.floor(titleTag.getHeight()) + nameTagBackgroundHeight;
            int dropYTitleTag = dropYNameTagBackground + (int) Math.floor(titleTag.getHeight());
            int centerXTitleTag = (int) Math.round((profileBackgroundImg.getWidth() - titleTag.getWidth()) / 2);

            canvas.setColor(Color.BLACK);
            if (hasTitle) {
                canvas.drawString(title, centerXTitleTag, dropYTitleTag);
            }

            Rectangle2D userLevelTag = fontMetrics.getStringBounds(userLevelName, canvas);
            int userLevelY = (int) Math.round(profileBackgroundImg.getHeight() - userLevelTag.getHeight());
            canvas.drawString(userLevelName, 10, userLevelY);
            Rectangle2D guildNameTag = fontMetrics.getStringBounds(guildName, canvas);
            int guildNameX = (int) Math.round(profileBackgroundImg.getWidth() - guildNameTag.getWidth()) - 10;
            canvas.drawString(guildName, guildNameX, userLevelY);

            canvas.setColor(nameTagColour);
            canvas.fillRoundRect(centerXNameTagBG, 10, nameTagBackgroundWidth, nameTagBackgroundHeight, 3, 3);

            canvas.setColor(Color.BLACK);
            canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
            canvas.drawString(nickname, centerXBackground, dropYNameTagBackground);
            canvas.drawImage(avatar, rightJustifiedAvatar, heightDropAvatar, null);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(profileImg, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());

            channel.sendFile(is, "profile_" + member.getUser().getId() + ".png").queue();
            return true;

        } catch (URISyntaxException | SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private BufferedImage getAvatar(URL avatarURL) {
        URLConnection openConnection = null;
        boolean check = false;
        try {
            openConnection = avatarURL.openConnection();
            openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            openConnection.connect();
            check = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (openConnection.getContentLength() > 8000000) {
            System.out.println(" file size is too big.");
            check = false;
        }
        if (check) {
            BufferedImage img = null;
            try {
                InputStream in = new BufferedInputStream(openConnection.getInputStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1 != (n = in.read(buf))) {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();
                img = ImageIO.read(new ByteArrayInputStream(response));
                return img;
            } catch (Exception e) {
                System.out.println(" couldn't read an image from this link.");
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String help(Long guildID, MessageChannel channel) {
        return localize(channel, "command.profile.help", dbMan.getPrefix(guildID), commandName);

    }

}
