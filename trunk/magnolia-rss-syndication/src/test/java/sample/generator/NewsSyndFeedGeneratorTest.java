package sample.generator;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import info.magnolia.cms.core.Content;
import static info.magnolia.cms.core.ItemType.CONTENTNODE;
import info.magnolia.cms.core.NodeData;
import info.magnolia.module.rssaggregator.util.MagnoliaTemplate;
import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;
import static sample.generator.NewsSyndFeedGenerator.MAPPER;

import javax.jcr.RepositoryException;
import java.util.Calendar;
import static java.util.Collections.emptyList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Tests {@link NewsSyndFeedGenerator}.
 *
 * @author Rob van der Linden Vooren
 */
public class NewsSyndFeedGeneratorTest {

    private NewsSyndFeedGenerator generator;
    private NewsSyndFeedGenerator.NewsMapper mapper;

    private MagnoliaTemplate mockMagnoliaTemplate;

    @Before
    public void before() {
        generator = new NewsSyndFeedGenerator();
        mapper = new NewsSyndFeedGenerator.NewsMapper();
        mockMagnoliaTemplate = createMock("mockMagnoliaTemplate", MagnoliaTemplate.class);
    }

    @Test
    public void setsFeedInformation() {
        SyndFeed syndFeed = new SyndFeedImpl();

        generator.setFeedInfo(syndFeed);

        assertEquals("Latest news", syndFeed.getTitle());
        assertEquals("The very latest news", syndFeed.getDescription());
        assertEquals("http://myserver.com/rss?aggregatorName=newsRssGenerator", syndFeed.getLink());
    }

    @Test
    public void loadsFeedEntriesFromRepository() {
        generator.setMagnoliaTemplate(mockMagnoliaTemplate);
        List<SyndEntry> expectedEntries = emptyList();

        expect(mockMagnoliaTemplate.xpathQueryForList("website", "/jcr:root/home/newsitems/*", CONTENTNODE, MAPPER))
                .andReturn(expectedEntries);
        replay(mockMagnoliaTemplate);

        List<SyndEntry> entries = generator.loadFeedEntries();

        assertSame(expectedEntries, entries);
    }

    @Test
    public void mapsNewsItemFromRepositoryToSyndEntry() throws RepositoryException {
        Content mockContent = createMock("mockContent", Content.class);
        NodeData mockTitleData = createMock("mockTitleData", NodeData.class);
        NodeData mockDateData = createMock("mockDateData", NodeData.class);
        NodeData mockTextData = createMock("mockTextData", NodeData.class);
        Calendar date = new GregorianCalendar();

        expect(mockContent.getNodeData("title")).andReturn(mockTitleData);
        expect(mockTitleData.getString()).andReturn("the title");
        expect(mockContent.getNodeData("date")).andReturn(mockDateData).times(2);
        expect(mockDateData.getDate()).andReturn(date).times(2);
        expect(mockContent.getNodeData("text")).andReturn(mockTextData);
        expect(mockTextData.getString()).andReturn("the text");
        replay(mockContent, mockTitleData, mockDateData, mockTextData);

        SyndEntry entry = mapper.map(mockContent);

        verify(mockContent, mockTitleData, mockDateData, mockTextData);
        assertEquals("the title", entry.getTitle());
        assertEquals(date.getTimeInMillis(), entry.getPublishedDate().getTime());
        assertEquals("the text", entry.getDescription().getValue());
    }
}
