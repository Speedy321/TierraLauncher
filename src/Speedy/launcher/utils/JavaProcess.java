/*
--- Copyright 2013 Speedy321(Christophe-André Gassmann)

--- This file is part of TierraLauncher.

TierraLauncher is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

TierraLauncher is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with TierraLauncher. If not, see http://www.gnu.org/licenses/ .

*/

//package Speedy.launcher.utils;
//
//import java.util.List;
//
//public class JavaProcess
//{
//
//    private final List commands;
//    private final Process process;
//    private final LimitedCapacityList sysOutLines = new LimitedCapacityList(java/lang/String, 5);
//    private JavaProcessRunnable onExit;
//    private ProcessMonitorThread monitor;
//
//    public JavaProcess(List commands, Process process)
//    {
//        monitor = new ProcessMonitorThread(this);
//        this.commands = commands;
//        this.process = process;
//        monitor.start();
//    }
//
//    public Process getRawProcess()
//    {
//        return process;
//    }
//
//    public List getStartupCommands()
//    {
//        return commands;
//    }
//
//    public String getStartupCommand()
//    {
//        return process.toString();
//    }
//
//    public LimitedCapacityList getSysOutLines()
//    {
//        return sysOutLines;
//    }
//
//    public boolean isRunning()
//    {
//        try
//        {
//            process.exitValue();
//        }
//        catch(IllegalThreadStateException ex)
//        {
//            return true;
//        }
//        return false;
//    }
//
//    public void setExitRunnable(JavaProcessRunnable runnable)
//    {
//        onExit = runnable;
//    }
//
//    public void safeSetExitRunnable(JavaProcessRunnable runnable)
//    {
//        setExitRunnable(runnable);
//        if(!isRunning() && runnable != null)
//        {
//            runnable.onJavaProcessEnded(this);
//        }
//    }
//
//    public JavaProcessRunnable getExitRunnable()
//    {
//        return onExit;
//    }
//
//    public int getExitCode()
//    {
//        return process.exitValue();
//        IllegalThreadStateException ex;
//        ex;
//        ex.fillInStackTrace();
//        throw ex;
//    }
//
//    public String toString()
//    {
//        return (new StringBuilder("JavaProcess[commands=")).append(commands).append(", isRunning=").append(isRunning()).append("]").toString();
//    }
//
//    public void stop()
//    {
//        process.destroy();
//    }
//}
