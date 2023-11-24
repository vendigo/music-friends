package com.github.vendigo.musicfriends.handler;

import com.github.vendigo.musicfriends.model.ArtistNode;
import com.github.vendigo.musicfriends.service.ArtistService;
import com.github.vendigo.musicfriends.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;

import java.util.List;

import static org.thymeleaf.util.StringUtils.isEmpty;

@Component
@Slf4j
@AllArgsConstructor
public class ArtistSearchHandler {

    //language=RegExp
    private static final String QUERY_PATTERN = "[^<>]{0,40}";
    private final ArtistService artistService;

    public AnswerInlineQuery handleQuery(InlineQuery inlineQuery) {

        String query = inlineQuery.getQuery();
        List<InlineQueryResultArticle> artists = processQuery(query);

        return AnswerInlineQuery.builder()
                .inlineQueryId(inlineQuery.getId())
                .results(artists)
                .build();
    }

    private List<InlineQueryResultArticle> processQuery(String query) {
        log.debug("Processing query: {}", query);

        if (isInvalidQuery(query)) {
            log.debug("Skipping invalid query: {}", query);
            return List.of();
        }

        return artistService.findArtist(query).stream()
                .filter(artist -> !isEmpty(artist.getName()))
                .map(this::mapArtist)
                .toList();
    }

    private InlineQueryResultArticle mapArtist(ArtistNode artist) {
        String content = String.format("%s\n%s%d", artist.getName(), Utils.DEEZER_ARTIST_LINK, artist.getId());
        InputTextMessageContent inputMessageContent = new InputTextMessageContent(content);
        inputMessageContent.setParseMode(ParseMode.HTML);

        return InlineQueryResultArticle.builder()
                .id(artist.getId().toString())
                .title(artist.getName())
                .inputMessageContent(inputMessageContent)
                .thumbnailUrl(artist.getPicture())
                .build();

    }

    private boolean isInvalidQuery(String query) {
        return !query.matches(QUERY_PATTERN);
    }
}
