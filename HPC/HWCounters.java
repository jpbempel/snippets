package com.bempel.hpc;

import ch.usi.overseer.OverHpc;

public class HWCounters
{
	private static String[] EVENTS = {
	    "UNHALTED_CORE_CYCLES",
	    "INSTRUCTION_RETIRED",
	    "L2_RQSTS:LD_HIT",
	    "L2_RQSTS:LD_MISS",
	    "LLC_REFERENCES",
	    "MEM_LOAD_RETIRED:LLC_MISS",
	    "PERF_COUNT_SW_CPU_MIGRATIONS",
	    "MEM_UNCORE_RETIRED:LOCAL_DRAM_AND_REMOTE_CACHE_HIT",
	    "MEM_UNCORE_RETIRED:REMOTE_DRAM"
	};
    
	private static String[] EVENTS_NAME = {
	    "Cycles",
	    "Instructions",
	    "L2 hits",
	    "L2 misses",
	    "LLC hits",
	    "LLC misses",
	    "CPU migrations",
	    "Local DRAM",
	    "Remote DRAM"
	};

	private static long[] results = new long[EVENTS.length];
	private static OverHpc oHpc = OverHpc.getInstance();

	public static void init()
	{
	    StringBuilder sb  = new StringBuilder();
	    for (int i = 0; i < EVENTS.length; i++)
		{
		    if (i > 0)
			{
			    sb.append(",");
			}
		    sb.append(EVENTS[i]);
		}
	    oHpc.initEvents(sb.toString());        
	}

	public static void start()
	{
	    int tid = oHpc.getThreadId();
	    oHpc.bindEventsToThread(tid);
	}

	public static void stop()
	{
	    int tid = oHpc.getThreadId();
	    for (int i = 0; i < EVENTS.length; i++)
	    {
		    results[i] = oHpc.getEventFromThread(tid, i);
	    }
	}

	public static void printResults()
	{
	    for (int i = 0; i < EVENTS.length; i++)
	    {
		    System.out.println(EVENTS_NAME[i] + ": " + String.format("%,d", results[i]));
	    }	    	    
	}

	public static void shutdown()
	{
	    oHpc.shutdown();
	}
}
