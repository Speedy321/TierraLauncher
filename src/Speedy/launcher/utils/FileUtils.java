/*
--- Copyright Speedy321(Christophe-André Gassmann)

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

package Speedy.launcher.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class FileUtils
{

    public FileUtils()
    {
    }

    public static void writeStringToFile(File file, String data)
        throws IOException
    {
        OutputStream out = null;
        if(file.exists())
        {
            if(file.isDirectory())
            {
                throw new IOException((new StringBuilder("File '")).append(file).append("' exists but is a directory").toString());
            }
            if(!file.canWrite())
            {
                throw new IOException((new StringBuilder("File '")).append(file).append("' cannot be written to").toString());
            }
        } else
        {
            File parent = file.getParentFile();
            if(parent != null && !parent.mkdirs() && !parent.isDirectory())
            {
                throw new IOException((new StringBuilder("Directory '")).append(parent).append("' could not be created").toString());
            }
        }
        out = new FileOutputStream(file, false);
        if(data != null)
        {
            out.write(data.getBytes(Charset.defaultCharset()));
        }
        out.close();
  //      break MISSING_BLOCK_LABEL_183;
        Exception exception;
        try
        {
            if(out != null)
            {
                out.close();
            }
        }
        catch(IOException ioexception) { ioexception.printStackTrace(); }

        try
        {
            if(out != null)
            {
                out.close();
            }
        }
        catch(IOException ioexception1) { }
        return;
    }

    public static String readFileToString(File file)
        throws IOException
    {
        InputStream inputStream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) 
        {
            if(result.length() > 0)
            {
                result.append("\n");
            }
            result.append(line);
        }
        reader.close();
        return result.toString();
    }
}
