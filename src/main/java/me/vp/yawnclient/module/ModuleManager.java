package me.vp.yawnclient.module;

import java.util.ArrayList;
import java.util.List;

import me.vp.yawnclient.event.Event;
import me.vp.yawnclient.event.events.KeyPressEvent;
import me.vp.yawnclient.module.modules.Clickgui;
import me.vp.yawnclient.module.modules.Test;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.quantumclient.energy.Subscribe;

public class ModuleManager {
	public ArrayList<Module> modules;

	public ModuleManager() {
		modules = new ArrayList<>();

        modules.add(new Clickgui());
		modules.add(new Test());
	}

	public boolean isModuleEnabled(String name) {
		Module m = modules.stream().filter(mm->mm.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
		return m.isEnabled();
	}

	public Module getModule(String name) {
		for (Module m : this.modules) {
			if(m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}

	public ArrayList<Module> getModules() {
		return modules;
	}

	public List<Module> getEnabledModules() {
		List<Module> modules = new ArrayList<Module>();

		for(Module m : modules) {
			if(m.isEnabled())
				modules.add(m);
		} return modules;
	}

	public List<Module> getModulesByCategory(Module.Category c) {
		List<Module> modules = new ArrayList<Module>();

		for(Module m : modules) {
			if(m.getCategory() == c)
				modules.add(m);
		} return modules;
	}

	// for key binds (called in MixinKeyboard).
    @Subscribe
	public void onKeyPress(KeyPressEvent event) {
		if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_F3))
			return;

		modules.stream().filter(m -> m.getKey() == event.getKey()).forEach(Module::toggle);
	}

}