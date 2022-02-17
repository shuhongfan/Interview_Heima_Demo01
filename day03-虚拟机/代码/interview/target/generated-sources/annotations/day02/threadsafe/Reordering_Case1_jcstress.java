package day02.threadsafe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.openjdk.jcstress.infra.runners.ForkedTestConfig;
import org.openjdk.jcstress.infra.collectors.TestResult;
import org.openjdk.jcstress.infra.runners.Runner;
import org.openjdk.jcstress.infra.runners.WorkerSync;
import org.openjdk.jcstress.util.Counter;
import org.openjdk.jcstress.os.AffinitySupport;
import org.openjdk.jcstress.vm.AllocProfileSupport;
import org.openjdk.jcstress.infra.runners.FootprintEstimator;
import org.openjdk.jcstress.infra.runners.VoidThread;
import org.openjdk.jcstress.infra.runners.LongThread;
import org.openjdk.jcstress.infra.runners.CounterThread;
import day02.threadsafe.Reordering.Case1;
import org.openjdk.jcstress.infra.results.II_Result;

public final class Reordering_Case1_jcstress extends Runner<II_Result> {

    volatile WorkerSync workerSync;
    Case1[] gs;
    II_Result[] gr;

    public Reordering_Case1_jcstress(ForkedTestConfig config) {
        super(config);
    }

    @Override
    public void sanityCheck(Counter<II_Result> counter) throws Throwable {
        sanityCheck_API(counter);
        sanityCheck_Footprints(counter);
    }

    private void sanityCheck_API(Counter<II_Result> counter) throws Throwable {
        final Case1 s = new Case1();
        final II_Result r = new II_Result();
        VoidThread a0 = new VoidThread() { protected void internalRun() {
            s.actor1();
        }};
        VoidThread a1 = new VoidThread() { protected void internalRun() {
            s.actor2(r);
        }};
        a0.start();
        a1.start();
        a0.join();
        if (a0.throwable() != null) {
            throw a0.throwable();
        }
        a1.join();
        if (a1.throwable() != null) {
            throw a1.throwable();
        }
        counter.record(r);
    }

    private void sanityCheck_Footprints(Counter<II_Result> counter) throws Throwable {
        config.adjustStrideCount(new FootprintEstimator() {
          public void runWith(int size, long[] cnts) {
            long time1 = System.nanoTime();
            long alloc1 = AllocProfileSupport.getAllocatedBytes();
            Case1[] ls = new Case1[size];
            II_Result[] lr = new II_Result[size];
            for (int c = 0; c < size; c++) {
                Case1 s = new Case1();
                II_Result r = new II_Result();
                lr[c] = r;
                ls[c] = s;
            }
            LongThread a0 = new LongThread() { public long internalRun() {
                long a1 = AllocProfileSupport.getAllocatedBytes();
                for (int c = 0; c < size; c++) {
                    ls[c].actor1();
                }
                long a2 = AllocProfileSupport.getAllocatedBytes();
                return a2 - a1;
            }};
            LongThread a1 = new LongThread() { public long internalRun() {
                long a1 = AllocProfileSupport.getAllocatedBytes();
                for (int c = 0; c < size; c++) {
                    ls[c].actor2(lr[c]);
                }
                long a2 = AllocProfileSupport.getAllocatedBytes();
                return a2 - a1;
            }};
            a0.start();
            a1.start();
            try {
                a0.join();
                cnts[0] += a0.result();
            } catch (InterruptedException e) {
            }
            try {
                a1.join();
                cnts[0] += a1.result();
            } catch (InterruptedException e) {
            }
            for (int c = 0; c < size; c++) {
                counter.record(lr[c]);
            }
            long time2 = System.nanoTime();
            long alloc2 = AllocProfileSupport.getAllocatedBytes();
            cnts[0] += alloc2 - alloc1;
            cnts[1] += time2 - time1;
        }});
    }

