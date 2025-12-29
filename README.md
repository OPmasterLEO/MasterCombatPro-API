# MasterCombat API

API for integrating with MasterCombatPro plugin.

## Installation

### Gradle

If you're developing a plugin in the same multi-project build, add:

```gradle
dependencies {
	compileOnly project(":api")
}
```

If you consume the published artifact, use `compileOnly` with the published coordinates.

## Getting Started

### Accessing the API

```java
import net.opmasterleo.combat.api.MasterCombatAPIProvider;
import net.opmasterleo.combat.api.MasterCombatAPI;

public class MyPlugin extends JavaPlugin {
	private MasterCombatAPI api;

	@Override
	public void onEnable() {
		if (getServer().getPluginManager().getPlugin("MasterCombat") != null) {
			api = MasterCombatAPIProvider.getAPI();
		}
	}
}
```

## API Usage

Example operations provided by the API:

```java
MasterCombatAPI api = MasterCombatAPIProvider.getAPI();

// Tag/untag a player by UUID
api.tagPlayer(player.getUniqueId());
api.untagPlayer(player.getUniqueId());

// Query state
String state = api.getMasterCombatState(player.getUniqueId()); // "Fighting" or "Idle"
int secondsLeft = api.getRemainingCombatTime(player.getUniqueId());
boolean glowing = api.isPlayerGlowing(player.getUniqueId());
```

## Events

MasterCombatPro exposes its own events (for example `MasterCombatLoadEvent`). Register listeners normally via Bukkit's event system.

## Support

If you need help integrating, open an issue in the project repository or contact the maintainers.
