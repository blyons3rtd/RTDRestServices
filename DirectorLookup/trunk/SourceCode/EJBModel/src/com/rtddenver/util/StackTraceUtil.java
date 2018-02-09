package com.rtddenver.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class StackTraceUtil {
	public StackTraceUtil() {
        super();
    }

	private static final Logger LOG = LogManager.getLogger(StackTraceUtil.class.getName());

    /**
     *  This method takes a exception as an input argument
     * and returns the stacktrace as a string.
     */
    public static String getStackTrace(Throwable exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionDetail = sw.toString();
        pw.close();
        try {
            sw.close();
        } catch (Exception ex) {
        	LOG.error(StackTraceUtil.getStackTrace(ex));
        }
        sw = null;
        pw = null;
        return exceptionDetail;
    }

}
