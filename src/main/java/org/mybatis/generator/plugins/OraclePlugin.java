package org.mybatis.generator.plugins;

import org.mybatis.generator.api.ShellRunner;

public class OraclePlugin {
	public static void generate() {
		String config = OraclePlugin.class.getClassLoader().getResource("generatorConfig.xml").getFile();
		String[] arg = { "-configfile", config, "-overwrite" };
		ShellRunner.main(arg);
	}

	public static void main(String[] args) {
		generate();
	}
	
}
