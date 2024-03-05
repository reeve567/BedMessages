package dev.reeve.bedmessages

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerBedLeaveEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

@Suppress("unused")
class BedMessages: JavaPlugin(), Listener {
	private val players = hashSetOf<UUID>()
	
	override fun onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this)
	}
	
	override fun onDisable() {
		HandlerList.unregisterAll(this as Listener)
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	fun onEnterBed(e: PlayerBedEnterEvent) {
		players.add(e.player.uniqueId)
		
		if (e.bedEnterResult != PlayerBedEnterEvent.BedEnterResult.OK) return
		
		server.broadcastMessage("&c${e.player.displayName} &6has entered their bed. &7(&a${players.size}&7/&a${server.onlinePlayers.size}&7)".convert())
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	fun onLeaveBed(e: PlayerBedLeaveEvent) {
		players.remove(e.player.uniqueId)

		if (e.player.world.time >= 23000 ) return
		
		server.broadcastMessage("&c${e.player.displayName} &6has left their bed. &7(&a${players.size}&7/&a${server.onlinePlayers.size}&7)".convert())
	}
	
	private fun String.convert(): String {
		return ChatColor.translateAlternateColorCodes('&', this)
	}
}