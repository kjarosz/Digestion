package Scripting;

import java.io.File;
import java.util.HashMap;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class ScriptFactory {   
   private PythonInterpreter mInterpreter;
   private HashMap<String, PyObject> mCompiledScripts;
	
	public ScriptFactory() {
      mInterpreter = new PythonInterpreter();
      mCompiledScripts = new HashMap<>();
	}
   
   public ScriptObject createScriptInstance(File scriptFile, String className, Class classType) {
      if(!scriptIsAlreadyCompiled(className)) 
         compileScript(scriptFile, className, classType);
      
		ScriptObject script = new ScriptObject();
		script.pwd = scriptFile.getParent();
		script.script = mCompiledScripts.get(className).__call__().__tojava__(classType);
      return script;
   }
   
   private boolean scriptIsAlreadyCompiled(String className) {
      return mCompiledScripts.get(className) != null;
   }
   
   private void compileScript(File scriptFile, String className, Class classType) {
		mInterpreter.execfile(scriptFile.getPath());
      PyObject code = mInterpreter.get(className);
      mCompiledScripts.put(className, code);
   }
}
