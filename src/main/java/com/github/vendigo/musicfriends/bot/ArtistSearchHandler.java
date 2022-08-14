package com.github.vendigo.musicfriends.bot;

import com.github.vendigo.musicfriends.model.ArtistNode;
import com.github.vendigo.musicfriends.service.ArtistService;
import com.github.vendigo.musicfriends.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class ArtistSearchHandler {

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
        log.info("Processing query: {}", query);
        return artistService.findArtist(query).stream()
                .map(this::mapArtist)
                .toList();
    }

    private InlineQueryResultArticle mapArtist(ArtistNode artist) {
        String content = String.format("%s\n%s%d", artist.getName(), Utils.DEEZER_ARTIST_LINK, artist.getId());
        InputTextMessageContent inputMessageContent = new InputTextMessageContent(content);
        inputMessageContent.setParseMode("html");

        return InlineQueryResultArticle.builder()
                .id(artist.getId().toString())
                .title(artist.getName())
                .inputMessageContent(inputMessageContent)
                .thumbUrl(artist.getPicture())
                .build();

    }
}
