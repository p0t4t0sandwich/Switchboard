package dev.neuralnexus.tatercomms;

import dev.neuralnexus.taterlib.plugin.ModuleLoader;
import dev.neuralnexus.taterlib.plugin.PluginModule;

import java.util.ArrayList;
import java.util.List;

/** TaterComms module loader. */
public class TaterCommsModuleLoader implements ModuleLoader {
    private static final List<PluginModule> modules = new ArrayList<>();

    @Override
    public List<PluginModule> modules() {
        return modules;
    }
}
