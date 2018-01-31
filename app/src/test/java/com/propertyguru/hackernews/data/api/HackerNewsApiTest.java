package com.propertyguru.hackernews.data.api;

import com.propertyguru.hackernews.BaseUnitTest;
import com.propertyguru.hackernews.common.Constant;
import com.propertyguru.hackernews.data.model.Item;
import com.propertyguru.hackernews.data.remote.api.HackerNewsApi;
import com.propertyguru.hackernews.di.component.TestAppComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;

import static com.propertyguru.hackernews.common.Constant.INVALID_ITEM_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class HackerNewsApiTest extends BaseUnitTest {
    @Override
    protected void injectDependencies(TestAppComponent testAppComponent) {
        testAppComponent.inject(this);
    }

    @Inject
    MockWebServer mockWebServer;
    @Inject
    HackerNewsApi SUT;

    @Before
    public void setUp() throws Exception {
        // do nothing
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void getTopStories_valid_shouldReturnIdList() throws Exception {
        // prepare response
        MockResponse mockResponse = new MockResponse()
                .setBody(readResponse("topstories.json"));
        mockWebServer.enqueue(mockResponse);

        // make request
        List<Long> longList = SUT.getTopStories().blockingFirst();

        // verify request
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("/topstories.json", recordedRequest.getPath());
        assertEquals("GET", recordedRequest.getMethod());
        // verify response
        assertNotNull(longList);
    }

    @Test
    public void getItem_validItemId_shouldReturnItem() throws Exception {
        // prepare response
        MockResponse mockResponse = new MockResponse()
                .setBody(readResponse("item.json"));
        mockWebServer.enqueue(mockResponse);

        // make request
        Item item = SUT.getItem(Constant.VALID_ITEM_ID).blockingFirst();

        // verify request
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals(String.format("/item/%d.json", Constant.VALID_ITEM_ID), recordedRequest.getPath());
        assertEquals("GET", recordedRequest.getMethod());
        // verify response
        assertNotNull(item);
        assertEquals(Constant.VALID_ITEM_ID, item.id);
    }

    @Test
    public void getItem_invalidItemId_shouldReturnException() throws Exception {
        // prepare response
        MockResponse mockResponse = new MockResponse()
                .setBody(readResponse("item_null.json"));
        mockWebServer.enqueue(mockResponse);

        try {
            // make request
            SUT.getItem(INVALID_ITEM_ID).blockingFirst();
            fail("Should throw NoSuchElementException (emits no items)");
        } catch (Exception e) {
            // verify request
            RecordedRequest recordedRequest = mockWebServer.takeRequest();
            assertEquals(String.format("/item/%d.json", INVALID_ITEM_ID), recordedRequest.getPath());
            assertEquals("GET", recordedRequest.getMethod());
            assertTrue(e instanceof NoSuchElementException);
        }
    }
}
