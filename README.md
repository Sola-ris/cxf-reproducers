# Reproducers for bugs in CXF's JAX-RS frontend

**Java version:** Temurin-21.0.6+7

**CXF version:** 4.1.0

**Run Reproducers:** `./mvnw test`

# EntityPartReproducerTest

CXF's implementation of `EntityPart.Builder` does not seem to work in a Java SE environment.
Calling the `build` method always results in the `NullPointerException` shown below.

<details>
    <summary>Stacktrace</summary>
<pre>
java.lang.NullPointerException: Cannot invoke "org.apache.cxf.message.Message.getExchange()" because "m" is null
	at org.apache.cxf.jaxrs.provider.ServerProviderFactory.getInstance(ServerProviderFactory.java:124)
	at org.apache.cxf.jaxrs.impl.EntityPartBuilderImpl.build(EntityPartBuilderImpl.java:111)
	at org.example.EntityPartReproducerTest.testBuildEntityPart(EntityPartReproducerTest.java:15)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
</pre>
</details>

# MpRestClientConfigurationReproducerTest

When using the Microprofile Rest Client, calling `ClientRequestContext#getConfiguration` always returns `null`,
making it impossible to access properties set via `RestClientBuilder#property` from the `ClientRequestContext`

<details>
    <summary>Stacktrace</summary>
<pre>
jakarta.ws.rs.ProcessingException: java.lang.NullPointerException: Cannot invoke "jakarta.ws.rs.core.Configuration.getProperty(String)" because the return value of "jakarta.ws.rs.client.ClientRequestContext.getConfiguration()" is null
	at org.apache.cxf.jaxrs.client.AbstractClient.checkClientException(AbstractClient.java:645)
	at org.apache.cxf.jaxrs.client.AbstractClient.preProcessResult(AbstractClient.java:619)
	at org.apache.cxf.jaxrs.client.ClientProxyImpl.doChainedInvocation(ClientProxyImpl.java:926)
	at org.apache.cxf.jaxrs.client.ClientProxyImpl.invoke(ClientProxyImpl.java:347)
	at org.apache.cxf.microprofile.client.proxy.MicroProfileClientProxyImpl.invokeActual(MicroProfileClientProxyImpl.java:496)
	at org.apache.cxf.microprofile.client.proxy.MicroProfileClientProxyImpl$Invoker.call(MicroProfileClientProxyImpl.java:515)
	at org.apache.cxf.microprofile.client.cdi.CDIInterceptorWrapper$BasicCDIInterceptorWrapper.invoke(CDIInterceptorWrapper.java:43)
	at org.apache.cxf.microprofile.client.proxy.MicroProfileClientProxyImpl.invoke(MicroProfileClientProxyImpl.java:492)
	at jdk.proxy2/jdk.proxy2.$Proxy21.greet(Unknown Source)
	at org.example.MpRestClientConfigurationReproducerTest.testAccessConfigurationInFilter(MpRestClientConfigurationReproducerTest.java:24)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
Caused by: java.lang.NullPointerException: Cannot invoke "jakarta.ws.rs.core.Configuration.getProperty(String)" because the return value of "jakarta.ws.rs.client.ClientRequestContext.getConfiguration()" is null
	at org.example.MpRestClientConfigurationReproducerTest$Filter.filter(MpRestClientConfigurationReproducerTest.java:38)
	at org.apache.cxf.jaxrs.client.spec.ClientRequestFilterInterceptor.handleMessage(ClientRequestFilterInterceptor.java:70)
	at org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:307)
	at org.apache.cxf.jaxrs.client.AbstractClient.doRunInterceptorChain(AbstractClient.java:717)
	at org.apache.cxf.microprofile.client.proxy.MicroProfileClientProxyImpl.doRunInterceptorChain(MicroProfileClientProxyImpl.java:184)
	at org.apache.cxf.jaxrs.client.ClientProxyImpl.doChainedInvocation(ClientProxyImpl.java:924)
	... 10 more
</pre>
</details>

<details>
    <summary>Log output</summary>
