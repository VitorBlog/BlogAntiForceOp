package com.vitorblog.antiop.sink

import com.vitorblog.antiop.dao.JarDao
import com.vitorblog.antiop.model.Jar
import org.benf.cfr.reader.api.OutputSinkFactory
import org.benf.cfr.reader.api.SinkReturns
import java.util.*
import java.util.function.Consumer

class CFRSink(val jar:Jar) : OutputSinkFactory {
    override
    fun getSupportedSinks(sinkType: OutputSinkFactory.SinkType, collection: Collection<OutputSinkFactory.SinkClass>): List<OutputSinkFactory.SinkClass> {
        return if (sinkType == OutputSinkFactory.SinkType.JAVA && collection.contains(OutputSinkFactory.SinkClass.DECOMPILED)) {
            Arrays.asList(OutputSinkFactory.SinkClass.DECOMPILED, OutputSinkFactory.SinkClass.STRING)
        } else {
            Collections.singletonList(OutputSinkFactory.SinkClass.STRING)
        }
    }

    var dumpDecompiled = Consumer<SinkReturns.Decompiled>{ data ->
        jar.files.put("${data.packageName}.${data.className}", data.java)
    }

    override fun <T> getSink(sinkType: OutputSinkFactory.SinkType, sinkClass: OutputSinkFactory.SinkClass): OutputSinkFactory.Sink<T> {
        if (sinkType == OutputSinkFactory.SinkType.JAVA && sinkClass == OutputSinkFactory.SinkClass.DECOMPILED) {
            return OutputSinkFactory.Sink { x -> dumpDecompiled.accept(x as SinkReturns.Decompiled) }
        }
        return OutputSinkFactory.Sink {}
    }
}