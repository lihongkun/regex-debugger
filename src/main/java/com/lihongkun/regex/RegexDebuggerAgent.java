package com.lihongkun.regex;

import java.lang.instrument.Instrumentation;

public class RegexDebuggerAgent {
	public static void premain(String agentArgs, Instrumentation inst) {
		inst.addTransformer(new RegexDebuggerTransformer());
	}
}