<pre>
Feb 11, 2025 11:43:30 PM org.apache.cxf.phase.PhaseInterceptorChain doDefaultLogging
WARNING: Interceptor for {http://example.org/}GreetingClient has thrown exception, unwinding now
java.lang.NullPointerException: Cannot invoke "jakarta.ws.rs.core.Configuration.getProperty(String)" because the return value of "jakarta.ws.rs.client.ClientRequestContext.getConfiguration()" is null
	at org.example.MpRestClientConfigurationReproducerTest$Filter.filter(MpRestClientConfigurationReproducerTest.java:38)
	at org.apache.cxf.jaxrs.client.spec.ClientRequestFilterInterceptor.handleMessage(ClientRequestFilterInterceptor.java:70)
	at org.apache.cxf.phase.PhaseInterceptorChain.doIntercept(PhaseInterceptorChain.java:307)
	at org.apache.cxf.jaxrs.client.AbstractClient.doRunInterceptorChain(AbstractClient.java:717)
	at org.apache.cxf.microprofile.client.proxy.MicroProfileClientProxyImpl.doRunInterceptorChain(MicroProfileClientProxyImpl.java:184)
	at org.apache.cxf.jaxrs.client.ClientProxyImpl.doChainedInvocation(ClientProxyImpl.java:924)
	at org.apache.cxf.jaxrs.client.ClientProxyImpl.invoke(ClientProxyImpl.java:347)
	at org.apache.cxf.microprofile.client.proxy.MicroProfileClientProxyImpl.invokeActual(MicroProfileClientProxyImpl.java:496)
	at org.apache.cxf.microprofile.client.proxy.MicroProfileClientProxyImpl$Invoker.call(MicroProfileClientProxyImpl.java:515)
	at org.apache.cxf.microprofile.client.cdi.CDIInterceptorWrapper$BasicCDIInterceptorWrapper.invoke(CDIInterceptorWrapper.java:43)
	at org.apache.cxf.microprofile.client.proxy.MicroProfileClientProxyImpl.invoke(MicroProfileClientProxyImpl.java:492)
	at jdk.proxy2/jdk.proxy2.$Proxy21.greet(Unknown Source)
	at org.example.MpRestClientConfigurationReproducerTest.testAccessConfigurationInFilter(MpRestClientConfigurationReproducerTest.java:24)
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
	at org.junit.platform.commons.util.ReflectionUtils.invokeMethod(ReflectionUtils.java:767)
	at org.junit.jupiter.engine.execution.MethodInvocation.proceed(MethodInvocation.java:60)
	at org.junit.jupiter.engine.execution.InvocationInterceptorChain$ValidatingInvocation.proceed(InvocationInterceptorChain.java:131)
	at org.junit.jupiter.engine.extension.TimeoutExtension.intercept(TimeoutExtension.java:156)
	at org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestableMethod(TimeoutExtension.java:147)
	at org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestMethod(TimeoutExtension.java:86)
	at org.junit.jupiter.engine.execution.InterceptingExecutableInvoker$ReflectiveInterceptorCall.lambda$ofVoidMethod$0(InterceptingExecutableInvoker.java:103)
	at org.junit.jupiter.engine.execution.InterceptingExecutableInvoker.lambda$invoke$0(InterceptingExecutableInvoker.java:93)
	at org.junit.jupiter.engine.execution.InvocationInterceptorChain$InterceptedInvocation.proceed(InvocationInterceptorChain.java:106)
	at org.junit.jupiter.engine.execution.InvocationInterceptorChain.proceed(InvocationInterceptorChain.java:64)
	at org.junit.jupiter.engine.execution.InvocationInterceptorChain.chainAndInvoke(InvocationInterceptorChain.java:45)
	at org.junit.jupiter.engine.execution.InvocationInterceptorChain.invoke(InvocationInterceptorChain.java:37)
	at org.junit.jupiter.engine.execution.InterceptingExecutableInvoker.invoke(InterceptingExecutableInvoker.java:92)
	at org.junit.jupiter.engine.execution.InterceptingExecutableInvoker.invoke(InterceptingExecutableInvoker.java:86)
	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeTestMethod$8(TestMethodTestDescriptor.java:217)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.invokeTestMethod(TestMethodTestDescriptor.java:213)
	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:138)
	at org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:68)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6(NodeTestTask.java:156)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:146)
	at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9(NodeTestTask.java:144)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:143)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:100)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:41)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6(NodeTestTask.java:160)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:146)
	at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9(NodeTestTask.java:144)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:143)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:100)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
	at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:41)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$6(NodeTestTask.java:160)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:146)
	at org.junit.platform.engine.support.hierarchical.Node.around(Node.java:137)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$9(NodeTestTask.java:144)
	at org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:143)
	at org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:100)
	at org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.submit(SameThreadHierarchicalTestExecutorService.java:35)
	at org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor.execute(HierarchicalTestExecutor.java:57)
	at org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine.execute(HierarchicalTestEngine.java:54)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:198)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:169)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:93)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.lambda$execute$0(EngineExecutionOrchestrator.java:58)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.withInterceptedStreams(EngineExecutionOrchestrator.java:141)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:57)
	at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:103)
	at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:85)
	at org.junit.platform.launcher.core.DelegatingLauncher.execute(DelegatingLauncher.java:47)
	at org.junit.platform.launcher.core.SessionPerRequestLauncher.execute(SessionPerRequestLauncher.java:63)
	at com.intellij.junit5.JUnit5IdeaTestRunner.startRunnerWithArgs(JUnit5IdeaTestRunner.java:57)
	at com.intellij.rt.junit.IdeaTestRunner$Repeater$1.execute(IdeaTestRunner.java:38)
	at com.intellij.rt.execution.junit.TestsRepeater.repeat(TestsRepeater.java:11)
	at com.intellij.rt.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:35)
	at com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:232)
	at com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:55)
</pre>
</details>
