package unitk.util;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;

public final class PackageUtil{
    private PackageUtil(){}
    private final static PackageUtil __instance = new PackageUtil();
    public static PackageUtil getInstance(){
        return __instance;
    }


    /**
     * 得到与cls同包的所有类
     *
     * @param cls
     * @param subPath 是否查找子目录
     * @return List<Class>
     */
    public List<Class> getClasses(Class cls,boolean subPath){
        return getClasses(cls.getPackage().getName(),subPath);
    }

    /**
     * 从一个包中查到所有的类
     *
     * @param packageName
     * @param subPath 是否查找子目录
     * @return List<Class>
     */
    public List<Class> getClasses(String packageName, boolean subPath){
        List<Class> classes = new ArrayList<Class>();
        try{
            return getClasses00(packageName,subPath);
        }catch(IOException err){
            return classes;
        }
    }

    public Map<Class,Class> getInterfacesMap(Map<String,String> pakmap){
        Map<Class,Class> map = new HashMap<Class,Class>();
        if(pakmap==null||pakmap.size()<=0)return map;
        for(Map.Entry<String,String> entry : pakmap.entrySet()){
            Map<Class,Class> map0 = getInterfacesMap(entry.getKey(),entry.getValue());
            map.putAll(map0);
        }
        return map;
    }
    @SuppressWarnings(value="unchecked")
    public Map<Class,Class> getInterfacesMap(String keyPackage,String valPackage){
        Map<Class,Class> map = new HashMap<Class,Class>();
        if(keyPackage==null||keyPackage.length()<=0)return map;
        if(valPackage==null||valPackage.length()<=0)return map;
        if(keyPackage.equals(valPackage))return map;
        List<Class> kset = getClasses(keyPackage,false);

        for(Class key : kset){
            if(!key.isInterface())continue;
            String sval = valPackage + "." + key.getSimpleName() + "Impl";
            Class val = null;
            try{
                val = Class.forName(sval);
            }catch(ClassNotFoundException err){
                continue;
            }
            if(!key.isAssignableFrom(val))continue;
            map.put(key,val);
        }
        return map;
    }


    //得到指定接口的实现类，类和接口均需在同一目录下
    @SuppressWarnings(value="unchecked")
    public Set<Class> getImplSet(Class src){
        List<Class> kset = getClasses(src,false);
        Set<Class> resp = new HashSet<Class>();
        for(Class key : kset){
            // 判定指定的 Class 对象是否表示一个接口类型。
            if(key.isInterface())continue;
            // 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口。
            //实现类为入参
            if(!src.isAssignableFrom(key))continue;
            resp.add(key);
        }
        return resp;
    }


    /**
     * 从一个包中查到所有的类
     *
     * @param packageName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private List<Class> getClasses00(String packageName, boolean subPath) throws IOException{
        List<Class> classes = new ArrayList<Class>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".","/");
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String type = resource.getProtocol();
            if(type.equals("jar")){
                JarFile jarFile = ((JarURLConnection) resource.openConnection()).getJarFile();
                classes.addAll(findClass(jarFile, packageName,subPath));
                continue;
            }

            File dir = new File(resource.getFile());
            classes.addAll(findClass(dir, packageName,subPath));
        }
        return classes;
    }

    private void addClass(String clsName,List<Class> list){//, String packageName,boolean subPath
        if (clsName.lastIndexOf("$")>0)return;
        Class cls = null;
        try{
            cls = Class.forName(clsName);
        }catch(ClassNotFoundException err){
            return;
        }
        if(cls==null)return;
        list.add(cls);
    }

    /**
     * @param directory 文件路径
     * @param packageName 包名
     * @param subPath 是否查找子目录
     * @return 查找这个包下面的所有的class文件
     * @throws
     */
    private List<Class> findClass(JarFile directory, String packageName,boolean subPath){
        List<Class> classes = new ArrayList<Class>();
        String packagePath = packageName.replace(".", "/");

        Enumeration<JarEntry> entrys = directory.entries();
        while (entrys.hasMoreElements()) {
            JarEntry jarEntry = entrys.nextElement();
            String entryName = jarEntry.getName();

            if (!entryName.startsWith(packagePath))continue;
            if (!entryName.endsWith(".class"))continue;

            int index = entryName.lastIndexOf("/");
            String myPackagePath;
            if (index != -1) {
                myPackagePath = entryName.substring(0, index);
            } else {
                myPackagePath = entryName;
            }

            if(!subPath&&!myPackagePath.equals(packagePath))continue;

            // if (myPackagePath.equals(packagePath)||subPath) {
            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
            addClass(entryName,classes);
        }
        return classes;
    }


    /**
     * @param directory 文件路径
     * @param packageName 包名
     * @param subPath 是否查找子目录
     * @return 查找这个包下面的所有的class文件
     * @throws
     */
    private List<Class> findClass(File directory, String packageName,boolean subPath){
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if(!subPath)continue;
                assert !file.getName().contains(".");
                classes.addAll(findClass(file, packageName + "." + file.getName(),subPath));
            } else if (file.getName().endsWith(".class")) {
                String entryName = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                addClass(entryName,classes);
            }
        }
        return classes;
    }

}
