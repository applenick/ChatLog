package tc.oc.occ.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatLog extends JavaPlugin implements Listener {

  @Override
  public void onEnable() {
    getServer().getPluginManager().registerEvents(this, this);
  }

  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
    String strippedFormat = ChatColor.stripColor(event.getFormat());

    // Custom format for private messages
    if (strippedFormat != null) {
      if (strippedFormat.startsWith("From")) {
        logMessage(String.format(ChatColor.GRAY + "From %s:", event.getPlayer().getDisplayName()));
        return;
      } else if (strippedFormat.startsWith("To")) {
        logMessage(
            String.format(
                ChatColor.GRAY + "To %s: %s",
                event.getPlayer().getDisplayName(),
                event.getMessage()));
        return;
      }
    }

    // Log all other chat messages
    String msg =
        String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage());
    logMessage(msg);
  }

  private void logMessage(String message) {
    Bukkit.getConsoleSender().sendMessage(message);
  }
}
