package com.propertyguru.hackernews.data.repository;

import com.propertyguru.hackernews.BaseUnitTest;
import com.propertyguru.hackernews.common.Constant;
import com.propertyguru.hackernews.data.constant.ItemType;
import com.propertyguru.hackernews.data.model.Comment;
import com.propertyguru.hackernews.data.model.Story;
import com.propertyguru.hackernews.di.component.TestAppComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import timber.log.Timber;

import static com.propertyguru.hackernews.common.Constant.VALID_COMMENT_ID_FIRST_LEVEL;
import static com.propertyguru.hackernews.common.Constant.VALID_COMMENT_ID_SECOND_LEVEL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class HackNewsRepositoryTest extends BaseUnitTest {

    public static final int VALID_STORY_ID = 16259373;

    @Inject
    MockWebServer mockWebServer;
    @Inject
    HackerNewsRepository SUT;

    @Override
    protected void injectDependencies(TestAppComponent testAppComponent) {
        testAppComponent.inject(this);
    }

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
    public void getStory_validStoryId_shouldReturnStory() throws Exception {
        // prepare response
        MockResponse mockResponse = new MockResponse()
                .setBody(readResponse("story.json"));
        mockWebServer.enqueue(mockResponse);

        // make request
        Story story = SUT.getStory(Constant.VALID_STORY_ID).blockingFirst();

        // verify request
        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals(String.format("/item/%d.json", Constant.VALID_STORY_ID), recordedRequest.getPath());
        assertEquals("GET", recordedRequest.getMethod());
        // verify response
        assertNotNull(story);
        assertEquals(Constant.VALID_STORY_ID, story.getId());
        assertEquals(ItemType.STORY, story.getType());
    }

    @Test
    public void getStory_invalidStoryId_shouldReturnException() throws Exception {
        // prepare response
        MockResponse mockResponse = new MockResponse()
                .setBody(readResponse("story_null.json"));
        mockWebServer.enqueue(mockResponse);

        try {
            // make request
            SUT.getStory(Constant.INVALID_STORY_ID).blockingFirst();
            fail("Should throw NoSuchElementException (emits no items)");
        } catch (Exception e) {
            // verify request
            RecordedRequest recordedRequest = mockWebServer.takeRequest();
            assertEquals(String.format("/item/%d.json", Constant.INVALID_STORY_ID), recordedRequest.getPath());
            assertEquals("GET", recordedRequest.getMethod());
            assertTrue(e instanceof NoSuchElementException);
        }
    }

    @Test
    public void getStory_validCommentId_shouldReturnException() throws Exception {
        // prepare response
        MockResponse mockResponse = new MockResponse()
                .setBody(readResponse("comment_first_level.json"));
        mockWebServer.enqueue(mockResponse);

        try {
            // make request
            SUT.getStory(VALID_COMMENT_ID_FIRST_LEVEL).blockingFirst();
            fail("Should throw NoSuchElementException (emits no items)");
        } catch (Exception e) {
            // verify request
            RecordedRequest recordedRequest = mockWebServer.takeRequest();
            assertEquals(String.format("/item/%d.json", VALID_COMMENT_ID_FIRST_LEVEL), recordedRequest.getPath());
            assertEquals("GET", recordedRequest.getMethod());
            assertTrue(e instanceof NoSuchElementException);
        }
    }

    @Test
    public void getComments_validStoryId_shouldReturnSuccess() throws Exception {
        // prepare response
        MockResponse storyMockResponse = new MockResponse()
                .setBody(readResponse("story.json"));
        mockWebServer.enqueue(storyMockResponse);
        MockResponse commentFirstLevelMockResponse = new MockResponse()
                .setBody(readResponse("comment_first_level.json"));
        mockWebServer.enqueue(commentFirstLevelMockResponse);
        MockResponse commentSecondLevelMockResponse = new MockResponse()
                .setBody(readResponse("comment_second_level.json"));
        mockWebServer.enqueue(commentSecondLevelMockResponse);

        // make request
        Iterable<Comment> commentIterable = SUT.getComments(VALID_STORY_ID).blockingNext();

        for (Comment comment : commentIterable) {
            assertNotNull(comment);
            assertEquals(VALID_COMMENT_ID_FIRST_LEVEL, comment.getId());

            for (Comment childComment : comment.getChildList()) {
                assertNotNull(childComment);
                assertEquals(VALID_COMMENT_ID_SECOND_LEVEL, childComment.getId());
            }
        }

        // verify request
        RecordedRequest storyRecordedRequest = mockWebServer.takeRequest();
        assertEquals(String.format("/item/%d.json", VALID_STORY_ID), storyRecordedRequest.getPath());
        assertEquals("GET", storyRecordedRequest.getMethod());

        RecordedRequest commentRecordedRequestFirstLevel = mockWebServer.takeRequest();
        assertEquals(String.format("/item/%d.json", VALID_COMMENT_ID_FIRST_LEVEL), commentRecordedRequestFirstLevel.getPath());
        assertEquals("GET", commentRecordedRequestFirstLevel.getMethod());

        RecordedRequest commentRecordedRequestSecondLevel = mockWebServer.takeRequest();
        assertEquals(String.format("/item/%d.json", VALID_COMMENT_ID_SECOND_LEVEL), commentRecordedRequestSecondLevel.getPath());
        assertEquals("GET", commentRecordedRequestSecondLevel.getMethod());
    }
}
