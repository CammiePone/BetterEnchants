package dev.decobr.betterenchants.gui;

import dev.decobr.betterenchants.config.Configuration;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WToggleButton;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.LiteralText;

@Environment(EnvType.CLIENT)
public class ConfigGUI extends LightweightGuiDescription {
    public ConfigGUI() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(150, 30);

        WLabel label = new WLabel(new LiteralText("BetterEnchants Config"));
        root.add(label, 0, 0, 2, 1);
        label.setHorizontalAlignment(HorizontalAlignment.LEFT);

        WToggleButton button = new WToggleButton(new LiteralText("Fix /enchant restriction")) {
            @Override
            public void onToggle(boolean on) {
                Configuration.INSTANCE.fixEnchantCommand = on;
                Configuration.INSTANCE.saveConfig();
            }
        };
        button.setToggle(Configuration.INSTANCE.fixEnchantCommand);

        root.add(button, 0, 1, 2, 1);

        root.validate(this);
    }
}
