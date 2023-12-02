package dev.neuralnexus.tatercomms;

import dev.neuralnexus.taterlib.plugin.Module;

import java.util.HashSet;
import java.util.Set;

/** Module loader. */
public class TaterCommsModuleLoader {
    private static final Set<Module> modules = new HashSet<>();

    /**
     * Register a module.
     *
     * @param module The module.
     */
    public static void registerModule(Module module) {
        modules.add(module);
    }

    /**
     * Unregister a module.
     *
     * @param moduleName The module.
     */
    public static void unregisterModule(String moduleName) {
        modules.removeIf(module -> module.getName().equals(moduleName));
    }

    /** Start the modules. */
    public static void startModules() {
        modules.forEach(Module::start);
    }

    /** Stop the modules. */
    public static void stopModules() {
        modules.forEach(Module::stop);
    }

    /** Reload the modules. */
    public static void reloadModules() {
        modules.forEach(Module::reload);
    }
}
