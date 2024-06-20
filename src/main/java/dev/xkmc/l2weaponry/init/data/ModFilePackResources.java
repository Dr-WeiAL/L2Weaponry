package dev.xkmc.l2weaponry.init.data;

import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;

import java.nio.file.Path;

public class ModFilePackResources extends PathPackResources {
	protected final IModFile modFile;
	protected final String sourcePath;

	public ModFilePackResources(String name, IModFile modFile, String sourcePath) {
		super(name, true, modFile.findResource(sourcePath));
		this.modFile = modFile;
		this.sourcePath = sourcePath;
	}

	protected Path resolve(String... paths) {
		String[] allPaths = new String[paths.length + 1];
		allPaths[0] = this.sourcePath;
		System.arraycopy(paths, 0, allPaths, 1, paths.length);
		return this.modFile.findResource(allPaths);
	}
}