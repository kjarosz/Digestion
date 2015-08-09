package Core.LevelEditor.Components;

public class UnselectedEntityException extends RuntimeException {
   
   public UnselectedEntityException() {
      super("No entity has been selected to add component to.");
   }
}
