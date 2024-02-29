package dev.neuralnexus.switchboard;

import dev.neuralnexus.taterlib.plugin.ModuleLoader;
import dev.neuralnexus.taterlib.plugin.PluginModule;

import java.util.ArrayList;
import java.util.List;

/** Switchboard module loader. */
public class SwitchboardModuleLoader implements ModuleLoader {
    private static final List<PluginModule> modules = new ArrayList<>();

    @Override
    public List<PluginModule> modules() {
        return modules;
    }
}
