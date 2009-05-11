package sample.generator;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import static info.magnolia.cms.core.ItemType.CONTENTNODE;
import info.magnolia.module.rssaggregator.util.MagnoliaTemplate;
import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;
import static sample.generator.NewsSyndFeedGenerator.MAPPER;

import static java.util.Collections.emptyList;
import java.util.List;

/**
 * Tests {@link NewsSyndFeedGenerator}.
 *
 * @author Rob van der Linden Vooren
 */
public class NewsSyndFeedGeneratorTest {

    private NewsSyndFeedGenerator generator;

    private MagnoliaTemplate mockMagnoliaTemplate;

    @Before
    public void before() {
        generator = new NewsSyndFeedGenerator();
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
}
