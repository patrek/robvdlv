package sample.generator;

import com.sun.syndication.feed.synd.SyndEntry;
import info.magnolia.cms.core.Content;
import info.magnolia.cms.core.NodeData;
import info.magnolia.module.rssaggregator.util.MagnoliaTemplate;
import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import javax.jcr.RepositoryException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Tests {@link sample.generator.NewsSyndFeedGenerator}.
 *
 * @author Rob van der Linden Vooren
 */
public class NewsMapperTest {

    private NewsMapper mapper;

    @Before
    public void before() {
        mapper = new NewsMapper();
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