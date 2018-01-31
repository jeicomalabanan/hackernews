package com.propertyguru.hackernews;

import com.propertyguru.hackernews.di.component.DaggerTestAppComponent;
import com.propertyguru.hackernews.di.component.TestAppComponent;

import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import okio.BufferedSource;
import okio.Okio;

public abstract class BaseUnitTest {
    protected abstract void injectDependencies(TestAppComponent testAppComponent);

    @Before
    public void baseSetup() throws Exception {
        injectDependencies(DaggerTestAppComponent.builder().build());
    }

    @After
    public void baseTearDown() throws Exception {}

    protected String readResponse(String fileName) throws IOException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-response/" + fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        return source.readString(StandardCharsets.UTF_8);
    }
}
