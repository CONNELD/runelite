package net.runelite.client.plugins.hydra;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class HydraOverlay extends Overlay {

    private Client client;
    private HydraPlugin plugin;

    @Inject
    private SkillIconManager iconManager;

    @Inject
    public HydraOverlay(Client client, HydraPlugin plugin)
    {
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.client = client;
        this.plugin = plugin;
    }

    private BufferedImage getIcon(int attackStyle)
    {
        switch (attackStyle)
        {
            case ProjectileID.ALCHEMICAL_HYDRA_RANGED: return iconManager.getSkillImage(Skill.RANGED);
            case ProjectileID.ALCHEMICAL_HYDRA_MAGIC: return iconManager.getSkillImage(Skill.MAGIC);
        }
        return null;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        BufferedImage icon = getIcon(HydraPlugin.getNextAttackID());
        graphics.drawImage(
                icon,
                100,
                100,
                null);
        return null;
    }
}
