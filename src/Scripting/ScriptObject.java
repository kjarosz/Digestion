package Scripting;

import java.io.File;

public class ScriptObject {
	public String name;
	public String pwd;
	public Object script;
   
   public File getScriptFile() {
      return new File(pwd, name + ".py");
   }
}