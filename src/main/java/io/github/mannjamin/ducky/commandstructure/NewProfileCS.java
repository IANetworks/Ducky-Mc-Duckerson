package io.github.mannjamin.ducky.commandstructure;

import io.github.mannjamin.ducky.McDucky;
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

    private final String BACKGROUND_000 = "/profile/profile_background000.png";
    private final String MONEY_BAG = "/profile/moneybag.png";

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
            URL avatarURL = new URL(member.getUser().getAvatarUrl());
            String nickname = member.getEffectiveName();
            UserProfile up;
            up = dbMan.getUserProfile(member.getGuild().getIdLong(), member.getUser().getIdLong());
            //obtain all the strings.. ALL the strings
            String title = up.getTitle();
            String rankName = up.getRankName();
            String guildName = member.getGuild().getName();
            String balance = up.getBalance().toString();
            String tableFlip = up.getFlipped().toString();
            String tableUnflip = up.getUnflipped().toString();
            String werewolfWins = up.getWerewolfWins().toString();
            String werewolfGames = up.getWerewolfGames().toString();
            //TODO TotalGames/TotalGamesWon
            String totalWins = up.getGameWins().toString();
            String totalGames = up.getTotalGames().toString();
            String rankExp = up.getRankExp().toString();
            String rankExpTotal;
            if (up.getRankExp() != null)
                rankExpTotal = up.getLevel().toString() + " / " + rankExp;
            else
                rankExpTotal = up.getLevel().toString() + " / ∞";
            String points = up.getPoints().toString();
            String tableFlipStr = "(╯°□°）╯︵ ┻━┻";
            String unflipStr = "┬─┬\uFEFF ノ( ゜-゜ノ)";

            final int padding = 10;

            boolean hasTitle = false;
            //TODO setup /profile/ folder for external profile background images

            if (title == null) title = "";
            if (!Objects.equals(title, "")) {
                hasTitle = true;
                //title = localize(channel, title);
            } else {
                title = "1";
            }


            //Get Avatar and Background
            BufferedImage profileBackgroundImg = ImageIO.read(McDucky.class.getResourceAsStream(BACKGROUND_000));
            BufferedImage moneyBag = ImageIO.read(McDucky.class.getResourceAsStream(MONEY_BAG));
            BufferedImage profileImg = new BufferedImage(profileBackgroundImg.getWidth(), profileBackgroundImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
            BufferedImage avatar = getAvatar(avatarURL);
            if (avatar == null) {
                return false;
            }

            //Start Canvas
            Graphics2D canvas = (Graphics2D) profileImg.getGraphics();
            canvas.drawImage(profileBackgroundImg, 0, 0, null); //Draw background

            //Get string size for NameTag
            canvas.setColor(Color.BLACK);
            canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
            FontMetrics fontMetrics = canvas.getFontMetrics();
            Rectangle2D nameTag = fontMetrics.getStringBounds(nickname, canvas);

            //Location for nameTag and Background
            int centerXBackground = Math.round((profileBackgroundImg.getWidth() - Math.round(nameTag.getWidth())) / 2);
            int dropYNameTagBackground = (int) Math.floor(nameTag.getHeight()) + 10;

            //Avatar Location
            int heightDropAvatar = dropYNameTagBackground + 20;
            int rightJustifiedAvatar = profileBackgroundImg.getWidth() - (int) Math.floor(avatar.getWidth()) - 20;

            //NameTagBox Location and size
            int nameTagBackgroundWidth = profileImg.getWidth() - 20;
            int centerXNameTagBG = Math.round((profileImg.getWidth() - nameTagBackgroundWidth) / 2);
            int nameTagY = padding;

            //TilteTag Location
            canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
            fontMetrics = canvas.getFontMetrics();
            Rectangle2D titleTag = fontMetrics.getStringBounds(title, canvas);
            int nameTagBackgroundHeight = (int) Math.floor(titleTag.getHeight()) + (int) Math.floor(nameTag.getHeight()) + 10;
            int dropYTitleTag = dropYNameTagBackground + (int) Math.floor(titleTag.getHeight());
            int centerXTitleTag = (int) Math.round((profileBackgroundImg.getWidth() - titleTag.getWidth()) / 2);

            //User Level Tag Location
            Rectangle2D userLevelTag = fontMetrics.getStringBounds(userLevelName, canvas);
            int userLevelY = (int) Math.round(profileBackgroundImg.getHeight() - userLevelTag.getHeight());
            int userLevelX = 10;

            //discord server name location
            Rectangle2D guildNameTag = fontMetrics.getStringBounds(guildName, canvas);
            int guildNameX = (int) Math.round(profileBackgroundImg.getWidth() - guildNameTag.getWidth()) - padding;

            //Statistics location
            canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            fontMetrics = canvas.getFontMetrics();
            Rectangle2D statTag = fontMetrics.getStringBounds("Statistic", canvas);
            int statTagYBG = heightDropAvatar + avatar.getHeight() - 2;
            int statTagYDrop = heightDropAvatar + avatar.getHeight() + (int) Math.round(statTag.getHeight()) - 2;
            int statTagBackgroundHeight = (int) Math.round(statTag.getHeight() + 10);
            int statTagX = (int) Math.round((profileBackgroundImg.getWidth() - statTag.getWidth()) / 2);

            int maxStatTagWidth;
            int maxGameTagWidth;
            int testMaxStatTagWidth;
            int testMaxGameTagWidth;
            //RankTag Locations
            canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
            fontMetrics = canvas.getFontMetrics();
            Rectangle2D rankTag = fontMetrics.getStringBounds("Rank :", canvas);
            int rankTagY = statTagYDrop + statTagBackgroundHeight + 5;
            maxStatTagWidth = (int) Math.floor(rankTag.getWidth()) + padding;

            //TODO replace with an image of coin(s?)
            int curTagY = rankTagY + (int) Math.floor(rankTag.getHeight());
            int imgCurTagY = curTagY - 15;
            Rectangle2D curTag = fontMetrics.getStringBounds(" :", canvas);
            testMaxStatTagWidth = (int) curTag.getWidth() + padding;
            maxStatTagWidth = Math.max(testMaxStatTagWidth, maxStatTagWidth);

            int tableFlipTagY = curTagY + (int) Math.floor(rankTag.getHeight());
            Rectangle2D flipTag = fontMetrics.getStringBounds(tableFlipStr + " :", canvas);
            testMaxStatTagWidth = (int) Math.floor(flipTag.getWidth()) + padding;
            maxStatTagWidth = Math.max(testMaxStatTagWidth, maxStatTagWidth);

            int unFlipTableTagY = tableFlipTagY + (int) Math.floor(rankTag.getHeight());
            Rectangle2D unflipTag = fontMetrics.getStringBounds(unflipStr + " :", canvas);
            testMaxStatTagWidth = (int) Math.floor(unflipTag.getWidth()) + padding;
            maxStatTagWidth = Math.max(testMaxStatTagWidth, maxStatTagWidth);

            int rankTagX = maxStatTagWidth - (int) Math.floor(rankTag.getWidth());
            int curTagX = maxStatTagWidth - (int) curTag.getWidth();
            int imgCurTagX = maxStatTagWidth - (moneyBag.getWidth() + (int) curTag.getWidth());
            int tableFlipTagX = maxStatTagWidth - (int) Math.floor(flipTag.getWidth());
            int unFlipTableTagX = maxStatTagWidth - (int) Math.floor(unflipTag.getWidth());

            int realStatX = maxStatTagWidth + 5;

            //Games location
            canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            fontMetrics = canvas.getFontMetrics();
            Rectangle2D gameTag = fontMetrics.getStringBounds("Games", canvas);
            int gameTagYBG = unFlipTableTagY + (int) unflipTag.getHeight() + 2;
            int gameTagYDrop = gameTagYBG + (int) Math.round(gameTag.getHeight());
            int gameTagBackgroundHeight = (int) Math.round(gameTag.getHeight() + 10);
            int gameTagX = (int) Math.round((profileBackgroundImg.getWidth() - gameTag.getWidth()) / 2);

            //Games Stat
            canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
            fontMetrics = canvas.getFontMetrics();
            Rectangle2D werewolfTag = fontMetrics.getStringBounds("Werewolf :", canvas);

            int werewolfTagY = gameTagYDrop + gameTagBackgroundHeight + 5;
            maxGameTagWidth = (int) Math.floor(werewolfTag.getWidth()) + padding;

            int totalGameTagY = werewolfTagY + (int) werewolfTag.getHeight();
            Rectangle2D totalGamesTag = fontMetrics.getStringBounds("Total Games :", canvas);
            testMaxGameTagWidth = (int) totalGamesTag.getWidth() + padding;
            maxGameTagWidth = Math.max(testMaxGameTagWidth, maxGameTagWidth);

            int pointGameTagY = totalGameTagY + (int) werewolfTag.getHeight();
            Rectangle2D pointsGameTag = fontMetrics.getStringBounds("Points :", canvas);
            testMaxGameTagWidth = (int) pointsGameTag.getWidth() + padding;
            maxGameTagWidth = Math.max(testMaxGameTagWidth, maxGameTagWidth);


            int werewolfTagX = maxGameTagWidth - (int) Math.floor(werewolfTag.getWidth());
            int totalGamesTagX = maxGameTagWidth - (int) Math.floor(totalGamesTag.getWidth());
            int pointGameTagX = maxGameTagWidth - (int) Math.floor(pointsGameTag.getWidth());

            int realGamesX = maxGameTagWidth + 5;

            //Content Background fill
            int contentBackgroundWidth = profileBackgroundImg.getWidth();
            int contentBackGroundHeight = profileBackgroundImg.getHeight() - statTagYBG - ((int) userLevelTag.getHeight() * 2);

            //place all elements onto canvas
            Color nameTagColour = new Color(0, 0, 0, 64);
            canvas.setColor(nameTagColour);
            canvas.fillRect(0, statTagYBG, contentBackgroundWidth, contentBackGroundHeight);
            canvas.fillRoundRect(centerXNameTagBG, nameTagY, nameTagBackgroundWidth, nameTagBackgroundHeight, 3, 3);
            canvas.fillRoundRect(centerXNameTagBG, statTagYBG, nameTagBackgroundWidth, statTagBackgroundHeight, 3, 3);
            canvas.fillRoundRect(centerXNameTagBG, gameTagYBG, nameTagBackgroundWidth, gameTagBackgroundHeight, 3, 3);
            //Images
            canvas.drawImage(avatar, rightJustifiedAvatar, heightDropAvatar, null);
            canvas.drawImage(moneyBag, imgCurTagX, imgCurTagY, null);

            canvas.setColor(Color.BLACK);
            //Font Size 19
            canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
            canvas.drawString(nickname, centerXBackground, dropYNameTagBackground);
            //Font Size 14
            canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            canvas.drawString("Statistic", statTagX, statTagYDrop);
            canvas.drawString("Games", gameTagX, gameTagYDrop);
            //Font Size 12
            canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
            canvas.drawString("Rank :", rankTagX, rankTagY);
            canvas.drawString(" :", curTagX, curTagY);
            canvas.drawString(tableFlipStr + " :", tableFlipTagX, tableFlipTagY);
            canvas.drawString(unflipStr + " :", unFlipTableTagX, unFlipTableTagY);
            canvas.drawString("Werewolf :", werewolfTagX, werewolfTagY);
            canvas.drawString("Total Games :", totalGamesTagX, totalGameTagY);
            canvas.drawString("Points :", pointGameTagX, pointGameTagY);
            //Font plain text - 12
            canvas.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            canvas.drawString(rankName + " (" + rankExpTotal + " exp)", realStatX, rankTagY);
            canvas.drawString(balance, realStatX, curTagY);
            canvas.drawString(tableFlip, realStatX, tableFlipTagY);
            canvas.drawString(tableUnflip, realStatX, unFlipTableTagY);
            canvas.drawString(werewolfWins + " / " + werewolfGames, realGamesX, werewolfTagY);
            canvas.drawString(totalWins + " / " + totalGames, realGamesX, totalGameTagY);
            canvas.drawString(points, realGamesX, pointGameTagY);

            //Font Size 10
            canvas.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
            canvas.drawString(userLevelName, userLevelX, userLevelY);
            canvas.drawString(guildName, guildNameX, userLevelY);
            if (hasTitle) {

                canvas.drawString(title, centerXTitleTag, dropYTitleTag);
            }


            //send final canvas to discord
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(profileImg, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            channel.sendFile(is, "profile_" + member.getUser().getId() + ".png").queue();
            return true; //successful send

        } catch (SQLException | IOException e) {
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
