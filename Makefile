JCC = javac
JCR = java
JFLAGS = -g

RM = rm

DEMO1_COMPILE = demo1/*.java
DEMO2_COMPILE = demo2/*.java
DEMO3_COMPILE = demo3/*.java
DEMO4_COMPILE = demo4/*.java
DEMO5_COMPILE = demo5/*.java
DEMO6_COMPILE = demo6/*.java
DEMO7_COMPILE = demo7/*.java
DEMO8_COMPILE = demo8/*.java
DEMO9_COMPILE = demo9/*.java
DEMO10_COMPILE = demo10/*.java

DEMO1_RUN = demo1.ExtendThread
DEMO2_RUN = demo2.ImplementRunnable
DEMO3_VOLATILE_RUN = demo3.Volatile
DEMO3_INTERRUPT_RUN = demo3.Interrupt
DEMO3_INTERRUPT_THREAD_POOL_RUN = demo3.InterruptThreadPool
DEMO4_RUN = demo4.Synchronized
DEMO5_RUN = demo5.Main
DEMO6_RUN = demo6.ThreadPool
DEMO7_RUN = demo7.Processor
DEMO8_HIGH_LEVEL_SYNCH_RUN = demo8.HighLevelSynchTechniques
DEMO8_LOW_LEVEL_SYNCH_RUN = demo8.LowLevelSynchTechniques
DEMO9_RUN = demo9.App
DEMO10_RUN = demo10.Main

default:
	@echo "Choose one:";
	@echo "    'demo[1-10]_c' for compilation";
	@echo "    'demo[1-10]_r' for execution";
	@echo "    'make clean' to remove all binary files";


demo1_c:
	$(JCC) $(JFLAGS) $(DEMO1_COMPILE)
demo2_c:
	$(JCC) $(JFLAGS) $(DEMO2_COMPILE)
demo3_c:
	$(JCC) $(JFLAGS) $(DEMO3_COMPILE)
demo4_c:
	$(JCC) $(JFLAGS) $(DEMO4_COMPILE)
demo5_c:
	$(JCC) $(JFLAGS) $(DEMO5_COMPILE)
demo6_c:
	$(JCC) $(JFLAGS) $(DEMO6_COMPILE)
demo7_c:
	$(JCC) $(JFLAGS) $(DEMO7_COMPILE)
demo8_c:
	$(JCC) $(JFLAGS) $(DEMO8_COMPILE)
demo9_c:
	$(JCC) $(JFLAGS) $(DEMO9_COMPILE)
demo10_c:
	$(JCC) $(JFLAGS) $(DEMO10_COMPILE)

demo1_r:
	$(JCR) $(DEMO1_RUN)
demo2_r:
	$(JCR) $(DEMO2_RUN)
demo3_r:
	@echo "Choose one:";
	@echo "    demo3_volatile_r";
	@echo "    demo3_interrupt_r";
	@echo "    demo3_interrupt_tp_r";
demo3_volatile_r:
	$(JCR) $(DEMO3_VOLATILE_RUN)
demo3_interrupt_r:
	$(JCR) $(DEMO3_INTERRUPT_RUN)
demo3_interrupt_tp_r:
	$(JCR) $(DEMO3_INTERRUPT_THREAD_POOL_RUN)
demo4_r:
	$(JCR) $(DEMO4_RUN)
demo5_r:
	$(JCR) $(DEMO5_RUN)
demo6_r:
	$(JCR) $(DEMO6_RUN)
demo7_r:
	$(JCR) $(DEMO7_RUN)
demo8_r:
	@echo "Choose one:";
	@echo "    demo8_high_r";
	@echo "    demo8_low_r";
demo8_high_r:
	$(JCR) $(DEMO8_HIGH_LEVEL_SYNCH_RUN)
demo8_low_r:
	$(JCR) $(DEMO8_LOW_LEVEL_SYNCH_RUN)
demo9_r:
	$(JCR) $(DEMO9_RUN)
demo10_r:
	$(JCR) $(DEMO10_RUN)

clean:
	$(RM) demo*/*.class
