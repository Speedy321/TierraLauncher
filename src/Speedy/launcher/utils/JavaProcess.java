package Speedy.launcher.utils;

import java.util.List;

public class JavaProcess
{

    private final List commands;
    private final Process process;
    private final LimitedCapacityList sysOutLines = new LimitedCapacityList(java/lang/String, 5);
    private JavaProcessRunnable onExit;
    private ProcessMonitorThread monitor;

    public JavaProcess(List commands, Process process)
    {
        monitor = new ProcessMonitorThread(this);
        this.commands = commands;
        this.process = process;
        monitor.start();
    }

    public Process getRawProcess()
    {
        return process;
    }

    public List getStartupCommands()
    {
        return commands;
    }

    public String getStartupCommand()
    {
        return process.toString();
    }

    public LimitedCapacityList getSysOutLines()
    {
        return sysOutLines;
    }

    public boolean isRunning()
    {
        try
        {
            process.exitValue();
        }
        catch(IllegalThreadStateException ex)
        {
            return true;
        }
        return false;
    }

    public void setExitRunnable(JavaProcessRunnable runnable)
    {
        onExit = runnable;
    }

    public void safeSetExitRunnable(JavaProcessRunnable runnable)
    {
        setExitRunnable(runnable);
        if(!isRunning() && runnable != null)
        {
            runnable.onJavaProcessEnded(this);
        }
    }

    public JavaProcessRunnable getExitRunnable()
    {
        return onExit;
    }

    public int getExitCode()
    {
        return process.exitValue();
        IllegalThreadStateException ex;
        ex;
        ex.fillInStackTrace();
        throw ex;
    }

    public String toString()
    {
        return (new StringBuilder("JavaProcess[commands=")).append(commands).append(", isRunning=").append(isRunning()).append("]").toString();
    }

    public void stop()
    {
        process.destroy();
    }
}
