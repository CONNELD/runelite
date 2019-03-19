package net.runelite.client.plugins.hydra;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.Getter;
import net.runelite.api.AnimationID;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.HeadIcon;
import net.runelite.api.Hitsplat;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.Player;
import net.runelite.api.Projectile;
import net.runelite.api.ProjectileID;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.events.PlayerDespawned;
import net.runelite.api.events.PlayerSpawned;
import net.runelite.api.events.ProjectileMoved;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
        name = "Hydra",
        description = "does hydra 4head",
        tags = {"combat", "overlay", "pve", "pvm"}
)
public class HydraPlugin extends Plugin{
    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private HydraOverlay overlay;

    @Inject
    private ClientThread clientThread;

    private List<Integer> PreviousAttacks;

    private static int nextAttackID;

    private int hydraID;

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(overlay);
        PreviousAttacks = new ArrayList<>();
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
        PreviousAttacks = null;
    }

    @Subscribe
    public void onProjectileMoved(ProjectileMoved event)
    {
        Projectile projectile = event.getProjectile();
        int projectileId = projectile.getId();
        if (projectileId != ProjectileID.ALCHEMICAL_HYDRA_RANGED &&
                projectileId != ProjectileID.ALCHEMICAL_HYDRA_MAGIC)
        {
            return;
        }

        // The event fires once before the projectile starts moving,
        // and we only want to check each projectile once
        if (client.getGameCycle() >= projectile.getStartMovementCycle())
        {
            return;
        }

        for (NPC npc : client.getNpcs())
        {
            if (npc.getName() == "Alchemical Hydra")
            {
                hydraID = npc.getId();
                PreviousAttacks.add(projectileId);
                nextAttack(hydraID);
            }
        }
        return;

    }

    private void nextAttack(int npcID) {

        if ((npcID == ) || (npcID == ) || (npcID == )) {
            if (PreviousAttacks.size() > 3) {
                if (PreviousAttacks.get(PreviousAttacks.size() - 1) == ProjectileID.ALCHEMICAL_HYDRA_RANGED &&
                        PreviousAttacks.get(PreviousAttacks.size() - 1) == PreviousAttacks.get(PreviousAttacks.size() - 2) &&
                        PreviousAttacks.get(PreviousAttacks.size() - 2) == PreviousAttacks.get(PreviousAttacks.size() - 3)) {
                    nextAttackID = ProjectileID.ALCHEMICAL_HYDRA_MAGIC;
                } else if(PreviousAttacks.get(PreviousAttacks.size() - 1) == ProjectileID.ALCHEMICAL_HYDRA_MAGIC &&
                        PreviousAttacks.get(PreviousAttacks.size() - 1) == PreviousAttacks.get(PreviousAttacks.size() - 2) &&
                        PreviousAttacks.get(PreviousAttacks.size() - 2) == PreviousAttacks.get(PreviousAttacks.size() - 3)) {
                    nextAttackID = ProjectileID.ALCHEMICAL_HYDRA_RANGED;
                }
                if (PreviousAttacks.get(PreviousAttacks.size() - 1) != PreviousAttacks.get(PreviousAttacks.size() - 2) ||
                        PreviousAttacks.get(PreviousAttacks.size() - 1) != PreviousAttacks.get(PreviousAttacks.size() - 3)) {
                    nextAttackID = PreviousAttacks.get(PreviousAttacks.size() - 1);
                }
            } else {
                nextAttackID = PreviousAttacks.get(PreviousAttacks.size() - 1);
            }

        }
        else if(npcID == ) {
            nextAttackID = PreviousAttacks.get(PreviousAttacks.size() - 2);

        }
    }

    public static int getNextAttackID(){
        return nextAttackID;
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned event)
    {
        NPC npc = event.getNpc();
        if(npc.getName()== "Alchemical Hydra")
            hydraID = npc.getId();
    }

    @Subscribe
    public void onNpcDespawn(NpcDespawned event)
    {
        NPC npc = event.getNpc();
        if(npc.getName() == "Alchemical Hydra")
            PreviousAttacks = new ArrayList<>();
    }

}
