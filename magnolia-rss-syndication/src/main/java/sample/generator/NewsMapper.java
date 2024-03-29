package sample.generator;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import info.magnolia.cms.core.Content;
import info.magnolia.cms.link.LinkResolverImpl;
import info.magnolia.module.rssaggregator.util.ContentMapper;

import javax.jcr.RepositoryException;

/**
 * Maps a {@link Content} node from the Repository to a {@link SyndEntry} instance.
 *
 * @author Rob van der Linden Vooren
 */
class NewsMapper implements ContentMapper<SyndEntry> {

    /**
     * {@inheritDoc}
     */
    @Override
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
