package dev.neuralnexus.tatercomms;

import dev.neuralnexus.taterlib.plugin.ModuleLoader;
import dev.neuralnexus.taterlib.plugin.PluginModule;

import java.util.HashSet;
import java.util.Set;

/** TaterComms module loader. */
public class TaterCommsModuleLoader implements ModuleLoader {
    private static final Set<PluginModule> modules = new HashSet<>();

    @Override
    public Set<PluginModule> modules() {
        return modules;
    }
}
