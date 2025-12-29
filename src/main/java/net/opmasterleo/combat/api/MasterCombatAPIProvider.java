package net.opmasterleo.combat.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MasterCombatAPIProvider {

    @Nullable
    private static MasterCombatAPI API = null;

    public MasterCombatAPIProvider() {
    }

    @Nullable
    public static MasterCombatAPI getAPI() {
        return API;
    }

    @Deprecated
    public static void register(@Nullable MasterCombatAPI api) {
        API = api;
    }

    public static void set(@NotNull MasterCombatAPI apiBackend) {
        API = apiBackend;
    }
}