    @Override
    public ArrayList<CounterThread<II_Result>> internalRun() {
        int len = config.strideSize * config.strideCount;
        gs = new Case1[len];
        gr = new II_Result[len];
        for (int c = 0; c < len; c++) {
            gs[c] = new Case1();
            gr[c] = new II_Result();
        }
        workerSync = new WorkerSync(false, 2, config.spinLoopStyle);

        control.isStopped = false;

        if (config.localAffinity) {
            try {
                AffinitySupport.tryBind();
            } catch (Exception e) {
                // Do not care
            }
        }

        ArrayList<CounterThread<II_Result>> threads = new ArrayList<>(2);
        threads.add(new CounterThread<II_Result>() { public Counter<II_Result> internalRun() {
            return task_actor1();
        }});
        threads.add(new CounterThread<II_Result>() { public Counter<II_Result> internalRun() {
            return task_actor2();
        }});

        for (CounterThread<II_Result> t : threads) {
            t.start();
        }

        if (config.time > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(config.time);
            } catch (InterruptedException e) {
            }
        }

        control.isStopped = true;

        return threads;
    }

    private void jcstress_consume(Counter<II_Result> cnt, int a) {
        Case1[] ls = gs;
        II_Result[] lr = gr;
        int len = config.strideSize * config.strideCount;
        int left = a * len / 2;
        int right = (a + 1) * len / 2;
        for (int c = left; c < right; c++) {
            II_Result r = lr[c];
            Case1 s = ls[c];
            s.x = 0;
            s.y = 0;
            cnt.record(r);
            r.r1 = 0;
            r.r2 = 0;
        }
    }

    private void jcstress_sink(int v) {};
    private void jcstress_sink(short v) {};
    private void jcstress_sink(byte v) {};
    private void jcstress_sink(char v) {};
    private void jcstress_sink(long v) {};
    private void jcstress_sink(float v) {};
    private void jcstress_sink(double v) {};
    private void jcstress_sink(Object v) {};

    private Counter<II_Result> task_actor1() {
        int len = config.strideSize * config.strideCount;
        int stride = config.strideSize;
        Counter<II_Result> counter = new Counter<>();
        if (config.localAffinity) AffinitySupport.bind(config.localAffinityMap[0]);
        while (true) {
            WorkerSync sync = workerSync;
            if (sync.stopped) {
                return counter;
            }
            int check = 0;
            for (int start = 0; start < len; start += stride) {
                run_actor1(gs, gr, start, start + stride);
                check += 2;
                sync.awaitCheckpoint(check);
            }
            jcstress_consume(counter, 0);
            if (sync.tryStartUpdate()) {
                workerSync = new WorkerSync(control.isStopped, 2, config.spinLoopStyle);
            }
            sync.postUpdate();
        }
    }

    private void run_actor1(Case1[] gs, II_Result[] gr, int start, int end) {
        Case1[] ls = gs;
        II_Result[] lr = gr;
        for (int c = start; c < end; c++) {
            Case1 s = ls[c];
            s.actor1();
        }
    }

    private Counter<II_Result> task_actor2() {
        int len = config.strideSize * config.strideCount;
        int stride = config.strideSize;
        Counter<II_Result> counter = new Counter<>();
        if (config.localAffinity) AffinitySupport.bind(config.localAffinityMap[1]);
        while (true) {
            WorkerSync sync = workerSync;
            if (sync.stopped) {
                return counter;
            }
            int check = 0;
            for (int start = 0; start < len; start += stride) {
                run_actor2(gs, gr, start, start + stride);
                check += 2;
                sync.awaitCheckpoint(check);
            }
            jcstress_consume(counter, 1);
            if (sync.tryStartUpdate()) {
                workerSync = new WorkerSync(control.isStopped, 2, config.spinLoopStyle);
            }
            sync.postUpdate();
        }
    }

    private void run_actor2(Case1[] gs, II_Result[] gr, int start, int end) {
        Case1[] ls = gs;
        II_Result[] lr = gr;
        for (int c = start; c < end; c++) {
            Case1 s = ls[c];
            II_Result r = lr[c];
            jcstress_sink(r.jcstress_trap);
            s.actor2(r);
        }
    }

}
