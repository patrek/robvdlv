package sample.generator;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import info.magnolia.cms.core.Content;
import info.magnolia.cms.core.ItemType;
import info.magnolia.cms.link.LinkResolverImpl;
import info.magnolia.module.rssaggregator.generator.AbstractSyndFeedGenerator;
import info.magnolia.module.rssaggregator.util.ContentMapper;
import info.magnolia.module.rssaggregator.util.MagnoliaTemplate;

import javax.jcr.RepositoryException;
import java.util.List;

/**
 * Generates a {@link SyndFeed} based on newsitems defined on the news page.
 *
 * @author Rob van der Linden Vooren
 */
public class NewsSyndFeedGenerator extends AbstractSyndFeedGenerator {

    /*default*/ static final NewsMapper MAPPER = new NewsMapper();

    private MagnoliaTemplate magnoliaTemplate;

    /**
     * Construct a new NewsSyndFeedGenerator initialized with a new MagnoliaTemplate.
     */
    public NewsSyndFeedGenerator() {
        setMagnoliaTemplate(new MagnoliaTemplate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFeedInfo(SyndFeed feed) {
        feed.setTitle("Latest news");
        feed.setDescription("The very latest news");
        feed.setLink("http://myserver.com/rss?aggregatorName=newsRssGenerator");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SyndEntry> loadFeedEntries() {
        return magnoliaTemplate.xpathQueryForList("website", "/jcr:root/home/newsitems/*", ItemType.CONTENTNODE, MAPPER);
    }

    /**
     * Set the MagnoliaTemplate to use. This method exists for testing purposes.
     *
     * @param magnoliaTemplate the MagnoliaTemplate to use
     */
    /*default*/ void setMagnoliaTemplate(MagnoliaTemplate magnoliaTemplate) {
        if (magnoliaTemplate == null) {
            throw new IllegalArgumentException("MagnoliaTemplate cannot be null");
        }
        this.magnoliaTemplate = magnoliaTemplate;
    }

    /*default*/ static class NewsMapper implements ContentMapper<SyndEntry> {

        public SyndEntry map(Content content) throws RepositoryException {
            SyndEntry entry = new SyndEntryImpl();
            entry.setTitle(content.getNodeData("title").getString());
            if (content.getNodeData("date").getDate() != null) {
                entry.setPublishedDate(content.getNodeData("date").getDate().getTime());
            }
            SyndContent description = new SyndContentImpl();
            description.setType("text/html");
            String text = content.getNodeData("text").getString();
            // Replace internal links that use the special magnolia link format (looks like ${link: {uuid: ... etc)
            // (see info.magnolia.cms.link.UUIDLink for an example of the special format that this next line handles)
            String textWithResolvedLinks = new LinkResolverImpl().convertToExternalLinks(text);
            description.setValue(textWithResolvedLinks);
            entry.setDescription(description);
            return entry;
        }
    }
}
