package io.github.javacodesign;

public class JavaCommandRunner extends CommandRunner {

    static String javaHome(){
        return System.getProperty("java.home");
    }

    private final String javaHome;

    public JavaCommandRunner(String javaHome) {
        this.javaHome = javaHome;
    }

    public JavaCommandRunner(){
        this(javaHome());
    }

    public String getJavaHome() {
        return javaHome;
    }
}
