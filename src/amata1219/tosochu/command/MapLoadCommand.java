package amata1219.tosochu.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import amata1219.tosochu.Tosochu;
import amata1219.tosochu.location.ImmutableLocation;
import amata1219.tosochu.playerdata.Permission;
import amata1219.tosochu.storage.MapSettingsStorage;

public class MapLoadCommand implements Command {

	@Override
	public String getName() {
		return "mapload";
	}

	@Override
	public Permission getPermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public void onCommand(Player sender, Args args) {
		String worldName = args.next();
		if(Bukkit.getWorld(worldName) != null){
			warn(sender, "指定されたマップは既にロードされています。");
			return;
		}

		MapSettingsStorage storage = Tosochu.getPlugin().getMapSettingsStorage();
		if(!storage.isSettingsExist(worldName)){
			warn(sender, "指定されたマップ(" + worldName + ")は設定ファイルが存在しないためロード出来ません。");
			return;
		}

		//ワールドをロードする
		World world = new WorldCreator(worldName).createWorld();

		Bukkit.getServer().broadcastMessage(world == null ? "ワールドが存在しない又は既に読み込まれています。" : "ワールドが正しく読み込まれました。");

		//全プレイヤーを初期スポーン地点にテレポートさせる
		ImmutableLocation location = storage.get(worldName).getFirstSpawnLocation();
		for(Player player : Bukkit.getOnlinePlayers())
			location.teleport(world, player);

		info(sender, "指定されたマップ(" + worldName + ")をロードしました。");
	}

}