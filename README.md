# MasterCombat API

API for integrating with MasterCombatPro plugin.

## Installation

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
	<dependency>
	    <groupId>com.github.OPmasterLEO</groupId>
	    <artifactId>MasterCombatPro-API</artifactId>
	    <version>master-SNAPSHOT</version>
	</dependency>
</dependencies>
```

### Gradle

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.OPmasterLEO:MasterCombatPro-API:master-SNAPSHOT'
}
```

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

### API Methods

| Method | Description | Returns |
|--------|-------------|---------|
| `tagPlayer(UUID)` | Tag a player for combat | `void` |
| `untagPlayer(UUID)` | Remove combat tag | `void` |
| `getMasterCombatState(UUID)` | Get combat state ("Fighting"/"Idle") | `String` |
| `isPlayerGlowing(UUID)` | Check if player is glowing | `boolean` |
| `getMasterCombatStateWithGlow(UUID)` | State with glow annotation | `String` |
| `getRemainingCombatTime(UUID)` | Get remaining combat seconds | `int` |
| `getTotalCombatTime(UUID)` | Get total combat duration | `long` |
| `getCombatOpponent(UUID)` | Get opponent's UUID | `UUID` |
| `isCombatSystemEnabled()` | Check if system is active | `boolean` |
| `getActiveCombatCount()` | Count active combat players | `int` |
| `setCombatVisibility(UUID, boolean)` | Show/hide combat UI | `void` |
| `isCombatVisible(UUID)` | Check UI visibility | `boolean` |

> 💡 **Combat Visibility Feature**: Hide combat UI (messages, action bar timer) while keeping mechanics active. Perfect for custom UIs or stealth modes!

### Example Usage

```java
import net.opmasterleo.combat.api.MasterCombatAPI;
import net.opmasterleo.combat.api.MasterCombatAPIProvider;

public class Example {
    public void example() {
        // Get API instance
        MasterCombatAPI api = MasterCombatAPIProvider.get();
        
        // Basic combat operations
        api.tagPlayer(player.getUniqueId());
        api.untagPlayer(player.getUniqueId());
        
        // Check combat status
        String state = api.getMasterCombatState(player.getUniqueId());
        boolean glowing = api.isPlayerGlowing(player.getUniqueId());
        String stateWithGlow = api.getMasterCombatStateWithGlow(player.getUniqueId());
        
        // Combat timing information
        int remainingTime = api.getRemainingCombatTime(player.getUniqueId());
        long totalTime = api.getTotalCombatTime(player.getUniqueId());
        
        // Combat relationships
        UUID opponent = api.getCombatOpponent(player.getUniqueId());
        
        // System status
        boolean systemEnabled = api.isCombatSystemEnabled();
        int activeCombats = api.getActiveCombatCount();
        
        // Combat visibility control (NEW in v5.2.4+)
        api.setCombatVisibility(player.getUniqueId(), false); // Hide combat UI
        boolean isVisible = api.isCombatVisible(player.getUniqueId());
        api.setCombatVisibility(player.getUniqueId(), true); // Show combat UI
        
        // Example: Print combat status
        if (api.isCombatSystemEnabled()) {
            System.out.printf("Player %s is %s with %d seconds remaining%n",
                player.getName(),
                api.getMasterCombatStateWithGlow(player.getUniqueId()),
                api.getRemainingCombatTime(player.getUniqueId()));
                
            UUID opponentId = api.getCombatOpponent(player.getUniqueId());
            if (opponentId != null) {
                System.out.printf("Fighting against: %s%n",
                    Bukkit.getPlayer(opponentId).getName());
            }
            
            // Check if player has UI visible
            if (!api.isCombatVisible(player.getUniqueId())) {
                System.out.println("Player has combat UI hidden (combat still active)");
            }
        }
    }
}
```

## 🧩 Placeholders

MasterCombat supports placeholders in its messages and UI. These resolve automatically, and if PlaceholderAPI is installed, any PAPI placeholders inside your messages will be applied too.

Built-in placeholders provided by the plugin (all prefixed with `mastercombat_`):

| Placeholder | Description |
|-------------|-------------|
| `%mastercombat_time%` | Remaining combat time formatted as MM:SS |
| `%mastercombat_command%` | The configured “disable protection” command name (from `NewbieProtection.settings.disableCommand`, defaults to `removeprotect`) |
| `%mastercombat_prefix%` | Message prefix from config (`Messages.Prefix`) |
| `%mastercombat_duration%` | Configured combat duration in seconds (`General.duration`) |
| `%mastercombat_enabled%` | Whether combat system is currently enabled (`true`/`false`) |
| `%mastercombat_status%` | Plugin status: `Fighting` or `Idle` |
| `%mastercombat_visibility%` | Player’s combat UI visibility: `ᴏɴ` or `ᴏꜰꜰ` (lowercase) |

Notes:
- You can also use any PlaceholderAPI placeholders if PlaceholderAPI is present.
- Developers can register additional custom placeholders at runtime via `PlaceholderAPI.registerCustomPlaceholder(String placeholder, String value)`.
- `%mastercombat_time%` is only meaningful in contexts where the message is rendered with a known remaining time (e.g., actionbar or combat messages).
- Legacy tokens like `%prefix%`, `%combat_enabled%`, `%combat_duration%`, and `%command%` are still accepted for backward compatibility, but new configs should prefer the `mastercombat_` variants.

Example (config):

```yaml
Messages:
    Prefix: "&7[&cCombat&7] "
    NowInCombat:
        type: both
        text: "%mastercombat_prefix% &fYou are now in combat for &c%mastercombat_time%&f."
    ElytraDisabled:
        type: actionbar
        text: "%mastercombat_prefix% &cElytra disabled during combat (&f%mastercombat_time%&c left)"
```

## Events

MasterCombatPro exposes its own events (for example `MasterCombatLoadEvent`). Register listeners normally via Bukkit's event system.

## Support

If you need help integrating, open an issue in the project repository or contact the maintainers.
